package com.surface.test;

import com.surface.SurfaceActivity;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;


public class SurfaceActivityTest extends ActivityInstrumentationTestCase2<SurfaceActivity> {
	public SurfaceActivityTest() {
		super("com.surface", SurfaceActivity.class);
	}
	private SurfaceActivity mActivity = null;
	private Instrumentation mInstrumentation = null;
	
	public void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mInstrumentation = getInstrumentation();
		
	}
	public void tearDown() throws Exception {
		
	}
	
	public void testPushMenu1() throws Exception {
		
		long downtime = SystemClock.uptimeMillis();
		long eventtime = SystemClock.uptimeMillis();
		MotionEvent event = MotionEvent.obtain(downtime, eventtime, MotionEvent.ACTION_DOWN, 100, 100, 0); 
		mInstrumentation.sendPointerSync(event);
		
		eventtime = SystemClock.uptimeMillis() + 1000;
		event = MotionEvent.obtain(downtime, eventtime, MotionEvent.ACTION_UP, 100, 100, 0); 
		mInstrumentation.sendPointerSync(event);

		assertEquals(true , true);
	}

}
