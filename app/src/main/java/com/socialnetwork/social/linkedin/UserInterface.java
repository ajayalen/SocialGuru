package com.socialnetwork.social.linkedin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialnetwork.social.linkedin.user.User;
import com.socialnetwork.social.utils.Parameter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    public static final String PATH_GET_INFO = "/v1/people/~:(id,first_name,last_name,picture-url)";

    public static final String URL_GET_INFO = "https://" + Linkedin.DEFAULT_API_HOST + PATH_GET_INFO;

    private String apiKey;
    private Transport transportAPI;

    public UserInterface(String apiKey, Transport transportAPI) {
        this.apiKey = apiKey;
        this.transportAPI = transportAPI;
    }

    /**
     * Get info about the specified user.
     * <p>
     * This method does not require authentication.
     *
     * @param userId The user ID
     * @return The User object
     * @throws IOException
     * @throws LinkedinException
     * @throws JSONException
     */
    public User getInfo(String userId) throws IOException, LinkedinException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("user_id", userId));
        parameters.add(new Parameter("api_key", apiKey));
        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new LinkedinException(response.getErrorCode(), response.getErrorMessage());
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
     * @throws LinkedinException
     * @throws JSONException
     */
    public User getInfo() throws IOException, LinkedinException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("oauth2_access_token", apiKey));
        parameters.add(new Parameter("format", "json"));

        String response = ((REST) transportAPI).getStringData(true, PATH_GET_INFO, parameters);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        User user = gson.fromJson(response, User.class);

        if (response == null) {
            throw new LinkedinException("Empty Response", "Empty Response");
        }

        return user;
    }
}
