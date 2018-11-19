package com.socialnetwork.social.twitter;


import com.socialnetwork.social.twitter.oauth.OAuth;
import com.socialnetwork.social.twitter.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;


public final class TwitterHelper {

    private static TwitterHelper instance = null;
    public static final String API_KEY = "2CKfIVgJ8trsJ9u1q16fci21o";
    public static final String API_SEC = "jf4PkuBIRAvtKAreRDotCXs2Tprkun16DoVCUOgZZBFAvovxyV";

    private TwitterHelper() {

    }

    public static TwitterHelper getInstance() {
        if (instance == null) {
            instance = new TwitterHelper();
        }

        return instance;
    }

    public Twitter getTwitter() {
        try {
            Twitter t = new Twitter(API_KEY, API_SEC, new REST());
            return t;
        } catch (ParserConfigurationException e) {
            return null;
        }
    }

    public Twitter getTwitterAuthed(String token, String secret) {
        Twitter f = getTwitter();
        RequestContext requestContext = RequestContext.getRequestContext();
        OAuth auth = new OAuth();
        auth.setToken(new OAuthToken(token, secret));
        requestContext.setOAuth(auth);
        return f;
    }

}
