package com.socialnetwork.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;

import com.luminous.pick.controller.MediaFactory;
import com.socialnetwork.R;
import com.socialnetwork.interfaces.DialogCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Utility {

    public static Uri mCamRequestedUri;
    private static String selectDate;
    private static MediaFactory mediaFactory;
    Context mContext;

	/*  public static <T> void enqueueCall(final View parentView, final ProgressWheel progressWheel, final Call<T> call, final Callback<T> callback) {
          DialogUtils.showProgressWheel(progressWheel);
	      if (progressWheel.getId() == R.id.toolbar_progress) {
	          parentView.setEnabled(false);
	      } else {
	          parentView.setEnabled(true);
	          Utils.hideView(parentView);
	      }
	      enqueueCall(call, new Callback<T>() {
	          @Override
	          public void onResponse(Response<T> response, Retrofit retrofit) {
	              DialogUtils.hideProgressWheel(progressWheel);
	              Utils.showView(parentView);
	              parentView.setEnabled(true);
	              callback.onResponse(response, retrofit);
	          }
	
	          @Override
	          public void onFailure(Throwable t) {
	              parentView.setEnabled(true);
	              DialogUtils.hideProgressWheel(progressWheel);
	              callback.onFailure(t);
	          }
	      });
	  }
	
	  public static <T> void enqueueCall(final Call<T> call, final Callback<T> callback) {
	      call.enqueue(new Callback<T>() {
	          @Override
	          public void onResponse(Response<T> response, Retrofit retrofit) {
	              if (response.body() == null)
	                  try {
	                      callback.onFailure(new Throwable(response.errorBody().string()));
	                  } catch (IOException | NullPointerException e) {
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
	  }*/

    public static void finishActivityWithRightToLeftAnimation(Activity ctx) {
        ctx.finish();
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void startHome(Activity activity, Class activityClass) {
        Intent in = new Intent(activity, activityClass);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(in);
    }

    public static void startActivityWithLeftToRightAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void startActivityWithZoomOut(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(R.anim.zoom_out, R.anim.slide_out_left);
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

    public static void startActivityWithNoAnimation(Activity ctx, Intent in) {
        ctx.startActivity(in);
        ctx.overridePendingTransition(0, 0);
    }

	/*  public static Bitmap decodeFile(File f) {
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
	          e.printStackTrace();
	      }
	      return null;
	  }*/

	/*  public static String getRealPathFromURI(Activity activity, Uri contentUri) {
	      try {
	          String[] proj = {MediaStore.Images.Media.DATA};
	          Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
	          int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	          cursor.moveToFirst();
	          return cursor.getString(column_index);
	      } catch (Exception e) {
	          return contentUri.getPath();
	      }
	  }*/

    public static void dialNumber(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static String removeWhiteSpaceFromString(String str) {
        return str.replaceAll("\\s", "");

    }

    //method to show the list popup window
    public static ListPopupWindow showListPopupWindow(Context context, View view, final List<String> mResultList) {
        ArrayAdapter mListAdapter = new ArrayAdapter(context, R.layout.popup_window_list_row, mResultList);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(context);
        listPopupWindow.setAdapter(mListAdapter);

        listPopupWindow.setAnchorView(view);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.5);

        listPopupWindow.setWidth(width);

        listPopupWindow.setModal(true);
        // listPopupWindow.show();
        return listPopupWindow;
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
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
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

    public static void setMap(final Context context, Double currentLat, Double currentLong, Double destiLat,
                              Double destiLong) {
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + currentLat + "," + currentLong + "&daddr="
                + destiLat + "," + destiLong;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(Intent.createChooser(intent, "Select an application"));
    }

    public static void getPictureOnActivity(final Activity activity) {
        File file = new File(AppConstants.ROOT_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface Dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    takePicFromCamera(activity, AppConstants.CAMERA_APP);
					/* File file = new File(AppConstants.ROOT_DIR, "profileImage.jpg");
					 if (file.exists()) {
					     file.delete();
					 }
					 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					 activity.startActivityForResult(intent, AppConstants.REQUEST_CAMERA);*/
                } else if (items[item].equals("Choose from Gallery")) {
                    takePicFromGallery(activity);
                } else if (items[item].equals("Cancel")) {
                    Dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void takePicFromGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), AppConstants.GALLERY_APP);
    }

    public static void takePicFromCamera(Activity activity, int requestCode) {
        File file = new File(AppConstants.ROOT_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        File picPath = new File(AppConstants.ROOT_DIR,
                AppConstants.APP_NAME + "_status_" + System.currentTimeMillis() + ".jpg");
        AppConstants.mCamRequestedUri = Uri.fromFile(picPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, AppConstants.mCamRequestedUri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void takePicFromGallery(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        fragment.startActivityForResult(Intent.createChooser(intent, "Select File"), AppConstants.GALLERY_APP);
    }

    public static void takePicFromCamera(final Fragment fragment, int requestCode) {
        File file = new File(AppConstants.ROOT_DIR);
        if (!file.exists()) {
            file.mkdir();
        }

        File picPath = new File(AppConstants.ROOT_DIR,
                AppConstants.APP_NAME + "_status_" + System.currentTimeMillis() + ".jpg");
        AppConstants.mCamRequestedUri = Uri.fromFile(picPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, AppConstants.mCamRequestedUri);
        fragment.startActivityForResult(intent, requestCode);
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

    public static void shareData(Context mContext, String strMessage, String url, View view) {
        String message = strMessage;
        Intent share = new Intent(Intent.ACTION_SEND);
        Uri uri = getViewToBitmapUri(view);
        if (view != null && uri != null) {
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.setType("image/*");
        } else {
            share.setType("text/plain");
        }

        share.putExtra(Intent.EXTRA_TEXT, message);
        mContext.startActivity(Intent.createChooser(share, AppConstants.APP_NAME));
    }

    public static Uri getViewToBitmapUri(View view) {
        /**
         * Handling crash
         *  java.lang.NullPointerException: uriString
         at android.net.Uri$StringUri.<init>(Uri.java)
         at android.net.Uri$StringUri.<init>(Uri.java)
         at android.net.Uri.parse(Uri.java)
         */
        try {

            Bitmap mScreenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            view.draw(new Canvas(mScreenshot));
            String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(), mScreenshot,
                    "tempShare", null);
            Uri mScreenshotUri = Uri.parse(path);
            return mScreenshotUri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    //    public static void setTypeFace(Context context, List<TextView> fontTextView) {
    //        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/icomoon.ttf");
    //        for (int i = 0; i < fontTextView.size(); i++)
    //            fontTextView.get(i).setTypeface(type);
    //    }

    public static void closeSoftInputKeyPad(@NonNull Context context, @NonNull View view) {
        try {
            if (context == null || view == null)
                return;

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getDistanceFromLatlong(Double lat1, Double lon1, Double lat2, Double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        float distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView, Context context) {

        ListAdapter listAdapter = listView.getAdapter();
        float density = context.getResources().getDisplayMetrics().density;
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();
            Log.v("numberofite", "numberOfItems : " + numberOfItems);
            //            // Get total height of all items.
            //            int totalItemsHeight = 0;
            //            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            //                View item = listAdapter.getView(itemPos, null, listView);
            //                item.measure(0, 0);
            //                Log.v("row hieght", "h" + item.getMeasuredHeight());
            //                totalItemsHeight += item.getMeasuredHeight();
            //            }
            //
            //            // Get total height of all item dividers.
            //            int totalDividersHeight = listView.getDividerHeight() *
            //                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = (int) (140 * numberOfItems * density);
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static void showImageChooserDialog(final Context context) {
        mediaFactory = MediaFactory.create();
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_picker_dialog_view);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(context, R.color.color_black_dark_opaque)));
        // set the custom dialog components - text, image and button
        TextView imageGallery = (TextView) dialog.findViewById(R.id.image_picker_dialog_gallery_iv);
        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MediaFactory.MediaBuilder mediaBuilder = new MediaFactory.MediaBuilder(context).getSingleMediaFiles()
                        .doCropping().fromGallery();
                mediaFactory = MediaFactory.create().start(mediaBuilder);
            }
        });
        TextView imageCamera = (TextView) dialog.findViewById(R.id.image_picker_dialog_camera_iv);
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MediaFactory.MediaBuilder mediaBuilder = new MediaFactory.MediaBuilder(context).getSingleMediaFiles()
                        .fromCamera().doCropping();
                mediaFactory = MediaFactory.create().start(mediaBuilder);
            }
        });

		/*  // skip for image
		  TextView skipForImage = (TextView) dialog.findViewById(R.id.skip_for_image);
		  skipForImage.setVisibility(View.VISIBLE);
		  skipForImage.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		          goToNextScreen("");
		          dialog.dismiss();
		      }
		  });
		  TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
		  cancel.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		          dialog.dismiss();
		      }
		  });*/

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public static void setColoronAddedPlatforms(int childCount, ImageView drawableView, String s,
                                                boolean isPlatformAdded) {

        int color = Color.CYAN;

        int noOfModes = childCount % 4;

        switch (noOfModes) {
            case 1:
                color = Color.parseColor("#FA4147");
                break;

            case 2:
                color = Color.parseColor("#FFB900");
                break;

            case 3:
                color = Color.parseColor("#3F54FD");
                break;

            case 0:
                color = Color.parseColor("#16B661");
                break;

        }
        TextDrawable drawable = null;
        if (isPlatformAdded) {
            drawable = TextDrawable.builder().buildRound(s, color);
        } else {
            drawable = TextDrawable.builder().beginConfig().withBorder(2).endConfig().buildRound(s, Color.TRANSPARENT,
                    color);
        }
        drawableView.setImageDrawable(drawable);

    }


    public static void setColoronAddedPlatformsMailContact(int childCount, ImageView drawableView, String s,
                                                           boolean isPlatformAdded) {

        int color = Color.CYAN;

        int noOfModes = childCount % 4;

        switch (noOfModes) {
            case 1:
                color = Color.parseColor("#FA4147");
                break;

            case 2:
                color = Color.parseColor("#FFB900");
                break;

            case 3:
                color = Color.parseColor("#3F54FD");
                break;

            case 0:
                color = Color.parseColor("#16B661");
                break;

        }
        TextDrawable drawable = null;
        if (isPlatformAdded) {
            drawable = TextDrawable.builder().buildRound(s, color);
        } else {
            drawable = TextDrawable.builder().buildRound(s, Color.parseColor("#AAAAAA"));
        }
        drawableView.setImageDrawable(drawable);

    }

    public static void changeDrawableColor(View view, int color) {
        GradientDrawable drawable = ((GradientDrawable) view.getBackground());
        drawable.mutate();

        drawable.setColor(color);
        drawable.setStroke(1, color);

    }

    public static void showSimpleDialog(final Context context, final String message, final DialogCallBack callBack) {
        try {
            new android.app.AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name))
                    .setMessage(message).setCancelable(true)
                    .setPositiveButton(context.getResources().getString(android.R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                    dialog.dismiss();
                                    if (callBack != null) {
                                        callBack.okPressed();
                                    }
                                }
                            })
                    .setNegativeButton(context.getResources().getString(android.R.string.cancel), null).create().show();
        } catch (Exception e) {
            Lg.e("PUSH_TAG", "Showing Push Dialog Failed - " + e.getMessage());
        }
    }


}
