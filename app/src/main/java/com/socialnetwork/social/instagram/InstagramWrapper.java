package com.socialnetwork.social.instagram;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.people.User;
import com.socialnetwork.social.instagram.oauth.OAuth;
import com.socialnetwork.social.instagram.oauth.OAuthToken;
import com.socialnetwork.utils.Lg;

import android.content.Context;
import android.content.SharedPreferences;

public class InstagramWrapper
{

	private static final String TAG = InstagramWrapper.class.getClass().getSimpleName();
	private static SharedPreferences settings;
	private static InstagramWrapper instance;

	private InstagramWrapper(SharedPreferences con) {
		settings=con;
	}


	public static InstagramWrapper getInstance(SharedPreferences con)
	{

		if (instance == null)
		{
			instance = new InstagramWrapper(con);
		}
		return instance;
	}

	public OAuth getInstagramOAuthToken()
	{
		//Restore preferences
		String oauthTokenString = settings.getString(SocialConstants.InstagramConstant.KEY_ACCESS_TOKEN, null);
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
