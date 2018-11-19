package com.socialnetwork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.socialnetwork.R;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;

import java.io.File;

public class Logout extends BaseActivity {

    private GoogleApiClient mGoogleApiClient;
    private int backpress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void doLogout(View v) {
        PrefUtils.logoutUser(this);


        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
        }


//        clearApplicationData();

        Utility.clearCookies(this);
        Intent in = new Intent(this, SplashActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
        finish();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    @Override
    public void initializeUi() {

        mBackImageView.setVisibility(View.GONE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder().requestProfile().requestId().requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mGoogleApiClient.connect();


        Intent in=new Intent(this,BusinessCardsActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_og;
    }


    @Override
    public void onBackPressed() {
        if (backpress == 1) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, getResources().getString(R.string.press_back_button_to_exit), Toast.LENGTH_SHORT).show();
            backpress = 1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // finish();
                    backpress = 0;
                }
            }, 1500);
        }
    }
}
