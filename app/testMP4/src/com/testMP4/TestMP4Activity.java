package com.testMP4;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestMP4Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MP4Config mp = null;
        try {
			mp = new MP4Config("/sdcard/sample.mp4");
			//mp = new MP4Config("/sdcard/xyz.mp4");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        Log.d("mp4", "profile:" + mp.getProfileLevel());
        
        Log.d("mp4", "PPS:" + mp.getB64PPS());
        
        Log.d("mp4", "SPS:" + mp.getB64SPS());
        
        byte []sps = mp.getSPS();
        Log.d("mp4", "sps lenght= " + sps.length);
        byte []pps = mp.getPPS();
        Log.d("mp4", "pps lenght= " + pps.length);
        
        //for(int i = 0;;)
        
    }
}