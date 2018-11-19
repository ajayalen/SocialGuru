package com.socialnetwork.social.reddit.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.socialnetwork.activities.LinkedSocialProfileActivity;
import com.socialnetwork.social.reddit.Reddit;
import com.socialnetwork.social.reddit.RedditHelper;
import com.socialnetwork.social.reddit.oauth.OAuth;
import com.socialnetwork.social.reddit.oauth.OAuthToken;

public class LoadUserTask extends AsyncTask<OAuth, Void, User>
{
	/**
	 *
	 */
	private final Context flickrjAndroidSampleActivity;

	public LoadUserTask(Context flickrjAndroidSampleActivity)
	{
		this.flickrjAndroidSampleActivity = flickrjAndroidSampleActivity;
	}

	/**
	 * The progress dialog before going to the browser.
	 */
	private ProgressDialog mProgressDialog;

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		mProgressDialog = ProgressDialog.show(flickrjAndroidSampleActivity, "", "Loading user information..."); //$NON-NLS-1$ //$NON-NLS-2$
		mProgressDialog.setCanceledOnTouchOutside(true);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dlg)
			{
				LoadUserTask.this.cancel(true);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected User doInBackground(OAuth... params)
	{
		OAuth oauth = params[0];
		User user = oauth.getUser();
		OAuthToken token = oauth.getToken();
		try
		{
			Reddit ins = RedditHelper.getInstance().getRedditAuthed(token.getOauthToken(),token.getRefreshToken(),token.getExpiresIn(),token.getTokenType());
			return ins.getUserInterface(token.getOauthToken(),token.getRefreshToken(),token.getExpiresIn()).getInfo();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(User user)
	{
		if (mProgressDialog != null)
		{
			mProgressDialog.dismiss();
		}
		if (user == null)
		{
			return;
		}
		if (flickrjAndroidSampleActivity instanceof LinkedSocialProfileActivity)
			((LinkedSocialProfileActivity) flickrjAndroidSampleActivity).setUserReddit(user);
	}

}