package com.socialnetwork.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.socialnetwork.services.ApiServices;
import com.socialnetwork.services.AppRetrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    protected BaseActivity mBaseActivity;
    protected ApiServices mApiServices;
    //  protected LogInRespBean.LogInData mUserData;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBaseActivity = (BaseActivity) getActivity();
        mApiServices = new AppRetrofit().getApiServices();
        //mUserData = new DbHelper().getUserData();
    }

    public String getName() {
        return getClass().getSimpleName();
    }


}
