package com.onScroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;

public class GestureDetectorView extends View implements OnGestureListener {
	ScaleGestureDetector mScaleGestureDetector;
	Drawable mImage;
	float mTranslateX = 0.0f;
	float mTranslateY =0.0f;
	
    GestureDetector mGestureDetector;
	public GestureDetectorView(Context context) {
		super(context);
		int resid = context.getResources().getIdentifier("s066", "drawable", context.getPackageName());
		mImage = context.getResources().getDrawable(resid);
		
		mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight() );
        mGestureDetector = new GestureDetector(context, this);

	}
	
	@Override
    public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.save();
		canvas.translate(mTranslateX, mTranslateY);
		mImage.draw(canvas);
		
		canvas.restore();
	}
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
    	return true;
    	
    }
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d("book", "distanceX=" + distanceX + "  distanceY=" + distanceY);
		mTranslateX -= distanceX;
		mTranslateY -=distanceY;
		invalidate();
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
 
}
