package com.GridView.test;

import com.GridView.R;
import com.GridView.GridViewActivity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewTest extends ActivityInstrumentationTestCase2<GridViewActivity>  {
	public GridViewTest() {
		super("com.GridView", GridViewActivity.class);
	}
	private GridViewActivity mActivity = null;
	private Instrumentation mInstrumentation = null;
	
	public void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mInstrumentation = getInstrumentation();
		
	}
	public void tearDown() throws Exception {
		
	}
	
	public void testPush() throws Exception {
		final GridView gridview =(GridView)mActivity.findViewById(R.id.gridView1);
		int gridnum = gridview.getAdapter().getCount();
		Log.d("GridViewActivity", "gridnum = " + gridnum);
		
		Log.d("GridViewActivity", "getFirstVisiblePosition = " + gridview.getFirstVisiblePosition());
		Log.d("GridViewActivity", "getLastVisiblePosition = " + gridview.getLastVisiblePosition ());
		Log.d("GridViewActivity", "getSelectedItemPosition = " + gridview.getSelectedItemPosition ());
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				Log.d("GridViewActivity", "setSelection 2");
				gridview.setSelection(2);
			}
		});
		mInstrumentation.waitForIdleSync();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				
				Log.d("GridViewActivity", "getSelectedItemPosition = " + gridview.getSelectedItemPosition ());
				
				Log.d("GridViewActivity", "getSelectedView");
				ImageView imageview = (ImageView)gridview.getSelectedView();
				Log.d("GridViewActivity", "performClick");
				imageview.performClick();
			}
		});
		mInstrumentation.waitForIdleSync();
		
		assertEquals(true , true);
	
	}
	
}
