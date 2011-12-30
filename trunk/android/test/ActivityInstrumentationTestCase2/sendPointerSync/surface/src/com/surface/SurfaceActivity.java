package com.surface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class SurfaceActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {
    	Log.d("surface", "x=" + me.getX() + " y=" + me.getY());
		return false;
    	
    }
}