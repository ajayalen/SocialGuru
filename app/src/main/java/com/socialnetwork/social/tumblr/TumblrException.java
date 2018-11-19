/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.socialnetwork.social.tumblr;

/**
 * Exception which wraps a tumblr error.
 *
 */
public class TumblrException extends Exception {

      private static final long serialVersionUID = 7958091410349084831L;
      private String errorCode;
    private String errorMessage;

    public TumblrException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @param arg0
     * @param arg1
     */
    public TumblrException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public TumblrException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public TumblrException(Throwable arg0) {
        super(arg0);
    }



    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
