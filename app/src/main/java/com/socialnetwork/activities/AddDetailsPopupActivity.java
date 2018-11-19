package com.socialnetwork.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.interfaces.DialogCallBack;
import com.socialnetwork.models.eventmodels.AddPlatformDetailEventBean;
import com.socialnetwork.models.eventmodels.DeletePlatformEventBean;
import com.socialnetwork.models.reqmodels.AddContactDetailReqBean;
import com.socialnetwork.models.reqmodels.AddMailDetailReqBean;
import com.socialnetwork.models.responsemodels.AddContactDetailResBean;
import com.socialnetwork.models.responsemodels.AddMailDetailResBean;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformReq;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformRes;
import com.socialnetwork.models.responsemodels.GetContactDetailsResBean;
import com.socialnetwork.models.responsemodels.GetMailDetailsResBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;
import com.socialnetwork.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddDetailsPopupActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mAddressLinearLayout;
    String popupFlag = "";
    private View mMainContainer;
    private String socialId = "";
    private GetMailDetailsResBean mMailDetailResponse;
    private GetContactDetailsResBean mContactDetailsResponse;
    private ArrayList<AddMailDetailReqBean.OtherAccountsBean> mMailOtherAcc;
    private ArrayList<AddContactDetailReqBean.OtherAccountsBean> mContactOtherAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
    @Override
    public void initializeUi() {
        if (getIntent().getStringExtra(AppConstants.CUSTOM_POPUP) != null) {
            popupFlag = getIntent().getStringExtra(AppConstants.CUSTOM_POPUP);
        }
        socialId = getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_ID);
        final String platformName = getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_MAIL);
        if (platformName != null && !platformName.isEmpty())
            mTitleTextView.setText(platformName);
        mMainContainer = findViewById(R.id.main_container);
        mAddressLinearLayout = (LinearLayout) findViewById(R.id.activity_add_view_lyt);
        Button mDoneBtn = (Button) findViewById(R.id.activity_add_details_save_btn);
        //   EditText mDetailEt = (EditText) findViewById(R.id.address_single_address);
        TextView mAddTv = (TextView) findViewById(R.id.add_details_popup_tv);

        if (popupFlag.equalsIgnoreCase(getString(R.string.contacts_txt))) {
            mAddTv.setText(getString(R.string.enter_number));

            //    mDetailEt.setHint(getString(R.string.number));
            getContactDetail();

        } else if (popupFlag.equalsIgnoreCase(getString(R.string.mail_txt))) {
            mAddTv.setText(getString(R.string.enter_details));
            mAddTv.setInputType(InputType.TYPE_CLASS_TEXT);

            //    mDetailEt.setHint(getString(R.string.add_address));
            getMailDetail();
        }
        mAddTv.setOnClickListener(this);
        mDoneBtn.setOnClickListener(this);
        if (getIntent().getBooleanExtra(AppConstants.DELETE_SOCIAL_FLAG, false)) {
            mDeleteTV.setVisibility(View.VISIBLE);
            mDeleteTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.showSimpleDialog(mThis, getString(R.string.are_you_sure_txt)+" "+platformName+" ?", new DialogCallBack() {
                        @Override
                        public void okPressed() {
                            callDeletePlatformApi();
                        }
                    });

                }
            });
        } else mDeleteTV.setVisibility(View.GONE);
    }

    //method to delete the new added platform
    private void callDeletePlatformApi() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<DeleteSocialPlatformRes> call = AppRetrofit.getInstance().getApiServices()
                .deleteSocialPlatforms(deleteNewPlatformApiParams());
        AppUtils.enqueueCall(call, new Callback<DeleteSocialPlatformRes>() {

            @Override
            public void onResponse(Response<DeleteSocialPlatformRes> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    finish();
                    DeletePlatformEventBean eventBean = new DeletePlatformEventBean();
                    eventBean.setPlatformType(popupFlag);
                    EventBus.getDefault().post(eventBean);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });

    }

    //method to add the request params for the api to delete the new added platforms
    private DeleteSocialPlatformReq deleteNewPlatformApiParams() {
        DeleteSocialPlatformReq reqBean = new DeleteSocialPlatformReq();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setIs_linked("0");
        reqBean.setSocial_media_id(getIntent().getStringExtra(AppConstants.SOCIAL_MEDIA_ID));
        if (popupFlag.equalsIgnoreCase(getString(R.string.contacts_txt)))
            reqBean.setType("3");
        else if (popupFlag.equalsIgnoreCase(getString(R.string.mail_txt)))
            reqBean.setType("2");
        return reqBean;
    }

    //method to get the mail details
    private void getMailDetail() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<GetMailDetailsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getEmailDetails(getContactDetailApiParams());
        AppUtils.enqueueCall(call, new Callback<GetMailDetailsResBean>() {

            @Override
            public void onResponse(Response<GetMailDetailsResBean> response, Retrofit retrofit) {
                mMailDetailResponse = response.body();
                if (mMailDetailResponse.isSuccess()) {
                    if (mMailDetailResponse.getResult().getOther_accounts().size() > 0)
                        setMailDetails();
                    else
                        addDetailView("", "");
                } else if (!mMailDetailResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mMailDetailResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the added view
    private void setMailDetails() {
        for (int i = 0; i < mMailDetailResponse.getResult().getOther_accounts().size(); i++) {
            String userName = mMailDetailResponse.getResult().getOther_accounts().get(i).getUsername();
            String addressName = mMailDetailResponse.getResult().getOther_accounts().get(i).getEmail_id();
            addDetailView(userName, addressName);
        }
    }

    //,method to get the contact details
    private void getContactDetail() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<GetContactDetailsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getContactDetails(getContactDetailApiParams());
        AppUtils.enqueueCall(call, new Callback<GetContactDetailsResBean>() {
            @Override
            public void onResponse(Response<GetContactDetailsResBean> response, Retrofit retrofit) {
                mContactDetailsResponse = response.body();
                if (mContactDetailsResponse.isSuccess()) {
                    if (mContactDetailsResponse.getResult().getOther_accounts().size() > 0)
                        setContactDetails();
                    else
                        addDetailView("", "");
                } else if (!mContactDetailsResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mContactDetailsResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    private void setContactDetails() {
        for (int i = 0; i < mContactDetailsResponse.getResult().getOther_accounts().size(); i++) {
            String userName = mContactDetailsResponse.getResult().getOther_accounts().get(i).getPhone_tag();
            String addressName = mContactDetailsResponse.getResult().getOther_accounts().get(i).getPhone_number();
            addDetailView(userName, addressName);
        }
    }

    //method to set the request params for the contact detail api
    private AddContactDetailReqBean getContactDetailApiParams() {
        AddContactDetailReqBean reqBean = new AddContactDetailReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setSocial_media_id(socialId);
        return reqBean;
    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_details_popup;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_details_popup_tv:
                addDetailView("", "");
                break;
            case R.id.activity_add_details_save_btn:
                Utils.hideKeyboard(mThis);
                if (popupFlag.equalsIgnoreCase(getString(R.string.mail_txt)))
                    addMailDetails();
                else if (popupFlag.equalsIgnoreCase(getString(R.string.contacts_txt)))
                    addContactDetails();
                break;
        }
    }

    //method to call the api to add the contact details api
    private void addContactDetails() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<AddContactDetailResBean> call = AppRetrofit.getInstance().getApiServices()
                .addContactDetails(addContactDetailApiParams());

        if(mContactOtherAcc.size()==0)
        {
            hideProgresDialog();
            DialogUtil.showSnackBar(mMainContainer, "Please add one acc");
            return;
        }
        else {
            for (int i = 0; i < mAddressLinearLayout.getChildCount(); i++) {
                if (mContactOtherAcc.get(i).getPhone_number().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill number");
                    return;
                }
                if (mContactOtherAcc.get(i).getPhone_tag().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill tag name");
                    return;
                }
            }
        }
        AppUtils.enqueueCall(call, new Callback<AddContactDetailResBean>() {
            @Override
            public void onResponse(Response<AddContactDetailResBean> response, Retrofit retrofit) {
                AddContactDetailResBean mResponse = response.body();
                if (mResponse.isSuccess()) {
                    if(mResponse.getResult().getOther_accounts().size()>0) {
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

    //method to add the request params for the api to add the contact details
    private AddContactDetailReqBean addContactDetailApiParams() {
        AddContactDetailReqBean reqBean = new AddContactDetailReqBean();
        List<AddContactDetailReqBean.OtherAccountsBean> other_accounts = new ArrayList<>();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setSocial_media_id(socialId);
        for (int i = 0; i < mAddressLinearLayout.getChildCount(); i++) {

            String tagName = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_tag))
                    .getText().toString().trim();
            String address = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_address))
                    .getText().toString().trim();
            AddContactDetailReqBean.OtherAccountsBean bean = new AddContactDetailReqBean.OtherAccountsBean();
        /*    if (!tagName.trim().isEmpty() && !address.trim().isEmpty()) {*/
                bean.setPhone_tag(tagName);
                bean.setPhone_number(address);
                other_accounts.add(bean);
           /* }*/

        }

        mContactOtherAcc = new ArrayList<>(other_accounts);
        reqBean.setOther_accounts(other_accounts);
        return reqBean;
    }

    //method to add the mail details
    private void addMailDetails() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        Call<AddMailDetailResBean> call = AppRetrofit.getInstance().getApiServices()
                .addEmailDetails(addMailDetailsApiParams());
        if(mMailOtherAcc.size()==0)
        {
            hideProgresDialog();
            DialogUtil.showSnackBar(mMainContainer, "Please add one acc");
            return;
        }
        else {
            for (int i = 0; i < mMailOtherAcc.size(); i++) {
                if (mMailOtherAcc.get(i).getEmail_id().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill email");
                    return;
                }
                if (mMailOtherAcc.get(i).getUsername().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill tag name");
                    return;
                }
            }
        }
        AppUtils.enqueueCall(call, new Callback<AddMailDetailResBean>() {
            @Override
            public void onResponse(Response<AddMailDetailResBean> response, Retrofit retrofit) {
                AddMailDetailResBean mResponse = response.body();
                if (mResponse.isSuccess()) {
                    if(mResponse.getResult().getOther_accounts().size()>0) {
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

    //method to set the request params for the mail detail api
    private AddMailDetailReqBean addMailDetailsApiParams() {
        AddMailDetailReqBean reqBean = new AddMailDetailReqBean();
        List<AddMailDetailReqBean.OtherAccountsBean> other_accounts = new ArrayList<>();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        reqBean.setSocial_media_id(socialId);
        for (int i = 0; i < mAddressLinearLayout.getChildCount(); i++) {
            String tagName = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_tag))
                    .getText().toString().trim();
            String address = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_address))
                    .getText().toString().trim();
            AddMailDetailReqBean.OtherAccountsBean bean = new AddMailDetailReqBean.OtherAccountsBean();
          /*  if (!tagName.trim().isEmpty() && !address.trim().isEmpty()) {*/
                bean.setUsername(tagName);
                bean.setEmail_id(address);
                other_accounts.add(bean);
            /*}*/
        }
        mMailOtherAcc = new ArrayList<>(other_accounts);
        reqBean.setOther_accounts(other_accounts);
        return reqBean;
    }

    private void addDetailView(String tagName, String addressName) {
        if (mAddressLinearLayout.getChildCount() == 5)
            return;

        final View view = LayoutInflater.from(this).inflate(R.layout.address_input_lyt, mAddressLinearLayout, false);
        final EditText mMailDetailET = (EditText) view.findViewById(R.id.address_single_address);
        final EditText mTagName = (EditText) view.findViewById(R.id.address_single_tag);
        view.findViewById(R.id.web_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!mMailDetailET.getText().toString().trim().isEmpty()
                        || !mTagName.getText().toString().trim().isEmpty())
				{*/
                if (mAddressLinearLayout.getChildCount() > 1) {
                    Utility.showSimpleDialog(mThis, getString(R.string.delete_confirmation_txt), new DialogCallBack() {
                        @Override
                        public void okPressed() {
                            mAddressLinearLayout.removeView(view);
                        }
                    });
                }
				/*}*/

            }
        });

        if (popupFlag.equalsIgnoreCase(getString(R.string.contacts_txt))) {
            mMailDetailET.setHint(getString(R.string.number));
            mMailDetailET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mMailDetailET.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        else if (popupFlag.equalsIgnoreCase(getString(R.string.mail_txt))) {
            mMailDetailET.setInputType(InputType.TYPE_CLASS_TEXT );
            mMailDetailET.setHint(getString(R.string.email_address));
        }

        mTagName.setText(tagName);
        mMailDetailET.setText(addressName);
        mAddressLinearLayout.addView(view);
    }
}
