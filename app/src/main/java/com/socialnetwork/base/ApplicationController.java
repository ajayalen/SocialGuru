package com.socialnetwork.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.socialnetwork.R;
import com.socialnetwork.models.eventmodels.NetworkStateChangeEvent;
import com.socialnetwork.utils.DialogUtil;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.Utils;

import de.greenrobot.event.EventBus;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by sakshi on 12/2/16.
 */
public class ApplicationController extends Application implements Application.ActivityLifecycleCallbacks
{
	private static boolean IS_CONNECTED;
	private static int numRunningActivities = 0;
	private static String TAG;
	private Activity mAct;


	public static boolean isConnected()
	{
		return IS_CONNECTED;
	}

	public boolean isConnected(boolean showToast)
	{
		if (!IS_CONNECTED)
			Toast.makeText(this, getString(R.string.no_network_msg), Toast.LENGTH_SHORT).show();
		return IS_CONNECTED;
	}

	public static boolean isConnected(View view)
	{
		if (!IS_CONNECTED)
			DialogUtil.showNoNetworkSnackBar(view);
		return IS_CONNECTED;
	}

	public static boolean isConnected(View view, View.OnClickListener retryListener)
	{
		if (!IS_CONNECTED)
			DialogUtil.showNoNetworkSnackBar(view, retryListener);
		return IS_CONNECTED;
	}

	public static boolean isAppInForeground()
	{
		return numRunningActivities > 0;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		numRunningActivities = 0;
		EventBus.getDefault().register(this);

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Lato-Regular.ttf")
				.setFontAttrId(R.attr.fontPath).build());
		//   ActiveAndroid.initialize(this, true);
		IS_CONNECTED = Utils.isNetworkAvailable(this);

		registerActivityLifecycleCallbacks(this);

	}

	public void setIsConnected(boolean connected)
	{
		IS_CONNECTED = connected;
	}

	@Override
	public void onTerminate()
	{
		EventBus.getDefault().unregister(this);
		//   ActiveAndroid.dispose();
		super.onTerminate();
	}

	/**
	 * This event listens to any network change in application
	 *
	 * @param networkStateChangeEvent contains the info about the state of the network
	 */
	public void onEvent(NetworkStateChangeEvent networkStateChangeEvent)
	{
		IS_CONNECTED = networkStateChangeEvent.isNetworkAvailable();
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState)
	{

	}

	@Override
	public void onActivityStarted(Activity activity)
	{
		numRunningActivities++;
	}

	@Override
	public void onActivityResumed(Activity activity)
	{
		mAct = activity;
	}

	@Override
	public void onActivityPaused(Activity activity)
	{

	}

	@Override
	public void onActivityStopped(Activity activity)
	{
		numRunningActivities--;
		if (numRunningActivities == 0)
		{
			Lg.i(TAG, "No running activities left, app has likely entered the background.");
		}
		else
		{
			Lg.i(TAG, numRunningActivities + " activities remaining");
		}
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState)
	{

	}

	@Override
	public void onActivityDestroyed(Activity activity)
	{

	}

}
