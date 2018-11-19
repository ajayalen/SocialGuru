package com.socialnetwork.models.reqmodels;

/**
 * Created by sakshi on 1/9/16.
 */
public class RegisterLoginRequestBean {

    /**
     * device_type : 1/2
     * device_token : 5435454534534gfdfgdf
     * mobile : 234254325435
     * country_code : +91
     * push_certificate : 1/2
     */

    private String device_type;
    private String device_token;
    private String mobile;
    private String country_code;
    private String push_certificate;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPush_certificate() {
        return push_certificate;
    }

    public void setPush_certificate(String push_certificate) {
        this.push_certificate = push_certificate;
    }
}
