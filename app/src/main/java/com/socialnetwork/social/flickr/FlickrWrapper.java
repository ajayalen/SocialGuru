package com.socialnetwork.social.flickr;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.oauth.OAuth;
import com.socialnetwork.social.flickr.oauth.OAuthToken;
import com.socialnetwork.social.flickr.people.User;
import com.socialnetwork.utils.Lg;

public class FlickrWrapper
{

	private static final String TAG = FlickrWrapper.class.getClass().getSimpleName();

	private static SharedPreferences settings;
	private static FlickrWrapper instance;

    private FlickrWrapper(SharedPreferences con) {
        settings=con;
    }

    public static FlickrWrapper getInstance(SharedPreferences con)
	{

		if (instance == null)
		{
			instance = new FlickrWrapper(con);
		}
		return instance;
	}

	public OAuth getFlickrOAuthToken()
	{
		//Restore preferences
		String oauthTokenString = settings.getString(SocialConstants.FlickrConstants.KEY_OAUTH_TOKEN, null);
		String tokenSecret = settings.getString(SocialConstants.FlickrConstants.KEY_TOKEN_SECRET, null);
		if (oauthTokenString == null && tokenSecret == null)
		{
			Lg.w(TAG, "No oauth token retrieved"); //$NON-NLS-1$
			return null;
		}
		OAuth oauth = new OAuth();
		String userName = settings.getString(SocialConstants.FlickrConstants.KEY_USER_NAME, null);
		String userId = settings.getString(SocialConstants.FlickrConstants.KEY_USER_ID, null);
		if (userId != null)
		{
			User user = new User();
			user.setUsername(userName);
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

	public void clearOauth()
	{

		settings.edit().clear()
				.apply();

	}
}
