package com.socialnetwork.social.flickr;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.interestingness.InterestingnessInterface;
import com.socialnetwork.social.flickr.oauth.OAuth;
import com.socialnetwork.social.flickr.oauth.OAuthToken;
import com.socialnetwork.social.flickr.photos.PhotosInterface;

import javax.xml.parsers.ParserConfigurationException;


public final class FlickrHelper {

	private static FlickrHelper instance = null;
	private static final String API_KEY = "506c018e655a4d732bd94db8ef0f4742"; //$NON-NLS-1$
	public static final String API_SEC = "03741ee9b0045f4d"; //$NON-NLS-1$

	private FlickrHelper() {

	}

	public static FlickrHelper getInstance() {
		if (instance == null) {
			instance = new FlickrHelper();
		}

		return instance;
	}

	public Flickr getFlickr() {
		try {
			Flickr f = new Flickr(API_KEY, API_SEC, new REST());
			return f;
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

	public Flickr getFlickrAuthed(String token, String secret) {
		Flickr f = getFlickr();
		RequestContext requestContext = RequestContext.getRequestContext();
		OAuth auth = new OAuth();
		auth.setToken(new OAuthToken(token, secret));
		requestContext.setOAuth(auth);
		return f;
	}

	public InterestingnessInterface getInterestingInterface() {
		Flickr f = getFlickr();
		if (f != null) {
			return f.getInterestingnessInterface();
		} else {
			return null;
		}
	}
	
	public PhotosInterface getPhotosInterface() {
		Flickr f = getFlickr();
		if (f != null) {
			return f.getPhotosInterface();
		} else {
			return null;
		}
	}

}
