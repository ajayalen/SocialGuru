package com.socialnetwork.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.socialnetwork.R;
import com.socialnetwork.activities.Logout;
import com.socialnetwork.activities.SocialPlatformsActivity;
import com.socialnetwork.activities.UserAddressDetailActivity;
import com.socialnetwork.activities.UserContactsDetailActivity;
import com.socialnetwork.activities.UserMailDetailActivity;
import com.socialnetwork.activities.UserProfileDetailActivity;
import com.socialnetwork.customui.ProgressWheel;
import com.socialnetwork.gcm.RegistrationIntentService;
import com.socialnetwork.interfaces.BaseInterfaces;
import com.socialnetwork.models.eventmodels.LeftDrawerToggleEvent;
import com.socialnetwork.models.eventmodels.RightDrawerToggleEvent;
import com.socialnetwork.services.ApiServices;
import com.socialnetwork.services.AppRetrofit;
import com.socialnetwork.utils.AppConstants;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utils;

import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity implements BaseInterfaces
{
	public static int API_LEVEL;
	public static boolean isLollipopOrGreater;
	protected static String TAG = "";
	//  private static int uiOptions;
	//  private static View mDecorView;
	// public PreferenceUtil mPreference;
	protected BaseActivity mThis;
	protected Resources mRes;
	//   protected ApplicationController mApplication;
	protected ProgressDialog mProgressDialog;
	protected ApiServices mApiServices;
	public DrawerLayout mDrawerLayout;
	protected Toolbar mToolbar;
	protected ProgressWheel mToolBarProgressWheel;
	private ActionBarDrawerToggle mDrawerToggle;
	public TextView mToolbarTitleTv, mToolbarSubTitleTv;
	public View mToolbarTitleLayout;

	protected TextView mTitleTextView,mDeleteTV;
	protected ImageView mBackImageView, mAddMoreImageView;

	/* @Unbinder
	 ButterKnife.Unbinder unbinder;*/
	//protected LogInRespBean.LogInData mUserData;

	@Override
	protected void attachBaseContext(Context newBase)
	{
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent serviceIntent = new Intent(this, RegistrationIntentService.class);
		startService(serviceIntent);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mThis = this;
		TAG = getClass().getSimpleName();
		isLollipopOrGreater = Build.VERSION.SDK_INT >= 21;
		API_LEVEL = Build.VERSION.SDK_INT;
		//   mDecorView = getWindow().getDecorView();
		mRes = getResources();
		//  mPreference = new PreferenceUtil(this);
		//   mApplication = (ApplicationController) getApplication();

		if (Build.VERSION.SDK_INT >= 21)
		{
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}

		mApiServices = new AppRetrofit().getApiServices();
		//mUserData = new DbHelper().getUserData();
		if (getLayoutId() != 0)
			setContentView(getLayoutId());
		//  ButterKnife.bind(this);

		mTitleTextView = (TextView) findViewById(R.id.app_title);
		mToolBarProgressWheel = (ProgressWheel) findViewById(R.id.toolbar_progress);
		mBackImageView = (ImageView) findViewById(R.id.toolbar_menu_image);
		mAddMoreImageView = (ImageView) findViewById(R.id.toolbar_add_image);
		mDeleteTV=(TextView)findViewById(R.id.added_platform_delete);

		if (mBackImageView != null)
			mBackImageView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					finish();
				}
			});
		initializeUi();
		FacebookSdk.sdkInitialize(getApplicationContext());
	}

	@Override
	public void finish()
	{
		super.finish();
		//  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		/*if (id == R.id.action_settings) {
		    return true;
		}*/

		return super.onOptionsItemSelected(item);
	}

	public BaseActivity getBaseActivity()
	{
		return mThis;
	}

	/**
	 *
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	protected <T> T findViewByIdAndCast(int id)
	{
		return (T) findViewById(id);
	}

	public void callingActivity(Context mContext, Class<?> cls)
	{
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
	}

	public void closeProgressDialog()
	{
		DialogUtil.hideProgressDialog(mProgressDialog, this);
	}

	public void showProgressDialog()
	{
		if (mProgressDialog == null)
		{
			mProgressDialog = DialogUtil.showProgressDialog(this);
		}
		mProgressDialog.show();
	}

	public void hideProgressDialog()
	{
		if (mProgressDialog == null)
		{
			mProgressDialog = DialogUtil.showProgressDialog(this);
		}
		mProgressDialog.hide();
	}

	public void showProgresDialog()
	{
		mToolBarProgressWheel.setVisibility(View.VISIBLE);
		mToolBarProgressWheel.spin();
	}

	public void hideProgresDialog()
	{
		mToolBarProgressWheel.setVisibility(View.INVISIBLE);
	}

	protected void hideView(View view)
	{
		view.setVisibility(View.GONE);
	}

	protected void enableView(View view)
	{
		view.setEnabled(true);
	}

	protected void disableView(View view)
	{
		view.setEnabled(false);
	}

	protected void showView(View view)
	{
		view.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mThis = BaseActivity.this;
	}

	@Override
	protected void onPause()
	{
		super.onPause();

	}

	protected void setupRightDrawer(String titleRes, int drawerId, final int toggleMenuId, @MenuRes int menuRes,
			final Toolbar.OnMenuItemClickListener listener)
	{

		mDrawerLayout = (DrawerLayout) findViewById(drawerId);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.lbl_drawer_open,
				R.string.lbl_drawer_close)
		{
			//comment below if drawer callbacks are needed
			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);

				Lg.e(TAG, "drawer opened");
				EventBus.getDefault().post(new RightDrawerToggleEvent(true));
			}

			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
				Lg.e(TAG, "drawer closed");
				EventBus.getDefault().post(new RightDrawerToggleEvent(false));
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.setDrawerIndicatorEnabled(false);
		/*  mDrawerToggle.setHomeAsUpIndicator(R.drawable.back);*/
		mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				onBackPressed();
			}
		});
		/*  mToolbar = (Toolbar) findViewById(R.id.tool_bar);
		  mToolbar.setTitle(getString(titleRes));
		  mToolbar.inflateMenu(menuRes);*/
		//        setUpToolBar(titleRes, menuRes);
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				if (item.getItemId() == toggleMenuId)
				{
					if (mDrawerLayout.isDrawerOpen(GravityCompat.END))
					{
						mDrawerLayout.closeDrawer(GravityCompat.END);
					}
					else
					{
						mDrawerLayout.openDrawer(GravityCompat.END);
					}
					return true;
				}
				else
				{
					listener.onMenuItemClick(item);
				}
				return false;
			}
		});
	}

	/**
	 * This method sync the navigation drawer and the home icon,it also broadcasts the drawer open and close event
	 *
	 * @param drawerId
	 */
	protected void setupNavigation(int drawerId)
	{
		mDrawerLayout = (DrawerLayout) findViewById(drawerId);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.lbl_drawer_open,
				R.string.lbl_drawer_close)
		{
			//comment below if drawer callbacks are needed
			@Override
			public void onDrawerOpened(View drawerView)
			{
				super.onDrawerOpened(drawerView);
				Utils.hideKeyboard(mThis);
				Lg.e(TAG, "drawer opened");
				EventBus.getDefault().post(new LeftDrawerToggleEvent(true));
			}

			@Override
			public void onDrawerClosed(View drawerView)
			{
				super.onDrawerClosed(drawerView);
				Lg.e(TAG, "drawer closed");
				EventBus.getDefault().post(new LeftDrawerToggleEvent(false));
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//if you want to handle drawer navigation explicitly comment the code below
		mDrawerToggle.syncState();
	}

	protected void setupNavigation(int drawerId, int homeIconId)
	{
		setupNavigation(drawerId);
		mDrawerToggle.setDrawerIndicatorEnabled(false);
		mDrawerToggle.setHomeAsUpIndicator(homeIconId);
		mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utils.hideKeyboard(mThis);
				if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
				{
					mDrawerLayout.closeDrawer(GravityCompat.START);
				}
				else
				{
					mDrawerLayout.openDrawer(GravityCompat.START);
				}
			}
		});
	}

	public void closeDrawer()
	{
		mDrawerLayout.closeDrawer(GravityCompat.START);
	}

	protected void redirectAccToStep()
	{

		String currentStep = PrefUtils.getSharedPrefString(mThis, PrefConstants.SCREEN_STEP);

		//		currentStep = String.valueOf(Integer.parseInt(currentStep) + 1);

		if (currentStep.equals(AppConstants.STEP_PROFILE))
		{
			Intent in = new Intent(mThis, UserProfileDetailActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(in);
		}

		else
			if (currentStep.equals(AppConstants.STEP_SOCIAL))
			{
				Intent in = new Intent(mThis, SocialPlatformsActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(in);
			}
			else
				if (currentStep.equals(AppConstants.STEP_EMAIL))
				{

					Intent in = new Intent(mThis, SocialPlatformsActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);

					//					Intent in = new Intent(mThis, UserMailDetailActivity.class);
					//					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					//					startActivity(in);
					startActivity(new Intent(mThis, UserMailDetailActivity.class));
				}

				else
					if (currentStep.equals(AppConstants.STEP_PHONE))
					{

						Intent in = new Intent(mThis, SocialPlatformsActivity.class);
						in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(in);

						startActivity(new Intent(mThis, UserMailDetailActivity.class));

						//						Intent in = new Intent(mThis, UserContactsDetailActivity.class);
						//						in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						//						startActivity(in);

						startActivity(new Intent(mThis, UserContactsDetailActivity.class));

					}

					else
						if (currentStep.equals(AppConstants.STEP_ADDRESS))
						{

							Intent in = new Intent(mThis, SocialPlatformsActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(in);

							startActivity(new Intent(mThis, UserMailDetailActivity.class));
							startActivity(new Intent(mThis, UserContactsDetailActivity.class));

							//							Intent in = new Intent(mThis, UserAddressDetailActivity.class);
							//							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
							//							startActivity(in);

							startActivity(new Intent(mThis, UserAddressDetailActivity.class));
						}
						else
							if (currentStep.equals(AppConstants.STEP_LOGOUT))
							{
								Intent in = new Intent(mThis, Logout.class);
								in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(in);
							}
	}

}
