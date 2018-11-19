package com.socialnetwork.social.reddit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.reddit.oauth.OAuth;
import com.socialnetwork.social.reddit.oauth.OAuthInterface;
import com.socialnetwork.social.reddit.oauth.OAuthToken;
import com.socialnetwork.social.reddit.oauth.OAuthUtils;
import com.socialnetwork.social.reddit.user.User;
import com.socialnetwork.social.utils.Parameter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInterface {

    public static final String PATH_GET_INFO = "/api/v1/me";

    public static final String URL_GET_INFO = "https://" + Reddit.DEFAULT_OAUTH_HOST + PATH_GET_INFO;

    private String apiKey;
    private String refreshToken;
    private String expiresIn;
    private Transport transportAPI;

    public UserInterface(String apiKey, String refreshToken, String expiresIn, Transport transportAPI) {
        this.apiKey = apiKey;
        this.transportAPI = transportAPI;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    /**
     * Get info about the specified user.
     * <p>
     * This method does not require authentication.
     *
     * @param userId The user ID
     * @return The User object
     * @throws IOException
     * @throws RedditException
     * @throws JSONException
     */
    public User getInfo(String userId) throws IOException, RedditException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("user_id", userId));
        parameters.add(new Parameter("api_key", apiKey));
        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new RedditException(response.getErrorCode(), response.getErrorMessage());
        }
        User user = new User();

        return user;
    }

    /**
     * Get info about the specified user.
     * <p>
     * This method does not require authentication.
     *
     * @return The User object
     * @throws IOException
     * @throws RedditException
     * @throws JSONException
     */
    public User getInfo() throws IOException, RedditException, JSONException {


        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("access_token", apiKey));
        parameters.add(new Parameter("format", "json"));

        String response = ((REST) transportAPI).getStringData(true, PATH_GET_INFO, null, apiKey);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        User user = gson.fromJson(response, User.class);

        if (response == null) {
            throw new RedditException("Empty Response", "Empty Response");
        }

        return user;
    }



}
