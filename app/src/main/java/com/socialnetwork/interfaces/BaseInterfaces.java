package com.socialnetwork.interfaces;

public interface BaseInterfaces {


    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
     void initializeUi();

    /**
     * Method to return the activities layout res id
     */
     int getLayoutId();
}
