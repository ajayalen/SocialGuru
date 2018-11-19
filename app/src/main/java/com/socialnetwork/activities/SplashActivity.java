package com.socialnetwork.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.socialnetwork.R;
import com.socialnetwork.base.BaseActivity;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.IsoToPhone;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity implements View.OnClickListener
{

	private static final String TAG = SplashActivity.class.getSimpleName();
	private static final int SPLASH_DURATION = 2000;
	private Timer mTimer;
	private Boolean mIsTimerCancelled = false;
	private TimerTask mTask;
	private TextView mLoginTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initializeUi()
	{
		mLoginTextView = (TextView) findViewById(R.id.login_btn);
		mLoginTextView.setOnClickListener(this);
		initTimerTask();
		mTimer = new Timer();
		mTimer.schedule(mTask, SPLASH_DURATION);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getSimCountryIso();

		if (countryCode != null)
			countryCode = IsoToPhone.getPhone(countryCode);

		if (countryCode == null)
			countryCode = "";

		countryCode = countryCode.replace("+", "");
		PrefUtils.setSharedPrefStringData(mThis, PrefConstants.COUNTRY_CODE, countryCode);
	}

	@Override
	public int getLayoutId()
	{
		return R.layout.activity_splash;
	}

	/**
	 * init the timer task, which is scheduled to run in future
	 */
	private void initTimerTask()
	{
		mTask = new TimerTask()
		{
			@Override
			public void run()
			{
				if (!mIsTimerCancelled)
				{
					//                    startActivity(new Intent(SplashActivity.this, SocialPlatformsActivity.class));
					//                    finish();
					runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							if (PrefUtils.getSharedPrefString(mThis, PrefConstants.USER_ID).equals("")
									|| PrefUtils.getSharedPrefString(SplashActivity.this, PrefConstants.SCREEN_STEP)
											.equals(AppConstants.STEP_LOGIN))
								mLoginTextView.setVisibility(View.VISIBLE);
							else
							{
								redirectAccToStep();
								finish();
							}
						}
					});

				}

			}
		};
	}

	@Override
	public void onBackPressed()
	{
		if (mTimer != null)
			mTimer.cancel();
		mIsTimerCancelled = true;
		super.onBackPressed();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.login_btn:
				startActivity(new Intent(SplashActivity.this, EnterMobileActivity.class));
				finish();
				break;
		}
	}
}
