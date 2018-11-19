package com.socialnetwork.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socialnetwork.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Utils {
    public static Uri mCamRequestedUri;
    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * This method is used to detect whether network is available or not
     *
     * @param context application Context
     * @return true if network available ,false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

    /**
     * Method to show keyboard
     *
     * @param context Context of the calling activity
     */
    public static void showKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {

        }
    }

    public static DatePickerDialog getBirthDatePicker(Context context, DatePickerDialog.OnDateSetListener callback) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, callback, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    /**
     * Method to show keyboard
     *
     * @param context  Context of the calling activity
     * @param editText Edittext which will receive focus
     */
    public static void showKeyboard(Context context, EditText editText) {
        showKeyboard(context);
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to hide keyboard
     *
     * @param mContext Context of the calling class
     */
    public static void hideKeyboard(Context mContext) {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public static void generateKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Lg.e("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        }
    }

    public static <T extends Object> String toJson(T model) {
        return new Gson().toJson(model);
    }

    public static <T extends Object> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public static void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }


    public static void startActivityWithNoAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(0, 0);
    }

    public static void startActivityForResultWithLeftToRightAnimation(Context ctx, Intent in, int requestCode) {
        ((Activity) ctx).startActivityForResult(in, requestCode);
        ((Activity) ctx).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void startActivityWithLeftToRightAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void startActivityWithZoomAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.zoom_out, R.anim.slide_in_left);
    }

    public static void startActivityWithFlipAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
    }

    public static void startActivityWithRightToLeftAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void startActivityForResultWithDownToUpAnimation(Context ctx, Intent in, int requestCode) {

        ((Activity) ctx).startActivityForResult(in, requestCode);
        ((Activity) ctx).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    public static void startActivityWithDownToUpAnimation(Context ctx, Intent in) {

        ctx.startActivity(in);
        ((Activity) ctx).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    public static void startActivityWithUpToDownAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }


    public static void finishActivityWithRightToLeftAnimation(Activity ctx) {
        ctx.finish();
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void finishActivityWithToptoBottomAnimation(Activity ctx) {
        ctx.finish();
        ctx.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
    }

    public static void finishActivityWithNoAnimation(Activity ctx) {
        ctx.finish();
        ctx.overridePendingTransition(0, 0);
    }
    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }

    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            FileOutputStream fileOutputStream = new FileOutputStream(f);

            b.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            return b;
        } catch (FileNotFoundException e) {
        }
        return null;
    }
    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }
    public static void getPictureOnActivity(final Activity activity) {
        File file = new File(AppConstants.ROOT_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
       // final CharSequence[] items = {"Camera", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_picker_dialog_view, null);
        TextView cameraTv = (TextView)dialogView.findViewById(R.id.image_picker_dialog_camera_iv);
        TextView galleryTv = (TextView)dialogView.findViewById(R.id.image_picker_dialog_gallery_iv);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        cameraTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                takePicFromCamera(activity, AppConstants.CAMERA_APP);
            }
        });
        galleryTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                takePicFromGallery(activity);


            }
        });

        alertDialog.show();
       // builder.setTitle("Add Photo!");
       /* builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface Dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    takePicFromCamera(activity, AppConstants.CAMERA_APP);
                   *//* File file = new File(AppConstants.ROOT_DIR, "profileImage.jpg");
                    if (file.exists()) {
                        file.delete();
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    activity.startActivityForResult(intent, AppConstants.REQUEST_CAMERA);*//*
                } else if (items[item].equals("Choose from Gallery")) {
                    takePicFromGallery(activity);
                } *//*else if (items[item].equals("Cancel")) {
                    Dialog.dismiss();
                }*//*
            }
        });
        builder.show();*/
    }


    public static void takePicFromGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"),
                AppConstants.GALLERY_APP);
    }


    public static void takePicFromCamera(Activity activity, int requestCode) {
        File file = new File(AppConstants.ROOT_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        File picPath = new File(AppConstants.ROOT_DIR, AppConstants.APP_NAME + "_status_" + System.currentTimeMillis() + ".jpg");
        AppConstants.mCamRequestedUri = Uri.fromFile(picPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, AppConstants.mCamRequestedUri);
        activity.startActivityForResult(intent, requestCode);
    }

}
