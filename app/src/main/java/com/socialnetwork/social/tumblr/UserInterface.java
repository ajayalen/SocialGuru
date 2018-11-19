package com.socialnetwork.social.tumblr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialnetwork.social.tumblr.oauth.OAuthInterface;
import com.socialnetwork.social.tumblr.oauth.OAuthUtils;
import com.socialnetwork.social.utils.Parameter;

public class UserInterface
{

	public static final String PATH_GET_INFO = "/v2/user/info";


	public static final String URL_GET_INFO = "https://" + Tumblr.DEFAULT_API_HOST + PATH_GET_INFO;
	private final String sharedSecret;

	private String apiKey;
	private Transport transportAPI;

	public UserInterface(String apiKey, String sharedSecret, Transport transportAPI)
	{
		this.apiKey = apiKey;
		this.sharedSecret = sharedSecret;
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
	 * @throws TumblrException
	 * @throws JSONException
	 */
	public User getInfo(String userId) throws IOException, TumblrException, JSONException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("user_id", userId));
		parameters.add(new Parameter("api_key", apiKey));
		Response response = transportAPI.get(transportAPI.getPath(), parameters);
		if (response.isError())
		{
			throw new TumblrException(response.getErrorCode(), response.getErrorMessage());
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
	 * @throws TumblrException
	 * @throws JSONException
	 */
	public User getInfo() throws IOException, TumblrException, JSONException
	{
		List<Parameter> headerParameters = new ArrayList<Parameter>();
		List<Parameter> parameters = new ArrayList<Parameter>();
		headerParameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, TumblrHelper.API_KEY));
		headerParameters.add(new Parameter("oauth_token", apiKey));
		OAuthUtils.addBasicOAuthParams(headerParameters);
		String signature = OAuthUtils.getSignature(OAuthUtils.REQUEST_METHOD_GET, URL_GET_INFO, headerParameters, TumblrHelper.API_SEC,
				sharedSecret);
		// This method call must be signed.
		headerParameters.add(new Parameter("oauth_signature", signature));

		String response = ((REST) transportAPI).getStringData(true, PATH_GET_INFO,null, headerParameters);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		User user = gson.fromJson(response, User.class);

		if (response == null)
		{
			throw new TumblrException("Empty Response", "Empty Response");
		}

		return user;
	}
}
