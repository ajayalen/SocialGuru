package com.socialnetwork.social.instagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socialnetwork.social.instagram.user.User;
import com.socialnetwork.social.utils.Parameter;

public class UserInterface
{

	public static final String PATH_GET_INFO = "/v1/users/self";

	public static final String URL_GET_INFO = "https://" + Instagram.DEFAULT_API_HOST + PATH_GET_INFO;

	private String apiKey;
	private Transport transportAPI;

	public UserInterface(String apiKey, Transport transportAPI)
	{
		this.apiKey = apiKey;
		this.transportAPI = transportAPI;
	}

	/**
	 * Get info about the specified user.
	 *
	 * This method does not require authentication.
	 *
	 * @param userId The user ID
	 * @return The User object
	 * @throws IOException
	 * @throws InstagramException
	 * @throws JSONException
	 */
	public User getInfo(String userId) throws IOException, InstagramException, JSONException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("user_id", userId));
		parameters.add(new Parameter("api_key", apiKey));
		Response response = transportAPI.get(transportAPI.getPath(), parameters);
		if (response.isError())
		{
			throw new InstagramException(response.getErrorCode(), response.getErrorMessage());
		}
		User user = new User();

		return user;
	}

	/**
	 * Get info about the specified user.
	 *
	 * This method does not require authentication.
	 *
	 * @return The User object
	 * @throws IOException
	 * @throws InstagramException
	 * @throws JSONException
	 */
	public User getInfo() throws IOException, InstagramException, JSONException
	{
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("access_token", apiKey));

		String response = ((REST) transportAPI).getStringData(true, PATH_GET_INFO, parameters);

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		User user = gson.fromJson(response, User.class);

		if (response == null)
		{
			throw new InstagramException("Empty Response", "Empty Response");
		}

		return user;
	}
}
