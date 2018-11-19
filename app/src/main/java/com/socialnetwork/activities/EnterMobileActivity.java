package com.socialnetwork.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.fragments.AlertFragment;
import com.socialnetwork.models.reqmodels.RegisterLoginRequestBean;
import com.socialnetwork.models.responsemodels.RegisterLoginResponseBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utils;
import com.socialnetwork.utils.ValidationHelper;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EnterMobileActivity extends BaseActivity implements View.OnClickListener {


    private View mMainContainer;
    private EditText mPhoneNoEt;
    private EditText mPhoneCountryCode;
    public RegisterLoginResponseBean mLoginResponse;
    private TextView mContibueBtn;
    private int backpress = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //method to initialize the UI and the values
    @Override
    public void initializeUi() {
        mContibueBtn = (TextView) findViewById(R.id.enter_mobile_login_btn);
        mPhoneCountryCode = (EditText) findViewById(R.id.enter_phone_country_code);
        mPhoneNoEt = (EditText) findViewById(R.id.enter_phone_number);
        mMainContainer = findViewById(R.id.main_container);
        mBackImageView.setVisibility(View.GONE);
        mTitleTextView.setText(getString(R.string.social_papa_setup_label));
        mContibueBtn.setOnClickListener(this);
        String countrycode = PrefUtils.getSharedPrefString(mThis, PrefConstants.COUNTRY_CODE);

        if (countrycode.isEmpty()) {
            mPhoneCountryCode.setFocusable(true);
            mPhoneCountryCode.setFocusableInTouchMode(true);
//            mPhoneCountryCode.setText("+");
            mPhoneCountryCode.setSelection(mPhoneCountryCode.getText().length());
        } else {
            mPhoneCountryCode.setFocusable(false);
            mPhoneCountryCode.setFocusableInTouchMode(false);
            mPhoneCountryCode.setText(countrycode);
        }
//        setTextChangeListener();
        //for demo purpose
//        mPhoneCountryCode.setText("+91");

        findViewById(R.id.enter_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoneCountryCode.requestFocus();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder().requestProfile().requestId().requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mGoogleApiClient.connect();

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void setTextChangeListener() {
        mPhoneCountryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0) {
                    if (s.length() == 0) {
                        mPhoneCountryCode.setText("+");
                        mPhoneCountryCode.setSelection(mPhoneCountryCode.getText().toString().length());
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_enter_mobile;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //when continue btn is clicked and to move to UserProfile screen
            case R.id.enter_mobile_login_btn:
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                }

                Utils.hideKeyboard(mThis);
                if (validData()) {
                    callRegisterLoginApi();
                }
                break;
        }
    }

    //method to call the register login api
    private void callRegisterLoginApi() {
        if (!ApplicationController.isConnected(mMainContainer)) return;
        mContibueBtn.setEnabled(false);
        showProgresDialog();
        Call<RegisterLoginResponseBean> call = AppRetrofit.getInstance().getApiServices().registerLogin(registerLoginApiParams());
        AppUtils.enqueueCall(call, new Callback<RegisterLoginResponseBean>() {
            @Override
            public void onResponse(Response<RegisterLoginResponseBean> response, Retrofit retrofit) {
                mLoginResponse = response.body();
                if (mLoginResponse.isSuccess()) {
                    hideProgresDialog();
                    mContibueBtn.setEnabled(true);
                    saveUserData();
                    showInfoDialog();
                  /*  if (mLoginResponse.getResult().getIs_phone_verified().equalsIgnoreCase("0"))
                        showInfoDialog();
                    else
                        startActivity(new Intent(mThis, UserProfileDetailActivity.class));*/
                } else if (!mLoginResponse.isSuccess()) {
                    mContibueBtn.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                mContibueBtn.setEnabled(true);
                hideProgresDialog();
                DialogUtil.showTryAgainToast(mThis);
            }
        });
    }

    //method to save the user data
    private void saveUserData() {
        PrefUtils.setSharedPrefStringData(mThis, PrefConstants.USER_ID, mLoginResponse.getResult().getUser_id());
        PrefUtils.setSharedPrefStringData(mThis, PrefConstants.OTP, mLoginResponse.getResult().getUser_id());
        PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, mLoginResponse.getResult().getStep());
        PrefUtils.setSharedPrefStringData(mThis, PrefConstants.IS_PHONE_VERIFIED, mLoginResponse.getResult().getIs_phone_verified());
    }

    //method to display the info dialog
    private void showInfoDialog() {
        DialogFragment newFragment = AlertFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.CUSTOM_DIALOG_FLAG, AppConstants.INFO_DIALOG);
        bundle.putString(AppConstants.ENTERED_NUMBER, mPhoneNoEt.getText().toString());
        bundle.putString(AppConstants.COUNTRY_CODE_DEFAULT, "+" + mPhoneCountryCode.getText().toString());
        // set Fragment class Arguments
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "dialog");
    }

    //method to set the reuest params for the register login api
    private RegisterLoginRequestBean registerLoginApiParams() {
        RegisterLoginRequestBean reqBean = new RegisterLoginRequestBean();
        reqBean.setCountry_code("+" + mPhoneCountryCode.getText().toString());
        reqBean.setDevice_token(PrefUtils.getSharedPrefString(mThis, PrefConstants.GCM_TOKEN));
        reqBean.setDevice_type(AppConstants.DEVICE_TYPE);
        reqBean.setMobile(mPhoneNoEt.getText().toString());
        reqBean.setPush_certificate("");
        return reqBean;
    }

    //method to set the field validations before continue to next screen
    private boolean validData() {
        ValidationHelper validatorHelper = null;
        if (ValidationHelper.isBlank(mPhoneCountryCode, getString(R.string.code_empty_txt))) {
            return false;
        } else if (!ValidationHelper.hasMinimumLength(mPhoneCountryCode, 1, getString(R.string.enter_valid_country_code))) {
            return false;
        } else if (ValidationHelper.isBlank(mPhoneNoEt, getString(R.string.number_empty_txt))) {
            return false;
        } else if (!ValidationHelper.isNoValid(mPhoneNoEt, getString(R.string.number_valid_txt))) {
            return false;
        }
        return true;
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
