/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.socialnetwork.social.flickr.favorites;

import com.socialnetwork.social.flickr.FlickrException;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.flickr.Response;
import com.socialnetwork.social.flickr.Transport;
import com.socialnetwork.social.flickr.oauth.OAuthInterface;
import com.socialnetwork.social.flickr.oauth.OAuthUtils;
import com.socialnetwork.social.flickr.photos.PhotoContext;
import com.socialnetwork.social.flickr.photos.PhotoList;
import com.socialnetwork.social.flickr.photos.PhotoUtils;
import com.socialnetwork.social.utils.StringUtilities;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONException;


/**
 * Interface for working with Flickr favorites.
 *
 * @author Anthony Eden
 * @version $Id: FavoritesInterface.java,v 1.17 2009/07/11 20:30:27 x-mago Exp $
 */
public class FavoritesInterface {

    public static final String METHOD_ADD = "flickr.favorites.add";
    public static final String METHOD_GET_CONTEXT = "flickr.favorites.getContext";
    public static final String METHOD_GET_LIST = "flickr.favorites.getList";
    public static final String METHOD_GET_PUBLIC_LIST = "flickr.favorites.getPublicList";
    public static final String METHOD_REMOVE = "flickr.favorites.remove";

    private String apiKey;
    private String sharedSecret;
    private Transport transportAPI;

    public FavoritesInterface(String apiKey, String sharedSecret, Transport transportAPI) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.transportAPI = transportAPI;
    }

    /**
     * Add a photo to the user's favorites.
     *
     * @param photoId The photo ID
     * @throws IOException
     * @throws JSONException
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public void add(String photoId) throws IOException, FlickrException, InvalidKeyException, NoSuchAlgorithmException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_ADD));
        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        OAuthUtils.addOAuthToken(parameters);

        Response response = transportAPI.postJSON(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }
    
    /**
     * Returns next and previous favorites for a photo in a user's favorites.
     * 
     * @param photoId The id of the photo to fetch the context for.
     * @param userId The user who counts the photo as a favorite.
     * @return an instance of <code>PhotoContext</code>
     * @throws IOException
     * @throws JSONException
     * @throws FlickrException
     */
    public PhotoContext getContext(String photoId, String userId) throws IOException, JSONException, FlickrException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_GET_CONTEXT));
        boolean signed = OAuthUtils.hasSigned();
        if (signed) {
            parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY,
                apiKey));
        } else {
            parameters.add(new Parameter("api_key", apiKey));
        }
        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("user_id", userId));
        
        if (signed) {
            OAuthUtils.addOAuthToken(parameters);
        }
        Response response = transportAPI.postJSON(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response
                    .getErrorMessage());
        }
        return PhotoUtils.createPhotoContext(response.getData());
    }

    /**
     * Get the collection of favorites for the calling user or the specified user ID.
     *
     * @param userId The optional user ID.  Null value will be ignored.
     * @param minFaveDate The optional minimum date that a photo was favorited on.
     * @param maxFaveDate The optional maximum date that a photo was favorited on.
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The page to view.  Values <= 0 will be ignored.
     * @param extras a Set Strings representing extra parameters to send
     * @return The Collection of Photo objects
     * @throws IOException
     * @throws JSONException 
     */
    public PhotoList getList(String userId, Date minFaveDate, Date maxFaveDate, int perPage, int page, Set<String> extras) throws IOException,
             FlickrException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_GET_LIST));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        if (userId != null) {
            parameters.add(new Parameter("user_id", userId));
        }
        if (minFaveDate != null) {
            parameters.add(new Parameter("min_fave_date", String.valueOf(minFaveDate.getTime() / 1000L)));
        }
        if (maxFaveDate != null) {
            parameters.add(new Parameter("max_fave_date", String.valueOf(maxFaveDate.getTime() / 1000L)));
        }
        if (extras != null) {
            parameters.add(new Parameter("extras", StringUtilities.join(extras, ",")));
        }
        if (perPage > 0) {
            parameters.add(new Parameter("per_page", Integer.valueOf(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter("page", Integer.valueOf(page)));
        }
        OAuthUtils.addOAuthToken(parameters);

        Response response = transportAPI.postJSON(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        return PhotoUtils.createPhotoList(response.getData());
    }

    /**
     * Get the specified user IDs public contacts.
     *
     * This method does not require authentication.
     *
     * @param userId The user ID
     * @param minFaveDate The optional minimum date that a photo was favorited on.
     * @param maxFaveDate The optional maximum date that a photo was favorited on.
     * @param perPage The optional per page value.  Values <= 0 will be ignored.
     * @param page The optional page to view.  Values <= 0 will be ignored
     * @param extras A Set of extra parameters to send
     * @return A Collection of Photo objects
     * @throws IOException
     * @throws FlickrException
     * @throws JSONException 
     */
    public PhotoList getPublicList(String userId, Date minFaveDate, Date maxFaveDate, int perPage, int page, Set<String> extras)
            throws IOException, FlickrException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_GET_PUBLIC_LIST));
        parameters.add(new Parameter("api_key", apiKey));

        parameters.add(new Parameter("user_id", userId));
        if (minFaveDate != null) {
            parameters.add(new Parameter("min_fave_date", String.valueOf(minFaveDate.getTime() / 1000L)));
        }
        if (maxFaveDate != null) {
            parameters.add(new Parameter("max_fave_date", String.valueOf(maxFaveDate.getTime() / 1000L)));
        }
        if (extras != null) {
            parameters.add(new Parameter("extras", StringUtilities.join(extras, ",")));
        }
        if (perPage > 0) {
            parameters.add(new Parameter("per_page", Integer.valueOf(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter("page", Integer.valueOf(page)));
        }

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        return PhotoUtils.createPhotoList(response.getData());
    }

    /**
     * Remove the specified photo from the user's favorites.
     *
     * @param photoId The photo id
     * @throws JSONException 
     */
    public void remove(String photoId) throws IOException, FlickrException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_REMOVE));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));
        parameters.add(new Parameter("photo_id", photoId));
        OAuthUtils.addOAuthToken(parameters);

        Response response = transportAPI.postJSON(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
