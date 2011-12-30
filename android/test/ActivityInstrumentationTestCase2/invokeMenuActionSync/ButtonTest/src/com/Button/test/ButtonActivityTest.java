package com.Button.test;

import com.Button.R;
import com.Button.ButtonActivity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
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
	
	public void testPushMenu1() throws Exception {
		
		
		mInstrumentation.invokeMenuActionSync(mActivity, Menu.FIRST, 0);
		
		assertEquals(true , true);
	}

}
