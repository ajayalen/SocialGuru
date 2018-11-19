package com.socialnetwork.social.linkedin;

import com.socialnetwork.social.linkedin.oauth.OAuth;
import com.socialnetwork.social.linkedin.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;

public final class LinkedinHelper
{

	private static LinkedinHelper instance = null;
	public static final String API_KEY = "8159fpjxofi6fw";
	public static final String API_SEC = "ekyd90YA5iZJAmqc";

	private LinkedinHelper()
	{

	}

	public static LinkedinHelper getInstance()
	{
		if (instance == null)
		{
			instance = new LinkedinHelper();
		}

		return instance;
	}

	public Linkedin getLinkedin()
	{
		try
		{
			Linkedin t = new Linkedin(API_KEY, API_SEC, new REST());
			return t;
		}
		catch (ParserConfigurationException e)
		{
			return null;
		}
	}

	public Linkedin getLinkedinAuthed(String token)
	{
		Linkedin f = getLinkedin();
		RequestContext requestContext = RequestContext.getRequestContext();
		OAuth auth = new OAuth();
		auth.setToken(new OAuthToken(token));
		requestContext.setOAuth(auth);
		return f;
	}

}
