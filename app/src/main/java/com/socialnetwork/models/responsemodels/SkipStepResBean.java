package com.socialnetwork.models.responsemodels;

/**
 * Created by sakshi on 2/9/16.
 */
public class SkipStepResBean {

    /**
     * Success : true
     * Message : User skipped step successfully
     * Result : {"user_id":"9","step_number":4}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 9
     * step_number : 4
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
        private int step_number;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getStep_number() {
            return step_number;
        }

        public void setStep_number(int step_number) {
            this.step_number = step_number;
        }
    }
}
