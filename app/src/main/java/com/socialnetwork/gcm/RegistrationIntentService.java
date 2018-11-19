package com.socialnetwork.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.socialnetwork.R;
import com.socialnetwork.utils.Lg;
import com.socialnetwork.utils.PrefConstants;
import com.socialnetwork.utils.PrefUtils;

import de.greenrobot.event.EventBus;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private GcmRegistrationEvent gcmRegistrationEvent;
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        PrefUtils mPref = new PrefUtils(this);
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.app_id_gcm),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Lg.i(TAG, "GCM Registration Token: " + token);


            // TODO: Implement this method to send any registration to your app's servers.
            //sendRegistrationToServer(token);


            // Subscribe to topic channels


            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
         //   mPref.save(PrefConstants.GCM_TOKEN, token);
            PrefUtils.setSharedPrefStringData(this, PrefConstants.GCM_TOKEN, token);
            gcmRegistrationEvent = new GcmRegistrationEvent(true);
            gcmRegistrationEvent.setGcmToken(token);
            // [END register_for_gcm]
        } catch (Exception e) {
         //   Lg.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            PrefUtils.setSharedPrefStringData(this,PrefConstants.GCM_TOKEN, "");
            gcmRegistrationEvent = new GcmRegistrationEvent(false);
            gcmRegistrationEvent.setGcmToken("");
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        //Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        // LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        finally {
            EventBus.getDefault().post(gcmRegistrationEvent);
        }

    }
}
