/**
 * 
 */
package com.socialnetwork.social.instagram.oauth;

import java.io.Serializable;

/**
 *
 */
public class OAuthToken implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String oauthToken;
    /**
     *
     */
    public OAuthToken()
    {
        super();
    }

    /**
     * @param oauthToken
     */
    public OAuthToken(String oauthToken)
    {
        super();
        this.oauthToken = oauthToken;
    }

    /**
     * @return the oauthToken
     */
    public String getOauthToken()
    {
        return oauthToken;
    }

    /**
     * @param oauthToken the oauthToken to set
     */
    public void setOauthToken(String oauthToken)
    {
        this.oauthToken = oauthToken;
    }


}
