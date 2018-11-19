package com.socialnetwork.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socialnetwork.R;
import com.socialnetwork.activities.Logout;
import com.socialnetwork.activities.OTPVerifyActivity;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.models.eventmodels.AddPlatformEventBean;
import com.socialnetwork.models.reqmodels.AddCustomPlatformReqBean;
import com.socialnetwork.models.responsemodels.AddPlatformResBean;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;
import com.socialnetwork.utils.Utils;
import com.socialnetwork.utils.ValidationHelper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AlertFragment extends DialogFragment
{

	private String platformType = "";
	private String mImagePath = "";
	private EditText mInfoET;

	/**
	 * Create a new instance of MyDialogFragment, providing "num"
	 * as an argument.
	 */
	public static AlertFragment newInstance()
	{
		AlertFragment f = new AlertFragment();

		/*  // Supply num input as an argument.
		Bundle args = new Bundle();
		//        args.putInt("num", num);
		f.setArguments(args);
		*/
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		if ((getArguments().get(AppConstants.CUSTOM_DIALOG_FLAG)) != null && (getArguments().get(AppConstants.CUSTOM_DIALOG_FLAG)).toString().equalsIgnoreCase(AppConstants.INFO_DIALOG)) {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

			// ...Irrelevant code for customizing the buttons and title
			View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_rounded_corner, null);
			dialogBuilder.setView(dialogView);

			final AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

			TextView mNumberTv = (TextView) dialogView.findViewById(R.id.entered_number_tv);
			mNumberTv.setText("(" + getArguments().get(AppConstants.COUNTRY_CODE_DEFAULT).toString() + ") "
					+ getArguments().get(AppConstants.ENTERED_NUMBER).toString());
			dialogView.findViewById(R.id.dialog_edit).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
				}
			});
			dialogView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
					getActivity().startActivity(new Intent(getActivity(), OTPVerifyActivity.class));

				}
			});
			return alertDialog;

			//        alertDialog.show();
		}
		if ((getArguments().get(AppConstants.CUSTOM_DIALOG_FLAG)).toString()
				.equalsIgnoreCase(AppConstants.ADD_VIEW_DIALOG))
		{
			final View mMianContainer;
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

			// ...Irrelevant code for customizing the buttons and title
			View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_view_dialog, null);
			dialogBuilder.setView(dialogView);
			final AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			TextView mInfoTv = (TextView) dialogView.findViewById(R.id.dialog_add_text_tv);
			ImageView mInfoIv = (ImageView) dialogView.findViewById(R.id.dialog_image);
			mMianContainer = dialogView.findViewById(R.id.main_container);
			mInfoET = (EditText) dialogView.findViewById(R.id.dialog_add_et);
			mInfoET.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS);
			final String dialogType = getArguments().get(AppConstants.DIALOG_IMAGE).toString();
			if (dialogType.equalsIgnoreCase(getString(R.string.mail_txt)))
			{
				mInfoTv.setText(getString(R.string.add_new_mail));
				Glide.with(getActivity()).load(R.drawable.new_mail_icon).asBitmap().into(mInfoIv);
				platformType = "2";
			}
			if (dialogType.equalsIgnoreCase(getString(R.string.social_label)))
			{
				mInfoTv.setText(getString(R.string.add_new_social_platform));
				Glide.with(getActivity()).load(R.drawable.add_new_platform).asBitmap().into(mInfoIv);
				platformType = "1";
			}
			if (dialogType.equalsIgnoreCase(getString(R.string.contacts_txt)))
			{
				mInfoTv.setText(getString(R.string.add_more_contacts));
				Glide.with(getActivity()).load(R.drawable.add_more_contacts).asBitmap().into(mInfoIv);
				platformType = "3";

			}

			dialogView.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					alertDialog.dismiss();

				}
			});
			dialogView.findViewById(R.id.dialog_done_btn).setOnClickListener(new View.OnClickListener()
			{
				public String mPlatformName = "";

				@Override
				public void onClick(View v)
				{
					mPlatformName = mInfoET.getText().toString();
					String validPlatformName = Utility.removeWhiteSpaceFromString(mPlatformName);
					if (validData())
					{
						if (!validPlatformName.isEmpty())
						{
							callAddPlatformApi(mMianContainer, validPlatformName);
						}
						else
							DialogUtil.showSnackBar(mMianContainer, getString(R.string.platform_name_empty_txt));
					}
					else
						DialogUtil.showSnackBar(mMianContainer, getString(R.string.platform_name_empty_txt));
				}
			});

			return alertDialog;

		}
		if ((getArguments().get(AppConstants.CUSTOM_DIALOG_FLAG)).toString()
				.equalsIgnoreCase(AppConstants.SUCCESS_DIALOG))
		{
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

			// ...Irrelevant code for customizing the buttons and title
			View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.success_dialog_view, null);
			dialogBuilder.setView(dialogView);
			final AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			alertDialog.setCanceledOnTouchOutside(false);
			dialogView.findViewById(R.id.dialog_ok_btn).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					alertDialog.dismiss();
					Intent in = new Intent(getActivity(), Logout.class);
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					PrefUtils.setSharedPrefStringData(getActivity(), PrefConstants.SCREEN_STEP, AppConstants.STEP_LOGOUT);
					startActivity(in);
				}
			});
			TextView mInfoTv = (TextView) dialogView.findViewById(R.id.success_dialog_tv);
			ImageView mInfoIv = (ImageView) dialogView.findViewById(R.id.success_dialog_image);
			mInfoTv.setText(getString(R.string.details_saved_successfully));
			return alertDialog;
		}
		return null;

	}

	//method to call the api to add the platform
	private void callAddPlatformApi(final View mMainContainer, final String mPlatformName)
	{
		Utils.hideKeyboard(getActivity());
		RequestBody fileBody = null;
		if (!TextUtils.isEmpty(mImagePath))
		{
			fileBody = RequestBody.create(MediaType.parse("image/*"), new File(mImagePath));
		}
		if (!ApplicationController.isConnected(mMainContainer))
			return;
		((BaseActivity) getActivity()).showProgressDialog();
		Call<AddPlatformResBean> call = AppRetrofit.getInstance().getApiServices().addPlatform(fileBody,
				addPlatformpApiParams(mPlatformName));
		AppUtils.enqueueCall(call, new Callback<AddPlatformResBean>()
		{
			public AddPlatformResBean mResponse;

			@Override
			public void onResponse(Response<AddPlatformResBean> response, Retrofit retrofit)
			{
				((BaseActivity) getActivity()).hideProgressDialog();
				mResponse = response.body();
				if (mResponse.isSuccess())
				{
					AlertFragment.this.dismiss();
					AddPlatformEventBean mailBean = new AddPlatformEventBean();
					mailBean.setMailText(mPlatformName);
					mailBean.setPlatformType(platformType);
					mailBean.setSocialId(mResponse.getResult().getSocial_media_id());
					EventBus.getDefault().post(mailBean);
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
				((BaseActivity) getActivity()).hideProgressDialog();
				//       DialogUtil.showTryAgainToast(getActivity());
			}
		});

	}

	//method to set the request params for the platform api
	private AddCustomPlatformReqBean addPlatformpApiParams(String platformName)
	{
		AddCustomPlatformReqBean reqBean = new AddCustomPlatformReqBean();
		reqBean.setColor_code("");
		reqBean.setType(platformType);
		reqBean.setNew_platform_name(platformName);
		reqBean.setUser_id(PrefUtils.getSharedPrefString(getActivity(), PrefConstants.USER_ID));
		return reqBean;
	}

	//method to set the field validations before continue to next screen
	private boolean validData()
	{
		ValidationHelper validatorHelper = null;
		/* if (!isSignupViafb && !isSignupViaInstagram) {*/
		return !ValidationHelper.isBlank(mInfoET, getString(R.string.platform_name_empty_txt));
	}
}
