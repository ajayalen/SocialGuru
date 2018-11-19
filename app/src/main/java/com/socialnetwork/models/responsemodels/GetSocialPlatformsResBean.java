package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 8/9/16.
 */
public class GetSocialPlatformsResBean {

    /**
     * Success : true
     * Message : User social platforms
     * Result : [{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"1","user_id":"0","social_media_name":"Facebook","user_status":"0","social_icon":"","new_platform_name":"Facebook","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"2","user_id":"0","social_media_name":"Pinterest","user_status":"0","social_icon":"","new_platform_name":"Pinterest","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"3","user_id":"0","social_media_name":"Twitter","user_status":"0","social_icon":"","new_platform_name":"Twitter","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"4","user_id":"0","social_media_name":"LinkedIn","user_status":"0","social_icon":"","new_platform_name":"LinkedIn","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"5","user_id":"0","social_media_name":"Google+","user_status":"0","social_icon":"","new_platform_name":"Google+","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"6","user_id":"0","social_media_name":"Tumbler","user_status":"0","social_icon":"","new_platform_name":"Tumbler","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"7","user_id":"0","social_media_name":"Instagram","user_status":"0","social_icon":"","new_platform_name":"Instagram","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"8","user_id":"0","social_media_name":"YouTube","user_status":"0","social_icon":"","new_platform_name":"YouTube","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"9","user_id":"0","social_media_name":"Reddit","user_status":"0","social_icon":"","new_platform_name":"Reddit","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"10","user_id":"0","social_media_name":"Flicker","user_status":"0","social_icon":"","new_platform_name":"Flicker","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"11","user_id":"0","social_media_name":"Skype","user_status":"0","social_icon":"","new_platform_name":"Skype","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"12","user_id":"0","social_media_name":"Vine","user_status":"0","social_icon":"","new_platform_name":"Vine","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"13","user_id":"0","social_media_name":"Quora","user_status":"0","social_icon":"","new_platform_name":"Quora","platform_id":"","color_code":"","other_accounts":[]},{"account_id":"","username":"","first_name":"","last_name":"","is_new":"0","social_media_id":"14","user_id":"0","social_media_name":"SnapChat","user_status":"0","social_icon":"","new_platform_name":"SnapChat","platform_id":"","color_code":"","other_accounts":[]}]
     * Status : 200
     */

    private boolean Success;
    private String Message;
    private int Status;
    /**
     * account_id :
     * username :
     * first_name :
     * last_name :
     * is_new : 0
     * social_media_id : 1
     * user_id : 0
     * social_media_name : Facebook
     * user_status : 0
     * social_icon :
     * new_platform_name : Facebook
     * platform_id :
     * color_code :
     * other_accounts : []
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
        private String account_id;
        private String username;
        private String first_name;
        private String last_name;
        private String is_new;
        private String social_media_id;
        private String user_id;
        private String social_media_name;
        private String user_status;
        private String social_icon;
        private String new_platform_name;
        private String platform_id;
        private String color_code;
        private List<?> other_accounts;

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

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(String social_media_id) {
            this.social_media_id = social_media_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSocial_media_name() {
            return social_media_name;
        }

        public void setSocial_media_name(String social_media_name) {
            this.social_media_name = social_media_name;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getSocial_icon() {
            return social_icon;
        }

        public void setSocial_icon(String social_icon) {
            this.social_icon = social_icon;
        }

        public String getNew_platform_name() {
            return new_platform_name;
        }

        public void setNew_platform_name(String new_platform_name) {
            this.new_platform_name = new_platform_name;
        }

        public String getPlatform_id() {
            return platform_id;
        }

        public void setPlatform_id(String platform_id) {
            this.platform_id = platform_id;
        }

        public String getColor_code() {
            return color_code;
        }

        public void setColor_code(String color_code) {
            this.color_code = color_code;
        }

        public List<?> getOther_accounts() {
            return other_accounts;
        }

        public void setOther_accounts(List<?> other_accounts) {
            this.other_accounts = other_accounts;
        }
    }
}
