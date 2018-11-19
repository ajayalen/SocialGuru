package com.socialnetwork.social.tumblr;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.tumblr.oauth.OAuth;
import com.socialnetwork.social.tumblr.oauth.OAuthToken;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.utils.UrlUtilities;
import com.socialnetwork.utils.Lg;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TumblrWrapper {

    private static final String TAG = TumblrWrapper.class.getClass().getSimpleName();
    private static SharedPreferences settings;
    private static TumblrWrapper instance;

    private TumblrWrapper(SharedPreferences con) {
        settings=con;
    }


    public static TumblrWrapper getInstance(SharedPreferences con) {

        if (instance == null) {
            instance = new TumblrWrapper(con);
        }
        return instance;
    }

    public OAuth getTumblrOAuthToken() {
        //Restore preferences
        String oauthTokenString = settings.getString(SocialConstants.TumblrConstant.KEY_OAUTH_TOKEN, null);
        String tokenSecret = settings.getString(SocialConstants.TumblrConstant.KEY_TOKEN_SECRET, null);
        if (oauthTokenString == null && tokenSecret == null) {
            Lg.w(TAG, "No oauth token retrieved"); //$NON-NLS-1$
            return null;
        }
        OAuth oauth = new OAuth();
        String userName = settings.getString(SocialConstants.TumblrConstant.KEY_USER_NAME, null);
        String userId = settings.getString(SocialConstants.TumblrConstant.KEY_USER_ID, null);
        if (userId != null) {
            User user = new User();
//			user.setUsername(userName);
//			user.setId(userId);
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
