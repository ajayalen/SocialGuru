package com.socialnetwork.social;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Part;

/**
 * Created by anand on 22/8/16.
 */
public interface SocialApis {

    String FLICKR_BASE = "https://www.flickr.com/services/oauth/";

    String SIGNING_REQUEST = "";

//    @GET(SIGNING_REQUEST)
//    Call<SignupResponseBean> signup(@Part("profilePicUrl\"; filename=\"image.png\" ") RequestBody file, @Part("json") SignupRequestBean bean);


}
