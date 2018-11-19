package com.socialnetwork.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.socialnetwork.R;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.reddit.RedditWrapper;

public class SocialWebView extends BaseActivity {

    private WebView mWebView;
    private ProgressDialog mSpinner;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initializeUi() {
        mWebView = (WebView) findViewById(R.id.social_web);
        setTitleText(mTitleTextView, getIntent().getIntExtra("mode", 0));

        mMode = getIntent().getIntExtra("mode", 0);

        mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");

        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new OAuthWebViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.clearCache(true);

        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.loadUrl(getIntent().getStringExtra("url"));



    }

    private void setTitleText(TextView mTitleTextView, int mode) {
        String[] titleArray = mTitleTextView.getContext().getResources().getStringArray(R.array.social_array);
        try {
            mTitleTextView.setText(titleArray[mode - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "Redirecting URL " + url);

            switch (mMode) {
                case SocialConstants.FLICKR:
                    if (url.startsWith(SocialConstants.FlickrConstants.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                case SocialConstants.TWITTER:
                    if (url.startsWith(SocialConstants.TwitterConstant.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                case SocialConstants.TUMBLR:
                    if (url.startsWith(SocialConstants.TumblrConstant.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                case SocialConstants.INSTAGRAM:
                    if (url.startsWith(SocialConstants.InstagramConstant.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                case SocialConstants.LINKEDIN:
                    if (url.startsWith(SocialConstants.LinkedinConstant.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                case SocialConstants.REDDIT:
                    if (url.startsWith(SocialConstants.RedditConstants.CALLBACK_SCHEME)) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }

                    //                pdk4854343444341992090://?access_token=AfmjyolznMrcdta8yFKmxwL8QE7FFHBvEjqr8vFDXhuxQ2BBkAAAAAA
                case SocialConstants.PINTEREST:
                    if (url.startsWith("pdk4854343444341992090")) {
                        Intent in = new Intent();
                        in.putExtra("scheme", url);
                        in.putExtra("mode", mMode);
                        setResult(RESULT_OK, in);
                        finish();
                        return true;
                    }
            }

            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d(TAG, "Page error: " + description);

            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading URL: " + url);

            if (mMode == SocialConstants.PINTEREST) {
                if (url.contains("facebook")) {
                    mWebView.stopLoading();
                    return;
                }
                if (url.contains("google")) {
                    mWebView.stopLoading();
                    return;
                }
                if(url.contains("https://accounts-oauth.pinterest.com/password/reset/"))
                {
                    mWebView.stopLoading();
                    return;
                }
            }

            super.onPageStarted(view, url, favicon);
            try {
                mSpinner.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = mWebView.getTitle();
            if (title != null && title.length() > 0) {
                //                mTitle.setText(title);
            }
            Log.d(TAG, "onPageFinished URL: " + url);
            try {
                mSpinner.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_social_web_view;
    }
}
