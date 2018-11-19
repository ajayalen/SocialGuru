package com.socialnetwork.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.fragments.AlertFragment;
import com.socialnetwork.models.eventmodels.AddPlatformDetailEventBean;
import com.socialnetwork.models.eventmodels.AddPlatformEventBean;
import com.socialnetwork.models.eventmodels.DeletePlatformEventBean;
import com.socialnetwork.models.reqmodels.GeneralReqBean;
import com.socialnetwork.models.responsemodels.GetMailPlatformsResBean;
import com.socialnetwork.models.responsemodels.SkipStepResBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserMailDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mAddMoreMailLl;
    HorizontalScrollView mHorizontalScrollView;
    private View mMainContainer;
    private GetMailPlatformsResBean mResponse;
    private ImageView mYahooSocialIcon, mGmailSocialIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
    @Override
    public void initializeUi() {
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.add_mail_platform_hscrollview);
        mMainContainer = findViewById(R.id.main_container);
        Button mNextBtn = (Button) findViewById(R.id.activity_user_mail_next_btn);
        View mSkipBtn = (findViewById(R.id.activity_user_mail_skip_btn));
        mTitleTextView.setText(getString(R.string.user_details_label));
        mAddMoreMailLl = (LinearLayout) findViewById(R.id.add_mail_platform_container);
        mGmailSocialIcon = (ImageView) findViewById(R.id.activity_user_mail_gmail_iv);
        mYahooSocialIcon = (ImageView) findViewById(R.id.activity_user_mail_yahoo_iv);
        mAddMoreImageView.setVisibility(View.VISIBLE);
        mAddMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = AlertFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.CUSTOM_DIALOG_FLAG, AppConstants.ADD_VIEW_DIALOG);
                bundle.putString(AppConstants.DIALOG_IMAGE, getString(R.string.mail_txt));
                // set Fragment class Arguments
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "dialog");
            }
        });
        mSkipBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        findViewById(R.id.activity_user_mail_gmail_ll).setOnClickListener(this);
        findViewById(R.id.activity_user_mail_yahoo_ll).setOnClickListener(this);
        getAllMailPlatforms();
    }

    //method to get the all email platforms
    private void getAllMailPlatforms() {
        mAddMoreMailLl.removeAllViews();
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        showProgresDialog();
        Call<GetMailPlatformsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getEmailPlatforms(getEmailPlatformsApiParams());
        AppUtils.enqueueCall(call, new Callback<GetMailPlatformsResBean>() {
            @Override
            public void onResponse(Response<GetMailPlatformsResBean> response, Retrofit retrofit) {
                hideProgresDialog();
                mResponse = response.body();
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
                hideProgresDialog();
                DialogUtil.showTryAgainToast(mThis);
            }
        });

    }

    //method to set the platform state
    private void setPlatformState() {
        if (mResponse.getResult().get(0).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.gmail_selected).asBitmap().into(mGmailSocialIcon);
        }
        if (mResponse.getResult().get(1).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.yahoo_selected).asBitmap().into(mYahooSocialIcon);
        }
    }

    //method to set the added mail platforms
    private void setAddedPlatforms() {
        for (int i = 0; i < mResponse.getResult().size(); i++) {
            String platformName = mResponse.getResult().get(i).getNew_platform_name();
            if (!platformName.isEmpty())
                if (!platformName.equalsIgnoreCase(getString(R.string.gmail_txt))
                        && !platformName.equalsIgnoreCase(getString(R.string.yahoo_txt))) {
                    addMoreMail(mResponse.getResult().get(i).getNew_platform_name(),
                            mResponse.getResult().get(i).getSocial_media_id(), mResponse.getResult().get(i).getOther_accounts() != null
                                    && mResponse.getResult().get(i).getOther_accounts().size() > 0);
                }
        }

    }

    //method to set the request params for the
    private GeneralReqBean getEmailPlatformsApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        return reqBean;
    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_mail_detail;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_mail_skip_btn:
                callSkipStepApi();
                break;
            case R.id.activity_user_mail_next_btn:
                PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_PHONE);
                startActivity(new Intent(UserMailDetailActivity.this, UserContactsDetailActivity.class));
                break;
            case R.id.activity_user_mail_gmail_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent intent = new Intent(mThis, AddDetailsPopupActivity.class);
                intent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.mail_txt));
                intent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(0).getSocial_media_id());
                intent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.gmail_txt));
                intent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(intent);
                break;
            case R.id.activity_user_mail_yahoo_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent yahooIntent = new Intent(mThis, AddDetailsPopupActivity.class);
                yahooIntent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.mail_txt));
                yahooIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(1).getSocial_media_id());
                yahooIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.yahoo_txt));
                yahooIntent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(yahooIntent);
                break;
        }
    }

    //method to call the api to skip the step
    private void callSkipStepApi() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        showProgresDialog();
        Call<SkipStepResBean> call = AppRetrofit.getInstance().getApiServices().skipstep(skipStepApiParams());
        AppUtils.enqueueCall(call, new Callback<SkipStepResBean>() {
            public SkipStepResBean mResponse;

            @Override
            public void onResponse(Response<SkipStepResBean> response, Retrofit retrofit) {
                hideProgresDialog();
                mResponse = response.body();
                if (mResponse.isSuccess()) {
                    PrefUtils.setSharedPrefStringData(UserMailDetailActivity.this, PrefConstants.SCREEN_STEP,
                            mResponse.getResult().getStep_number() + "");
                    startActivity(new Intent(UserMailDetailActivity.this, UserContactsDetailActivity.class));
                    finish();
                } else if (!mResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgresDialog();
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the api params for the skip step api
    private GeneralReqBean skipStepApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setStep(AppConstants.STEP_EMAIL);
        return reqBean;
    }

    //event to add the the plaform
    public void onEvent(AddPlatformEventBean eventBean) {
        if (eventBean.getPlatformType().equalsIgnoreCase("2"))
            addMoreMail(eventBean.getMailText(), eventBean.getSocialId(), false);
    }

    //event to add the the plaform
    public void onEvent(DeletePlatformEventBean eventBean) {
        getAllMailPlatforms();
    }

    //method to add the more mail options
    private void addMoreMail(final String mailText, final String socialId, final boolean isPlatformAdded) {
        final View view = LayoutInflater.from(this).inflate(R.layout.mail_view, null);
        final String text = mailText.substring(0, 1).toUpperCase() + mailText.substring(1);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mThis, AddDetailsPopupActivity.class);
                intent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.mail_txt));
                intent.putExtra(AppConstants.SOCIAL_MEDIA_ID, socialId);
                intent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, text);
                intent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, true);
                startActivity(intent);
            }
        });

        TextView mMailPlatformTv = (TextView) view.findViewById(R.id.add_mail_tv);
        mMailPlatformTv.setText(text);
        mMailPlatformTv.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        ImageView drawableView = (ImageView) view.findViewById(R.id.add_mail_icon_tv);
        mAddMoreMailLl.addView(view);
        mHorizontalScrollView.setPadding(0, 0, 0, 0);
        Utility.setColoronAddedPlatformsMailContact(mAddMoreMailLl.getChildCount(), drawableView,
                String.valueOf(mailText.charAt(0)).toUpperCase(), isPlatformAdded);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //event to change the social icon when information is added
    public void onEvent(AddPlatformDetailEventBean eventBean) {
        if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.gmail_txt))) {
            Glide.with(mThis).load(R.drawable.gmail_selected).asBitmap().into(mGmailSocialIcon);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.yahoo_txt))) {
            Glide.with(mThis).load(R.drawable.yahoo_selected).asBitmap().into(mYahooSocialIcon);
        } else
            getAllMailPlatforms();

    }
}
