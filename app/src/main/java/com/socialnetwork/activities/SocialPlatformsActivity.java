package com.socialnetwork.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.fragments.AlertFragment;
import com.socialnetwork.models.eventmodels.AddPlatformDetailEventBean;
import com.socialnetwork.models.eventmodels.AddPlatformEventBean;
import com.socialnetwork.models.eventmodels.DeletePlatformEventBean;
import com.socialnetwork.models.reqmodels.GeneralReqBean;
import com.socialnetwork.models.responsemodels.GetSocialPlatformsResBean;
import com.socialnetwork.models.responsemodels.SkipStepResBean;
import com.socialnetwork.models.responsemodels.SocialMediaInfoBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.social.OAuthTask;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.FlickrWrapper;
import com.socialnetwork.social.flickr.oauth.OAuth;
import com.socialnetwork.social.flickr.oauth.OAuthToken;
import com.socialnetwork.social.flickr.people.User;
import com.socialnetwork.social.flickr.tasks.GetOAuthTokenTask;
import com.socialnetwork.social.instagram.InstagramWrapper;
import com.socialnetwork.social.linkedin.LinkedinWrapper;
import com.socialnetwork.social.pinterest.PDKCallback;
import com.socialnetwork.social.pinterest.PDKClient;
import com.socialnetwork.social.pinterest.PDKException;
import com.socialnetwork.social.pinterest.PDKResponse;
import com.socialnetwork.social.reddit.RedditWrapper;
import com.socialnetwork.social.tumblr.TumblrWrapper;
import com.socialnetwork.social.twitter.TwitterWrapper;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

@SuppressWarnings("ALL")
public class SocialPlatformsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SocialPlatformsActivity.class.getSimpleName();

    private LinearLayout mAddMoreSocialLl;

    //pinterest
    private PDKClient pdkClient;
    private static final String appID = AppConstants.PINTEREST_APP_ID;
    private View mMainContainer;
    private GetSocialPlatformsResBean mResponse;
    private TextView mSkypeTV, mQuoraTV, mVineTV, mSnapchatTV;
    private LinearLayout mFbLinearLayout, mPinLinearLayout, mTwitterLinearLayout, mLinkedInLinearLayout,
            mGoogleLinearLayout, mTumblrLinearLayout, mInstaLinearLayout, mYoutubeLinearLayout, mFlickrLinearLayout,
            mRedditLinearLayout;
    private CallbackManager callbackManager;
    private List<String> READ_PERMISSION = Arrays.asList("email", "user_friends", "public_profile", "user_photos",
            "user_likes", "user_birthday");
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private int backpress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        pdkClient = PDKClient.configureInstance(this, appID);
        pdkClient.onConnect(this);
        pdkClient.setDebugMode(true);
    }

    @Override
    public void initializeUi() {
        callbackManager = CallbackManager.Factory.create();
        mTitleTextView.setText(getString(R.string.user_details_label));
        mAddMoreImageView.setVisibility(View.VISIBLE);
        mAddMoreSocialLl = (LinearLayout) findViewById(R.id.add_social_platform_ll);
        mAddMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = AlertFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.CUSTOM_DIALOG_FLAG, AppConstants.ADD_VIEW_DIALOG);
                bundle.putString(AppConstants.DIALOG_IMAGE, getString(R.string.social_label));
                // set Fragment class Arguments
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "dialog");
            }
        });

        mFbLinearLayout = (LinearLayout) findViewById(R.id.fb_frame);
        mPinLinearLayout = (LinearLayout) findViewById(R.id.pinterest_frame);
        mRedditLinearLayout = (LinearLayout) findViewById(R.id.reddit_frame);
        mTumblrLinearLayout = (LinearLayout) findViewById(R.id.tumblr_frame);
        mFlickrLinearLayout = (LinearLayout) findViewById(R.id.flickr_frame);
        mTwitterLinearLayout = (LinearLayout) findViewById(R.id.twitter_frame);
        mInstaLinearLayout = (LinearLayout) findViewById(R.id.instagram_frame);
        mLinkedInLinearLayout = (LinearLayout) findViewById(R.id.linkedin_frame);
        mGoogleLinearLayout = (LinearLayout) findViewById(R.id.google_frame);
        mYoutubeLinearLayout = (LinearLayout) findViewById(R.id.youtube_frame);
        mFbLinearLayout.setOnClickListener(this);
        mPinLinearLayout.setOnClickListener(this);
        mRedditLinearLayout.setOnClickListener(this);
        mTumblrLinearLayout.setOnClickListener(this);
        mFlickrLinearLayout.setOnClickListener(this);
        mTwitterLinearLayout.setOnClickListener(this);
        mInstaLinearLayout.setOnClickListener(this);
        mLinkedInLinearLayout.setOnClickListener(this);
        mGoogleLinearLayout.setOnClickListener(this);
        mYoutubeLinearLayout.setOnClickListener(this);
        findViewById(R.id.social_skip_btn).setOnClickListener(this);

        findViewById(R.id.social_next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_EMAIL);
                startActivity(new Intent(SocialPlatformsActivity.this, UserMailDetailActivity.class));
            }
        });
        mSkypeTV = (TextView) findViewById(R.id.activity_skype_platform_tv);
        mQuoraTV = (TextView) findViewById(R.id.activity_quora_platform_tv);
        mVineTV = (TextView) findViewById(R.id.activity_vine_platform_tv);
        mSnapchatTV = (TextView) findViewById(R.id.activity_snapchat_tv);
        mMainContainer = (View) findViewById(R.id.main_container);
        mSkypeTV.setOnClickListener(this);
        mQuoraTV.setOnClickListener(this);
        mVineTV.setOnClickListener(this);
        mSnapchatTV.setOnClickListener(this);
        getAllSocialPlatforms();
        initGoogle();

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initGoogle() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder().requestProfile().requestId().requestEmail().build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Lg.d(TAG, connectionResult.getErrorMessage());
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    //method to call the api to get the all social platforma
    private void getAllSocialPlatforms() {
        mAddMoreSocialLl.removeAllViews();
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<GetSocialPlatformsResBean> call = AppRetrofit.getInstance().getApiServices()
                .geSocialPlatforms(getSocialPlatformsApiParams());
        AppUtils.enqueueCall(call, new Callback<GetSocialPlatformsResBean>() {
            @Override
            public void onResponse(Response<GetSocialPlatformsResBean> response, Retrofit retrofit) {
                mResponse = response.body();
                if (mResponse == null)
                    return;
                if (mResponse.isSuccess()) {
                    setPlatformState();
                    setAddedPlatforms();
                    //  startActivity(new Intent(UserMailDetailActivity.this, UserContactsDetailActivity.class));
                } else if (!mResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the request params for the api to get the social platforms
    private GeneralReqBean getSocialPlatformsApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        return reqBean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_social_platforms;
    }

    @Override
    public void onClick(View v) {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        switch (v.getId()) {
            case R.id.fb_frame:
                signUpFromFacebook();
                break;

            case R.id.flickr_frame:
                startConnectionToFlickr();
                break;

            case R.id.reddit_frame:
                startConnectionToReddit();
                break;
            case R.id.pinterest_frame:
                onLogin();
                break;

            case R.id.tumblr_frame:
                startConnectionToTumblr();
                break;

            case R.id.twitter_frame:
                startConnectionToTwitter();
                break;

            case R.id.instagram_frame:
                startConnectionToInstagram();
                break;

            case R.id.linkedin_frame:
                startConnectionToLinkedin();
                break;

            case R.id.google_frame:
                startConnectionToGoogle();
                break;

            case R.id.youtube_frame:
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
                break;

            case R.id.activity_skype_platform_tv:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent intent = new Intent(mThis, LinkedSocialProfileActivity.class);
                intent.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, false);
                intent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(10).getSocial_media_id());
                intent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.skype_txt));
                startActivity(intent);
                break;
            case R.id.activity_quora_platform_tv:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent quoraIntent = new Intent(mThis, LinkedSocialProfileActivity.class);
                quoraIntent.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, false);
                quoraIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(12).getSocial_media_id());
                quoraIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.quora_txt));
                startActivity(quoraIntent);
                break;
            case R.id.activity_vine_platform_tv:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent vineIntent = new Intent(mThis, LinkedSocialProfileActivity.class);
                vineIntent.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, false);
                vineIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(11).getSocial_media_id());
                vineIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.vine_txt));
                startActivity(vineIntent);
                break;
            case R.id.activity_snapchat_tv:

                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent intnt = new Intent(mThis, LinkedSocialProfileActivity.class);
                intnt.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, false);
                intnt.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(13).getSocial_media_id());
                intnt.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.snapchat_txt));
                startActivity(intnt);
                break;
            case R.id.social_skip_btn:
                callSkipStepApi();
                break;

        }
    }

    private void startConnectionToGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //method to call the skip step api
    private void callSkipStepApi() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<SkipStepResBean> call = AppRetrofit.getInstance().getApiServices().skipstep(skipStepApiParams());
        AppUtils.enqueueCall(call, new Callback<SkipStepResBean>() {
            public SkipStepResBean mResponse;

            @Override
            public void onResponse(Response<SkipStepResBean> response, Retrofit retrofit) {
                mResponse = response.body();
                if (mResponse.isSuccess()) {
                    PrefUtils.setSharedPrefStringData(SocialPlatformsActivity.this, PrefConstants.SCREEN_STEP,
                            mResponse.getResult().getStep_number() + "");
                    startActivity(new Intent(mThis, UserMailDetailActivity.class));
                    //					finish();
                } else if (!mResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the api params for the skip step api
    private GeneralReqBean skipStepApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setStep(AppConstants.STEP_SOCIAL);
        return reqBean;
    }

    private void startConnectionToLinkedin() {

        com.socialnetwork.social.linkedin.oauth.OAuth oauth = LinkedinWrapper.getInstance(getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getLinkedinOAuthToken();
        if (oauth == null || oauth.getToken() == null || oauth.getToken().getOauthToken() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.LINKEDIN);
            task.execute();
        } else {
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.LINKEDIN);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.linked_in));
            startActivity(in);
        }
    }

    private void startConnectionToInstagram() {
        com.socialnetwork.social.instagram.oauth.OAuth oauth = InstagramWrapper.getInstance(getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getInstagramOAuthToken();
        if (oauth == null || oauth.getToken() == null || oauth.getToken().getOauthToken() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.INSTAGRAM);
            task.execute();
        } else {
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.INSTAGRAM);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.insta_label));
            startActivity(in);
        }
    }

    private void startConnectionToTumblr() {

        com.socialnetwork.social.tumblr.oauth.OAuth oauth = TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getTumblrOAuthToken();
        if (oauth == null || oauth.getToken() == null || oauth.getToken().getOauthToken() == null
                || oauth.getToken().getOauthTokenSecret() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.TUMBLR);
            task.execute();
        } else {

            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.TUMBLR);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.tum_label));
            startActivity(in);
        }
    }

    private void startConnectionToTwitter() {
        com.socialnetwork.social.twitter.oauth.OAuth oauth = TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getTwitterOAuthToken();
        if (oauth == null || oauth.getToken() == null || oauth.getToken().getOauthToken() == null
                || oauth.getToken().getOauthTokenSecret() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.TWITTER);
            task.execute();
        } else {
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.TWITTER);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.twitter_label));
            startActivity(in);
        }
    }

    private void startConnectionToReddit() {

        com.socialnetwork.social.reddit.oauth.OAuth oauth = RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getRedditOAuthToken();
        if (oauth == null || oauth.getToken() == null || oauth.getToken().getOauthToken() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.REDDIT);
            task.execute();
        } else {
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.REDDIT);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.reddit_label));
            startActivity(in);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        if (resultCode == RESULT_OK && requestCode == 1000) {

            int mode = in.getIntExtra("mode", 0);

            switch (mode) {
                case SocialConstants.FLICKR: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).getScheme();
                    OAuth savedToken = FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                            Context.MODE_PRIVATE)).getFlickrOAuthToken();
                    if (SocialConstants.FlickrConstants.CALLBACK_SCHEME.equals(scheme)
                            && (savedToken == null || savedToken.getUser() == null)) {
                        Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                        String query = uri.getQuery();
                        //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                        String[] data = query.split("&"); //$NON-NLS-1$
                        if (data != null && data.length == 2) {
                            String oauthToken = data[0].substring(data[0].indexOf("=") + 1); //$NON-NLS-1$
                            String oauthVerifier = data[1].substring(data[1].indexOf("=") + 1); //$NON-NLS-1$
                            //                logger.debug("OAuth Token: {}; OAuth Verifier: {}", oauthToken, oauthVerifier); //$NON-NLS-1$

                            OAuth oauth = FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                                    Context.MODE_PRIVATE)).getFlickrOAuthToken();
                            if (oauth != null && oauth.getToken() != null
                                    && oauth.getToken().getOauthTokenSecret() != null) {
                                GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                                task.execute(oauthToken, oauth.getToken().getOauthTokenSecret(), oauthVerifier);
                            }
                        }
                    }
                }
                break;

                case SocialConstants.TUMBLR: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).getScheme();
                    com.socialnetwork.social.tumblr.oauth.OAuth savedToken = TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                            Context.MODE_PRIVATE))
                            .getTumblrOAuthToken();
                    if (SocialConstants.TumblrConstant.CALLBACK_SCHEME.equals(scheme)
                            && (savedToken == null || savedToken.getUser() == null)) {
                        Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                        String query = uri.getQuery();
                        //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                        String[] data = query.split("&"); //$NON-NLS-1$
                        if (data != null && data.length == 2) {
                            String oauthToken = data[0].substring(data[0].indexOf("=") + 1); //$NON-NLS-1$
                            String oauthVerifier = data[1].substring(data[1].indexOf("=") + 1); //$NON-NLS-1$
                            //                logger.debug("OAuth Token: {}; OAuth Verifier: {}", oauthToken, oauthVerifier); //$NON-NLS-1$

                            com.socialnetwork.social.tumblr.oauth.OAuth oauth = TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                                    Context.MODE_PRIVATE))
                                    .getTumblrOAuthToken();
                            if (oauth != null && oauth.getToken() != null
                                    && oauth.getToken().getOauthTokenSecret() != null) {
                                GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                                task.execute(oauthToken, oauth.getToken().getOauthTokenSecret(), oauthVerifier);
                            }
                        }
                    }
                }
                break;

                case SocialConstants.TWITTER: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).getScheme();
                    com.socialnetwork.social.twitter.oauth.OAuth savedToken = TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                            Context.MODE_PRIVATE))
                            .getTwitterOAuthToken();
                    if (SocialConstants.TwitterConstant.CALLBACK_SCHEME.equals(scheme)
                            && (savedToken == null || savedToken.getUser() == null)) {
                        Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                        String query = uri.getQuery();
                        //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                        String[] data = query.split("&"); //$NON-NLS-1$
                        if (data != null && data.length == 2) {
                            String oauthToken = data[0].substring(data[0].indexOf("=") + 1); //$NON-NLS-1$
                            String oauthVerifier = data[1].substring(data[1].indexOf("=") + 1); //$NON-NLS-1$
                            //                logger.debug("OAuth Token: {}; OAuth Verifier: {}", oauthToken, oauthVerifier); //$NON-NLS-1$

                            com.socialnetwork.social.twitter.oauth.OAuth oauth = TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                                    Context.MODE_PRIVATE))
                                    .getTwitterOAuthToken();
                            if (oauth != null && oauth.getToken() != null
                                    && oauth.getToken().getOauthTokenSecret() != null) {
                                GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                                task.execute(oauthToken, oauth.getToken().getOauthTokenSecret(), oauthVerifier);
                            }
                        }
                    }
                }
                break;

                case SocialConstants.INSTAGRAM: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).toString();
                    com.socialnetwork.social.instagram.oauth.OAuth savedToken = InstagramWrapper.getInstance(getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                            Context.MODE_PRIVATE))
                            .getInstagramOAuthToken();
                    Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                    String query = uri.getQuery();
                    //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                    String[] data = query.split("&");
                    if (data != null && data.length == 1) {
                        String code = data[0].substring(data[0].indexOf("=") + 1);

                        GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                        task.execute("", "", code);
                    }
                }

                break;

                case SocialConstants.LINKEDIN: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).toString();
                    com.socialnetwork.social.linkedin.oauth.OAuth savedToken = LinkedinWrapper.getInstance(getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                            Context.MODE_PRIVATE))
                            .getLinkedinOAuthToken();
                    Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                    String query = uri.getQuery();
                    //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                    String[] data = query.split("&");
                    if (data != null && data.length == 2) {
                        String code = data[0].substring(data[0].indexOf("=") + 1);

                        GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                        task.execute("", "", code);
                    }
                }

                break;

                case SocialConstants.REDDIT: {
                    String scheme = Uri.parse(in.getExtras().getString("scheme")).toString();
                    com.socialnetwork.social.reddit.oauth.OAuth savedToken = RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                            Context.MODE_PRIVATE))
                            .getRedditOAuthToken();
                    Uri uri = Uri.parse(in.getExtras().getString("scheme"));
                    String query = uri.getQuery();
                    //            logger.debug("Returned Query: {}", query); //$NON-NLS-1$
                    String[] data = query.split("&");
                    if (data != null && data.length == 2) {
                        String code = data[1].substring(data[1].indexOf("=") + 1);

                        GetOAuthTokenTask task = new GetOAuthTokenTask(this, mode);
                        task.execute("", "", code);
                    }
                }

                break;
                case SocialConstants.PINTEREST:
                    pdkClient.onOauthResponse(requestCode, resultCode, in);
                    break;
                //                case SocialConstants.FB:
                //                    callbackManager.onActivityResult(requestCode, resultCode, in);
                //                    break;

            }

        } else {

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(in);
                handleSignInResult(result);
                return;
            }

            try {
                callbackManager.onActivityResult(requestCode, resultCode, in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.GOOGLE);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.gplus_label));
            startActivity(in);
        }

    }

    private void startConnectionToFlickr() {

        OAuth oauth = FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getFlickrOAuthToken();
        if (oauth == null || oauth.getUser() == null) {
            OAuthTask task = new OAuthTask(this, SocialConstants.FLICKR);
            task.execute();
        } else {
            //            load(oauth);
            //user already logged in
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.FLICKR);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.flickr_label));
            startActivity(in);
        }
    }

    public void saveOAuthToken(String userName, String userId, String token, String tokenSecret, int mode) {
        //        logger.debug("Saving userName=%s, userId=%s, oauth token={}, and token secret={}", new String[]{userName, userId, token, tokenSecret}); //$NON-NLS-1$

        if (mode == SocialConstants.FLICKR) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.FlickrConstants.KEY_OAUTH_TOKEN, token);
            editor.putString(SocialConstants.FlickrConstants.KEY_TOKEN_SECRET, tokenSecret);
            editor.putString(SocialConstants.FlickrConstants.KEY_USER_NAME, userName);
            editor.putString(SocialConstants.FlickrConstants.KEY_USER_ID, userId);
            editor.commit();
        } else if (mode == SocialConstants.TUMBLR) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.TumblrConstant.KEY_OAUTH_TOKEN, token);
            editor.putString(SocialConstants.TumblrConstant.KEY_TOKEN_SECRET, tokenSecret);
            editor.putString(SocialConstants.TumblrConstant.KEY_USER_NAME, userName);
            editor.putString(SocialConstants.TumblrConstant.KEY_USER_ID, userId);
            editor.commit();
        } else if (mode == SocialConstants.TWITTER) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.TwitterConstant.KEY_OAUTH_TOKEN, token);
            editor.putString(SocialConstants.TwitterConstant.KEY_TOKEN_SECRET, tokenSecret);
            editor.putString(SocialConstants.TwitterConstant.KEY_USER_NAME, userName);
            editor.putString(SocialConstants.TwitterConstant.KEY_USER_ID, userId);
            editor.commit();
        } else if (mode == SocialConstants.INSTAGRAM) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.InstagramConstant.KEY_ACCESS_TOKEN, token);
            editor.commit();
        } else if (mode == SocialConstants.LINKEDIN) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.LinkedinConstant.KEY_ACCESS_TOKEN, token);
            editor.commit();
        } else if (mode == SocialConstants.REDDIT) {
            SharedPreferences sp = getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SocialConstants.RedditConstants.KEY_ACCESS_TOKEN, token);
            editor.commit();
        }
    }

    public void onOAuthDone(Object result, int mMode) {
        if (result == null) {
            Toast.makeText(this, "Authorization failed", //$NON-NLS-1$
                    Toast.LENGTH_LONG).show();
        } else {

            if (mMode == SocialConstants.FLICKR) {

                User user = ((OAuth) result).getUser();
                OAuthToken token = ((OAuth) result).getToken();
                if (user == null || user.getId() == null || token == null || token.getOauthToken() == null
                        || token.getOauthTokenSecret() == null) {
                    Toast.makeText(this, "Authorization failed", //$NON-NLS-1$
                            Toast.LENGTH_LONG).show();
                    return;
                }
                saveOAuthToken(user.getUsername(), user.getId(), token.getOauthToken(), token.getOauthTokenSecret(),
                        mMode);
            } else if (mMode == SocialConstants.TUMBLR) {
                com.socialnetwork.social.tumblr.User user = ((com.socialnetwork.social.tumblr.oauth.OAuth) result)
                        .getUser();
                com.socialnetwork.social.tumblr.oauth.OAuthToken token = ((com.socialnetwork.social.tumblr.oauth.OAuth) result)
                        .getToken();
                if (/*user == null || user.getId() == null ||*/token == null || token.getOauthToken() == null
                        || token.getOauthTokenSecret() == null) {
                    Toast.makeText(this, "Authorization failed", //$NON-NLS-1$
                            Toast.LENGTH_LONG).show();
                    return;
                }
                saveOAuthToken("", "", token.getOauthToken(), token.getOauthTokenSecret(), mMode);
            } else if (mMode == SocialConstants.TWITTER) {
                com.socialnetwork.social.twitter.user.User user = ((com.socialnetwork.social.twitter.oauth.OAuth) result)
                        .getUser();
                com.socialnetwork.social.twitter.oauth.OAuthToken token = ((com.socialnetwork.social.twitter.oauth.OAuth) result)
                        .getToken();
                if (/*user == null || user.getId() == null ||*/token == null || token.getOauthToken() == null
                        || token.getOauthTokenSecret() == null) {
                    Toast.makeText(this, "Authorization failed", //$NON-NLS-1$
                            Toast.LENGTH_LONG).show();
                    return;
                }
                saveOAuthToken(user.getScreen_name(), user.getId(), token.getOauthToken(),
                        token.getOauthTokenSecret(), mMode);
            } else if (mMode == SocialConstants.INSTAGRAM) {
                com.socialnetwork.social.instagram.oauth.OAuthToken token = ((com.socialnetwork.social.instagram.oauth.OAuth) result)
                        .getToken();
                saveOAuthToken("", "", token.getOauthToken(), "", mMode);
            } else if (mMode == SocialConstants.LINKEDIN) {
                com.socialnetwork.social.linkedin.oauth.OAuthToken token = ((com.socialnetwork.social.linkedin.oauth.OAuth) result)
                        .getToken();
                saveOAuthToken("", "", token.getOauthToken(), "", mMode);
            } else if (mMode == SocialConstants.REDDIT) {
                com.socialnetwork.social.reddit.oauth.OAuthToken token = ((com.socialnetwork.social.reddit.oauth.OAuth) result)
                        .getToken();
                saveOAuthTokenReddit(token);
            }

            manageSocialPlatformsColors();
        }
    }

    private void saveOAuthTokenReddit(com.socialnetwork.social.reddit.oauth.OAuthToken oauthToken) {

        SharedPreferences sp = getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SocialConstants.RedditConstants.KEY_ACCESS_TOKEN, oauthToken.getOauthToken());
        editor.putString(SocialConstants.RedditConstants.KEY_EXPIRES_IN, oauthToken.getExpiresIn());
        editor.putString(SocialConstants.RedditConstants.KEY_REFRESH_TOKEN, oauthToken.getRefreshToken());
        editor.putString(SocialConstants.RedditConstants.KEY_TOKEN_TYPE, oauthToken.getTokenType());
        editor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        manageSocialPlatformsColors();
    }


    //event to add the platform after api call
    public void onEvent(AddPlatformEventBean eventBean) {
        if (eventBean.getPlatformType().equalsIgnoreCase("1"))
            addMoreMail(eventBean.getMailText(), eventBean.getSocialId(), false);
    }


    //event to add the platform after api call
    public void onEvent(DeletePlatformEventBean eventBean) {
        getAllSocialPlatforms();
    }

    //method to add the more mail options
    private void addMoreMail(final String mailText, final String socialId, final boolean isPlatformAdded) {
        final View view = LayoutInflater.from(this).inflate(R.layout.mail_view, mAddMoreSocialLl, false);

        TextView mMailPlatformTv = (TextView) view.findViewById(R.id.add_mail_tv);
        final String text = mailText.substring(0, 1).toUpperCase() + mailText.substring(1);

        mMailPlatformTv.setText(text);
        mMailPlatformTv.setTextColor(ContextCompat.getColor(mThis, R.color.white));
        ImageView drawableView = (ImageView) view.findViewById(R.id.add_mail_icon_tv);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mThis, LinkedSocialProfileActivity.class);
                intent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.mail_txt));
                intent.putExtra(AppConstants.SOCIAL_MEDIA_ID, socialId);
                intent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, text);
                intent.putExtra("show_delete", true);
                startActivity(intent);
            }
        });

        mAddMoreSocialLl.addView(view);
        mAddMoreSocialLl.invalidate();

        Utility.setColoronAddedPlatforms(mAddMoreSocialLl.getChildCount(), drawableView,
                String.valueOf(mailText.charAt(0)).toUpperCase(), isPlatformAdded);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //pinterest
    private void onLogin() {
        List scopes = new ArrayList<String>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_RELATIONSHIPS);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_RELATIONSHIPS);

        pdkClient.login(this, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                onLoginSuccess();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }

    private void onLoginSuccess() {
//        Intent i = new Intent(this, LinkedSocialProfileActivity.class);
//        i.putExtra("mode", SocialConstants.PINTEREST);
//        i.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
//        i.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.pin_label));
//        startActivity(i);
    }

    //method to set the added mail platforms
    private void setAddedPlatforms() {

        if (mResponse == null || mResponse.getResult() == null)
            return;
        for (int i = 0; i < mResponse.getResult().size(); i++) {
            String platformName = mResponse.getResult().get(i).getNew_platform_name();
            if (!platformName.isEmpty())
                if (!platformName.equalsIgnoreCase(getString(R.string.skype_txt))
                        && !platformName.equalsIgnoreCase(getString(R.string.vine_txt))
                        && !platformName.equalsIgnoreCase(getString(R.string.quora_txt))
                        && !platformName.equalsIgnoreCase(getString(R.string.snapchat_txt))) {

                    if (!(Integer.parseInt(mResponse.getResult().get(i).getSocial_media_id()) > 0
                            && Integer.parseInt(mResponse.getResult().get(i).getSocial_media_id()) < 11))
                        addMoreMail(mResponse.getResult().get(i).getNew_platform_name(),
                                mResponse.getResult().get(i).getSocial_media_id(),
                                mResponse.getResult().get(i).getOther_accounts() != null
                                        && mResponse.getResult().get(i).getOther_accounts().size() > 0);
                }
        }
    }

    //method to set the platform state
    private void setPlatformState() {
        if (mResponse == null || mResponse.getResult() == null)
            return;

        if (mResponse.getResult().get(0).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mFbLinearLayout, ContextCompat.getColor(this, R.color.fb_selected));

        }
        if (mResponse.getResult().get(1).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mPinLinearLayout, ContextCompat.getColor(this, R.color.pin_selected));
        }
        if (mResponse.getResult().get(2).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mTwitterLinearLayout, ContextCompat.getColor(this, R.color.twitter_selected));
        }
        if (mResponse.getResult().get(3).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mLinkedInLinearLayout, ContextCompat.getColor(this, R.color.linkedin_selected));
        }
        if (mResponse.getResult().get(4).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mGoogleLinearLayout, ContextCompat.getColor(this, R.color.google_selected));
        }
        if (mResponse.getResult().get(5).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mTumblrLinearLayout, ContextCompat.getColor(this, R.color.tumblr_selected));
        }
        if (mResponse.getResult().get(6).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mInstaLinearLayout, ContextCompat.getColor(this, R.color.instagram_selected));
        }
        if (mResponse.getResult().get(7).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mYoutubeLinearLayout, ContextCompat.getColor(this, R.color.youtube_selected));
        }
        if (mResponse.getResult().get(8).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mFlickrLinearLayout, ContextCompat.getColor(this, R.color.flickr_selected));
        }
        if (mResponse.getResult().get(9).getOther_accounts().size() > 0) {
            Utility.changeDrawableColor(mRedditLinearLayout, ContextCompat.getColor(this, R.color.reddit_selected));
        }
        if (mResponse.getResult().get(10).getOther_accounts().size() > 0) {
            mSkypeTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.skype_selected, 0, 0);
        }
        if (mResponse.getResult().get(11).getOther_accounts().size() > 0) {
            mVineTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vine_selected, 0, 0);

        }
        if (mResponse.getResult().get(12).getOther_accounts().size() > 0) {
            mQuoraTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.quora_selected, 0, 0);

        }
        if (mResponse.getResult().get(13).getOther_accounts().size() > 0) {
            mSnapchatTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.snapchat_selected, 0, 0);

        }
    }

    //event to change the social icon when information is added
    public void onEvent(AddPlatformDetailEventBean eventBean) {
        if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.skype_txt))) {
            mSkypeTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.skype_selected, 0, 0);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.vine_txt))) {
            mVineTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vine_selected, 0, 0);

        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.quora_txt))) {
            mQuoraTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.quora_selected, 0, 0);

        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.snapchat_txt))) {
            mSnapchatTV.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.snapchat_selected, 0, 0);

        } else
            getAllSocialPlatforms();

    }

    private void signUpFromFacebook() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, loginResult.toString());
//				Intent in = new Intent(SocialPlatformsActivity.this, LinkedSocialProfileActivity.class);
//				in.putExtra("mode", SocialConstants.FB);
//				in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
//				in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.fb_label));
//				startActivity(in);
            }

            @Override
            public void onCancel() {
                // App code
                Log.e(TAG, "sdf".toString());
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG, exception.toString());
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null)
            loginManager.logInWithReadPermissions(this, READ_PERMISSION);
        else {
            Intent in = new Intent(this, LinkedSocialProfileActivity.class);
            in.putExtra("mode", SocialConstants.FB);
            in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
            in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.fb_label));
            startActivity(in);
        }
    }

    //method to move the fb data to next screenR
    private void moveDataToNextScreen(SocialMediaInfoBean socialMediaInfoBean) {
        //user already logged in
        Intent in = new Intent(this, LinkedSocialProfileActivity.class);
        in.putExtra("mode", SocialConstants.FB);
        in.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
        in.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.fb_label));
        in.putExtra(AppConstants.LINKED_PLATFORM_USERNAME, socialMediaInfoBean.getName());
        in.putExtra(AppConstants.LINKED_PLATFORM_ID, socialMediaInfoBean.getId());
        in.putExtra(AppConstants.LINKED_PLATFORM_PROFILE, socialMediaInfoBean.getPicture().getData().getUrl());
        startActivity(in);
    }

    private void manageSocialPlatformsColors() {

        //facebook
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null)
            Utility.changeDrawableColor(mFbLinearLayout, ContextCompat.getColor(this, R.color.fb_selected));
        else {

            mFbLinearLayout.setBackgroundDrawable(null);
            mFbLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }

        //flickr
        OAuth oauth = FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getFlickrOAuthToken();
        if (!(oauth == null || oauth.getUser() == null))
            Utility.changeDrawableColor(mFlickrLinearLayout, ContextCompat.getColor(this, R.color.flickr_selected));
        else {
            mFlickrLinearLayout.setBackgroundDrawable(null);
            mFlickrLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        //reddit
        com.socialnetwork.social.reddit.oauth.OAuth oauthRed = RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getRedditOAuthToken();
        if (!(oauthRed == null || oauthRed.getToken() == null || oauthRed.getToken().getOauthToken() == null))
            Utility.changeDrawableColor(mRedditLinearLayout, ContextCompat.getColor(this, R.color.reddit_selected));
        else {
            mRedditLinearLayout.setBackgroundDrawable(null);
            mRedditLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        //pinterest
        String tokenString = getSharedPreferences("com.pinterest.android.pdk.PREF_FILE_KEY", Context.MODE_PRIVATE)
                .getString("PDK_SHARED_PREF_TOKEN_KEY", null);
        if (tokenString != null)
            Utility.changeDrawableColor(mPinLinearLayout, ContextCompat.getColor(this, R.color.pin_selected));
        else {
            mPinLinearLayout.setBackgroundDrawable(null);
            mPinLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        //tumblr
        com.socialnetwork.social.tumblr.oauth.OAuth oauthTumblr = TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getTumblrOAuthToken();
        if (!(oauthTumblr == null || oauthTumblr.getToken() == null || oauthTumblr.getToken().getOauthToken() == null
                || oauthTumblr.getToken().getOauthTokenSecret() == null))
            Utility.changeDrawableColor(mTumblrLinearLayout, ContextCompat.getColor(this, R.color.tumblr_selected));
        else {
            mTumblrLinearLayout.setBackgroundDrawable(null);
            mTumblrLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        //twitter
        com.socialnetwork.social.twitter.oauth.OAuth oauthTwitter = TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                Context.MODE_PRIVATE))
                .getTwitterOAuthToken();
        if (!(oauthTwitter == null || oauthTwitter.getToken() == null || oauthTwitter.getToken().getOauthToken() == null
                || oauthTwitter.getToken().getOauthTokenSecret() == null))
            Utility.changeDrawableColor(mTwitterLinearLayout, ContextCompat.getColor(this, R.color.twitter_selected));
        else {
            mTwitterLinearLayout.setBackgroundDrawable(null);
            mTwitterLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        //instagram
        com.socialnetwork.social.instagram.oauth.OAuth oauthInst = InstagramWrapper.getInstance(getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                Context.MODE_PRIVATE))
                .getInstagramOAuthToken();
        if (!(oauthInst == null || oauthInst.getToken() == null || oauthInst.getToken().getOauthToken() == null))
            Utility.changeDrawableColor(mInstaLinearLayout, ContextCompat.getColor(this, R.color.instagram_selected));
        else {
            mInstaLinearLayout.setBackgroundDrawable(null);
            mInstaLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        com.socialnetwork.social.linkedin.oauth.OAuth oauthLink = LinkedinWrapper.getInstance(getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                Context.MODE_PRIVATE))
                .getLinkedinOAuthToken();
        if (!(oauthLink == null || oauthLink.getToken() == null || oauthLink.getToken().getOauthToken() == null))
            Utility.changeDrawableColor(mLinkedInLinearLayout, ContextCompat.getColor(this, R.color.linkedin_selected));
        else {
            mLinkedInLinearLayout.setBackgroundDrawable(null);
            mLinkedInLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);


        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done" and the GoogleSignInResult will be available instantly.
            Log.d("TAG", "Got cached sign-in");

            GoogleSignInResult result = opr.get();
            if (result.getStatus().getStatusCode() == 0) {
                Utility.changeDrawableColor(mGoogleLinearLayout, ContextCompat.getColor(this, R.color.google_selected));
            } else if (!mGoogleApiClient.isConnected()) {
                mGoogleLinearLayout.setBackgroundDrawable(null);
                mGoogleLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
            }
        } else {
            mGoogleLinearLayout.setBackgroundDrawable(null);
            mGoogleLinearLayout.setBackgroundResource(R.drawable.social_rounder_btn);
        }

    }

    public void moveToDetailPin() {

        Intent i = new Intent(this, LinkedSocialProfileActivity.class);
        i.putExtra("mode", SocialConstants.PINTEREST);
        i.putExtra(AppConstants.SOCIAL_PROFILE_FLAG, true);
        i.putExtra(AppConstants.LINKED_PLATFORM_NAME, getString(R.string.pin_label));
        startActivity(i);

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
