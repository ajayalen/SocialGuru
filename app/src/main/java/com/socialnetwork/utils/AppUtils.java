package com.socialnetwork.utils;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class AppUtils {



    /*public enum CoachMarkType {
        HOME
    }

    public static void showCoachMarks(final View view, final CoachMarkType listType) {
        boolean isAlreadyShown = false;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
        switch (listType) {

            case HOME:
                isAlreadyShown = PreferenceUtil.getGlobalSharedPrefBooleanData(view.getContext(), PreferenceUtil.KEY_HOME_COACH_MARK);
                PreferenceUtil.setGlobalSharedPrefBooleanData(view.getContext(), PreferenceUtil.KEY_HOME_COACH_MARK, true);
                break;
        }

        if (!isAlreadyShown) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);

    }*/

  /*  public static <T> void enqueueCall(final View parentView, final ProgressWheel progressWheel, final Call<T> call, final Callback<T> callback) {
        DialogUtil.showProgressWheel(progressWheel);
        if (progressWheel.getId() == R.id.toolbar_progress) {
            parentView.setEnabled(false);
        } else {
            parentView.setEnabled(true);
            Utils.hideView(parentView);
        }
        enqueueCall(call, new Callback<T>() {
            @Override
            public void onResponse(Response<T> response, Retrofit retrofit) {
                DialogUtil.hideProgressWheel(progressWheel);
                Utils.showView(parentView);
                parentView.setEnabled(true);
                callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                parentView.setEnabled(true);
                DialogUtil.hideProgressWheel(progressWheel);
                callback.onFailure(t);
            }
        });
    }*/

    //T extends Comparable<? super T
    public static <T> void enqueueCall(final Call<T> call, final Callback<T> callback) {
        call.enqueue(new Callback<T>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(Response<T> response, Retrofit retrofit) {
                if (response.body() == null)
                    try {
                        //noinspection ConstantConditions
                        if (response != null)
                            callback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        callback.onFailure(new Throwable("Unknown"));
                        e.printStackTrace();
                    }
                else
                    callback.onResponse(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                callback.onFailure(t);

            }
        });
    }

 /*   public static void startWebViewActivity(Context context, ApiServices.PAGE_NAME pageName) {

        if (!Utils.isNetworkAvailable(context)) {
            DialogUtil.showNoNetworkToast(context);
            return;
        }

        String url = "";
        String title = "";
        switch (pageName) {
            case FAQ:
                url = ApiServices.FAQ;
                title = context.getString(R.string.faq);
                break;
            case ABOUT_US:
                url = ApiServices.ABOUT_US;
                title = context.getString(R.string.aboutus);
                break;
            case TERMS_CONDITION:
                url = ApiServices.TERMS_CONDITION;
                title = context.getString(R.string.tersm_condition);
                break;
            default:
                url = ApiServices.BASE_URL;
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_URL, url);
        bundle.putString(AppConstants.EXTRA_TITLE, title);
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(bundle);
        HomeActivity homeActivity = new HomeActivity();
        homeActivity.changeFragment(webViewFragment, title);

//        Intent i = new Intent(context, WebViewActivity.class);
//        i.putExtra(AppConstants.EXTRA_URL, url);
//        i.putExtra(AppConstants.EXTRA_TITLE, title);
//        context.startActivity(i);

    }


    public static void showDialog(Activity mAct, PushEntitity pushEntity) {
        Utility.showSimpleDialog(mAct, pushEntity.getMessage(), new DialogCallBack() {
            @Override
            public void okPressed() {


            }
        });
    }
*/
}
