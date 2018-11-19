package com.socialnetwork.models.eventmodels;


public class RightDrawerToggleEvent {
    private boolean isDrawerOpened;

    public RightDrawerToggleEvent(boolean isDrawerOpened) {
        this.isDrawerOpened = isDrawerOpened;
    }

    public boolean isDrawerOpened() {
        return isDrawerOpened;
    }

    public void setIsDrawerOpened(boolean isDrawerOpened) {
        this.isDrawerOpened = isDrawerOpened;
    }
}
