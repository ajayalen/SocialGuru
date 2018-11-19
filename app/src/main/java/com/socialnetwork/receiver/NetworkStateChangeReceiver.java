package com.socialnetwork.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;


import com.socialnetwork.models.eventmodels.NetworkStateChangeEvent;
import com.socialnetwork.utils.PrefUtils;
import com.socialnetwork.utils.Utils;

import de.greenrobot.event.EventBus;

/**
 * Created by Rahil on 11/16/2015.
 */
public class NetworkStateChangeReceiver extends WakefulBroadcastReceiver {

    PrefUtils mPref;

    @Override
    public void onReceive(Context context, Intent intent) {
        mPref = new PrefUtils(context);


        if (Utils.isNetworkAvailable(context)) { // connected to the internet
            EventBus.getDefault().post(new NetworkStateChangeEvent(true));
            // boolean isLoggedIn = mPref.getBooleanValue(PrefConstants.IS_LOGGED_IN);
            //if (isLoggedIn) syncApis();
            //AppController.getInstance().setIsConnected(true);

        } else {
            // not connected to the internet
            //AppController.getInstance().setIsConnected(true);
            EventBus.getDefault().post(new NetworkStateChangeEvent(false));
        }

    }

    private void syncApis() {
        //mContext.startService(new Intent(mContext, SettingApiService.class));
    }
}
