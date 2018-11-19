package com.socialnetwork.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.gson.Gson;
import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.interfaces.DialogCallBack;
import com.socialnetwork.models.eventmodels.AddPlatformDetailEventBean;
import com.socialnetwork.models.eventmodels.DeletePlatformEventBean;
import com.socialnetwork.models.reqmodels.AddContactDetailReqBean;
import com.socialnetwork.models.reqmodels.AddSocialDetailsReqBean;
import com.socialnetwork.models.responsemodels.AddSocialDetailsResBean;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformReq;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformRes;
import com.socialnetwork.models.responsemodels.GetSocialDetailsResBean;
import com.socialnetwork.models.responsemodels.SocialMediaInfoBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.FlickrWrapper;
import com.socialnetwork.social.flickr.oauth.OAuth;
import com.socialnetwork.social.flickr.people.User;
import com.socialnetwork.social.flickr.tasks.LoadUserTask;
import com.socialnetwork.social.instagram.InstagramWrapper;
import com.socialnetwork.social.linkedin.LinkedinWrapper;
import com.socialnetwork.social.pinterest.PDKCallback;
import com.socialnetwork.social.pinterest.PDKClient;
import com.socialnetwork.social.pinterest.PDKResponse;
import com.socialnetwork.social.reddit.RedditWrapper;
import com.socialnetwork.social.reddit.RefreshOAuthTokenTask;
import com.socialnetwork.social.tumblr.TumblrWrapper;
import com.socialnetwork.social.twitter.TwitterWrapper;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;
import com.socialnetwork.utils.Utils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LinkedSocialProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LinkedSocialProfileActivity.class.getSimpleName();

    private LinearLayout mAddMoreProfileLl;
    private ImageView mSocialImage;
    private TextView mNameTextView;
    private View mMainContainer;
    private GetSocialDetailsResBean mSocialDetailResponse;
    private ArrayList<String> mProfileNameList;
    List<EditText> allEds = new ArrayList<EditText>();
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private String mSocialAccountId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
    @Override
    public void initializeUi() {
        mSocialImage = (ImageView) findViewById(R.id.activity_linked_social_profile_iv);
        mProfileNameList = new ArrayList<>();
        TextView mSocialPlatformName = (TextView) findViewById(R.id.activity_linked_social_platform_name);
        View mSocialProfileLl = findViewById(R.id.activity_linked_social_profile_ll);
        TextView mAddWebsiteLinkTv = (TextView) findViewById(R.id.add_more_profile_tv);
        setTitleText(mTitleTextView, getIntent().getIntExtra("mode", 0));
        mAddMoreProfileLl = (LinearLayout) findViewById(R.id.add_more_profile_ll);
        mNameTextView = (TextView) findViewById(R.id.linked_social_profile_name_tv);
        mMainContainer = findViewById(R.id.main_container);
        /* EditText mInputEt = (EditText) findViewById(R.id.input_et);
         mInputEt.setHint(getString(R.string.add_profile_name));*/
        Button mSaveBtn = (Button) findViewById(R.id.activity_linked_social_profile_save_btn);
        mAddWebsiteLinkTv.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);

        if (!getIntent().getBooleanExtra(AppConstants.SOCIAL_PROFILE_FLAG, false)) {
            mTitleTextView.setText(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_MAIL));
            mSocialPlatformName.setVisibility(View.GONE);
            mSocialProfileLl.setVisibility(View.GONE);
        } else {
            setTitleText(mTitleTextView, getIntent().getIntExtra("mode", 0));
            mSocialPlatformName.setVisibility(View.VISIBLE);
            mSocialProfileLl.setVisibility(View.VISIBLE);
        }
        loadUI();
        getSocialDetails();

        findViewById(R.id.activity_linked_social_profile_ll).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showLogoutDialog();
                return false;
            }
        });

        if (getIntent().getBooleanExtra("show_delete", false)) {
            TextView delete = (TextView) findViewById(R.id.added_platform_delete);
            delete.setVisibility(View.VISIBLE);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    deleteNewPlatform(mTitleTextView.getText().toString());


                }
            });
        }

    }

    private void deleteNewPlatform(String text) {
        Utility.showSimpleDialog(this, "Are you sure you want to delete " + text + " ?", new DialogCallBack() {
            @Override
            public void okPressed() {
                callDeleteApi();
            }
        });


    }

    private void callDeleteApi() {


        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<DeleteSocialPlatformRes> call = AppRetrofit.getInstance().getApiServices()
                .deleteSocialPlatforms(deleteNewPlatformApiParams(false));
        AppUtils.enqueueCall(call, new Callback<DeleteSocialPlatformRes>() {

            @Override
            public void onResponse(Response<DeleteSocialPlatformRes> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    DeletePlatformEventBean eventBean = new DeletePlatformEventBean();
                    eventBean.setLinked(false);
                    EventBus.getDefault().post(eventBean);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });

    }

    private DeleteSocialPlatformReq deleteNewPlatformApiParams(boolean isLinked) {
        DeleteSocialPlatformReq reqBean = new DeleteSocialPlatformReq();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        if (isLinked) {
            reqBean.setIs_linked("1");
            reqBean.setSocial_media_id(String.valueOf(getIntent().getIntExtra("mode", 0)));
        } else {
            reqBean.setSocial_media_id(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_ID));
            reqBean.setIs_linked("0");
        }

        reqBean.setType("1");
        return reqBean;
    }

    private void showLogoutDialog() {


        Utility.showSimpleDialog(this, "Are you sure you want to unlink this account?", new DialogCallBack() {
            @Override
            public void okPressed() {
                manageLogout();
                callDeleteApiLinked();
            }
        });


    }

    private void callDeleteApiLinked() {

        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<DeleteSocialPlatformRes> call = AppRetrofit.getInstance().getApiServices()
                .deleteSocialPlatforms(deleteNewPlatformApiParams(true));
        AppUtils.enqueueCall(call, new Callback<DeleteSocialPlatformRes>() {

            @Override
            public void onResponse(Response<DeleteSocialPlatformRes> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    DeletePlatformEventBean eventBean = new DeletePlatformEventBean();
                    eventBean.setLinked(true);
                    EventBus.getDefault().post(eventBean);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });

    }


    private void manageLogout() {
        Utility.clearCookies(this);
        switch (getIntent().getIntExtra("mode", 0)) {
            case SocialConstants.FLICKR:
                FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                finish();
                break;

            case SocialConstants.PINTEREST:
                PDKClient.getInstance().logout();
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                getSharedPreferences("com.pinterest.android.pdk.PREF_FILE_KEY", Context.MODE_PRIVATE).edit().clear()
                        .commit();
                finish();
                break;

            case SocialConstants.INSTAGRAM:
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                InstagramWrapper.getInstance(getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                break;

            case SocialConstants.LINKEDIN:
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                LinkedinWrapper.getInstance(getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                finish();
                break;

            case SocialConstants.REDDIT:
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                finish();
                break;

            case SocialConstants.FB:
                LoginManager.getInstance().logOut();
                finish();
                break;

            case SocialConstants.GOOGLE:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
              /*  mGoogleApiClient.clearDefaultAccountAndReconnect();
                mGoogleApiClient.disconnect();*/
                finish();
                break;

            case SocialConstants.TWITTER:
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                finish();
                break;

            case SocialConstants.TUMBLR:
                deleteDatabase("webview.db");
                deleteDatabase("webviewCache.db");
                TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                        Context.MODE_PRIVATE)).clearOauth();
                finish();
                break;

        }
    }

    public void getFbDetails() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.e("FbResponse", object.toString());
                    SocialMediaInfoBean socialMediaInfoBean = Utils.fromJson(object.toString(),
                            SocialMediaInfoBean.class);

                    setUserFacebook(socialMediaInfoBean);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields",
                "id,name,gender,birthday,friends,email,education,first_name,last_name,location,relationship_status,work,about,posts.limit(3){id,picture},photos.limit(6){id,picture},picture,albums,likes.limit(100)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void setUserFacebook(SocialMediaInfoBean socialMediaInfoBean) {
        mSocialAccountId = socialMediaInfoBean.getId();
        Glide.with(mThis).load(socialMediaInfoBean.getPicture().getData().getUrl()).asBitmap().centerCrop()
                .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mThis.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mSocialImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (!socialMediaInfoBean.getPicture().getData().getUrl().isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }
        mNameTextView.setText(socialMediaInfoBean.getName());
    }

    //method to call the api to get the social details
    private void getSocialDetails() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<GetSocialDetailsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getSocialDetails(getSocialDetailApiParams());
        AppUtils.enqueueCall(call, new Callback<GetSocialDetailsResBean>() {

            @Override
            public void onResponse(Response<GetSocialDetailsResBean> response, Retrofit retrofit) {
                mSocialDetailResponse = response.body();
                if (mSocialDetailResponse.isSuccess()) {
                    if (mSocialDetailResponse.getResult().getOther_accounts().size() > 0)
                        setMailDetails();
                    else
                        addProfileView("");
                } else if (!mSocialDetailResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mSocialDetailResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the social details to call the api to get the social details
    private AddContactDetailReqBean getSocialDetailApiParams() {
        AddContactDetailReqBean reqBean = new AddContactDetailReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));

        if (getIntent().getBooleanExtra(AppConstants.SOCIAL_PROFILE_FLAG, false))
            reqBean.setSocial_media_id(String.valueOf(getIntent().getIntExtra("mode", 0)));
        else
            reqBean.setSocial_media_id(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_ID));
        return reqBean;
    }

    private void setTitleText(TextView mTitleTextView, int mode) {
        String[] titleArray = mTitleTextView.getContext().getResources().getStringArray(R.array.social_array);
        try {
            mTitleTextView.setText(titleArray[mode - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUI() {

        if (!ApplicationController.isConnected(mMainContainer))
            return;
        if (getIntent() == null)
            return;

        switch (getIntent().getIntExtra("mode", 0)) {
            case SocialConstants.FLICKR:
                loadFlickrUI();
                break;

            case SocialConstants.PINTEREST:
                loadPinterestUI();
                break;

            case SocialConstants.INSTAGRAM:
                loadInstagramUI();
                break;

            case SocialConstants.LINKEDIN:
                loadLinkedinUI();
                break;

            case SocialConstants.REDDIT:
                loadRedditUI();
                break;

            case SocialConstants.FB:
                getFbDetails();
                break;

            case SocialConstants.GOOGLE:
                initGoogle();
                break;

            case SocialConstants.TWITTER:
                loadTwitterUI();
                break;

            case SocialConstants.TUMBLR:
                loadTumblrUI();
                break;
        }

    }

    private void loadTumblrUI() {

        com.socialnetwork.social.tumblr.oauth.OAuth oauth = TumblrWrapper.getInstance(getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getTumblrOAuthToken();

        if (oauth != null) {
            new com.socialnetwork.social.tumblr.LoadUserTask(this).execute(oauth);
        }

    }

    private void initGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder().requestProfile().requestId().requestEmail().build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        if (mGoogleApiClient.isConnected()) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done" and the GoogleSignInResult will be available instantly.
                Log.d("TAG", "Got cached sign-in");

                GoogleSignInResult result = opr.get();

                handleSignInResult(result);
            }
        } else {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    }

    private void loadRedditUI() {

        com.socialnetwork.social.reddit.oauth.OAuth oauth = RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getRedditOAuthToken();

        RefreshOAuthTokenTask task = new RefreshOAuthTokenTask(this, SocialConstants.REDDIT);
        task.execute("", "", oauth.getToken().getRefreshToken());
    }

    private void loadLinkedinUI() {
        com.socialnetwork.social.linkedin.oauth.OAuth oauth = LinkedinWrapper.getInstance(getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getLinkedinOAuthToken();
        if (oauth != null) {
            new com.socialnetwork.social.linkedin.user.LoadUserTask(this).execute(oauth);
        }
    }

    private void loadTwitterUI() {
        com.socialnetwork.social.twitter.oauth.OAuth oauth = TwitterWrapper.getInstance(getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getTwitterOAuthToken();
        if (oauth != null) {
            new com.socialnetwork.social.twitter.user.LoadUserTask(this).execute(oauth);
        }
    }

    private void loadInstagramUI() {
        com.socialnetwork.social.instagram.oauth.OAuth oauth = InstagramWrapper.getInstance(getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
                Context.MODE_PRIVATE)).getInstagramOAuthToken();
        if (oauth != null) {
            new com.socialnetwork.social.instagram.user.LoadUserTask(this).execute(oauth);
        }
    }

    private void loadPinterestUI() {


        PDKClient.getInstance().getMe("first_name,last_name,image", new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                super.onSuccess(response);
                mSocialAccountId = response.getUser().getUid();

                //                mImagePath = response.getUser().getImageUrl();
                Glide.with(mThis).load(response.getUser().getImageUrl()).asBitmap().centerCrop()
                        .placeholder(R.drawable.default_profile_image_holder)
                        .into(new BitmapImageViewTarget(mSocialImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                        .create(mThis.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                mSocialImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                if (!response.getUser().getImageUrl().isEmpty()) {
                    mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
                    mSocialImage.setPadding(5, 5, 5, 5);
                }
                mNameTextView.setText(response.getUser().getFirstName());
            }
        });

    }

    private void loadFlickrUI() {
        OAuth oauth = FlickrWrapper.getInstance(getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getFlickrOAuthToken();
        if (oauth != null) {
            new LoadUserTask(this).execute(oauth);
            //            new LoadPhotostreamTask(this, listView).execute(oauth);
        }
    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_linked_social_profile;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_more_profile_tv:
                addProfileView("");
                break;
            case R.id.activity_linked_social_profile_save_btn:
                saveProfileApi();
                break;
        }
    }

    //method to call the api to save the profile
    private void saveProfileApi() {
        Utils.hideKeyboard(this);
        RequestBody fileBody = null;
        String mImagePath = "";
        if (!TextUtils.isEmpty(mImagePath)) {
            fileBody = RequestBody.create(MediaType.parse("image/*"), new File(mImagePath));
        }
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<AddSocialDetailsResBean> call = AppRetrofit.getInstance().getApiServices().addSocialPlatformDetails(fileBody,
                addSocialDetailsApiParams());
        getProfileNameList();
        if (getIntent().getBooleanExtra("show_delete", false))
            if (mProfileNameList.size() == 0) {
                DialogUtil.showSnackBar(mMainContainer, "Please add one acc");
                return;
            }

        AppUtils.enqueueCall(call, new Callback<AddSocialDetailsResBean>() {
            public AddSocialDetailsResBean mResponse;

            @Override
            public void onResponse(Response<AddSocialDetailsResBean> response, Retrofit retrofit) {
                mResponse = response.body();
                if (mResponse.isSuccess()) {
                    if (mResponse.getResult().getOther_accounts().size() > 0) {
                        AddPlatformDetailEventBean eventBean = new AddPlatformDetailEventBean();
                        eventBean.setSocialMediaText(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_MAIL));
                        EventBus.getDefault().post(eventBean);
                    }
                    finish();
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

    //method to set the request params for the api to add the social details
    private AddSocialDetailsReqBean addSocialDetailsApiParams() {
        AddSocialDetailsReqBean reqBean = new AddSocialDetailsReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));

        if (getIntent().getBooleanExtra(AppConstants.SOCIAL_PROFILE_FLAG, false)) {
            reqBean.setAccount_id(mSocialAccountId);
            reqBean.setSocial_media_id(String.valueOf(getIntent().getIntExtra("mode", 0)));
        } else {
            reqBean.setAccount_id("");
            reqBean.setSocial_media_id(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_ID));

        }
        reqBean.setUsername("");
        reqBean.setFirst_name("");
        reqBean.setLast_name("");
        getProfileNameList();
        reqBean.setOther_accounts(mProfileNameList);
        Gson gson = new Gson();
        String json = gson.toJson(reqBean);
        Lg.e("socialRequestjson", json);
        return reqBean;
    }

    //method to get the website links list
    private void getProfileNameList() {
        mProfileNameList.clear();
        for (int i = 0; i < allEds.size(); i++) {
            if (!allEds.get(i).getText().toString().trim().isEmpty())
                mProfileNameList.add(allEds.get(i).getText().toString());
        }
    }

    private void addProfileView(String profileName) {

        if (mAddMoreProfileLl.getChildCount() == 4)
            return;

        final View view = LayoutInflater.from(this).inflate(R.layout.web_input_lyt, mAddMoreProfileLl, false);
        final EditText mInputEt = (EditText) view.findViewById(R.id.input_et);
        mInputEt.setHint(getString(R.string.add_profile_name));
        mInputEt.setText(profileName);
        allEds.add(mInputEt);
        view.findViewById(R.id.web_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddMoreProfileLl.getChildCount() > 1) {
                    Utility.showSimpleDialog(mThis, getString(R.string.delete_confirmation_txt), new DialogCallBack() {
                        @Override
                        public void okPressed() {
                            mAddMoreProfileLl.removeView(view);
                            allEds.remove(mInputEt);
                        }
                    });
                }
            }
        });
        mAddMoreProfileLl.addView(view);
        //        mWebLinearLayout.getChildAt(mWebLinearLayout.getChildCount() - 1).findViewById(R.id.web_divider).setVisibility(View.INVISIBLE);

    }

    public void setUser(User user) {
        mSocialAccountId = user.getId();
        Lg.d(TAG, user.getUsername());
        Glide.with(mThis).load(user.getBuddyIconUrl()).asBitmap().centerCrop()
                .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mThis.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mSocialImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (!user.getBuddyIconUrl().isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }
        mNameTextView.setText(user.getRealName());
    }

    public void setUserIntagram(com.socialnetwork.social.instagram.user.User user) {
        //set profile pic and name
        mSocialAccountId = user.getData().getId();
        Lg.d(TAG, user.getData().getUsername());
        Glide.with(mThis).load(user.getData().getProfile_picture()).asBitmap().centerCrop()
                .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mThis.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mSocialImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (!user.getData().getProfile_picture().isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }
        mNameTextView.setText(user.getData().getFull_name());
    }

    public void setUserLinkedin(com.socialnetwork.social.linkedin.user.User user) {
        Lg.d(TAG, user.getFirstName());

        mSocialAccountId = user.getId();

        Glide.with(mThis).load(user.getPictureUrl()).asBitmap().centerCrop()
                .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mThis.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mSocialImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (!user.getPictureUrl().isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }
        mNameTextView.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void setUserReddit(com.socialnetwork.social.reddit.user.User user) {
        mSocialAccountId = user.getId();
        mNameTextView.setText(user.getName());
    }

    //method to set the added view
    private void setMailDetails() {
        for (int i = 0; i < mSocialDetailResponse.getResult().getOther_accounts().size(); i++) {
            String userName = mSocialDetailResponse.getResult().getOther_accounts().get(i).getOther_account();
            addProfileView(userName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result != null)
                handleSignInResult(result);
            return;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            if (result.getSignInAccount() == null)
                return;
            mSocialAccountId = result.getSignInAccount().getId();
            // Signed in successfully, show authenticated UI.
            Glide.with(mThis).load(result.getSignInAccount().getPhotoUrl()).asBitmap().centerCrop()
                    .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                            .create(mThis.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mSocialImage.setImageDrawable(circularBitmapDrawable);
                }
            });
            mNameTextView.setText(result.getSignInAccount().getDisplayName());
            if (result.getSignInAccount().getPhotoUrl() != null && !result.getSignInAccount().getPhotoUrl().toString().isEmpty()) {
                mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
                mSocialImage.setPadding(5, 5, 5, 5);
            }
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    public void setUserTwitter(com.socialnetwork.social.twitter.user.User user) {

        mSocialAccountId = user.getId();
        Glide.with(mThis).load(user.getProfile_image_url()).asBitmap().centerCrop()
                .placeholder(R.drawable.default_profile_image_holder).into(new BitmapImageViewTarget(mSocialImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(mThis.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mSocialImage.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (!user.getProfile_image_url().isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }
        mNameTextView.setText(user.getName());
    }

    public void setUserTumblr(com.socialnetwork.social.tumblr.User user) {

        String picUrl = "";
        mNameTextView.setText(user.getResponse().getUser().getName());
        try {
            picUrl = "http://api.tumblr.com/v2/blog/" + user.getResponse().getUser().getBlogs().get(0).getName()
                    + ".tumblr.com/avatar/128";

            picUrl = picUrl.replace(" ", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (picUrl.equalsIgnoreCase(""))
            return;

        mSocialAccountId = user.getResponse().getUser().getName();

        Glide.with(mThis).load(picUrl).asBitmap().centerCrop().placeholder(R.drawable.default_profile_image_holder)
                .into(new BitmapImageViewTarget(mSocialImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(mThis.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mSocialImage.setImageDrawable(circularBitmapDrawable);
                    }
                });
        if (!picUrl.isEmpty()) {
            mSocialImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
            mSocialImage.setPadding(5, 5, 5, 5);
        }

    }

    public void onRefreshToken(Object result, int mMode) {
        com.socialnetwork.social.reddit.oauth.OAuthToken oauthToken = ((com.socialnetwork.social.reddit.oauth.OAuth) result)
                .getToken();

        SharedPreferences sp = getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SocialConstants.RedditConstants.KEY_ACCESS_TOKEN, oauthToken.getOauthToken());
        editor.putString(SocialConstants.RedditConstants.KEY_EXPIRES_IN, oauthToken.getExpiresIn());
        //        editor.putString(SocialConstants.RedditConstants.KEY_REFRESH_TOKEN, oauthToken.getRefreshToken());
        editor.putString(SocialConstants.RedditConstants.KEY_TOKEN_TYPE, oauthToken.getTokenType());
        editor.commit();

        com.socialnetwork.social.reddit.oauth.OAuth oauth = RedditWrapper.getInstance(getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
                Context.MODE_PRIVATE)).getRedditOAuthToken();
        if (oauth != null) {
            new com.socialnetwork.social.reddit.user.LoadUserTask(this).execute(oauth);
        }

    }
}
