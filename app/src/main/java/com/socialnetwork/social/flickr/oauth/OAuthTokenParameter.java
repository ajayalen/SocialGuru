/**
 * 
 */
package com.socialnetwork.social.flickr.oauth;


import com.socialnetwork.social.utils.Parameter;

/**
 * @author Toby Yu(yuyang226@gmail.com)
 *
 */
public class OAuthTokenParameter extends Parameter {

    /**
     * @param value
     */
    public OAuthTokenParameter(Object value) {
        super(OAuthInterface.KEY_OAUTH_TOKEN, value);
    }

}
