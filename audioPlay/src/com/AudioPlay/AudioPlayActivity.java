package com.AudioPlay;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;

public class AudioPlayActivity extends Activity {
    /** Called when the activity is first created. */
	 private static final String TAG = "audio";
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG,"onCreate");

        int bufSize = android.media.AudioTrack.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 
        		8000, 
        		AudioFormat.CHANNEL_CONFIGURATION_MONO, 
        		AudioFormat.ENCODING_PCM_16BIT, 
        		bufSize, 
        		AudioTrack.MODE_STREAM);
        
        
        Log.d(TAG,"bufSize" + bufSize);
        
        File file = new File("/sdcard/rec.raw");
     
        long datalength =  file.length();

        Log.d(TAG,"file length" + datalength);
        
        FileInputStream in = null;
        DataInputStream indata = null;
        try {
            in = new FileInputStream(file);
            indata = new DataInputStream(in);
            
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        	}
       track.play();

        short[] audioData = new short[8000];
       
        
        Log.d(TAG,"start" + datalength);
        for(int i=0;i < datalength;i = i + bufSize) {
            Log.d(TAG,"loopin " + i);
    		int readsize = 0;
        	try {
        		
        		if ((bufSize) < (int)datalength - i) {
        			readsize = bufSize;
        		} else {
        			readsize = (int)datalength - i;
        		}
        		for ( int l =0;l<(readsize/2);l++) {
        			audioData[l] = indata.readShort();
        			if (audioData[l] == 0) {
        				Log.d(TAG,"data " + audioData[l] + " " + l);
        			}
        			
        			
        		}
			} catch (IOException e) {
				e.printStackTrace();
				
			}
            Log.d(TAG,"read " + i + " " + audioData[0]);
            
        	track.write(audioData, 0, (readsize/2));
            Log.d(TAG,"write " + i);
        	
            
            Log.d(TAG,"loop out" + ((datalength) / 8000) );
        }
        Log.d(TAG,"end");
        
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        track.stop();
        track.release();
        
        }
    
}