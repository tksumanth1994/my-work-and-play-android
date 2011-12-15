package com.ShareApplication;

import com.ShareApplication.Test.TesterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ShareApplicationActivity extends Activity {
    /** Called when the activity is first created. */
	TestApp _testApp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _testApp = (TestApp) this.getApplication();
        
        _testApp.setNumber(8);
        Log.d("test", "Number = "+ _testApp.getNumber());
        
		Intent intent = new Intent();
		intent.setClass(this, TesterActivity.class);
		startActivity(intent);
        
    }
}