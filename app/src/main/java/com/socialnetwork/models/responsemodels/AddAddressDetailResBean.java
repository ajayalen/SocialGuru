package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 14/9/16.
 */
public class AddAddressDetailResBean {

    /**
     * Success : true
     * Message : Address saved successfully
     * Result : {"user_id":"86","other_accounts":[]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 86
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
        private List<?> other_accounts;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<?> getOther_accounts() {
            return other_accounts;
        }

        public void setOther_accounts(List<?> other_accounts) {
            this.other_accounts = other_accounts;
        }
    }
}
