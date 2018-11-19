package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 14/9/16.
 */
public class AddMailDetailResBean {

    /**
     * Success : true
     * Message : Email account added successfully
     * Result : {"user_id":"86","social_media_id":"15","other_accounts":[{"email_id":"dsfgsdfg","username":"dfgsdffdg"}]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 86
     * social_media_id : 15
     * other_accounts : [{"email_id":"dsfgsdfg","username":"dfgsdffdg"}]
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
         * email_id : dsfgsdfg
         * username : dfgsdffdg
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
            private String email_id;
            private String username;

            public String getEmail_id() {
                return email_id;
            }

            public void setEmail_id(String email_id) {
                this.email_id = email_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
