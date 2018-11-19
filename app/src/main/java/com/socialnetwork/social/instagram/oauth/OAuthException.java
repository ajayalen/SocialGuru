/**
 * 
 */
package com.socialnetwork.social.instagram.oauth;


import com.socialnetwork.social.instagram.InstagramException;

/**
 *
 */
public class OAuthException extends InstagramException {

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
