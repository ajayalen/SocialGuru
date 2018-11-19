package com.socialnetwork.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.fragments.AlertFragment;
import com.socialnetwork.interfaces.DialogCallBack;
import com.socialnetwork.models.reqmodels.AddAddressDetailsReqBean;
import com.socialnetwork.models.reqmodels.GeneralReqBean;
import com.socialnetwork.models.responsemodels.AddAddressDetailResBean;
import com.socialnetwork.models.responsemodels.GetAddressDetailsResBean;
import com.socialnetwork.models.responsemodels.SkipStepResBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserAddressDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mAddressEditText;
    private LinearLayout mAddressLinearLayout;
    private View mMainContainer;
    private GetAddressDetailsResBean mAddressDetailsResponse;
    private ArrayList<AddAddressDetailsReqBean.AddressBean> mOtherAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method to initialize ui parameters and will be called just after setContentView Method in any activity
     */
    @Override
    public void initializeUi() {
        mTitleTextView.setText(getString(R.string.user_details_label));
        mAddressEditText = (TextView) findViewById(R.id.activity_profile_website_link_et);
        mAddressLinearLayout = (LinearLayout) findViewById(R.id.activity_address_lyt);
        mMainContainer = findViewById(R.id.main_container);
        Button mSkipBtn = (Button) findViewById(R.id.activity_user_address_skip_btn);
        mSkipBtn.setOnClickListener(this);
        mAddressEditText.setOnClickListener(this);
        findViewById(R.id.activity_user_address_next_btn).setOnClickListener(this);
        getAddressDetails();
    }

    //mathod to get the address details
    private void getAddressDetails() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        showProgresDialog();
        Call<GetAddressDetailsResBean> call = AppRetrofit.getInstance().getApiServices()
                .getAddressDetails(getAddressDetailApiParams());
        AppUtils.enqueueCall(call, new Callback<GetAddressDetailsResBean>() {
            @Override
            public void onResponse(Response<GetAddressDetailsResBean> response, Retrofit retrofit) {
                hideProgresDialog();
                mAddressDetailsResponse = response.body();
                if (mAddressDetailsResponse.isSuccess()) {
                    if (mAddressDetailsResponse.getResult().getAddress().size() > 0)
                        setAddressDetails();
                    else
                        addAddressView("", "");
                } else if (!mAddressDetailsResponse.isSuccess()) {
                    DialogUtil.showSnackBar(mMainContainer, mAddressDetailsResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgresDialog();
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to set the address details
    private void setAddressDetails() {
        for (int i = 0; i < mAddressDetailsResponse.getResult().getAddress().size(); i++) {
            String userName = mAddressDetailsResponse.getResult().getAddress().get(i).getAddress_tag();
            String addressName = mAddressDetailsResponse.getResult().getAddress().get(i).getAddress();
            addAddressView(userName, addressName);
        }
    }

    private AddAddressDetailsReqBean getAddressDetailApiParams() {
        AddAddressDetailsReqBean reqBean = new AddAddressDetailsReqBean();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        return reqBean;
    }

    /**
     * Method to return the activities layout res id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_address_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //when website link edit text is clicked
            case R.id.activity_profile_website_link_et:
                addAddressView("", "");
                break;
            //when skip button is clicked
            case R.id.activity_user_address_skip_btn:
                callSkipStepApi();
                break;
            //when next button is clicked
            case R.id.activity_user_address_next_btn:
                callSaveDetailsApi();
                break;
        }
    }

    //method to call the api to save the details
    private void callSaveDetailsApi() {
        if (!ApplicationController.isConnected(mMainContainer))
            return;
        showProgresDialog();
        Call<AddAddressDetailResBean> call = AppRetrofit.getInstance().getApiServices()
                .addAddressDetails(addAddressDetailApiParams());

        if (mAddressLinearLayout.getChildCount()==0)
        {
            hideProgresDialog();
            DialogUtil.showSnackBar(mMainContainer, "Please fill address");
            return;
        }

        if(mOtherAcc.size()==0)
        {
            hideProgresDialog();
            DialogUtil.showSnackBar(mMainContainer, "Please fill atleast one address");
            return;
        }
        else {
            for (int i = 0; i < mAddressLinearLayout.getChildCount(); i++) {
                if (mOtherAcc.get(i).getAddress().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill address");
                    return;
                }
                if (mOtherAcc.get(i).getAddress_tag().isEmpty()) {
                    hideProgresDialog();
                    DialogUtil.showSnackBar(mMainContainer, "Please fill tag name");
                    return;
                }
            }
        }
        AppUtils.enqueueCall(call, new Callback<AddAddressDetailResBean>() {
            @Override
            public void onResponse(Response<AddAddressDetailResBean> response, Retrofit retrofit) {
                hideProgresDialog();
                AddAddressDetailResBean mResponse = response.body();
                if (mResponse.isSuccess()) {
                    DialogFragment newFragment = AlertFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.CUSTOM_DIALOG_FLAG, AppConstants.SUCCESS_DIALOG);
                    // set Fragment class Arguments
                    newFragment.setArguments(bundle);
                    newFragment.show(getFragmentManager(), "dialog");
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

    //method to add the address details
    private AddAddressDetailsReqBean addAddressDetailApiParams() {
        AddAddressDetailsReqBean reqBean = new AddAddressDetailsReqBean();
        List<AddAddressDetailsReqBean.AddressBean> other_accounts = new ArrayList<>();
        reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
        for (int i = 0; i < mAddressLinearLayout.getChildCount(); i++) {

            String tagName = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_tag))
                    .getText().toString().trim();
            String address = ((EditText) mAddressLinearLayout.getChildAt(i).findViewById(R.id.address_single_address))
                    .getText().toString().trim();
            AddAddressDetailsReqBean.AddressBean bean = new AddAddressDetailsReqBean.AddressBean();
            bean.setAddress_tag(tagName);
            bean.setAddress(address);
          //  if (!address.trim().equals(""))
                other_accounts.add(bean);
        }
        mOtherAcc = new ArrayList<>(other_accounts);
        reqBean.setAddress(other_accounts);
        return reqBean;
    }

    //method to add the dynamic view of address
    private void addAddressView(final String tagName, final String addressName) {
        if (mAddressLinearLayout.getChildCount() == 5)
            return;
        final View view = LayoutInflater.from(this).inflate(R.layout.address_input_lyt, mAddressLinearLayout, false);
        final EditText mTagName = (EditText) view.findViewById(R.id.address_single_tag);
        final EditText mAddressName = (EditText) view.findViewById(R.id.address_single_address);
        mTagName.setText(tagName);
        mAddressName.setText(addressName);
        view.findViewById(R.id.web_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddressLinearLayout.getChildCount()>1) {
              /*  if (!mAddressName.getText().toString().trim().isEmpty()
                        || !mTagName.getText().toString().trim().isEmpty()) {*/
                    Utility.showSimpleDialog(mThis, getString(R.string.delete_confirmation_txt), new DialogCallBack() {
                        @Override
                        public void okPressed() {
                            mAddressLinearLayout.removeView(view);
                        }
                    });
                }
               /* }*/
                //   mAddressLinearLayout.removeView(view);
            }
        });
        mAddressLinearLayout.addView(view);
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
                    PrefUtils.setSharedPrefStringData(UserAddressDetailActivity.this, PrefConstants.SCREEN_STEP,
                            mResponse.getResult().getStep_number() + "");
                    DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());

                    Intent in = new Intent(UserAddressDetailActivity.this, Logout.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_LOGOUT);
                    startActivity(in);
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
        reqBean.setStep(AppConstants.STEP_ADDRESS);
        return reqBean;
    }
}
