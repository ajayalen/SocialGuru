package com.socialnetwork.models.reqmodels;

import java.util.List;

/**
 * Created by sakshi on 5/9/16.
 */
public class AddMailDetailReqBean {

    /**
     * user_id : 1
     * social_media_id : 15
     * other_accounts : [{"username":"sdfdg","email_id":"shgdhf@jdkjf.com"},{"username":"sdfdg","email_id":"shgdhf@jdkjf.com"},{"username":"sdfdg","email_id":"shgdhf@jdkjf.com"}]
     */

    private String user_id;

    public String getSocial_media_id() {
        return social_media_id;
    }

    public void setSocial_media_id(String social_media_id) {
        this.social_media_id = social_media_id;
    }

    private String social_media_id;
    /**
     * username : sdfdg
     * email_id : shgdhf@jdkjf.com
     */

    private List<OtherAccountsBean> other_accounts;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<OtherAccountsBean> getOther_accounts() {
        return other_accounts;
    }

    public void setOther_accounts(List<OtherAccountsBean> other_accounts) {
        this.other_accounts = other_accounts;
    }

    public static class OtherAccountsBean {
        private String username;
        private String email_id;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail_id() {
            return email_id;
        }

        public void setEmail_id(String email_id) {
            this.email_id = email_id;
        }
    }
}
