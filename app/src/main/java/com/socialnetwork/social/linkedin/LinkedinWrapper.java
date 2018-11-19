package com.socialnetwork.social.linkedin;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.linkedin.oauth.OAuth;
import com.socialnetwork.social.linkedin.oauth.OAuthToken;
import com.socialnetwork.utils.Lg;

public class LinkedinWrapper
{

	private static final String TAG = LinkedinWrapper.class.getClass().getSimpleName();
	private static SharedPreferences settings;
	private static LinkedinWrapper instance;

	private LinkedinWrapper(SharedPreferences con) {
		settings=con;
	}


	public static LinkedinWrapper getInstance(SharedPreferences con)
	{

		if (instance == null)
		{
			instance = new LinkedinWrapper(con);
		}
		return instance;
	}

	public OAuth getLinkedinOAuthToken()
	{
		//Restore preferences
		String oauthTokenString = settings.getString(SocialConstants.LinkedinConstant.KEY_ACCESS_TOKEN, null);
		if (oauthTokenString == null)
		{
			Lg.w(TAG, "No oauth token retrieved"); //$NON-NLS-1$
			return null;
		}
		OAuth oauth = new OAuth();
		OAuthToken oauthToken = new OAuthToken();
		oauth.setToken(oauthToken);
		oauthToken.setOauthToken(oauthTokenString);
		return oauth;
	}
	public void clearOauth() {
		settings.edit().clear().apply();
	}

}
