package com.socialnetwork.social.reddit;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.reddit.oauth.OAuth;
import com.socialnetwork.social.reddit.oauth.OAuthToken;
import com.socialnetwork.utils.Lg;

public class RedditWrapper {

    private static final String TAG = RedditWrapper.class.getClass().getSimpleName();
    private static SharedPreferences settings;
    private static RedditWrapper instance;

    private RedditWrapper(SharedPreferences con) {
        settings=con;

    }


    public static RedditWrapper getInstance(SharedPreferences con) {

        if (instance == null) {
            instance = new RedditWrapper(con);
        }
        return instance;
    }

    public OAuth getRedditOAuthToken() {
        //Restore preferences
        String oauthTokenString = settings.getString(SocialConstants.RedditConstants.KEY_ACCESS_TOKEN, null);
        if (oauthTokenString == null) {
            Lg.w(TAG, "No oauth token retrieved"); //$NON-NLS-1$
            return null;
        }
        OAuth oauth = new OAuth();
        OAuthToken oauthToken = new OAuthToken();
        oauth.setToken(oauthToken);
        oauthToken.setOauthToken(oauthTokenString);
        oauthToken.setExpiresIn(settings.getString(SocialConstants.RedditConstants.KEY_EXPIRES_IN, null));
        oauthToken.setRefreshToken(settings.getString(SocialConstants.RedditConstants.KEY_REFRESH_TOKEN, null));
        oauthToken.setTokenType(settings.getString(SocialConstants.RedditConstants.KEY_TOKEN_TYPE, null));
        
        return oauth;
    }

    public void clearOauth() {
        settings.edit().clear().apply();
    }

}
