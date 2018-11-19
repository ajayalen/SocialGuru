package com.socialnetwork.social.twitter;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.twitter.oauth.OAuth;
import com.socialnetwork.social.twitter.oauth.OAuthToken;
import com.socialnetwork.social.twitter.user.User;
import com.socialnetwork.utils.Lg;

import android.content.Context;
import android.content.SharedPreferences;

public class TwitterWrapper
{

	private static final String TAG = TwitterWrapper.class.getClass().getSimpleName();
	private static SharedPreferences settings;
	private static TwitterWrapper instance;

	private TwitterWrapper(SharedPreferences con) {
		settings=con;
	}


	public static TwitterWrapper getInstance(SharedPreferences con)
	{

		if (instance == null)
		{
			instance = new TwitterWrapper(con);
		}
		return instance;
	}

	public OAuth getTwitterOAuthToken()
	{
		//Restore preferences
		String oauthTokenString = settings.getString(SocialConstants.TwitterConstant.KEY_OAUTH_TOKEN, null);
		String tokenSecret = settings.getString(SocialConstants.TwitterConstant.KEY_TOKEN_SECRET, null);
		if (oauthTokenString == null && tokenSecret == null)
		{
			Lg.w(TAG, "No oauth token retrieved"); //$NON-NLS-1$
			return null;
		}
		OAuth oauth = new OAuth();
		String userName = settings.getString(SocialConstants.TwitterConstant.KEY_USER_NAME, null);
		String userId = settings.getString(SocialConstants.TwitterConstant.KEY_USER_ID, null);
		if (userId != null)
		{
			User user = new User();
//			user.setUsername(userName);
			user.setId(userId);
			oauth.setUser(user);
		}
		OAuthToken oauthToken = new OAuthToken();
		oauth.setToken(oauthToken);
		oauthToken.setOauthToken(oauthTokenString);
		oauthToken.setOauthTokenSecret(tokenSecret);
		//        Lg.d(TAG,"Retrieved token from preference store: oauth token={}, and token secret={}", oauthTokenString, tokenSecret); //$NON-NLS-1$
		return oauth;
	}
	public void clearOauth() {
		settings.edit().clear().apply();
	}
}
