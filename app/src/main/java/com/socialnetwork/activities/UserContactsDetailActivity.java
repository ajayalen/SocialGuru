package com.socialnetwork.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

public class UserContactsDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mAddMoreSocialPlatformLl;
    private View mMainContainer;
    private GetMailPlatformsResBean mResponse;
    private ImageView mWhatsAppIV, mViberIV, mHikeIV, mCallsIV, mTelegramIV;

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
        Button mNextBtn = (Button) findViewById(R.id.activity_user_contacts_next_btn);
        View mSkipBtn = (findViewById(R.id.activity_user_contacts_skip_btn));
        mTitleTextView.setText(getString(R.string.user_details_label));
        mAddMoreSocialPlatformLl = (LinearLayout) findViewById(R.id.add_mail_platform_container);
        mMainContainer = findViewById(R.id.main_container);
        mWhatsAppIV = (ImageView) findViewById(R.id.activity_user_mail_whatsapp_iv);
        mViberIV = (ImageView) findViewById(R.id.activity_user_mail_viber_iv);
        mHikeIV = (ImageView) findViewById(R.id.activity_user_mail_hike_iv);
        mCallsIV = (ImageView) findViewById(R.id.activity_user_mail_calls_iv);
        mTelegramIV = (ImageView) findViewById(R.id.activity_user_mail_telegram_iv);
        mAddMoreImageView.setVisibility(View.VISIBLE);
        mAddMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = AlertFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.CUSTOM_DIALOG_FLAG, AppConstants.ADD_VIEW_DIALOG);
                bundle.putString(AppConstants.DIALOG_IMAGE, getString(R.string.contacts_txt));
                // set Fragment class Arguments
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "dialog");
            }
        });
        mSkipBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        findViewById(R.id.activity_user_mail_whatsapp_ll).setOnClickListener(this);
        findViewById(R.id.activity_user_mail_viber_ll).setOnClickListener(this);
        findViewById(R.id.activity_user_mail_telegram_ll).setOnClickListener(this);
        findViewById(R.id.activity_user_mail_hike_ll).setOnClickListener(this);
        findViewById(R.id.activity_user_mail_calls_ll).setOnClickListener(this);
        getContactPlatforms();
    }

    //method to get the contact platforms
    private void getContactPlatforms() {
        mAddMoreSocialPlatformLl.removeAllViews();
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        showProgresDialog();
        Call<GetMailPlatformsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getContactPlatforms(getContactPlatformsApiParams());
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

    //method to set the request params for api to get the contact platforms
    private GeneralReqBean getContactPlatformsApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        return reqBean;
    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_contacts_detail;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //when next button is clicked
            case R.id.activity_user_contacts_next_btn:
                PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_ADDRESS);
                startActivity(new Intent(UserContactsDetailActivity.this, UserAddressDetailActivity.class));
                break;

            //when next button is clicked
            case R.id.activity_user_contacts_skip_btn:
                callSkipStepApi();
                break;

            //when skip button is clicked
            case R.id.activity_user_mail_whatsapp_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent intent = new Intent(mThis, AddDetailsPopupActivity.class);
                intent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
                intent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(0).getSocial_media_id());
                intent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.whats_app));
                intent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(intent);
                break;

            case R.id.activity_user_mail_viber_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent viberIntent = new Intent(mThis, AddDetailsPopupActivity.class);
                viberIntent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
                viberIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(1).getSocial_media_id());
                viberIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.viber_txt));
                viberIntent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(viberIntent);
                break;
            case R.id.activity_user_mail_telegram_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent telegramIntent = new Intent(mThis, AddDetailsPopupActivity.class);
                telegramIntent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
                telegramIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID,
                        mResponse.getResult().get(2).getSocial_media_id());
                telegramIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.telegram_txt));
                telegramIntent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(telegramIntent);
                break;
            case R.id.activity_user_mail_hike_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent hikeIntent = new Intent(mThis, AddDetailsPopupActivity.class);
                hikeIntent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
                hikeIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(3).getSocial_media_id());
                hikeIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.hike_txt));
                hikeIntent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(hikeIntent);
                break;
            case R.id.activity_user_mail_calls_ll:
                if (mResponse == null || mResponse.getResult() == null || mResponse.getResult().size() == 0)
                    return;
                Intent callsIntent = new Intent(mThis, AddDetailsPopupActivity.class);
                callsIntent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
                callsIntent.putExtra(AppConstants.SOCIAL_MEDIA_ID, mResponse.getResult().get(4).getSocial_media_id());
                callsIntent.putExtra(AppConstants.SOCIAL_MEDIA_MAIL, getString(R.string.calls_txt));
                callsIntent.putExtra(AppConstants.DELETE_SOCIAL_FLAG, false);
                startActivity(callsIntent);
                break;

        }
    }

    //method to set the platform state
    private void setPlatformState() {
        if (mResponse.getResult().get(0).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.whatsapp_active).asBitmap().into(mWhatsAppIV);
        }
        if (mResponse.getResult().get(1).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.viber_active).asBitmap().into(mViberIV);
        }
        if (mResponse.getResult().get(2).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.telegram_active).asBitmap().into(mTelegramIV);
        }
        if (mResponse.getResult().get(3).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.hike_active).asBitmap().into(mHikeIV);
        }
        if (mResponse.getResult().get(4).getOther_accounts().size() > 0) {
            Glide.with(mThis).load(R.drawable.call_active).asBitmap().into(mCallsIV);
        }
    }

    //event to add the the plaform
    public void onEvent(AddPlatformEventBean eventBean) {
        if (eventBean.getPlatformType().equalsIgnoreCase("3"))
            addMoreMail(eventBean.getMailText(), eventBean.getSocialId(), false);
    }

    //event to add the the plaform
    public void onEvent(DeletePlatformEventBean eventBean) {
        getContactPlatforms();
    }

    //method to add the more mail options
    private void addMoreMail(final String mailText, final String socialId, final boolean isPlatformAdded) {
        final View view = LayoutInflater.from(this).inflate(R.layout.mail_view, mAddMoreSocialPlatformLl, false);
        final String text = mailText.substring(0, 1).toUpperCase() + mailText.substring(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mThis, AddDetailsPopupActivity.class);
                intent.putExtra(AppConstants.CUSTOM_POPUP, getString(R.string.contacts_txt));
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
        mAddMoreSocialPlatformLl.addView(view);
        Utility.setColoronAddedPlatformsMailContact(mAddMoreSocialPlatformLl.getChildCount(), drawableView,
                String.valueOf(mailText.charAt(0)).toUpperCase(), isPlatformAdded);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    PrefUtils.setSharedPrefStringData(UserContactsDetailActivity.this, PrefConstants.SCREEN_STEP,
                            mResponse.getResult().getStep_number() + "");
                    startActivity(new Intent(UserContactsDetailActivity.this, UserAddressDetailActivity.class));
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

    //method to set the api params for the skip step api
    private GeneralReqBean skipStepApiParams() {
        GeneralReqBean reqBean = new GeneralReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setStep(AppConstants.STEP_PHONE);
        return reqBean;
    }

    //method to set the added mail platforms
    private void setAddedPlatforms() {
        for (int i = 0; i < mResponse.getResult().size(); i++) {
            String platformName = mResponse.getResult().get(i).getNew_platform_name();
            if (!platformName.equalsIgnoreCase(getString(R.string.whats_app))
                    && !platformName.equalsIgnoreCase(getString(R.string.viber_txt))
                    && !platformName.equalsIgnoreCase(getString(R.string.telegram_txt))
                    && !platformName.equalsIgnoreCase(getString(R.string.hike_txt))
                    && !platformName.equalsIgnoreCase(getString(R.string.calls_txt)))
                addMoreMail(mResponse.getResult().get(i).getNew_platform_name(),
                        mResponse.getResult().get(i).getSocial_media_id(),
                        mResponse.getResult().get(i).getOther_accounts() != null
                                && mResponse.getResult().get(i).getOther_accounts().size() > 0);
        }

    }

    //event to change the social icon when information is added
    public void onEvent(AddPlatformDetailEventBean eventBean) {
        if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.whats_app))) {
            Glide.with(mThis).load(R.drawable.whatsapp_active).asBitmap().into(mWhatsAppIV);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.viber_txt))) {
            Glide.with(mThis).load(R.drawable.viber_active).asBitmap().into(mViberIV);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.telegram_txt))) {
            Glide.with(mThis).load(R.drawable.telegram_active).asBitmap().into(mTelegramIV);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.hike_txt))) {
            Glide.with(mThis).load(R.drawable.hike_active).asBitmap().into(mHikeIV);
        } else if (eventBean.getSocialMediaText().equalsIgnoreCase(getString(R.string.calls_txt))) {
            Glide.with(mThis).load(R.drawable.call_active).asBitmap().into(mCallsIV);
        } else getContactPlatforms();
    }
}
