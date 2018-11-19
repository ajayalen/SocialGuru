package com.socialnetwork.models.reqmodels;

import java.util.List;

/**
 * Created by sakshi on 8/9/16.
 */
public class AddSocialDetailsReqBean {

    /**
     * user_id : 1
     * social_media_id : 22
     * account_id : 34673465734659848
     * first_name : deepali
     * last_name : vig
     * username : deepali
     * other_accounts : ["account221","account222","account223"]
     */

    private String user_id;
    private String social_media_id;
    private String account_id;
    private String first_name;
    private String last_name;
    private String username;
    private List<String> other_accounts;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSocial_media_id() {
        return social_media_id;
    }

    public void setSocial_media_id(String social_media_id) {
        this.social_media_id = social_media_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getOther_accounts() {
        return other_accounts;
    }

    public void setOther_accounts(List<String> other_accounts) {
        this.other_accounts = other_accounts;
    }
}
