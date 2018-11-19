package com.socialnetwork.models.eventmodels;

/**
 * Created by sakshi on 14/9/16.
 */
public class DeletePlatformEventBean {
    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    String platformType;

    public Boolean getLinked() {
        return linked;
    }

    public void setLinked(Boolean linked) {
        this.linked = linked;
    }

    Boolean linked;

}
