/*
 *-------------------------------------------------------
 * (c) 2006 Das B&uuml;ro am Draht GmbH - All Rights reserved
 *-------------------------------------------------------
 */
package com.socialnetwork.social.flickr.interestingness;

import com.socialnetwork.social.flickr.FlickrException;
import com.socialnetwork.social.utils.Parameter;
import com.socialnetwork.social.flickr.Response;
import com.socialnetwork.social.flickr.Transport;
import com.socialnetwork.social.flickr.photos.Extras;
import com.socialnetwork.social.flickr.photos.PhotoList;
import com.socialnetwork.social.flickr.photos.PhotoUtils;
import com.socialnetwork.social.utils.StringUtilities;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONException;


/**
 *
 * @author till
 * @version $Id: InterestingnessInterface.java,v 1.9 2009/07/11 20:30:27 x-mago Exp $
 */
public class InterestingnessInterface {

    public static final String METHOD_GET_LIST = "flickr.interestingness.getList";

    private static final String KEY_METHOD = "method";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_DATE = "date";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_PER_PAGE = "per_page";
    private static final String KEY_PAGE = "page";

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATS = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private String apiKey;
    private String sharedSecret;
    private Transport transportAPI;

    public InterestingnessInterface(
        String apiKey,
        String sharedSecret,
        Transport transportAPI
    ) {
        this.apiKey = apiKey;
        this.sharedSecret = sharedSecret;
        this.transportAPI = transportAPI;
    }

    /**
     * Returns the list of interesting photos for the most recent day or a user-specified date.
     *
     * This method does not require authentication.
     *
     * @param date
     * @param extras A set of Strings controlling the extra information to fetch for each returned record. Currently supported fields are: license, date_upload, date_taken, owner_name, icon_server, original_format, last_update, geo. Set to null or an empty set to not specify any extras.
     * @param perPage The number of photos to show per page
     * @param page The page offset
     * @return PhotoList
     * @throws IOException
     * @throws JSONException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public PhotoList getList(String date, Set<String> extras, int perPage, int page)
    throws FlickrException, IOException, InvalidKeyException, NoSuchAlgorithmException, JSONException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new Parameter(KEY_METHOD, METHOD_GET_LIST));
        parameters.add(new Parameter(KEY_API_KEY, apiKey));

        if (date != null) {
             parameters.add(new Parameter(KEY_DATE, date));
        }

        if (extras != null) {
            parameters.add(new Parameter(KEY_EXTRAS, StringUtilities.join(extras, ",")));
        }

        if (perPage > 0) {
            parameters.add(new Parameter(KEY_PER_PAGE, String.valueOf(perPage)));
        }
        if (page > 0) {
            parameters.add(new Parameter(KEY_PAGE, String.valueOf(page)));
        }

        Response response = transportAPI.get(transportAPI.getPath(), parameters);
        if (response.isError()) {
            throw new FlickrException(response.getErrorCode(), response.getErrorMessage());
        }
        return PhotoUtils.createPhotoList(response.getData());
    }

    /**
     * 
     * @param date
     * @param extras
     * @param perPage
     * @param page
     * @return PhotoList
     * @throws FlickrException
     * @throws IOException
     * @throws JSONException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public PhotoList getList(Date date, Set<String> extras, int perPage, int page)
      throws FlickrException, IOException, InvalidKeyException, NoSuchAlgorithmException, JSONException {
        String dateString = null;
        if (date != null) {
            DateFormat df = DATE_FORMATS.get();
            dateString = df.format(date);
        }
        return getList(dateString, extras, perPage, page);
    }

    /**
     * convenience method to get the list of all 500 most recent photos
     * in flickr explore with all known extra attributes.
     *
     * @return a List of Photos
     * @throws FlickrException
     * @throws IOException
     * @throws JSONException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public PhotoList getList() throws FlickrException, IOException, InvalidKeyException, NoSuchAlgorithmException, JSONException {
        return getList((String) null, Extras.ALL_EXTRAS, 500, 1);
    }

}
