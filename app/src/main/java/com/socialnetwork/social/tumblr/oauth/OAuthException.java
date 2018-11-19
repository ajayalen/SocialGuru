/**
 * 
 */
package com.socialnetwork.social.tumblr.oauth;


import com.socialnetwork.social.tumblr.TumblrException;

/**
 *
 */
public class OAuthException extends TumblrException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public OAuthException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public OAuthException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
