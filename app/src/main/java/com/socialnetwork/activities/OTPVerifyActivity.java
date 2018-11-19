package com.socialnetwork.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.models.reqmodels.VerifyOtpRequestBean;
import com.socialnetwork.models.responsemodels.GeneralResponseBean;
import com.socialnetwork.models.responsemodels.ResendOtpResponseBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class OTPVerifyActivity extends BaseActivity implements View.OnClickListener
{

	private EditText mFourthOtp;
	private EditText mFirstOtp;
	private EditText mSecondOtp;
	private EditText mThirdOtp;
	private TextView mVerifyTextView;
	private View mMainContainer;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initializeUi()
	{
		mFirstOtp = (EditText) findViewById(R.id.otp_one);
		mSecondOtp = (EditText) findViewById(R.id.otp_two);
		mThirdOtp = (EditText) findViewById(R.id.otp_three);
		mFourthOtp = (EditText) findViewById(R.id.otp_four);
		mVerifyTextView = (TextView) findViewById(R.id.verify_otp);
		mMainContainer = findViewById(R.id.main_container);
		TextView mResendOtpTv = (TextView) findViewById(R.id.resend_otp_tc);
		mResendOtpTv.setOnClickListener(this);
		mVerifyTextView.setOnClickListener(this);
		mTitleTextView.setText(getString(R.string.social_papa_setup_label));
	//	mBackImageView.setVisibility(View.GONE);
		setEditTextChangeListner();

		mBackImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

	}

	@Override
	public int getLayoutId()
	{
		return R.layout.activity_otpverify;
	}

	private void setEditTextChangeListner()
	{

		mFirstOtp.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (!mFirstOtp.getText().toString().isEmpty())
					mSecondOtp.requestFocus();

			}
		});

		mSecondOtp.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				mFirstOtp.requestFocus();
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (!mSecondOtp.getText().toString().isEmpty())
					mThirdOtp.requestFocus();
			}
		});

		mThirdOtp.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				mSecondOtp.requestFocus();
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (!mThirdOtp.getText().toString().isEmpty())
					mFourthOtp.requestFocus();
			}
		});
		mFourthOtp.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				//   setAlphaColorOnNextButton();
				//  mDigit3.requestFocus();
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (!mFourthOtp.getText().toString().isEmpty())
				{
					//                    Utility.closeSoftInputKeyPad(VerifyMobileNumberActivity.this, mFourthOtp);

				}
				else
				{
					mThirdOtp.requestFocus();

				}

			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.verify_otp:
				Utils.hideKeyboard(mThis);
				if(getOtpString().length()>0)
				callVerifyOtpApi();
				else
				DialogUtil.showSnackBar(mMainContainer,getString(R.string.fill_otp));
				break;
			case R.id.resend_otp_tc:
				Utils.hideKeyboard(mThis);
				callResendOtpApi();
				break;

		}
	}



	@Override
	public void onBackPressed() {
		PrefUtils.setSharedPrefStringData(OTPVerifyActivity.this,PrefConstants.SCREEN_STEP,AppConstants.STEP_LOGIN);

		super.onBackPressed();

	}

	//method to set the request params for the resend otp api
	private void callResendOtpApi()
	{
		if (!ApplicationController.isConnected(mMainContainer))
			return;

		showProgresDialog();
		Call<ResendOtpResponseBean> call = AppRetrofit.getInstance().getApiServices().resendOtp(resendOtpApiParams());
		AppUtils.enqueueCall(call, new Callback<ResendOtpResponseBean>()
		{

			public ResendOtpResponseBean mResponse;

			@Override
			public void onResponse(Response<ResendOtpResponseBean> response, Retrofit retrofit)
			{
				hideProgresDialog();
				mResponse = response.body();
				if (mResponse.isSuccess())
				{
					PrefUtils.setSharedPrefStringData(mThis, PrefConstants.OTP, mResponse.getResult().getOtp());
					DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
				}
				else
					if (!mResponse.isSuccess())
					{
						DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
					}
			}

			@Override
			public void onFailure(Throwable t)
			{
				hideProgresDialog();
				DialogUtil.showTryAgainToast(mThis);
			}
		});
	}

	//method to set the request params for resend the otp api
	private VerifyOtpRequestBean resendOtpApiParams()
	{
		VerifyOtpRequestBean requestBean = new VerifyOtpRequestBean();
		requestBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
		return requestBean;
	}

	//method to get the string of the otp
	private String getOtpString()
	{
		return mFirstOtp.getText().toString() +
				mSecondOtp.getText().toString() +
				mThirdOtp.getText().toString() +
				mFourthOtp.getText().toString();
	}

	//method to call the verify otp api
	private void callVerifyOtpApi()
	{
		if (!ApplicationController.isConnected(mMainContainer))
			return;
		showProgresDialog();
		mVerifyTextView.setEnabled(false);
		Call<GeneralResponseBean> call = AppRetrofit.getInstance().getApiServices().verifyOtp(verifyOtpApiParams());
		AppUtils.enqueueCall(call, new Callback<GeneralResponseBean>()
		{

			public GeneralResponseBean mResponse;

			@Override
			public void onResponse(Response<GeneralResponseBean> response, Retrofit retrofit)
			{
				hideProgresDialog();
				mResponse = response.body();
				if (mResponse.isSuccess())
				{

					String currentStep = PrefUtils.getSharedPrefString(mThis, PrefConstants.SCREEN_STEP);

					if(currentStep.equals(AppConstants.STEP_LOGIN))
						PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_PROFILE);

					redirectAccToStep();

//					startActivity(new Intent(mThis, UserProfileDetailActivity.class));
//					finish();
				}
				else
					if (!mResponse.isSuccess())
					{
						mVerifyTextView.setEnabled(true);
						DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
					}
			}

			@Override
			public void onFailure(Throwable t)
			{
				mVerifyTextView.setEnabled(true);
				hideProgresDialog();
				DialogUtil.showTryAgainToast(mThis);
			}
		});

	}



    //method to set thq request params for the verify otp api
	private VerifyOtpRequestBean verifyOtpApiParams()
	{
		VerifyOtpRequestBean requestBean = new VerifyOtpRequestBean();
		requestBean.setOtp(getOtpString());
		requestBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
		return requestBean;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	public void setCode(String message)
	{

		Lg.d(TAG, "otp from sms--" + message);

	}
}
