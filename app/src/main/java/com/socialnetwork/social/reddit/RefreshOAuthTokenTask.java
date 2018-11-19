package com.socialnetwork.social.reddit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.socialnetwork.activities.LinkedSocialProfileActivity;
import com.socialnetwork.activities.SocialPlatformsActivity;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.Flickr;
import com.socialnetwork.social.flickr.FlickrHelper;
import com.socialnetwork.social.flickr.oauth.OAuthInterface;
import com.socialnetwork.social.instagram.Instagram;
import com.socialnetwork.social.instagram.InstagramHelper;
import com.socialnetwork.social.linkedin.Linkedin;
import com.socialnetwork.social.linkedin.LinkedinHelper;
import com.socialnetwork.social.tumblr.Tumblr;
import com.socialnetwork.social.tumblr.TumblrHelper;
import com.socialnetwork.social.twitter.Twitter;
import com.socialnetwork.social.twitter.TwitterHelper;

public class RefreshOAuthTokenTask extends AsyncTask<String, Integer, Object> {

    private final int mMode;
    private Context activity;
    private ProgressDialog mProgressDialog;

    public RefreshOAuthTokenTask(Context context, int mode) {
        this.activity = context;
        this.mMode = mode;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = ProgressDialog.show(activity, "", "Loading user information..."); //$NON-NLS-1$ //$NON-NLS-2$
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dlg)
            {
                RefreshOAuthTokenTask.this.cancel(true);
            }
        });

    }

    @Override
    protected Object doInBackground(String... params) {
        String oauthToken = params[0];
        String oauthTokenSecret = params[1];
        String verifier = params[2];

        switch (mMode) {


            case SocialConstants.REDDIT:
                Reddit red = RedditHelper.getInstance().getReddit();
                com.socialnetwork.social.reddit.oauth.OAuthInterface reddOuth = red.getOAuthInterface();
                try {
                    return reddOuth.refreshAccessToken(verifier);
                } catch (Exception e) {
                    //			logger.error(e.getLocalizedMessage(), e);
                    return null;
                }

        }

        return null;

    }

    @Override
    protected void onPostExecute(Object result) {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        if (activity != null) {
            ((LinkedSocialProfileActivity) activity).onRefreshToken(result, mMode);
        }
    }

}
