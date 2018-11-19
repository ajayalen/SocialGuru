/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.socialnetwork.social.flickr.uploader;

import com.socialnetwork.social.flickr.Flickr;
import com.socialnetwork.social.flickr.FlickrException;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.flickr.REST;
import com.socialnetwork.social.flickr.Transport;
import com.socialnetwork.social.flickr.oauth.OAuthInterface;
import com.socialnetwork.social.flickr.oauth.OAuthUtils;
import com.socialnetwork.social.utils.StringUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;



/**
 * Upload a photo.<p>
 *
 * you can switch between synchronous and asynchronous uploads.<p>
 *
 * Synchronous uploads return the photoId, whilst asynchronous uploads
 * return a ticketId.<p>
 *
 * TicketId's can be tracked with
 * for completion.
 *
 * @author Anthony Eden
 * @version $Id: Uploader.java,v 1.12 2009/12/15 20:57:49 x-mago Exp $
 */
public class Uploader {
    public static final String UPLOAD_PATH = "/services/upload/";
    public static final String URL_UPLOAD = "https://" + Flickr.DEFAULT_API_HOST + UPLOAD_PATH;
    public static final String REPLACE_PATH = "/services/replace/";
    public static final String URL_REPLACE = "https://" + Flickr.DEFAULT_API_HOST + REPLACE_PATH;
    private String apiKey;
    private String sharedSecret;
    private Transport transport;

    /**
     * Construct an Uploader.
     *
     * @param apiKey The API key
     */
    public Uploader(String apiKey, String sharedSecret) {
        try {
            this.apiKey = apiKey;
            this.sharedSecret = sharedSecret;
            this.transport = new REST(Flickr.DEFAULT_API_HOST);
            this.transport.setResponseClass(UploaderResponse.class);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Upload a photo from a byte-array.
     *
     * @param data The photo data as a byte array
     * @param metaData The meta data
     * @return photoId for sync mode or ticketId for async mode
     * @throws FlickrException
     * @throws IOException
     * @throws SAXException
     */
    public String upload(String imageName, byte[] data, UploadMetaData metaData) throws FlickrException, IOException, SAXException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, this.apiKey));
        String title = metaData.getTitle();
        if (title != null)
            parameters.add(new Parameter("title", title));

        String description = metaData.getDescription();
        if (description != null)
            parameters.add(new Parameter("description", description));

        Collection<String> tags = metaData.getTags();
        if (tags != null)
            parameters.add(new Parameter("tags", StringUtilities.join(tags, " ")));

        parameters.add(new Parameter("is_public", metaData.isPublicFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_family", metaData.isFamilyFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_friend", metaData.isFriendFlag() ? "1" : "0"));

        parameters.add(new ImageParameter(imageName, data));

        if (metaData.isHidden() != null) {
            parameters.add(new Parameter("hidden", metaData.isHidden().booleanValue() ? "1" : "0"));
        }

        if (metaData.getSafetyLevel() != null) {
            parameters.add(new Parameter("safety_level", metaData.getSafetyLevel()));
        }

        parameters.add(new Parameter("async", metaData.isAsync() ? "1" : "0"));

        if (metaData.getContentType() != null) {
            parameters.add(new Parameter("content_type", metaData.getContentType()));
        }
        OAuthUtils.addOAuthToken(parameters);

        UploaderResponse response = (UploaderResponse) transport.upload(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        String id = "";
        if (metaData.isAsync()) {
            id = response.getTicketId();
        } else {
            id = response.getPhotoId();
        }
        return id;
    }

    /**
     * Upload a photo from an InputStream.
     *
     * @param in
     * @param metaData
     * @return photoId for sync mode or ticketId for async mode
     * @throws IOException
     * @throws FlickrException
     * @throws SAXException
     */
    public String upload(String imageName, InputStream in, UploadMetaData metaData) throws IOException, FlickrException, SAXException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, this.apiKey));

        String title = metaData.getTitle();
        if (title != null)
            parameters.add(new Parameter("title", title));

        String description = metaData.getDescription();
        if (description != null)
            parameters.add(new Parameter("description", description));

        Collection<String> tags = metaData.getTags();
        if (tags != null) {
            parameters.add(new Parameter("tags", StringUtilities.join(tags, " ")));
        }

        parameters.add(new Parameter("is_public", metaData.isPublicFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_family", metaData.isFamilyFlag() ? "1" : "0"));
        parameters.add(new Parameter("is_friend", metaData.isFriendFlag() ? "1" : "0"));
        parameters.add(new Parameter("async", metaData.isAsync() ? "1" : "0"));
        if (metaData.getSafetyLevel() != null) {
            parameters.add(new Parameter("safety_level", metaData.getSafetyLevel()));
        }
        if (metaData.getContentType() != null) {
            parameters.add(new Parameter("content_type", metaData.getContentType()));
        }

        parameters.add(new ImageParameter(imageName, in));
        OAuthUtils.addOAuthToken(parameters);

        UploaderResponse response = (UploaderResponse) transport.upload(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        String id = "";
        if (metaData.isAsync()) {
            id = response.getTicketId();
        } else {
            id = response.getPhotoId();
        }
        return id;
    }

    /**
     * Upload a photo from an InputStream.
     * 
     *
     * @param imageName
     * @param in
     * @param photoId
     * @return photoId for sync mode or ticketId for async mode
     * @throws IOException
     * @throws FlickrException
     * @throws SAXException
     * @deprecated This is not working at moment!
     */
    public String replace(String imageName, InputStream in, String photoId, boolean async) throws IOException, FlickrException, SAXException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, this.apiKey));

        parameters.add(new Parameter("async", async ? "1" : "0"));
        parameters.add(new Parameter("photo_id", photoId));

        parameters.add(new ImageParameter(imageName, in));
        OAuthUtils.addOAuthToken(parameters);

        UploaderResponse response = (UploaderResponse) transport.replace(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        String id = "";
        if (async) {
            id = response.getTicketId();
        } else {
            id = response.getPhotoId();
        }
        return id;
    }

    /**
     * Upload a photo from an InputStream.
     *
     * @param imageName
     * @param photoId
     * @return photoId for sync mode or ticketId for async mode
     * @throws IOException
     * @throws FlickrException
     * @throws SAXException
     * @deprecated This is not working at moment!
     */
    public String replace(String imageName, byte[] data, String photoId, boolean async) throws IOException, FlickrException, SAXException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(OAuthInterface.PARAM_OAUTH_CONSUMER_KEY, this.apiKey));

        parameters.add(new Parameter("async", async ? "1" : "0"));
        parameters.add(new Parameter("photo_id", photoId));

        parameters.add(new ImageParameter(imageName, data));
        OAuthUtils.addOAuthToken(parameters);

        UploaderResponse response = (UploaderResponse) transport.replace(sharedSecret, parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        String id = "";
        if (async) {
            id = response.getTicketId();
        } else {
            id = response.getPhotoId();
        }
        return id;
    }
}
