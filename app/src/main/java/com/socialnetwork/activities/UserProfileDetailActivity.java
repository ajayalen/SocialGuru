package com.socialnetwork.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.luminous.pick.CustomGallery;
import com.luminous.pick.controller.MediaFactory;
import com.socialnetwork.R;
import com.socialnetwork.base.ApplicationController;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.models.reqmodels.GeneralReqBean;
import com.socialnetwork.models.reqmodels.SaveProfileRequestBean;
import com.socialnetwork.models.responsemodels.GeneralResponseBean;
import com.socialnetwork.models.responsemodels.ResendOtpResponseBean;
import com.socialnetwork.msupport.MSupportConstants;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.AppUtils;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utility;
import com.socialnetwork.utils.Utils;
import com.socialnetwork.utils.ValidationHelper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserProfileDetailActivity extends BaseActivity
		implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener
{

	String[] Permission = { MSupportConstants.READ_EXTERNAL_STORAGE, MSupportConstants.WRITE_EXTERNAL_STORAGE };
	private ImageView mProfileImage;
	private String mImagePath = "";
	private MediaFactory mediaFactory;
	private LinearLayout mWebLinearLayout;
	private View mMainContainer;
	private EditText mUserNameET, mAboutYourselfET, mAgeET, mNameET;
	private EditText mHobbiesET, mDesignationET, mCompanyNameET;
	private ArrayList<String> mWebsiteLinksList;
	EditText ed;
	List<EditText> allEds = new ArrayList<EditText>();
	private String selectedGender = "";
	private String selectedPrivacy = "0";
	private ImageView mUsernameAcceptedIv;
	private Button mSaveBtn;
	private int backpress=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * Method to initialize ui parameters and will be called just after setContentView Method in any activity
	 */
	@Override
	public void initializeUi()
	{
		mediaFactory = MediaFactory.create();
		mWebsiteLinksList = new ArrayList<>();
		mProfileImage = (ImageView) findViewById(R.id.activity_profile_iv);
		mUserNameET = (EditText) findViewById(R.id.activity_profile_username_et);
		mNameET = (EditText) findViewById(R.id.activity_profile_name_et);
		mMainContainer = findViewById(R.id.main_container);
		mUsernameAcceptedIv = (ImageView) findViewById(R.id.username_accepted_iv);
		mWebLinearLayout = (LinearLayout) findViewById(R.id.activity_profile_website_link_lyt);
		CheckBox mPrivacyCheckbox = (CheckBox) findViewById(R.id.activity_profile_privacy_checkbox);
		mPrivacyCheckbox.setOnCheckedChangeListener(this);
		RadioGroup mGenderRG = (RadioGroup) findViewById(R.id.activity_profile_gender_rg);
		mAgeET = (EditText) findViewById(R.id.activity_profile_age_et);
		mAboutYourselfET = (EditText) findViewById(R.id.activity_profile_about_yourself_et);
		mHobbiesET = (EditText) findViewById(R.id.activity_profile_hobbies_n_interests_et);
		mCompanyNameET = (EditText) findViewById(R.id.activity_profile_company_name_et);
		mDesignationET = (EditText) findViewById(R.id.activity_profile_designation_et);
		EditText mAddWebsiteLinkET = (EditText) findViewById(R.id.activity_profile_website_link_et);
		mSaveBtn = (Button) findViewById(R.id.activity_profile_next_step_btn);
		ImageView mAddProfileImage = (ImageView) findViewById(R.id.activity_profile_add_image);
		mBackImageView.setVisibility(View.GONE);
		mNameET.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
				| InputType.TYPE_TEXT_FLAG_CAP_WORDS);
		/*      Glide.with(mThis).load(R.drawable.profile_dummy_image)
		        .asBitmap().centerCrop()
		        .placeholder(R.drawable.default_profile_image_holder)
		        .into(new BitmapImageViewTarget(mProfileImage) {
		            @Override
		            protected void setResource(Bitmap resource) {
		                RoundedBitmapDrawable circularBitmapDrawable =
		                        RoundedBitmapDrawableFactory.create(mThis.getResources(), resource);
		                circularBitmapDrawable.setCircular(true);
		                mProfileImage.setImageDrawable(circularBitmapDrawable);
		            }
		        });*/
		mTitleTextView.setText(getString(R.string.profile_details_label));
		mSaveBtn.setOnClickListener(this);
		mAddProfileImage.setOnClickListener(this);
		mProfileImage.setOnClickListener(this);
		mAddWebsiteLinkET.setOnClickListener(this);
		mGenderRG.setOnCheckedChangeListener(this);




		mUserNameET.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				String s = ((EditText) v).getText().toString();
				if (s.length() == 0)
				{
					mUsernameAcceptedIv.setVisibility(View.GONE);
					return;
				}
				if (!hasFocus)
				{

					if (new ValidationHelper(UserProfileDetailActivity.this).isValidUserName(mUserNameET,
							getString(R.string.enter_valid_username), false))
					{
						if (s.length() > 0)
							checkUsernameAvailable(s);
						else
							mUsernameAcceptedIv.setVisibility(View.GONE);

						mUserNameET.clearFocus();
					}
					else
					{
						mUsernameAcceptedIv.setVisibility(View.GONE);
					}

				}
			}
		});

		//        mUserNameET.addTextChangedListener(new TextWatcher() {
		//            @Override
		//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		//
		//            }
		//
		//            @Override
		//            public void onTextChanged(CharSequence s, int start, int before, int count) {
		//                if (s.length() > 0)
		//                    checkUsernameAvailable(s.toString());
		//                else
		//                    mUsernameAcceptedIv.setVisibility(View.GONE);
		//            }
		//
		//            @Override
		//            public void afterTextChanged(Editable s) {
		//
		//            }
		//        });
	}

	//method to check the username available
	private void checkUsernameAvailable(String username)
	{
		if (!ApplicationController.isConnected(mMainContainer))
			return;
		Call<GeneralResponseBean> call = AppRetrofit.getInstance().getApiServices()
				.checkUsername(checkUsernameApiParams(username));
		AppUtils.enqueueCall(call, new Callback<GeneralResponseBean>()
		{
			public GeneralResponseBean mResponse;

			@Override
			public void onResponse(Response<GeneralResponseBean> response, Retrofit retrofit)
			{
				mResponse = response.body();
				if (mResponse.isSuccess())
				{
					mUsernameAcceptedIv.setVisibility(View.VISIBLE);
				}
				else
					if (!mResponse.isSuccess())
					{
						DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
						mUserNameET.setText("");
						mUsernameAcceptedIv.setVisibility(View.GONE);
					}
			}

			@Override
			public void onFailure(Throwable t)
			{
				DialogUtil.showTryAgainToast(mThis);
				mUsernameAcceptedIv.setVisibility(View.GONE);
			}
		});
	}

	//method to set the request params for the username available api
	private GeneralReqBean checkUsernameApiParams(String username)
	{
		GeneralReqBean reqBean = new GeneralReqBean();
		reqBean.setUsername(username);
		return reqBean;
	}

	/**
	 * Method to return the activities layout res id
	 */
	@Override
	public int getLayoutId()
	{
		return R.layout.activity_user_profile_detail;
	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			//when add profile image icon is clicked
			case R.id.activity_profile_add_image:
				Utility.showImageChooserDialog(this);
				break;

			//when  profile image icon is clicked
			case R.id.activity_profile_iv:
				Utility.showImageChooserDialog(this);
				break;

			//when  profile image icon is clicked
			case R.id.activity_profile_website_link_et:
				Utils.hideKeyboard(mThis);
				addWebsiteView();
				break;

			//when  profile image icon is clicked
			case R.id.activity_profile_next_step_btn:
				if (new ValidationHelper(this).isProfileDataValid(mUserNameET, mNameET))
				{
					saveProfileDetails();
				}
				break;
		}
	}

	//method to call the api to save the profile details
	private void saveProfileDetails()
	{
		Utils.hideKeyboard(this);
		RequestBody fileBody = null;
		if (!TextUtils.isEmpty(mImagePath))
		{
			fileBody = RequestBody.create(MediaType.parse("image/*"), new File(mImagePath));
		}
		if (!ApplicationController.isConnected(mMainContainer))
			return;
		mSaveBtn.setEnabled(false);
		showProgresDialog();
		Call<ResendOtpResponseBean> call = AppRetrofit.getInstance().getApiServices().saveProfileDetails(fileBody,
				saveProfileApiParams());
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
					startActivity(new Intent(UserProfileDetailActivity.this, SocialPlatformsActivity.class));
					PrefUtils.setSharedPrefStringData(mThis, PrefConstants.SCREEN_STEP, AppConstants.STEP_SOCIAL);
					finish();
					PrefUtils.setSharedPrefStringData(mThis, PrefConstants.OTP, mResponse.getResult().getOtp());
				}
				else
					if (!mResponse.isSuccess())
					{
						mSaveBtn.setEnabled(true);
						DialogUtil.showSnackBar(mMainContainer, mResponse.getMessage());
					}
			}

			@Override
			public void onFailure(Throwable t)
			{
				mSaveBtn.setEnabled(true);
				hideProgresDialog();
				DialogUtil.showTryAgainToast(mThis);
			}
		});

	}

	//method to set the request params for the save profile details api
	private SaveProfileRequestBean saveProfileApiParams()
	{

		SaveProfileRequestBean reqBean = new SaveProfileRequestBean();
		reqBean.setUser_id(PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID));
		reqBean.setAbout(mAboutYourselfET.getText().toString());
		reqBean.setAge(mAgeET.getText().toString());
		reqBean.setCompany_name(mCompanyNameET.getText().toString());
		reqBean.setDesignation(mDesignationET.getText().toString());
		reqBean.setFirst_name(mNameET.getText().toString());
		reqBean.setHobbies(mHobbiesET.getText().toString());
		reqBean.setLast_name("");
		reqBean.setGender(selectedGender);
		reqBean.setIs_private(selectedPrivacy);
		getWebsiteList();
		reqBean.setUsername(mUserNameET.getText().toString());
		reqBean.setWebsite(mWebsiteLinksList);
		Gson gson = new Gson();
		String json = gson.toJson(reqBean);
		Lg.e("socialRequestjson", json);
		return reqBean;
	}

	//method to get the website links list
	private void getWebsiteList()
	{
		mWebsiteLinksList.clear();
		for (int i = 0; i < allEds.size(); i++)
		{
			mWebsiteLinksList.add(allEds.get(i).getText().toString());
		}
	}

	//method to add the dynamic website link view
	private void addWebsiteView()
	{
		if (mWebLinearLayout.getChildCount() == 5)
			return;

		final View view = LayoutInflater.from(this).inflate(R.layout.web_input_lyt, mWebLinearLayout, false);
		ed = (EditText) view.findViewById(R.id.input_et);
		view.findViewById(R.id.web_delete).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mWebLinearLayout.removeView(view);
			}
		});
		allEds.add(ed);
		mWebLinearLayout.addView(view);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode)
		{
			case MSupportConstants.REQUESTCODE_CAMERA:
			{
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					Utility.showImageChooserDialog(this);
				}
				else
				{
					finish();
				}
			}
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		ArrayList<String> all_path = mediaFactory.onActivityResult(requestCode, resultCode, data);
		if (all_path != null && all_path.size() > 0)
		{
			for (String string : all_path)
			{
				CustomGallery item = new CustomGallery();
				item.sdcardPath = string;
				item.sdCardUri = Uri.parse(string);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				mImagePath = string;
				// set the image picked from gallery or camera through glide
				Glide.with(mThis).load(mImagePath).asBitmap().centerCrop()
						.placeholder(R.drawable.default_profile_image_holder)
						.into(new BitmapImageViewTarget(mProfileImage)
						{
							@Override
							protected void setResource(Bitmap resource)
							{
								RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
										.create(mThis.getResources(), resource);
								circularBitmapDrawable.setCircular(true);
								mProfileImage.setImageDrawable(circularBitmapDrawable);
							}
						});
				if (!mImagePath.isEmpty())
				{
					mProfileImage.setBackground(getResources().getDrawable(R.drawable.circle_image_border));
					mProfileImage.setPadding(5, 5, 5, 5);
				}
				else
					mProfileImage.setPadding(0, 0, 0, 0);
			}
		}
	}

	/**
	 * <p>Called when the checked radio button has changed. When the
	 * selection is cleared, checkedId is -1.</p>
	 *
	 * @param group     the group in which the checked radio button has changed
	 * @param checkedId the unique identifier of the newly checked radio button
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		if (checkedId == R.id.male_rb)
		{
			selectedGender = "M";
		}
		if (checkedId == R.id.female_rb)
		{
			selectedGender = "F";
		}
	}

	/**
	 * Called when the checked state of a compound button has changed.
	 *
	 * @param buttonView The compound button view whose state has changed.
	 * @param isChecked  The new checked state of buttonView.
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (isChecked)
			selectedPrivacy = "1";
		else
			selectedPrivacy = "0";
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
