package com.CameraInfo;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;

public class CameraInfoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Camera camera = Camera.open();
        
        int numberofcamera = Camera.getNumberOfCameras();
        Log.d("camera", "number = " + numberofcamera);
        
        for (int i = 0 ; i < numberofcamera;i++) {
        	CameraInfo camerainfo = new Camera.CameraInfo();
        	Camera.getCameraInfo(i, camerainfo);
        	
        	Log.d("camera", "facing =" + camerainfo.facing);
        	Log.d("camera", "orientation =" + camerainfo.orientation);
        }
    }
}