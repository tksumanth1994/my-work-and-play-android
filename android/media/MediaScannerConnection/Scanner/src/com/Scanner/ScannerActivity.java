package com.Scanner;

import android.app.Activity;
import android.os.Bundle;

import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


public class ScannerActivity extends Activity implements SurfaceHolder.Callback {
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

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (!_isRecording) {
					_recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
					_recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					_recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

					_recorder.setOutputFile("/sdcard/sample.3gp");
					_recorder.setPreviewDisplay(_holder.getSurface());

					try {
						_recorder.prepare();
					} catch (Exception e) {
						Log.e("test", "recorder error");
					}
					_recorder.start();
					_isRecording = true;

				} else {
					_recorder.stop();
					_recorder.reset();
					_isRecording = false;
					String[] paths = {"/sdcard/sample.3gp"};
					String[] mimeTypes = {"video/3gpp"};
					
					MediaScannerConnection.scanFile(this, paths, mimeTypes, null);
				}
			}
			return true;
		}
	}
	


