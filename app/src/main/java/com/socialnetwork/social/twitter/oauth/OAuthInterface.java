package com.socialnetwork.social.twitter.oauth;


import com.socialnetwork.social.twitter.REST;
import com.socialnetwork.social.twitter.RequestContext;
import com.socialnetwork.social.twitter.Response;
import com.socialnetwork.social.twitter.Transport;
import com.socialnetwork.social.twitter.Twitter;
import com.socialnetwork.social.twitter.TwitterException;
import com.socialnetwork.social.twitter.user.User;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.utils.UrlUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Authentication interface for the new Tumblr OAuth 1a support:
 */
public class OAuthInterface {

    public static final String METHOD_TEST_LOGIN = "twitter.test.login";

    public static final String KEY_OAUTH_CALLBACK_CONFIRMED = "oauth_callback_confirmed";
    public static final String KEY_OAUTH_TOKEN = "oauth_token";
    public static final String KEY_OAUTH_TOKEN_SECRET = "oauth_token_secret";
    public static final String KEY_OAUTH_VERIFIER = "oauth_verifier";

    public static final String PATH_OAUTH_REQUEST_TOKEN = "/oauth/request_token";
    public static final String PATH_OAUTH_ACCESS_TOKEN = "/oauth/access_token";
    public static final String URL_REQUEST_TOKEN = "https://" + Twitter.DEFAULT_API_HOST + PATH_OAUTH_REQUEST_TOKEN;
    public static final String URL_ACCESS_TOKEN = "https://" + Twitter.DEFAULT_API_HOST + PATH_OAUTH_ACCESS_TOKEN;

    public static final String PATH_REST = "";
    public static final String URL_REST = "https://" + Twitter.DEFAULT_API_HOST;
    public static final String PARAM_OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String PARAM_OAUTH_TOKEN = "oauth_token";


    private String apiKey;
    private String sharedSecret;
    private REST oauthTransport;

    /**
     * Construct the AuthInterface.
     *
     * @param apiKey    The API key
     * @param transport The Transport interface
     */
    public OAuthInterface(
            String apiKey,
            String sharedSecret,
            Transport transport
    ) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.oauthTransport = (REST) transport;
    }

    public User testLogin()
            throws TwitterException, IOException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_TEST_LOGIN));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        OAuthUtils.addOAuthToken(parameters);
        Response response = this.oauthTransport.postJSON(this.sharedSecret, parameters);
        if (response.isError()) {
            throw new TwitterException(response.getErrorCode(), response.getErrorMessage());
        }

        JSONObject jObj = response.getData();
        JSONObject userObj = jObj.getJSONObject("user");
        String id = userObj.getString("id");
        String name = userObj.getJSONObject("username").getString("_content");
        User user = new User();
        user.setId(id);
//        user.setUsername(name);
        return user;
    }

    /**
     * Get a request token.
     *
     * @return the frob
     * @throws IOException
     * @throws TwitterException
     */
    public OAuthToken getRequestToken(String callbackUrl) throws IOException, TwitterException {
        if (callbackUrl == null)
            callbackUrl = "oob";
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("oauth_callback", callbackUrl));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        OAuthUtils.addBasicOAuthParams(parameters);
        String signature = OAuthUtils.getSignature(
                OAuthUtils.REQUEST_METHOD_GET,
                URL_REQUEST_TOKEN,
                parameters,
                sharedSecret, null);
        // This method call must be signed.
        parameters.add(new Parameter("oauth_signature", signature));

//        logger.info("Getting Request Token with parameters: {}", parameters);
        Map<String, String> response = this.oauthTransport.getMapData(
                true, PATH_OAUTH_REQUEST_TOKEN, parameters);
        if (response.isEmpty()) {
            throw new TwitterException("Empty Response", "Empty Response");
        }

        if (response.containsKey(KEY_OAUTH_CALLBACK_CONFIRMED) == false
                || Boolean.valueOf(response.get(KEY_OAUTH_CALLBACK_CONFIRMED)) == false) {
            throw new TwitterException("Error", "Invalid response: " + response);
        }
        String token = response.get(KEY_OAUTH_TOKEN);
        String token_secret = response.get(KEY_OAUTH_TOKEN_SECRET);
//        logger.info("Response: {}", response);
        OAuth oauth = new OAuth();
        oauth.setToken(new OAuthToken(token, token_secret));
        //RequestContext.getRequestContext().setOAuth(oauth);
        return oauth.getToken();
    }

    /**
     * @param tokenSecret
     * @param oauthVerifier
     * @throws IOException
     * @throws TwitterException
     */
    public OAuth getAccessToken(String token, String tokenSecret, String oauthVerifier)
            throws IOException, TwitterException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        parameters.add(new OAuthTokenParameter(token));
        parameters.add(new Parameter(KEY_OAUTH_VERIFIER, oauthVerifier));
        OAuthUtils.addBasicOAuthParams(parameters);
        //OAuthUtils.signPost(sharedSecret, URL_ACCESS_TOKEN, parameters);

        String signature = OAuthUtils.getSignature(
                OAuthUtils.REQUEST_METHOD_POST,
                URL_ACCESS_TOKEN,
                parameters,
                sharedSecret, tokenSecret);
        // This method call must be signed.
        parameters.add(new Parameter("oauth_signature", signature));

        //OAuthUtils.addOAuthParams(this.sharedSecret, URL_ACCESS_TOKEN, parameters);

        Map<String, String> response = this.oauthTransport.getMapData(false, PATH_OAUTH_ACCESS_TOKEN, parameters);
        if (response.isEmpty()) {
            throw new TwitterException("Empty Response", "Empty Response");
        }
//        logger.info("Response: {}", response);
        OAuth result = new OAuth();
        User user = new User();
        user.setId(response.get("user_nsid"));
//        user.setUsername(response.get("username"));
//        user.setRealName(response.get("fullname"));
        result.setUser(user);
        result.setToken(new OAuthToken(
                response.get("oauth_token"), response.get("oauth_token_secret")));
        RequestContext.getRequestContext().setOAuth(result);
        return result;
    }


    /**
     * Build the authentication URL using the given permission and frob.
     * <p/>
     * The hostname used here is www.flickr.com. It differs from the api-default
     * api.flickr.com.
     * <p/>
     * //     * @param permission The Permission
     *
     * @return The URL
     * @throws MalformedURLException
     */
    public URL buildAuthenticationUrl(/*Permission permission, */OAuthToken oauthToken) throws MalformedURLException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(PARAM_OAUTH_TOKEN, oauthToken.getOauthToken()));
//        if (permission != null) {
//            parameters.add(new Parameter("perms", permission.toString()));
//        }

        int port = oauthTransport.getPort();
        String path = "/oauth/authorize";

        return UrlUtilities.buildUrl(Twitter.DEFAULT_API_HOST, port, path, parameters);
    }


}
