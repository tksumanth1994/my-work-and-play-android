package com.CameraProfile;

import android.app.Activity;
import android.media.CameraProfile;
import android.os.Bundle;
import android.util.Log;

public class CameraProfileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int quality  = CameraProfile.getJpegEncodingQualityParameter(CameraProfile.QUALITY_HIGH);
        Log.d("test", "QUALITY_HIGH=" + quality);
        
        quality  = CameraProfile.getJpegEncodingQualityParameter(CameraProfile.QUALITY_MEDIUM);
        Log.d("test", "QUALITY_MEDIUM=" + quality);
        
        quality  = CameraProfile.getJpegEncodingQualityParameter(CameraProfile.QUALITY_LOW);
        Log.d("test", "QUALITY_LOW=" + quality);
        
    }
}