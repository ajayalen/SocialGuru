/**
 * 
 */
package com.socialnetwork.social.linkedin.oauth;


import com.socialnetwork.social.linkedin.LinkedinException;

/**
 *
 */
public class OAuthException extends LinkedinException {

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
