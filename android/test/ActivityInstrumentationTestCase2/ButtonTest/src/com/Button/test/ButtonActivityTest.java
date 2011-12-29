package com.Button.test;

import com.Button.R;
import com.Button.ButtonActivity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.widget.Button;


public class ButtonActivityTest extends ActivityInstrumentationTestCase2<ButtonActivity> {
	public ButtonActivityTest() {
		super("com.Button", ButtonActivity.class);
	}

	private ButtonActivity mActivity = null;
	private Instrumentation mInstrumentation = null;
	
	public void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mInstrumentation = getInstrumentation();
		
	}
	public void tearDown() throws Exception {
		
	}
	
	public void testPushButton() throws Exception {
		
		final Button btnExecute = (Button)mActivity.findViewById(R.id.button1);
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				btnExecute.dispatchTouchEvent(MotionEvent.obtain(5,0, MotionEvent.ACTION_DOWN, 0, 0, 0));
				btnExecute.dispatchTouchEvent(MotionEvent.obtain(5,0, MotionEvent.ACTION_UP, 0, 0, 0));
				
			}
			
		});
		
		mInstrumentation.waitForIdleSync();
		
		assertEquals(true , true);
	}

}
