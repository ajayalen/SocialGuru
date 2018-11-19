package com.socialnetwork.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.socialnetwork.R;
import com.socialnetwork.customui.ProgressWheel;

import java.util.List;


public class DialogUtil {

    private static final String TAG = DialogUtil.class.getSimpleName();

    public static ProgressDialog showProgressDialog(Context mContext) {
        try {
            if (mContext != null && !((Activity) mContext).isFinishing()) {
                ProgressDialog mProgressDialog = ProgressDialog.show(mContext,
                        "", mContext.getString(R.string.please_wait));
                return mProgressDialog;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ProgressDialog(mContext);
    }

    public static void showProgressDialog(@NonNull ProgressDialog progressDialog, Activity activity) {
        try {
            if (progressDialog != null) {
                if (activity != null && !activity.isFinishing()) {
                    if (!progressDialog.isShowing()) progressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(@NonNull ProgressDialog progressDialog, Activity activity) {
        try {
            if (progressDialog != null) {
                if (activity != null && !activity.isFinishing()) {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /* public static ProgressDialog getProgressDialog(Context mContext) {
         if (mContext != null) {
             ProgressDialog mProgressDialog = new ProgressDialog(mContext);
             mProgressDialog.setMessage(mContext.getString(R.string.please_wait));
             return mProgressDialog;
         }

         return null;
     }
 */
    public static void showNoNetworkToast(Context context) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        String msg = context.getResources().getString(R.string.no_network_msg);
        showToastShortLength(context, msg);
    }

    public static Snackbar showRetrySnackBar(@NonNull View parentView, String msg, final View.OnClickListener retryListener) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        try {
            final Snackbar snackBar = Snackbar.make(parentView, msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setActionTextColor(Color.WHITE);
            View view = snackBar.getView();
            TextView tv = (TextView)
                    view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackBar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                    retryListener.onClick(v);
                }
            });
            snackBar.show();
            return snackBar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View parentView, final View.OnClickListener listener) {
        String msg = parentView.getContext().getResources().getString(R.string.no_network_msg);
        return showRetrySnackBar(parentView, msg, listener);
    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View parentView) {
        return showSnackBar(parentView, R.string.no_network_msg);
    }

    public static Snackbar showSnackBar(View parentLayout, String msg) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        final Snackbar snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.WHITE);
        View view = snackBar.getView();
        TextView tv = (TextView)
                view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackBar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
        return snackBar;
    }

    public static Snackbar showSnackBar(View parentView, int msg) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Resources res = parentView.getContext().getResources();
        return showSnackBar(parentView, res.getString(msg));
    }


    public static void showToast(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(@NonNull Context context, int msg) {
        Toast.makeText(context.getApplicationContext(), context.getResources().getString(msg), Toast.LENGTH_LONG).show();
    }

    public static void showToastShortLength(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showTryAgainToast(@NonNull Context context) {
        String msg = context.getString(R.string.server_error);
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    public static Snackbar showTryAgainSnackBar(@NonNull View parentView, final View.OnClickListener listener) {
        String msg = parentView.getContext().getResources().getString(R.string.server_error);
        return showRetrySnackBar(parentView, msg, listener);
    }

    public static Snackbar showTryAgainSnackBar(@NonNull View parentView) {
        String msg = parentView.getContext().getResources().getString(R.string.server_error);
        return showSnackBar(parentView, msg);
    }

    /*public static void showProgressBar(Activity activity) {
        View v = activity.findViewById(R.id.progress);
        if (v != null) {
            ProgressWheel wheel = (ProgressWheel) v;
            if (!wheel.isSpinning()) wheel.spin();
            if (v.getVisibility() == View.GONE || v.getVisibility() == View.INVISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("PROGRESS BAR", "progressbar is not attached");
        }
    }*/

    public static void showProgressWheel(ProgressWheel progressWheel) {
        if (progressWheel != null) {

            if (!progressWheel.isSpinning()) progressWheel.spin();
            if (progressWheel.getVisibility() == View.GONE || progressWheel.getVisibility() == View.INVISIBLE) {
                progressWheel.setVisibility(View.VISIBLE);
            }
        } else {
            Lg.e("PROGRESS BAR", "progressbar is not attached");
        }
    }

    public static void hideProgressWheel(ProgressWheel progressWheel) {
        try {


            if (progressWheel != null) {

                if (progressWheel.isSpinning()) progressWheel.stopSpinning();
                if (progressWheel.getVisibility() == View.VISIBLE) {
                    progressWheel.setVisibility(View.GONE);
                }
            } else {
                Lg.e("PROGRESS BAR", "progressbar is not attached");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void hideProgressBar(Activity activity) {
        View v = activity.findViewById(R.id.progress);
        if (v != null) {
            ProgressWheel wheel = (ProgressWheel) v;
            if (wheel.isSpinning()) wheel.stopSpinning();
            if (v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.GONE);
            }
        } else {
            Log.e("PROGRESS BAR", "progressbar is not attached");
        }
    }*/

   /* public static void showDialogAsToast(final Context context, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().setContentView(R.layout.layout_dialog_toast);
        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ((TextView) alert.findViewById(R.id.layout_alert_dialog_toast_text_view)).setText(msg);
        alert.findViewById(R.id.layout_alert_dialog_toast_image_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isShowing() && !((Activity) context).isFinishing()) {
                    alert.dismiss();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing() && !((Activity) context).isFinishing()) {
                    alert.dismiss();
                }
            }
        }, 5000);
    }*/

    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, int msg, AlertDialog.OnClickListener yesListner) {
        return getOkCancelDialog(context, context.getResources().getString(msg), yesListner);
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(final Context context, String msg, AlertDialog.OnClickListener yesListner) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, R.style.AppDialog);
        builder.setMessage(msg);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setPositiveButton(context.getString(R.string.text_yes), yesListner);
        builder.setNegativeButton(context.getString(R.string.text_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.setMessage(msg);
        return builder.create();

    }

    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, int title, int items, int checkedItem, final DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, List<? extends Object> items, int checkedItem, final DialogInterface.OnClickListener listener) {
        int size = items.size();
        String[] itemArray = new String[size];
        for (int i = 0; i < size; i++) {
            itemArray[i] = items.get(i).toString();
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        //builder.setTitle(context.getResources().getString(R.string.lbl_select_lang));

        builder.setSingleChoiceItems(itemArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static android.support.v7.app.AlertDialog getListDialog(Context context, List<? extends Object> items, String title, final DialogInterface.OnClickListener listener) {
        int size = items.size();
        String[] itemArray = new String[size];
        for (int i = 0; i < size; i++) {
            itemArray[i] = items.get(i).toString();
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(itemArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }


    public static void closeAlertDialog(android.support.v7.app.AlertDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static android.support.v7.app.AlertDialog getOkCancelDialog(Context context, int msg, int style, final DialogInterface.OnClickListener listener) {
        //android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context, style);
        builder.setCancelable(true);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", listener);

        builder.setMessage(msg);
        //builder.setCustomTitle()
        final android.support.v7.app.AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        listener.onClick(dialog, 1);

                    }
                });
            }
        });
        return alertDialog;
    }





   /* public static Dialog getFullScreenDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context, R.style.full_screen_dialog);
        //dialog.setContentView(R.layout.dialog_edit_comment);
        dialog.getWindow().setContentView(layoutId);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }*/


    /*public static android.support.v7.app.AlertDialog getDialogBuilder(Context context, int titleResId, int viewResId, final DialogInterface.OnClickListener yesListener*//*,final DialogInterface.OnClickListener radioListener*//*) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setView(viewResId);
        builder.setTitle(titleResId);
        //this option has been removed
        //builder.setSingleChoiceItems(R.array.Audio_setting_gender, 0,radioListener);


        builder.setPositiveButton(R.string.Audio_dialog_Save, yesListener);
        builder.setNegativeButton(R.string.Audio_dialog_Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }*/

    /*public static android.support.v7.app.AlertDialog getDialogBox(final Context context, int titleResId, final DialogInterface.OnClickListener yesListener) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(titleResId);
        builder.setPositiveButton(R.string.Okay, yesListener);
        return builder.create();
    }*/


}
