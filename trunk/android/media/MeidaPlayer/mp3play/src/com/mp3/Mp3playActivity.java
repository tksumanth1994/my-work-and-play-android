package com.mp3;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class Mp3playActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MediaPlayer mP = new MediaPlayer();
        
        try {
			mP.setDataSource("/sdcard/music/sample.mp3");
		} catch (IllegalArgumentException e) {
			Log.d("mp3", "IllegalArgumentException");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.d("mp3", "IllegalStateException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("mp3", "IllegalStateException");
			e.printStackTrace();
		}
        
        try {
			mP.prepare();
		} catch (IllegalStateException e) {
			Log.d("mp3", "prepareIllegalStateException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("mp3", "prepareIOException");
			e.printStackTrace();
		}
        mP.start();
        
    }
}