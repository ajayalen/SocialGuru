package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 14/9/16.
 */
public class AddContactDetailResBean {

    /**
     * Success : true
     * Message : Contact account added successfully
     * Result : {"user_id":"86","social_media_id":"17","other_accounts":[{"phone_number":"dfdfgf","phone_tag":"fvd"}]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 86
     * social_media_id : 17
     * other_accounts : [{"phone_number":"dfdfgf","phone_tag":"fvd"}]
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
        private String social_media_id;
        /**
         * phone_number : dfdfgf
         * phone_tag : fvd
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
            private String phone_number;
            private String phone_tag;

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getPhone_tag() {
                return phone_tag;
            }

            public void setPhone_tag(String phone_tag) {
                this.phone_tag = phone_tag;
            }
        }
    }
}
