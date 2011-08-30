package com.audiorecorder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AudiorecorderActivity extends Activity  implements OnClickListener {

	//Sampling rate
	private static final int AUDIO_SAMPLE_FREQ = 8000;
	 
	private static final int AUDIO_BUFFER_SIZE = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_FREQ,
	    AudioFormat.CHANNEL_CONFIGURATION_MONO,
	    AudioFormat.ENCODING_PCM_16BIT) * 2;
	 
	private static final String TAG = "audio";
	 
	AudioRecord record;
	File recFile;
	FileOutputStream out;	
	DataOutputStream dataOutputStreamInstance;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View startButton = findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        View stopButton = findViewById(R.id.button2);
        stopButton.setOnClickListener(this);
        
        Log.d(TAG, "buffer size = " + AUDIO_BUFFER_SIZE);
        try{
        	
        	record = new AudioRecord(MediaRecorder.AudioSource.MIC,
        			AUDIO_SAMPLE_FREQ,
        			AudioFormat.CHANNEL_CONFIGURATION_MONO,
        			AudioFormat.ENCODING_PCM_16BIT,
        			AUDIO_BUFFER_SIZE);
        } catch(IllegalArgumentException e){
        	e.printStackTrace();
            Log.d(TAG,"IllegalArgumentException");
        }
  
    }        

	boolean isRecording  = false;
	 
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.button1){
			recFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/rec.raw");
			try {
				recFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
		       Log.d("audio", "error1");
			}
			try {
				out =  new FileOutputStream(recFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
		        Log.d("audio", "error2");
			}
			dataOutputStreamInstance = new DataOutputStream(out);		
			
			try {
				record.startRecording();
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						while(isRecording) {
							Log.d("audio", "thread run");
							short[] audioBuffer = new short[AUDIO_SAMPLE_FREQ];
							int return_value = record.read(audioBuffer,0,AUDIO_SAMPLE_FREQ);
							Log.d("audio", "periodic" + return_value);
							try {
								for (int i= 0;i<return_value;i++) {
									dataOutputStreamInstance.writeShort(audioBuffer[i]);
								}
							} catch (IOException e) {
								e.printStackTrace();
						        Log.d("audio", "error3");
							}
							
						}
					}
					
				}).start();
				isRecording = true;
			} catch (IllegalStateException e) {
			    Log.d("audio", "IllegalStateException");
				
			}
			
		    Log.d("audio", "startRecording");
			
		} else {
				isRecording = false;
				record.stop();
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				Log.d("audio", "error4");
				}
			    Log.d("audio", "stopRecording");
			}		
	}
}