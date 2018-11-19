package com.socialnetwork.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;


public class BaseDialog extends Dialog{
	public View rootView;
	protected Context mContext;
	private LayoutInflater layoutInflater;

	public BaseDialog(Context context,boolean isOutSideTouch , boolean isCancelable, int dialogWidth, int dialogHeight,int layoutId) {
		super(context);
		mContext       					= 	context;
		implementDialog(isOutSideTouch , isCancelable , dialogWidth , dialogHeight,layoutId);
	}
	private void implementDialog(boolean isOutSideTouch , boolean isCancelable,
			int dialogWidth, int dialogHeight, int layoutId){
		layoutInflater = LayoutInflater.from(mContext);
		rootView       = layoutInflater.inflate(layoutId, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(isOutSideTouch);
		setContentView(rootView);
		setCancelable(isCancelable);

		DisplayDeviceConfiguration displayDeviceConfig = new DisplayDeviceConfiguration();
		displayDeviceConfig.getDisplayConfiguration();
		int width  = calculatingWidthAndHeight(displayDeviceConfig.getDeviceWidth(), dialogWidth);
		int height = calculatingWidthAndHeight(displayDeviceConfig.getDeviceHeight(),dialogHeight);
		setCancelable(isCancelable);
		setCanceledOnTouchOutside(isOutSideTouch);
		getWindow().setBackgroundDrawableResource(android.R.color.white);
		getWindow().setLayout(width, height);
		show();
	}

	private int calculatingWidthAndHeight(int screenValue, double newValue){
		return (int) Math.floor(screenValue * newValue/ 100.0);
	}

	private class DisplayDeviceConfiguration {
		int deviceWidth , deviceHeight ;
		private void getDisplayConfiguration(){
			DisplayMetrics displaymetrics = Resources.getSystem().getDisplayMetrics();
			deviceWidth					  = displaymetrics.widthPixels;
			deviceHeight 				  = displaymetrics.heightPixels;
		}

		private int getDeviceWidth() {
			return deviceWidth;
		}

		private int getDeviceHeight() {
			return deviceHeight;
		}
	}
	/**
	 *
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public <T> T findViewByIdAndCast(int id) {

		return (T) rootView.findViewById(id);
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
}

