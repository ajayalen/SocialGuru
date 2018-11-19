package com.socialnetwork.social.reddit;

import com.socialnetwork.social.reddit.oauth.OAuth;
import com.socialnetwork.social.reddit.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;

public final class RedditHelper {

    private static RedditHelper instance = null;
    public static final String API_KEY = "yG4BlXDUeVQlOw";
    public static final String API_SEC = "BuIIyUXTiVo44cnkHEKGAKmBi8o";//dummy value- not getting used currently

    private RedditHelper() {

    }

    public static RedditHelper getInstance() {
        if (instance == null) {
            instance = new RedditHelper();
        }

        return instance;
    }

    public Reddit getReddit() {
        try {
            Reddit t = new Reddit(API_KEY, API_SEC, new REST());
            return t;
        } catch (ParserConfigurationException e) {
            return null;
        }
    }

    public Reddit getRedditAuthed(String token, String refreshToken, String expiresIn, String tokenType) {
        Reddit f = getReddit();
        RequestContext requestContext = RequestContext.getRequestContext();
        OAuth auth = new OAuth();
        auth.setToken(new OAuthToken(token, refreshToken, expiresIn, tokenType));
        requestContext.setOAuth(auth);
        return f;
    }

}
