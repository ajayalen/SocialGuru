package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 14/9/16.
 */
public class AddSocialDetailsResBean {

    /**
     * Success : true
     * Message : Account added successfully
     * Result : {"user_id":"86","account_id":"","social_media_id":"11","username":"","first_name":"","last_name":"","profile_picture_url":"","other_accounts":[]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 86
     * account_id :
     * social_media_id : 11
     * username :
     * first_name :
     * last_name :
     * profile_picture_url :
     * other_accounts : []
     */

    private ResultBean Result;
    private int Status;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public static class ResultBean {
        private String user_id;
        private String account_id;
        private String social_media_id;
        private String username;
        private String first_name;
        private String last_name;
        private String profile_picture_url;
        private List<?> other_accounts;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public String getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(String social_media_id) {
            this.social_media_id = social_media_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getProfile_picture_url() {
            return profile_picture_url;
        }

        public void setProfile_picture_url(String profile_picture_url) {
            this.profile_picture_url = profile_picture_url;
        }

        public List<?> getOther_accounts() {
            return other_accounts;
        }

        public void setOther_accounts(List<?> other_accounts) {
            this.other_accounts = other_accounts;
        }
    }
}
