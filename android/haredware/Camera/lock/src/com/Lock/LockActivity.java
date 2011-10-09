package com.Lock;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class LockActivity extends Activity {
    /** Called when the activity is first created. */
    Camera _camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }

    MediaRecorder _mediarecorder;
    SurfaceHolder _holder;

    public void startRecord() {
    	_mediarecorder = new MediaRecorder();
		_camera.unlock();
        _mediarecorder.setCamera(_camera);
        
        _mediarecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        _mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        _mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        _mediarecorder.setOutputFile("/sdcard/sample.3gp");
//        _mediarecorder.setVideoFrameRate(27);
//        _mediarecorder.setVideoSize(320, 240);
//        _mediarecorder.setPreviewDisplay(_holder.getSurface());
        
		try {
			_mediarecorder.prepare();
			_mediarecorder.start();
		} catch (Exception e) {
			Log.d("camera", "prepare err");
		}
    	
    	
    }
    void stopRecord() {
		_mediarecorder.stop();
		_mediarecorder.reset();
		_mediarecorder.release();
    }
    boolean isRecording = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		if (isRecording == false) {
    			isRecording = true;
				Log.d("camera", "start record");
    			//ò^âÊäJénèàóù
    			startRecord();
    			
    			
    		} else {
    			isRecording = false;
				Log.d("camera", "stop record");
    			//ò^âÊí‚é~èàóù
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
    		configure( format,  width, height) ;
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
		protected void setPictureFormat(int format) {
			Camera.Parameters params = _camera.getParameters();
			List<Integer> supported = params.getSupportedPictureFormats();
			if (supported != null) {
				for (int f : supported) {
					if (f == format) {
						params.setPreviewFormat(format);
						_camera.setParameters(params);
						break;
					}
				}
			}
		}
		
		protected void setPreviewSize(int width, int height) {
			Camera.Parameters params = _camera.getParameters();
			List<Camera.Size> supported = params.getSupportedPreviewSizes();
			if (supported != null) {
				for (Camera.Size size : supported) {
					if (size.width <= width && size.height <= height) {
						params.setPreviewSize(size.width, size.height);
						_camera.setParameters(params);
						break;
					}
				}
			}
		}
		public void configure(int format, int width, int height) {
			_camera.stopPreview();
			setPictureFormat(format);
			setPreviewSize(width, height);
			_camera.startPreview();
		}
    }    
    
}