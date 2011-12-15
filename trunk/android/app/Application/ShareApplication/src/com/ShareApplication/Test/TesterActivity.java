package com.ShareApplication.Test;

import com.ShareApplication.R;
import com.ShareApplication.TestApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TesterActivity extends Activity {
	TestApp _testApp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tester);
        
        _testApp = (TestApp) this.getApplication();
        
        Log.d("test", "Tester Number = "+ _testApp.getNumber());
        
    }

}
    