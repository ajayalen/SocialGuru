/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.socialnetwork.social.instagram;

import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public interface Response {

    void parse(String rawMessage) throws JSONException;

    boolean isError();

    String getErrorCode();

    String getErrorMessage();
    
    JSONObject getData();
    
    String getRawResponse();
    
}
