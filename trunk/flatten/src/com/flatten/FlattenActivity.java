package com.flatten;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;

public class FlattenActivity extends Activity {
    /** Called when the activity is first created. */
    Camera camera;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		camera = Camera.open();
		Camera.Parameters param = camera.getParameters();
		String flatten = param.flatten();
		
		Log.d("camera", flatten);
    }
}