package com.socialnetwork.models.reqmodels;

/**
 * Created by sakshi on 2/9/16.
 */
public class AddCustomPlatformReqBean {


    /**
     * user_id : 1
     * new_platform_name : wewser
     * color_code : #fghgf
     * type : 1/2/3
     */

    private String user_id;
    private String new_platform_name;
    private String color_code;
    private String type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
