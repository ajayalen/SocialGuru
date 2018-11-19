package com.socialnetwork.models.responsemodels;

/**
 * Created by anand on 14/9/16.
 */
public class DeleteSocialPlatformReq {

    /**
     * user_id : 1
     * social_media_id : 1
     * type : 1/2/3
     * is_linked : 0/1
     */

    private String user_id;
    private String social_media_id;
    private String type;
    private String is_linked;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_linked() {
        return is_linked;
    }

    public void setIs_linked(String is_linked) {
        this.is_linked = is_linked;
    }
}
