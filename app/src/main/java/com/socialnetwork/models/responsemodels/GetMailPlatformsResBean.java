package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 5/9/16.
 */
public class GetMailPlatformsResBean {

    /**
     * Success : true
     * Message : User Email Platforms
     * Result : [{"is_new":"0","platform_id":"","social_media_id":"15","new_platform_name":"Gmail","color_code":"","other_accounts":[{"other_account_id":"34","username":"ggfh","email_id":"gdg"}]},{"is_new":"0","platform_id":"","social_media_id":"16","new_platform_name":"Yahoo","color_code":"","other_accounts":[]}]
     * Status : 200
     */

    private boolean Success;
    private String Message;
    private int Status;
    /**
     * is_new : 0
     * platform_id :
     * social_media_id : 15
     * new_platform_name : Gmail
     * color_code :
     * other_accounts : [{"other_account_id":"34","username":"ggfh","email_id":"gdg"}]
     */

    private List<ResultBean> Result;

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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        private String is_new;
        private String platform_id;
        private String social_media_id;
        private String new_platform_name;
        private String color_code;
        /**
         * other_account_id : 34
         * username : ggfh
         * email_id : gdg
         */

        private List<OtherAccountsBean> other_accounts;

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getPlatform_id() {
            return platform_id;
        }

        public void setPlatform_id(String platform_id) {
            this.platform_id = platform_id;
        }

        public String getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(String social_media_id) {
            this.social_media_id = social_media_id;
        }

        public String getNew_platform_name() {
            return new_platform_name;
        }

        public void setNew_platform_name(String new_platform_name) {
            this.new_platform_name = new_platform_name;
        }

        public String getColor_code() {
            return color_code;
        }

        public void setColor_code(String color_code) {
            this.color_code = color_code;
        }

        public List<OtherAccountsBean> getOther_accounts() {
            return other_accounts;
        }

        public void setOther_accounts(List<OtherAccountsBean> other_accounts) {
            this.other_accounts = other_accounts;
        }

        public static class OtherAccountsBean {
            private String other_account_id;
            private String username;
            private String email_id;

            public String getOther_account_id() {
                return other_account_id;
            }

            public void setOther_account_id(String other_account_id) {
                this.other_account_id = other_account_id;
            }

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
}
