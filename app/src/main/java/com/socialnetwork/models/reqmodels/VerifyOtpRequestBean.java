package com.socialnetwork.models.reqmodels;

/**
 * Created by sakshi on 1/9/16.
 */
public class VerifyOtpRequestBean {

    /**
     * user_id : 1
     * otp : 1234
     */

    private String user_id;
    private String otp;

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
}
