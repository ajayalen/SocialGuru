package com.socialnetwork.models.eventmodels;

/**
 * Created by sakshi on 31/8/16.
 */
public class AddPlatformEventBean {
    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    String mailText;
    String socialId;

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    String platformType;

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
}
