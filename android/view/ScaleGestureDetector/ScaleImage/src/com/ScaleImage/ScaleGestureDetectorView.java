package com.ScaleImage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

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
		int resid = context.getResources().getIdentifier("s066", "drawable", context.getPackageName());
		mImage = context.getResources().getDrawable(resid);
		Log.d("ges", "id=" + resid);
		
		mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight() );
		mScaleGestureDetector = new ScaleGestureDetector(context, mSimpleListener);
		

	}
	
	@Override
    public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.save();
		
		float mx = canvas.getWidth();
		float my = canvas.getHeight();
		canvas.translate(mx/2, my/2);
		canvas.scale(mScaleFactor, mScaleFactor);
		canvas.translate(-(mx/2), -(my/2));
		mImage.draw(canvas);
		
		
		canvas.restore();
	}
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	mScaleGestureDetector.onTouchEvent(ev);
    	return true;
    }

}
