package com.BookReader;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

public class BookReaderActivity extends Activity implements OnGestureListener {
    /** Called when the activity is first created. */
    GestureDetector mGestureDetector;
    
    int mPagenumber = 0;
    
    static int mMaxPage = 20;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mGestureDetector = new GestureDetector(this, this);
    }
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		return false;
	}
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.d("book", "vx=" + velocityX + " vy=" + velocityY);
		if (velocityY < 0) {//right
			mPagenumber++;
			if (mPagenumber > mMaxPage) {
				mPagenumber = mMaxPage;
			}
			Log.d("book", "right");
		}else {//left
			mPagenumber--;
			if (mPagenumber < 0) {
				mPagenumber = 0;
			}
			Log.d("book", "left");
		}
		String filename = null;
		if (mPagenumber < 10) {
			filename = new String("s00" + mPagenumber ); 
		} else  {
			filename = new String("s0" + mPagenumber  ); 
		}
		Resources res = getResources();
		int resid = res.getIdentifier(filename, "drawable", getPackageName());
		Log.d("book", "filename=" + filename + " id=" + resid);
		ImageView img = (ImageView)findViewById(R.id.imageView1);
		img.setImageResource(resid);
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
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
}