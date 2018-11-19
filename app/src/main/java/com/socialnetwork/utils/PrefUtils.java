package com.socialnetwork.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.facebook.login.LoginManager;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.FlickrWrapper;
import com.socialnetwork.social.instagram.InstagramWrapper;
import com.socialnetwork.social.linkedin.LinkedinWrapper;
import com.socialnetwork.social.reddit.RedditWrapper;
import com.socialnetwork.social.tumblr.TumblrWrapper;
import com.socialnetwork.social.twitter.TwitterWrapper;

public class PrefUtils
{

	public static final String SHARED_PREF_NAME = "socialpapa";
	public static final String IS_LOGGEDIN = "is_logged_in";
	public static final String DEVICE_TOKEN = "device_token";
	private final SharedPreferences mSpref;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void logoutUser(Context context)
	{
		SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.clear();
		appInstallInfoEditor.apply();

		context.deleteDatabase("webview.db");
		context.deleteDatabase("webviewCache.db");
		FlickrWrapper.getInstance(context.getSharedPreferences(SocialConstants.FlickrConstants.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();

		context.getSharedPreferences("com.pinterest.android.pdk.PREF_FILE_KEY", Context.MODE_PRIVATE).edit().clear()
				.apply();

		InstagramWrapper.getInstance(context.getSharedPreferences(SocialConstants.InstagramConstant.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();

		LinkedinWrapper.getInstance(context.getSharedPreferences(SocialConstants.LinkedinConstant.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();

		RedditWrapper.getInstance(context.getSharedPreferences(SocialConstants.RedditConstants.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();

		LoginManager.getInstance().logOut();

		TwitterWrapper.getInstance(context.getSharedPreferences(SocialConstants.TwitterConstant.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();

		TumblrWrapper.getInstance(context.getSharedPreferences(SocialConstants.TumblrConstant.PREFS_NAME,
				Context.MODE_PRIVATE)).clearOauth();


	}

	public PrefUtils(Context context)
	{
		mSpref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
	}

	public static void setSharedPrefStringData(Context context, String key, String value)
	{
		SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putString(key, value);
		appInstallInfoEditor.apply();
	}

	public static boolean getSharedPrefBoolean(Context context, String key)
	{
		SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

		return userAcountPreference.getBoolean(key, false);
	}

	public static String getSharedPrefString(Context context, String key)
	{
		SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

		return userAcountPreference.getString(key, "");
	}

	public static float getSharedPrefFloat(Context context, String key)
	{
		SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

		return userAcountPreference.getFloat(key, 0);
	}

	public static void setSharedPrefBooleanData(Context context, String key, boolean value)
	{
		SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putBoolean(key, value);
		appInstallInfoEditor.apply();
	}

	public static void setSharedPrefFloatData(Context context, String key, float value)
	{
		SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
		appInstallInfoEditor.putFloat(key, value);
		appInstallInfoEditor.apply();
	}

}