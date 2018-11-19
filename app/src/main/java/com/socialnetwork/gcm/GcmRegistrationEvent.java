package com.socialnetwork.gcm;
public class GcmRegistrationEvent {

    private boolean isRegistered;
    private String gcmToken = "";

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public GcmRegistrationEvent(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }


}
