package com.socialnetwork.social.flickr.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.socialnetwork.activities.SocialPlatformsActivity;
import com.socialnetwork.social.SocialConstants;
import com.socialnetwork.social.flickr.Flickr;
import com.socialnetwork.social.flickr.FlickrHelper;
import com.socialnetwork.social.flickr.oauth.OAuth;
import com.socialnetwork.social.flickr.oauth.OAuthInterface;
import com.socialnetwork.social.instagram.Instagram;
import com.socialnetwork.social.instagram.InstagramHelper;
import com.socialnetwork.social.linkedin.Linkedin;
import com.socialnetwork.social.linkedin.LinkedinHelper;
import com.socialnetwork.social.reddit.Reddit;
import com.socialnetwork.social.reddit.RedditHelper;
import com.socialnetwork.social.tumblr.Tumblr;
import com.socialnetwork.social.tumblr.TumblrHelper;
import com.socialnetwork.social.twitter.Twitter;
import com.socialnetwork.social.twitter.TwitterHelper;

/**
 * @author Toby Yu(yuyang226@gmail.com)
 */
public class GetOAuthTokenTask extends AsyncTask<String, Integer, Object>
{

	private final int mMode;
	private Context activity;

	public GetOAuthTokenTask(Context context, int mode)
	{
		this.activity = context;
		this.mMode = mode;
	}

	@Override
	protected Object doInBackground(String... params)
	{
		String oauthToken = params[0];
		String oauthTokenSecret = params[1];
		String verifier = params[2];

		switch (mMode)
		{
			case SocialConstants.FLICKR:
				Flickr f = FlickrHelper.getInstance().getFlickr();
				OAuthInterface oauthApi = null;
				if (f != null) {
					oauthApi = f.getOAuthInterface();
				}
				try
				{
					if (oauthApi != null) {
						return oauthApi.getAccessToken(oauthToken, oauthTokenSecret, verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}

			case SocialConstants.TUMBLR:
				Tumblr t = TumblrHelper.getInstance().getTumblr();
				com.socialnetwork.social.tumblr.oauth.OAuthInterface tumblerOuth = null;
				if (t != null) {
					tumblerOuth = t.getOAuthInterface();
				}
				try
				{
					if (tumblerOuth != null) {
						return tumblerOuth.getAccessToken(oauthToken, oauthTokenSecret, verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}

			case SocialConstants.TWITTER:
				Twitter tw = TwitterHelper.getInstance().getTwitter();
				com.socialnetwork.social.twitter.oauth.OAuthInterface twitterOuth = null;
				if (tw != null) {
					twitterOuth = tw.getOAuthInterface();
				}
				try
				{
					if (twitterOuth != null) {
						return twitterOuth.getAccessToken(oauthToken, oauthTokenSecret, verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}

			case SocialConstants.INSTAGRAM:
				Instagram ins = InstagramHelper.getInstance().getInstagram();
				com.socialnetwork.social.instagram.oauth.OAuthInterface insOuth = null;
				if (ins != null) {
					insOuth = ins.getOAuthInterface();
				}
				try
				{
					if (insOuth != null) {
						return insOuth.getAccessToken(verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}



			case SocialConstants.LINKEDIN:
				Linkedin link = LinkedinHelper.getInstance().getLinkedin();
				com.socialnetwork.social.linkedin.oauth.OAuthInterface linkOuth = null;
				if (link != null) {
					linkOuth = link.getOAuthInterface();
				}
				try
				{
					if (linkOuth != null) {
						return linkOuth.getAccessToken(verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}


			case SocialConstants.REDDIT:
				Reddit red = RedditHelper.getInstance().getReddit();
				com.socialnetwork.social.reddit.oauth.OAuthInterface reddOuth = null;
				if (red != null) {
					reddOuth = red.getOAuthInterface();
				}
				try
				{
					if (reddOuth != null) {
						return reddOuth.getAccessToken(verifier);
					}
				}
				catch (Exception e)
				{
					//			logger.error(e.getLocalizedMessage(), e);
					return null;
				}

		}

		return null;

	}

	@Override
	protected void onPostExecute(Object result)
	{
		if (activity != null)
		{
			((SocialPlatformsActivity) activity).onOAuthDone(result, mMode);
		}
	}

}
