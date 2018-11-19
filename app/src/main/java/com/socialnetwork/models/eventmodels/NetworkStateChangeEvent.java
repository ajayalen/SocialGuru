package com.socialnetwork.models.eventmodels;

/**
 * Created by Rahil on 11/16/2015.
 */
public class NetworkStateChangeEvent {
    private boolean isNetworkAvailable;

    public NetworkStateChangeEvent(boolean isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
}
