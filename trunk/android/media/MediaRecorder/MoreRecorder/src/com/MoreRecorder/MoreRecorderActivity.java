package com.MoreRecorder;


import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class MoreRecorderActivity extends Activity implements SurfaceHolder.Callback {
	private MediaRecorder _recorder;
	private boolean _isRecording;
	SurfaceHolder _holder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		SurfaceView mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		SurfaceHolder holder = mySurfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		_recorder = new MediaRecorder();
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		_holder = holder;
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	MediaRecorder.OnErrorListener mErrorListener = new MediaRecorder.OnErrorListener() {
		
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			switch (what) {
			case MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN :
				_recorder.stop();
				_recorder.reset();
				_isRecording = false;
				Log.d("camera", "stop");
				Log.d("camera", "onError=" +  MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN);
				break;
			default:
				break;
			}
		}
	};
	
	MediaRecorder.OnInfoListener mInfoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			// TODO Auto-generated method stub
			switch (what) {
			case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED :
				_recorder.stop();
				_recorder.reset();
				_isRecording = false;
				Log.d("camera", "stop");
				Log.d("camera", "onInfo=" +  MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED);
				break;
			case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED :
				_recorder.stop();
				_recorder.reset();
				_isRecording = false;
				Log.d("camera", "stop");
				Log.d("camera", "onInfo=" +  MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED);
				break;
			case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN :
				Log.d("camera", "onInfo=" +  MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN);
				break;
			}
		}
	};
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!_isRecording) {
				_recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
				_recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				_recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

				_recorder.setOutputFile("/sdcard/sample.3gp");
				_recorder.setPreviewDisplay(_holder.getSurface());
				
				_recorder.setMaxDuration(30000); //max 30 seconds
				_recorder.setMaxFileSize(40000000); //max 40M bytes
				
				_recorder.setOnErrorListener(mErrorListener);
				_recorder.setOnInfoListener(mInfoListener);

				_recorder.setOrientationHint(0);
				
				try {
					_recorder.prepare();
				} catch (Exception e) {
					Log.e("camera", "recorder error");
				}
				_recorder.start();
				_isRecording = true;
				Log.d("camera", "start");

			} else {
				_recorder.stop();
				_recorder.reset();
				_isRecording = false;
				Log.d("camera", "stop");
			}
		}
		return true;
	}
}


