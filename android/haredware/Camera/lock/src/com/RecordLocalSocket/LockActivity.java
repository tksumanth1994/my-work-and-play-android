package com.RecordLocalSocket;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;

import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
    MediaRecorder _mediarecorder;
    SurfaceHolder _holder;
    LocalSocketLoop _sl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }
    

    public void runLocalSocket() {
    	
    	FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream("/sdcard/xyz.mp4");
	    	FileDescriptor fd = _sl.getReceiverFileDescriptor();
	    	FileInputStream fr = new FileInputStream(fd);
			byte[] byteBuffer = new byte[10*1024];
	    	for(;;) {
	    		int size = 0;
    			size = fr.read(byteBuffer);
				if (size == -1){
					break;
				} else {
					outFile.write(byteBuffer, 0, size);
				}
	    	}
			outFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	
    	
    	Log.d("camera", "close");

    }
    public void runSocket() {
        String hostname = "192.168.1.5";
        int port = 5000;
		Socket s = null;
    	DataOutputStream out = null;
		byte[] byteBuffer = new byte[10*1024];
		try {
			s = new Socket(InetAddress.getByName(hostname), port);
			out = new DataOutputStream(s.getOutputStream());
	    	FileDescriptor fd = _sl.getReceiverFileDescriptor();
	    	FileInputStream fr = new FileInputStream(fd);
	    	for(;;) {
	    		int size = 0;
    			size = fr.read(byteBuffer);
				if (size == -1){
					break;
				} else {
					out.write(byteBuffer, 0, size);
				}
	    	}
    		out.close();
    		s.close();
		} catch (UnknownHostException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
    	
    }

    public void startRecord() {
		
		_camera.unlock();
		
    	_mediarecorder = new MediaRecorder();
        
		_mediarecorder.setCamera(_camera);

        _mediarecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        _mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        _mediarecorder.setVideoSize(640, 480);
        _mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

        
        _sl = new LocalSocketLoop("sample");
        _sl.InitLoop();
        _mediarecorder.setOutputFile(_sl.getSenderFileDescriptor());
        _mediarecorder.setPreviewDisplay(_holder.getSurface());

		try {
			_mediarecorder.prepare();
			_mediarecorder.start();
		} catch (Exception e) {
			Log.d("camera", "err");
		}
		  new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	int j = 0;
			        String hostname = "192.168.1.5";
			        int port = 5000;
					Socket s = null;
					try {
						s = new Socket(InetAddress.getByName(hostname), port);
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
			    	
			    	FileOutputStream outFile = null;
			    	DataOutputStream out = null;
					try {
						out = new DataOutputStream(s.getOutputStream());
						//outFile = new FileOutputStream("/sdcard/xyz"  + ".mp4");
						j++;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			    	FileDescriptor fd = _sl.getReceiverFileDescriptor();
			    	FileInputStream fr = new FileInputStream(fd);
			    	
			    	
					byte[] byteBuffer = new byte[10*1024];
			    	
			    	while(isRecording) {
			    		int size = 0;
			    		try {
			    			if (fr.available() != 0) {
			    				size = fr.read(byteBuffer);
								if (size == -1){
									break;
								} else {
									try {
										out.write(byteBuffer, 0, size);
										//outFile.write(byteBuffer, 0, size);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
			    				
			    			} else {
			    				// no data
			    			}
			    		} catch (IOException e1) {
					    	Log.d("camera", "close");
			    		}
			    		
			    	}
			    	try {
			    		out.close();
			    		s.close();
						//outFile.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	Log.d("camera", "close");

			    }
			  }).start();    	
    	
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
    			startRecord();
    			
    			
    		} else {
				Log.d("camera", "stop record");
    			stopRecord();
				_camera.lock();
    			isRecording = false;
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