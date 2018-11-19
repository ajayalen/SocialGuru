package com.socialnetwork.social;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.socialnetwork.activities.SocialPlatformsActivity;
import com.socialnetwork.activities.SocialWebView;
import com.socialnetwork.social.flickr.Flickr;
import com.socialnetwork.social.flickr.FlickrHelper;
import com.socialnetwork.social.flickr.auth.Permission;
import com.socialnetwork.social.flickr.oauth.OAuthToken;
import com.socialnetwork.social.instagram.Instagram;
import com.socialnetwork.social.instagram.InstagramHelper;
import com.socialnetwork.social.linkedin.Linkedin;
import com.socialnetwork.social.linkedin.LinkedinHelper;
import com.socialnetwork.social.reddit.Reddit;
import com.socialnetwork.social.reddit.RedditHelper;
import com.socialnetwork.social.reddit.RedditWrapper;
import com.socialnetwork.social.tumblr.Tumblr;
import com.socialnetwork.social.tumblr.TumblrHelper;
import com.socialnetwork.social.twitter.Twitter;
import com.socialnetwork.social.twitter.TwitterHelper;

public class OAuthTask extends AsyncTask<Void, Integer, String> {

    private int mMode;
    private static final Uri OAUTH_CALLBACK_URI_FLICKR = Uri.parse(SocialConstants.FlickrConstants.CALLBACK_SCHEME
            + "://oauth");
    private static final Uri OAUTH_CALLBACK_URI_TUMBLR = Uri.parse(SocialConstants.TumblrConstant.CALLBACK_SCHEME
            + "://oauth");
    private static final Uri OAUTH_CALLBACK_URI_TWITTER = Uri.parse(SocialConstants.TwitterConstant.CALLBACK_SCHEME
            + "://oauth");
    private static final Uri OAUTH_CALLBACK_URI_INSTAGRAM = Uri
            .parse(SocialConstants.InstagramConstant.CALLBACK_SCHEME);

    private static final Uri OAUTH_CALLBACK_URI_LINKEDIN = Uri
            .parse(SocialConstants.LinkedinConstant.CALLBACK_SCHEME);

    private static final Uri OAUTH_CALLBACK_URI_REDDIT = Uri
            .parse(SocialConstants.RedditConstants.CALLBACK_SCHEME);
    /**
     * The context.
     */
    private Context mContext;

    /**
     * The progress dialog before going to the browser.
     */
    private ProgressDialog mProgressDialog;

    /**
     * Constructor.
     *
     * @param context
     */
    public OAuthTask(Context context, int mode) {
        super();
        this.mContext = context;
        mMode = mode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "", "Generating the authorization request..."); //$NON-NLS-1$ //$NON-NLS-2$
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                OAuthTask.this.cancel(true);
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(Void... params) {

        switch (mMode) {
            case SocialConstants.REDDIT:
                try {
                    Reddit ins = RedditHelper.getInstance().getReddit();
                    URL oauthUrl = ins.getOAuthInterface().buildAuthenticationUrl(
                            OAUTH_CALLBACK_URI_REDDIT.toString());
                    return oauthUrl.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

            case SocialConstants.FLICKR:

                try {
                    Flickr f = FlickrHelper.getInstance().getFlickr();
                    OAuthToken oauthToken = f.getOAuthInterface().getRequestToken(OAUTH_CALLBACK_URI_FLICKR.toString());
                    saveTokenSecrent(oauthToken.getOauthTokenSecret(), mMode);
                    URL oauthUrl = f.getOAuthInterface().buildAuthenticationUrl(Permission.DELETE, oauthToken);
                    return oauthUrl.toString();
                } catch (Exception e) {
                    //			logger.error("Error to oauth", e); //$NON-NLS-1$
                    return "error:" + e.getMessage(); //$NON-NLS-1$
                }

            case SocialConstants.TUMBLR:

                try {
                    Tumblr t = TumblrHelper.getInstance().getTumblr();
                    com.socialnetwork.social.tumblr.oauth.OAuthToken oauthToken = t.getOAuthInterface().getRequestToken(
                            OAUTH_CALLBACK_URI_TUMBLR.toString());
                    saveTokenSecrent(oauthToken.getOauthTokenSecret(), mMode);
                    URL oauthUrl = t.getOAuthInterface().buildAuthenticationUrl(oauthToken);
                    return oauthUrl.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case SocialConstants.TWITTER:

                try {
                    Twitter t = TwitterHelper.getInstance().getTwitter();
                    com.socialnetwork.social.twitter.oauth.OAuthToken oauthToken = t.getOAuthInterface().getRequestToken(
                            OAUTH_CALLBACK_URI_TWITTER.toString());
                    saveTokenSecrent(oauthToken.getOauthTokenSecret(), mMode);
                    URL oauthUrl = t.getOAuthInterface().buildAuthenticationUrl(oauthToken);
                    return oauthUrl.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case SocialConstants.INSTAGRAM:

                try {
                    Instagram ins = InstagramHelper.getInstance().getInstagram();
                    URL oauthUrl = ins.getOAuthInterface().buildAuthenticationUrl(
                            OAUTH_CALLBACK_URI_INSTAGRAM.toString());
                    return oauthUrl.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case SocialConstants.LINKEDIN:

                try {
                    Linkedin ins = LinkedinHelper.getInstance().getLinkedin();
                    URL oauthUrl = ins.getOAuthInterface().buildAuthenticationUrl(
                            OAUTH_CALLBACK_URI_LINKEDIN.toString());
                    return oauthUrl.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


        }

        return null;

    }

    /**
     * Saves the oauth token secrent.
     *
     * @param tokenSecret
     */
    private void saveTokenSecrent(String tokenSecret, int mode) {
        //		logger.debug("request token: " + tokenSecret); //$NON-NLS-1$
        SocialPlatformsActivity act = (SocialPlatformsActivity) mContext;
        act.saveOAuthToken(null, null, null, tokenSecret, mode);
        //		logger.debug("oauth token secret saved: {}", tokenSecret); //$NON-NLS-1$
    }

    @Override
    protected void onPostExecute(String result) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        if (result != null && !result.startsWith("error")) { //$NON-NLS-1$
            //			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
            //					.parse(result)));

            Intent in = new Intent(mContext, SocialWebView.class);
            in.putExtra("url", result);
            in.putExtra("mode", mMode);
            ((SocialPlatformsActivity) mContext).startActivityForResult(in, 1000);

        } else {
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        }
    }

}
