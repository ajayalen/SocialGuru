package com.socialnetwork.models.reqmodels;

import java.util.List;

/**
 * Created by sakshi on 5/9/16.
 */
public class AddContactDetailReqBean {

    /**
     * user_id : 1
     * social_media_id : 17
     * other_accounts : [{"phone_tag":"home","phone_number":"2396475894"},{"phone_tag":"personal","phone_number":"983457893"}]
     */

    private String user_id;
    private String social_media_id;
    /**
     * phone_tag : home
     * phone_number : 2396475894
     */

    private List<OtherAccountsBean> other_accounts;

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

    public List<OtherAccountsBean> getOther_accounts() {
        return other_accounts;
    }

    public void setOther_accounts(List<OtherAccountsBean> other_accounts) {
        this.other_accounts = other_accounts;
    }

    public static class OtherAccountsBean {
        private String phone_tag;
        private String phone_number;

        public String getPhone_tag() {
            return phone_tag;
        }

        public void setPhone_tag(String phone_tag) {
            this.phone_tag = phone_tag;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }
    }
}
