package com.socialnetwork.models.responsemodels;

/**
 * Created by sakshi on 7/9/16.
 */
public class AddPlatformResBean {

    /**
     * Success : true
     * Message : Platform added successfully
     * Result : {"user_id":"9","social_media_name":"fdsf","color_code":"","social_icon":"","type":"2","social_media_id":42}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 9
     * social_media_name : fdsf
     * color_code :
     * social_icon :
     * type : 2
     * social_media_id : 42
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
        private String social_media_name;
        private String color_code;
        private String social_icon;
        private String type;

        public String getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(String social_media_id) {
            this.social_media_id = social_media_id;
        }

        private String social_media_id;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


    }
}
