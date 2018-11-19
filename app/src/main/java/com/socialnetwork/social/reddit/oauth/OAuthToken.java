/**
 *
 */
package com.socialnetwork.social.reddit.oauth;

import java.io.Serializable;

/**
 *
 */
public class OAuthToken implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String oauthToken;
    private String expiresIn;
    private String refreshToken;
    private String tokenType;


    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     *
     */


    public OAuthToken() {
        super();
    }

    /**
     * @param oauthToken
     */
    public OAuthToken(String oauthToken,String refreshToken,String expiresIn,String tokenType) {
        super();
        this.oauthToken = oauthToken;
        this.refreshToken=refreshToken;
        this.expiresIn=expiresIn;
        this.tokenType=tokenType;
    }

    /**
     * @return the oauthToken
     */
    public String getOauthToken() {
        return oauthToken;
    }

    /**
     * @param oauthToken the oauthToken to set
     */
    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }


}
