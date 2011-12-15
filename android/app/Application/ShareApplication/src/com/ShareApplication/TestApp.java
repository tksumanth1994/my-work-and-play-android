package com.ShareApplication;

import android.app.Application;
import android.util.Log;

public class TestApp extends Application {
	private int _number = 0;
	@Override
    public void onCreate() {
		Log.d("test", "onCreate");
	}
    @Override
    public void onTerminate() {
		Log.d("test", "onTerminate");
    }
    public void setNumber(int number){
		Log.d("test", "setNumber");
    	_number = number;
    }
    public int getNumber(){
		Log.d("test", "getNumber");
    	return _number;
    }

}
