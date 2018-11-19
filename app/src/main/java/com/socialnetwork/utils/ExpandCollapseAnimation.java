package com.socialnetwork.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import java.lang.reflect.Method;

public class ExpandCollapseAnimation {
	
	private static final int ANIMATION_DURATION = 200;
	public static Animation expand(final View v, final boolean expand) {
	    try {
	        Method m = v.getClass().getDeclaredMethod("onMeasure", int.class, int.class);
	        m.setAccessible(true);
	        m.invoke(
	            v,
	            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
	            MeasureSpec.makeMeasureSpec(((View)v.getParent()).getMeasuredWidth(), MeasureSpec.AT_MOST)
	        );
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    final int initialHeight = v.getMeasuredHeight();
	    
	    if (expand) {
	    	v.getLayoutParams().height = 0;
	    }
	    else {
	    	v.getLayoutParams().height = initialHeight;
	    }
	    v.setVisibility(View.VISIBLE);
	    
	    Animation a = new Animation() {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	        /*    int newHeight = 0;
	            if (expand) {
	            	newHeight = (int) (initialHeight * interpolatedTime);
	            } else {
	            	newHeight = (int) (initialHeight * (1 - interpolatedTime));
	            }*/
	            v.getLayoutParams().height = interpolatedTime == 1
	                    ? LayoutParams.WRAP_CONTENT
	                    : (int)(initialHeight * interpolatedTime);
//	            v.getLayoutParams().height = newHeight;	            
	            v.requestLayout();
	            
	            if (interpolatedTime == 1 && !expand)
	            	v.setVisibility(View.GONE);
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };
	    a.setDuration(ANIMATION_DURATION);
	    v.startAnimation(a);
	    return a;
	}

	public static void collapse(final View v) {
	    final int initialHeight = v.getMeasuredHeight();

	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            if(interpolatedTime == 1){
	                v.setVisibility(View.GONE);
	            }else{
	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
	                v.requestLayout();
	            }
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration(ANIMATION_DURATION);
	    v.startAnimation(a);
	}
	
	public static void expand(final View v) {
	    v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    final int targtetHeight = v.getMeasuredHeight();

	    v.getLayoutParams().height = 0;
	    v.setVisibility(View.VISIBLE);
	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            v.getLayoutParams().height = interpolatedTime == 1
	                    ? LayoutParams.WRAP_CONTENT
	                    : (int)(targtetHeight * interpolatedTime);
	            v.requestLayout();
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	        
	    };

	    // 1dp/ms
	    a.setDuration(ANIMATION_DURATION);
	    v.startAnimation(a);
	}
	// To animate view slide out from top to bottom
	public static void slideToBottom(View view){
		TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.GONE);
	}

	// To animate view slide out from bottom to top
	public static void slideToTop(View view){
		TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.GONE);
	}
}
