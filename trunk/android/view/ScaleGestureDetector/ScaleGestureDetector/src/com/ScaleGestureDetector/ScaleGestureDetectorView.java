package com.ScaleGestureDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.WindowManager;

public class ScaleGestureDetectorView extends View {
	ScaleGestureDetector mScaleGestureDetector;
	Drawable mImage;
	float mScaleFactor = 1.0f;

	SimpleOnScaleGestureListener mSimpleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.d("ges", "onScaleBegin "+ detector.getScaleFactor());
			invalidate();
			return super.onScaleBegin(detector);
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			Log.d("ges", "onScaleEnd "+ detector.getScaleFactor());
			mScaleFactor *= detector.getScaleFactor();
			invalidate();
			super.onScaleEnd(detector);
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.d("ges", "onScale "+ detector.getScaleFactor());
			mScaleFactor *= detector.getScaleFactor();
			invalidate();
			return true;
		};		
	};

	public ScaleGestureDetectorView(Context context) {
		super(context);
		int resid = context.getResources().getIdentifier("s093", "drawable", context.getPackageName());
		mImage = context.getResources().getDrawable(resid);
		Log.d("ges", "id=" + resid);
		
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		int width = disp.getWidth();
		int img_height2 = mImage.getIntrinsicHeight();
		float img_height = (float)img_height2;
		float hi_width = (float)width / mImage.getIntrinsicWidth(); 
		img_height = img_height * hi_width;
		int height = (int)img_height;
		mImage.setBounds(0, 0, width, height );
		mScaleGestureDetector = new ScaleGestureDetector(context, mSimpleListener);
		

	}
	
	@Override
    public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.save();
		canvas.scale(mScaleFactor, mScaleFactor);
		mImage.draw(canvas);
		
		canvas.restore();
	}
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	mScaleGestureDetector.onTouchEvent(ev);
    	return true;
    }

}
