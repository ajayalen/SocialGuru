package com.socialnetwork.models.responsemodels;

/**
 * Created by sakshi on 1/9/16.
 */
public class RegisterLoginResponseBean {

    /**
     * Success : true
     * Message : Data saved successfully
     * Result : {"user_id":"4","otp":"1278","step":"1","is_phone_verified":"1"}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 4
     * otp : 1278
     * step : 1
     * is_phone_verified : 1
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
        private String otp;
        private String step;
        private String is_phone_verified;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getIs_phone_verified() {
            return is_phone_verified;
        }

        public void setIs_phone_verified(String is_phone_verified) {
            this.is_phone_verified = is_phone_verified;
        }
    }
}
