package com.socialnetwork.social.instagram;

import com.socialnetwork.social.instagram.oauth.OAuth;
import com.socialnetwork.social.instagram.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;

public final class InstagramHelper
{

	private static InstagramHelper instance = null;
	public static final String API_KEY = "0aef7d5a18c24190afa8ec839ab6996c";
	public static final String API_SEC = "234e0f9fa39b4eddb8e184c0dea4b23e";

	private InstagramHelper()
	{

	}

	public static InstagramHelper getInstance()
	{
		if (instance == null)
		{
			instance = new InstagramHelper();
		}

		return instance;
	}

	public Instagram getInstagram()
	{
		try
		{
			Instagram t = new Instagram(API_KEY, API_SEC, new REST());
			return t;
		}
		catch (ParserConfigurationException e)
		{
			return null;
		}
	}

	public Instagram getInstagramAuthed(String token)
	{
		Instagram f = getInstagram();
		RequestContext requestContext = RequestContext.getRequestContext();
		OAuth auth = new OAuth();
		auth.setToken(new OAuthToken(token));
		requestContext.setOAuth(auth);
		return f;
	}

}
