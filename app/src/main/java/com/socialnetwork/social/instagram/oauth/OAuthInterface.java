package com.socialnetwork.social.instagram.oauth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.instagram.Instagram;
import com.socialnetwork.social.instagram.InstagramHelper;
import com.socialnetwork.social.instagram.REST;
import com.socialnetwork.social.instagram.RequestContext;
import com.socialnetwork.social.instagram.Response;
import com.socialnetwork.social.instagram.Transport;
import com.socialnetwork.social.instagram.InstagramException;
import com.socialnetwork.social.instagram.user.User;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.utils.UrlUtilities;

/**
 * Authentication interface for the new Instagram OAuth 2 support:
 */
public class OAuthInterface
{

	public static final String METHOD_TEST_LOGIN = "twitter.test.login";

	public static final String KEY_OAUTH_CALLBACK_CONFIRMED = "oauth_callback_confirmed";
	public static final String KEY_OAUTH_TOKEN = "oauth_token";
	public static final String KEY_OAUTH_TOKEN_SECRET = "oauth_token_secret";
	public static final String KEY_OAUTH_VERIFIER = "oauth_verifier";

	public static final String PATH_OAUTH_ACCESS_TOKEN = "/oauth/access_token";
	public static final String URL_ACCESS_TOKEN = "https://" + Instagram.DEFAULT_API_HOST + PATH_OAUTH_ACCESS_TOKEN;

	public static final String PATH_REST = "";
	public static final String URL_REST = "https://" + Instagram.DEFAULT_API_HOST;
	public static final String PARAM_OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String PARAM_OAUTH_TOKEN = "oauth_token";
	public static final String PARAM_CLIENT_ID = "client_id";
	public static final String PARAM_CLIENT_SECRET = "client_secret";
	public static final String PARAM_GRANT_TYPE = "grant_type";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_REDIRECT_URI = "redirect_uri";
	public static final String PARAM_RESPONSE_TYPE = "response_type";
	public static final String PARAM_DISPLAY = "display";
	public static final String PARAM_SCOPE = "scope";

	private String apiKey;
	private String sharedSecret;
	private REST oauthTransport;

	/**
	 * Construct the AuthInterface.
	 *
	 * @param apiKey    The API key
	 * @param transport The Transport interface
	 */
	public OAuthInterface(String apiKey, String sharedSecret, Transport transport)
	{
		this.apiKey = apiKey;
		this.sharedSecret = sharedSecret;
		this.oauthTransport = (REST) transport;
	}

	public User testLogin() throws InstagramException, IOException, JSONException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("method", METHOD_TEST_LOGIN));
		parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
		OAuthUtils.addOAuthToken(parameters);
		Response response = this.oauthTransport.postJSON(this.sharedSecret, parameters);
		if (response.isError())
		{
			throw new InstagramException(response.getErrorCode(), response.getErrorMessage());
		}

		JSONObject jObj = response.getData();
		JSONObject userObj = jObj.getJSONObject("user");
		String id = userObj.getString("id");
		String name = userObj.getJSONObject("username").getString("_content");
		User user = new User();
		return user;
	}

	/**
	 * @throws IOException
	 * @throws InstagramException
	 */
	public OAuth getAccessToken(String code) throws IOException,
			InstagramException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(OAuthInterface.PARAM_CLIENT_ID, apiKey));
		parameters.add(new Parameter(OAuthInterface.PARAM_CLIENT_SECRET, sharedSecret));
		parameters.add(new Parameter(OAuthInterface.PARAM_CODE, code));
		parameters.add(new Parameter(OAuthInterface.PARAM_GRANT_TYPE, "authorization_code"));
		parameters.add(new Parameter(OAuthInterface.PARAM_REDIRECT_URI,
				SocialConstants.InstagramConstant.CALLBACK_SCHEME));
		OAuthUtils.addBasicOAuthParams(parameters);
		//OAuthUtils.signPost(sharedSecret, URL_ACCESS_TOKEN, parameters);

		String signature = OAuthUtils.getSignature(OAuthUtils.REQUEST_METHOD_POST, URL_ACCESS_TOKEN, parameters,
				sharedSecret, sharedSecret);
		// This method call must be signed.
		parameters.add(new Parameter("oauth_signature", signature));

		//OAuthUtils.addOAuthParams(this.sharedSecret, URL_ACCESS_TOKEN, parameters);

		Map<String, String> response = this.oauthTransport.getMapData(false, PATH_OAUTH_ACCESS_TOKEN, parameters);
		if (response.isEmpty())
		{
			throw new InstagramException("Empty Response", "Empty Response");
		}
		//        logger.info("Response: {}", response);
		OAuth result = new OAuth();
		result.setToken(new OAuthToken(response.get("access_token")));
		RequestContext.getRequestContext().setOAuth(result);
		return result;
	}

	/**
	 * @return The URL
	 * @throws MalformedURLException
	 */
	public URL buildAuthenticationUrl(String callbackUrl) throws MalformedURLException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(PARAM_CLIENT_ID, InstagramHelper.API_KEY));
		parameters.add(new Parameter(PARAM_REDIRECT_URI, callbackUrl));
		parameters.add(new Parameter(PARAM_RESPONSE_TYPE, "code"));
		parameters.add(new Parameter(PARAM_DISPLAY, "touch"));
		parameters.add(new Parameter(PARAM_SCOPE, "likes+comments+relationships+follower_list"));

		int port = oauthTransport.getPort();
		String path = "/oauth/authorize";

		return UrlUtilities.buildUrlInstagram(Instagram.DEFAULT_API_HOST, port, path, parameters);
	}

}
