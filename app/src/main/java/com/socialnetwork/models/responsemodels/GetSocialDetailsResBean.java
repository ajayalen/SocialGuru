package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 8/9/16.
 */
public class GetSocialDetailsResBean {


    /**
     * Success : true
     * Message : Platform detailed information
     * Result : {"linked_account":{"is_new":"0","color_code":"","social_icon":"","platform_id":"","new_platform_name":"","account_id":"3543578908900000","username":"","first_name":"deepali","last_name":"vig","profile_pic":"http://103.25.130.197/social_papa/assets/userImages/1472795553_xCreative-Gift-Wrap_jpg_pagespeed_ic_xHAUajZNJM.jpg","user_status":"1","user_id":"31","social_media_id":"1"},"other_accounts":[{"other_account_id":"30","other_account":"dhdh"}]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * linked_account : {"is_new":"0","color_code":"","social_icon":"","platform_id":"","new_platform_name":"","account_id":"3543578908900000","username":"","first_name":"deepali","last_name":"vig","profile_pic":"http://103.25.130.197/social_papa/assets/userImages/1472795553_xCreative-Gift-Wrap_jpg_pagespeed_ic_xHAUajZNJM.jpg","user_status":"1","user_id":"31","social_media_id":"1"}
     * other_accounts : [{"other_account_id":"30","other_account":"dhdh"}]
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
        /**
         * is_new : 0
         * color_code :
         * social_icon :
         * platform_id :
         * new_platform_name :
         * account_id : 3543578908900000
         * username :
         * first_name : deepali
         * last_name : vig
         * profile_pic : http://103.25.130.197/social_papa/assets/userImages/1472795553_xCreative-Gift-Wrap_jpg_pagespeed_ic_xHAUajZNJM.jpg
         * user_status : 1
         * user_id : 31
         * social_media_id : 1
         */

        private LinkedAccountBean linked_account;
        /**
         * other_account_id : 30
         * other_account : dhdh
         */

        private List<OtherAccountsBean> other_accounts;

        public LinkedAccountBean getLinked_account() {
            return linked_account;
        }

        public void setLinked_account(LinkedAccountBean linked_account) {
            this.linked_account = linked_account;
        }

        public List<OtherAccountsBean> getOther_accounts() {
            return other_accounts;
        }

        public void setOther_accounts(List<OtherAccountsBean> other_accounts) {
            this.other_accounts = other_accounts;
        }

        public static class LinkedAccountBean {
            private String is_new;
            private String color_code;
            private String social_icon;
            private String platform_id;
            private String new_platform_name;
            private String account_id;
            private String username;
            private String first_name;
            private String last_name;
            private String profile_pic;
            private String user_status;
            private String user_id;
            private String social_media_id;

            public String getIs_new() {
                return is_new;
            }

            public void setIs_new(String is_new) {
                this.is_new = is_new;
            }

            public String getColor_code() {
                return color_code;
            }

            public void setColor_code(String color_code) {
                this.color_code = color_code;
            }

            public String getSocial_icon() {
                return social_icon;
            }

            public void setSocial_icon(String social_icon) {
                this.social_icon = social_icon;
            }

            public String getPlatform_id() {
                return platform_id;
            }

            public void setPlatform_id(String platform_id) {
                this.platform_id = platform_id;
            }

            public String getNew_platform_name() {
                return new_platform_name;
            }

            public void setNew_platform_name(String new_platform_name) {
                this.new_platform_name = new_platform_name;
            }

            public String getAccount_id() {
                return account_id;
            }

            public void setAccount_id(String account_id) {
                this.account_id = account_id;
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

            public String getProfile_pic() {
                return profile_pic;
            }

            public void setProfile_pic(String profile_pic) {
                this.profile_pic = profile_pic;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

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
        }

        public static class OtherAccountsBean {
            private String other_account_id;
            private String other_account;

            public String getOther_account_id() {
                return other_account_id;
            }

            public void setOther_account_id(String other_account_id) {
                this.other_account_id = other_account_id;
            }

            public String getOther_account() {
                return other_account;
            }

            public void setOther_account(String other_account) {
                this.other_account = other_account;
            }
        }
    }
}
