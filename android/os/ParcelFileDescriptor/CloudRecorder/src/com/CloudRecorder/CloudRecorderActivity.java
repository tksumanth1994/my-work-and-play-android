package com.CloudRecorder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CloudRecorderActivity extends Activity  {
    /** Called when the activity is first created. */
    Camera _camera;
    MediaRecorder _mediarecorder;
    SurfaceHolder _holder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }
    
    ParcelFileDescriptor pfd = null;
    Socket  socket = null;

    public void startRecord() {
		
		_camera.unlock();
		
    	_mediarecorder = new MediaRecorder();
        
		_mediarecorder.setCamera(_camera);

        _mediarecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        _mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        _mediarecorder.setVideoSize(640, 480);
        _mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        
        String hostname = "192.168.1.2";
        int port = 5000;
        try {
			socket = new Socket(InetAddress.getByName(hostname), port);
			pfd = ParcelFileDescriptor.fromSocket(socket);
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        _mediarecorder.setOutputFile(pfd.getFileDescriptor());
        _mediarecorder.setPreviewDisplay(_holder.getSurface());
		try {
			_mediarecorder.prepare();
			_mediarecorder.start();
		} catch (Exception e) {
			Log.d("camera", "err");
		}
    	
    	
    }
    void stopRecord() {
		_mediarecorder.stop();
		_mediarecorder.reset();
		_mediarecorder.release();
		try {
			pfd.close();
			socket.close();
		} catch (IOException e) {
			Log.d("camera", "pfd close err");
			e.printStackTrace();
		}
    }
    boolean isRecording = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		if (isRecording == false) {
    			isRecording = true;
				Log.d("camera", "start record");
    			startRecord();
    			
    			
    		} else {
    			isRecording = false;
				Log.d("camera", "stop record");
    			stopRecord();
				_camera.lock();
    		}
     	}
    	
		return super.onTouchEvent(event); 
	}    
    
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
     
        CameraPreview(Context context) {
            super(context);
            _holder = getHolder();
            _holder.addCallback(this);
            _holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
     
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            _camera.stopPreview();
			try {
				_camera.setPreviewDisplay(holder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_camera.startPreview();
        }
        
        public void surfaceDestroyed(SurfaceHolder holder) {
            _camera.stopPreview();
            _camera.release();
        }

		public void surfaceCreated(SurfaceHolder holder) {
			_camera = Camera.open();
			
			try {
				_camera.setPreviewDisplay(holder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_camera.startPreview();

			
		}
    }    
}