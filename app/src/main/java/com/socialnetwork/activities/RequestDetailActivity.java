package com.socialnetwork.activities;

import android.os.Bundle;

import com.socialnetwork.R;
import com.socialnetwork.base.BaseActivity;

public class RequestDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
    @Override
    public void initializeUi() {

    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_request_detail;
    }
}
