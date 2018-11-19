package com.socialnetwork.models.eventmodels;

/**
 * Created by techahead on 10/2/16.
 */
public interface DialogCallback {
        <T>void onSelect(T data, int pos, boolean isChecked);


}
