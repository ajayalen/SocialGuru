package com.socialnetwork.social.tumblr;


import com.socialnetwork.social.tumblr.oauth.OAuth;
import com.socialnetwork.social.tumblr.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;


public final class TumblrHelper {

	private static TumblrHelper instance = null;
	public static final String API_KEY = "QSCUaQTStkNVwswRsfM2N8jJHk0K7ZGUC7f3VbqcC9hXEsgTIL";
	public static final String API_SEC = "HZvwFdhhweFVSLSTkVugkMk6WMay9GqgogFBXggvlkibI31w2g"; //$NON-NLS-1$

	private TumblrHelper() {

	}

	public static TumblrHelper getInstance() {
		if (instance == null) {
			instance = new TumblrHelper();
		}

		return instance;
	}

	public Tumblr getTumblr() {
		try {
			Tumblr t = new Tumblr(API_KEY, API_SEC, new REST());
			return t;
		} catch (ParserConfigurationException e) {
			return null;
		}
	}
	public Tumblr getTumblrAuthed(String token, String secret) {
		Tumblr f = getTumblr();
		RequestContext requestContext = RequestContext.getRequestContext();
		OAuth auth = new OAuth();
		auth.setToken(new OAuthToken(token, secret));
		requestContext.setOAuth(auth);
		return f;
	}


}
