/**
 * 
 */
package com.socialnetwork.social.reddit.oauth;


import com.socialnetwork.social.utils.Parameter;

/**
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
