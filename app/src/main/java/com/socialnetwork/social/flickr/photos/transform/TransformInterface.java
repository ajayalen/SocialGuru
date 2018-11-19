/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.socialnetwork.social.flickr.photos.transform;

import com.socialnetwork.social.flickr.FlickrException;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.flickr.Response;
import com.socialnetwork.social.flickr.Transport;
import com.socialnetwork.social.flickr.oauth.OAuthInterface;
import com.socialnetwork.social.flickr.oauth.OAuthUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;


/**
 * @author Anthony Eden
 * @version $Id: TransformInterface.java,v 1.6 2008/01/28 23:01:44 x-mago Exp $
 */
public class TransformInterface {

    public static final String METHOD_ROTATE = "flickr.photos.transform.rotate";

    private String apiKey;
    private String sharedSecret;
    private Transport transportAPI;

    public TransformInterface(
        String apiKey,
        String sharedSecret,
        Transport transportAPI
    ) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.transportAPI = transportAPI;
    }

    /**
     * Rotate the specified photo.  The only allowed values for degrees are 90, 180 and 270.
     *
     * @param photoId The photo ID
     * @param degrees The degrees to rotate (90, 170 or 270)
     * @throws JSONException 
     */
    public void rotate(String photoId, int degrees)
        throws IOException, FlickrException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter("method", METHOD_ROTATE));
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, apiKey));

        parameters.add(new Parameter("photo_id", photoId));
        parameters.add(new Parameter("degrees", String.valueOf(degrees)));
        OAuthUtils.addOAuthToken(parameters);

        Response response = transportAPI.postJSON(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
    }

}
