package com.socialnetwork.services;

import com.socialnetwork.models.reqmodels.AddAddressDetailsReqBean;
import com.socialnetwork.models.reqmodels.AddContactDetailReqBean;
import com.socialnetwork.models.reqmodels.AddCustomPlatformReqBean;
import com.socialnetwork.models.reqmodels.AddMailDetailReqBean;
import com.socialnetwork.models.reqmodels.AddSocialDetailsReqBean;
import com.socialnetwork.models.reqmodels.GeneralReqBean;
import com.socialnetwork.models.reqmodels.RegisterLoginRequestBean;
import com.socialnetwork.models.reqmodels.SaveProfileRequestBean;
import com.socialnetwork.models.reqmodels.VerifyOtpRequestBean;
import com.socialnetwork.models.responsemodels.AddAddressDetailResBean;
import com.socialnetwork.models.responsemodels.AddContactDetailResBean;
import com.socialnetwork.models.responsemodels.AddMailDetailResBean;
import com.socialnetwork.models.responsemodels.AddPlatformResBean;
import com.socialnetwork.models.responsemodels.AddSocialDetailsResBean;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformReq;
import com.socialnetwork.models.responsemodels.DeleteSocialPlatformRes;
import com.socialnetwork.models.responsemodels.GeneralResponseBean;
import com.socialnetwork.models.responsemodels.GetAddressDetailsResBean;
import com.socialnetwork.models.responsemodels.GetContactDetailsResBean;
import com.socialnetwork.models.responsemodels.GetMailDetailsResBean;
import com.socialnetwork.models.responsemodels.GetMailPlatformsResBean;
import com.socialnetwork.models.responsemodels.GetSocialDetailsResBean;
import com.socialnetwork.models.responsemodels.GetSocialPlatformsResBean;
import com.socialnetwork.models.responsemodels.RegisterLoginResponseBean;
import com.socialnetwork.models.responsemodels.ResendOtpResponseBean;
import com.socialnetwork.models.responsemodels.SkipStepResBean;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface ApiServices {

    String BASE_URL = "http://112.252.123.197/social_papa/";
    //	String BASE_URL = " http://103.25.130.197/social_papa_dev/";
    String APP_VERSION = "1.0";
    String AUTHENTICATION_KEY = "AuthenticationKey";
    int DEVICE_TYPE = 0;
    String MULTIPART_KEY_IMAGE = "image";
    String MULTIPART_KEY_JSON = "json";
    //API NAMES
    String REGISTERLOGIN = "sp_register";
    String VERIFY_OTP = "sp_verify_otp";
    String RESEND_OTP = "sp_send_otp";
    String SAVE_PROFILE_DETAILS = "sp_set_profile";
    String ADD_PLATFORM = "sp_user_custom_platform";
    String CHECK_USERNAME_AVAILABLE = "sp_check_username";
    String SKIP_STEP = "sp_skip_step";
    String GET_EMAIL_PLATFORMS = "sp_get_all_email_platforms";
    String GET_CONTACT_PLATFORMS = "sp_get_all_contact_platforms";
    String GET_SOCIAL_PLATFORMS = "sp_get_all_social_platforms";
    String ADD_SOCIAL_PLATFORM_DETAILS = "sp_add_social_platform";
    String ADD_EMAIL_PLATFORM_DETAILS = "sp_add_email_platform";
    String ADD_CONTACT_PLATFORM_DETAILS = "sp_add_contact_platform";
    String ADD_ADDRESS_PLATFORM_DETAILS = "sp_add_address";
    String GET_EMAIL_PLATFORM_DETAILS = "sp_get_email_detail";
    String GET_CONTACT_PLATFORM_DETAILS = "sp_get_contact_detail";
    String GET_ADDRESS_DETAILS = "sp_get_addresses";
    String GET_SOCIAL_PLATFORMS_DETAILS = "sp_get_platform_detail";
    String DELETE_SOCIAL_PLATFORMS = "sp_delete_platform";

    enum PAGE_NAME {
        FAQ, ABOUT_US, TERMS_CONDITION
    }

    /**
     * method to call the register login api
     *
     * @param req
     */
    @POST(REGISTERLOGIN)
    Call<RegisterLoginResponseBean> registerLogin(@Body RegisterLoginRequestBean req);

    /**
     * method to call the register login api
     *
     * @param req
     */
    @POST(VERIFY_OTP)
    Call<GeneralResponseBean> verifyOtp(@Body VerifyOtpRequestBean req);

    /**
     * method to call the resend otp  api
     *
     * @param req
     */
    @POST(RESEND_OTP)
    Call<ResendOtpResponseBean> resendOtp(@Body VerifyOtpRequestBean req);

    /**
     * method to call the register api
     *
     * @param file
     * @param bean
     */
    @Multipart
    @POST(SAVE_PROFILE_DETAILS)
    Call<ResendOtpResponseBean> saveProfileDetails(@Part("profilePicUrl\"; filename=\"image.png\" ") RequestBody file,
                                                   @Part("json") SaveProfileRequestBean bean);

    /**
     * method to call the add platform api
     *
     * @param file
     * @param bean
     */
    @Multipart
    @POST(ADD_PLATFORM)
    Call<AddPlatformResBean> addPlatform(@Part("profilePicUrl\"; filename=\"image.png\" ") RequestBody file,
                                         @Part("json") AddCustomPlatformReqBean bean);

    /**
     * //method to call the resend otp  api
     *
     * @param req
     * @POST(ADD_PLATFORM) Call<ResendOtpResponseBean> addPlatform(@Body AddCustomPlatformReqBean req);
     */
    //method to call the  username available  api
    @POST(CHECK_USERNAME_AVAILABLE)
    Call<GeneralResponseBean> checkUsername(@Body GeneralReqBean req);

    /**
     * method to call the api to skip the step
     *
     * @param req
     */
    @POST(SKIP_STEP)
    Call<SkipStepResBean> skipstep(@Body GeneralReqBean req);

    /**
     * method to call the api to get all the social platforms
     *
     * @param req
     */
    @POST(GET_SOCIAL_PLATFORMS)
    Call<GetSocialPlatformsResBean> geSocialPlatforms(@Body GeneralReqBean req);

    /**
     * method to call the api to get all the email platforms
     *
     * @param req
     */
    @POST(GET_EMAIL_PLATFORMS)
    Call<GetMailPlatformsResBean> getEmailPlatforms(@Body GeneralReqBean req);

    /**
     * method to call the api to get all the contact platforms
     *
     * @param req
     */
    @POST(GET_CONTACT_PLATFORMS)
    Call<GetMailPlatformsResBean> getContactPlatforms(@Body GeneralReqBean req);

    /**
     * method to call the api to add the email details
     *
     * @param req
     */
    @POST(ADD_EMAIL_PLATFORM_DETAILS)
    Call<AddMailDetailResBean> addEmailDetails(@Body AddMailDetailReqBean req);

    /**
     * method to call the api to add the contact details
     *
     * @param req
     */
    @POST(ADD_CONTACT_PLATFORM_DETAILS)
    Call<AddContactDetailResBean> addContactDetails(@Body AddContactDetailReqBean req);

    /**
     * method to call the api to add the social details
     */
    @Multipart
    @POST(ADD_SOCIAL_PLATFORM_DETAILS)
    Call<AddSocialDetailsResBean> addSocialPlatformDetails(
            @Part("profilePicUrl\"; filename=\"image.png\" ") RequestBody file,
            @Part("json") AddSocialDetailsReqBean bean);

    /**
     * method to call the api to add the address details
     *
     * @param req
     */
    @POST(ADD_ADDRESS_PLATFORM_DETAILS)
    Call<AddAddressDetailResBean> addAddressDetails(@Body AddAddressDetailsReqBean req);

    /**
     * method to call the api to get the social details
     *
     * @param req
     */
    @POST(GET_SOCIAL_PLATFORMS_DETAILS)
    Call<GetSocialDetailsResBean> getSocialDetails(@Body AddContactDetailReqBean req);

    @POST(DELETE_SOCIAL_PLATFORMS)
    Call<DeleteSocialPlatformRes> deleteSocialPlatforms(@Body DeleteSocialPlatformReq req);

    /**
     * method to call the api to get the email details
     *
     * @param req
     */
    @POST(GET_EMAIL_PLATFORM_DETAILS)
    Call<GetMailDetailsResBean> getEmailDetails(@Body AddContactDetailReqBean req);

    /**
     * method to call the api to get the contact details
     *
     * @param req
     */
    @POST(GET_CONTACT_PLATFORM_DETAILS)
    Call<GetContactDetailsResBean> getContactDetails(@Body AddContactDetailReqBean req);

    /**
     * method to call the api to get the address details
     *
     * @param req
     */
    @POST(GET_ADDRESS_DETAILS)
    Call<GetAddressDetailsResBean> getAddressDetails(@Body AddAddressDetailsReqBean req);
}
