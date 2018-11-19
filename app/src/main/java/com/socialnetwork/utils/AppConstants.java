package com.socialnetwork.utils;

import android.net.Uri;
import android.os.Environment;

public class AppConstants {

    public final static String  PINTEREST_APP_ID = "4854343444341992090";
    public final static int CAMERA_APP = 100;
    public static final int GALLERY_APP = 101;
    public static final String APP_NAME = "SocialPapa";
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/";
    public static Uri mCamRequestedUri;
    public final static String CUSTOM_DIALOG_FLAG = "custom_dialog_flag";
    public final static String DIALOG_IMAGE = "custom_dialog_image";
    public final static String CUSTOM_POPUP = "custom_popup";
    public final static String SUCCESS_DIALOG = "single_button_image";
    public final static String ADD_VIEW_DIALOG = "add_view_dialog";
    public final static String INFO_DIALOG = "info_dialog";
    public final static String COUNTRY_CODE_DEFAULT = "91";
    public final static String DEVICE_TYPE = "1";
    public final static String ENTERED_NUMBER = "entered_number";
    public final static String CONTEXT = "context";
    public final static String STEP_LOGIN = "0";
    public final static String STEP_PROFILE = "1";
    public final static String STEP_SOCIAL = "2";
    public final static String STEP_EMAIL = "3";
    public final static String STEP_PHONE = "4";
    public final static String STEP_ADDRESS = "5";
    public final static String STEP_LOGOUT = "6";
    public final static String SOCIAL_MEDIA_ID = "social_media_id";
    public final static String SOCIAL_MEDIA_MAIL = "social_media_mail";
    public final static String SOCIAL_PROFILE_FLAG = "social_media_mail";
    public final static String LINKED_PLATFORM_NAME = "linked_platform_name";
    public final static String LINKED_PLATFORM_PROFILE = "linked_platform_profile";
    public final static String LINKED_PLATFORM_USERNAME = "linked_platform_username";
    public final static String LINKED_PLATFORM_ID= "linked_platform_id";
    public final static String DELETE_SOCIAL_FLAG= "delete_social_flag";
}
