package com.Picture;

import android.app.Activity;
import android.os.Bundle;

import android.os.Environment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PictureActivity extends Activity {
    /** Called when the activity is first created. */
    Camera camera;
    Camera.AutoFocusCallback mAutoFocusListener  =  
    	new Camera.AutoFocusCallback() {    
    		@Override  
    		public void onAutoFocus(boolean success, Camera camera) {  
    	    	Log.d("camera", "autofocus complete");
        		//Camera.Parameters param = camera.getParameters();
        		//float[] distances = new float[3];
        		//param.getFocusDistances(distances);
        		//Log.d("camera", "near index = " + distances[Camera.Parameters.FOCUS_DISTANCE_NEAR_INDEX]);
        		//Log.d("camera", "optimal index = " + distances[Camera.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX]);
        		//Log.d("camera", "far index = " + distances[Camera.Parameters.FOCUS_DISTANCE_FAR_INDEX]);
    	    	camera.takePicture(null, null, null, mPictureListener);
    	    }
    	};
    	private Camera.PictureCallback mPictureListener =   
    			    new Camera.PictureCallback() {     
    			    @Override  
    			    public void onPictureTaken(byte[] data, Camera camera) {
    	    	    	Log.d("camera", "onPictureTaken");
    			    	String path = Environment.getExternalStorageDirectory().getPath();
    			    	FileOutputStream fos;
						try {
							fos = new FileOutputStream(path + "/test.jpg");
	    			    	fos.write(data);
	    			    	fos.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
			        		Log.d("camera", "err");
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
			        		Log.d("camera", "err");
							e.printStackTrace();
						}
						camera.startPreview();
    			    }  
    			  };      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		camera.autoFocus(mAutoFocusListener);
    	}
		return super.onTouchEvent(event); 
	}    
    	    
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
     
        CameraPreview(Context context) {
            super(context);
            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
     
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    		configure( format,  width, height) ;
        }
        
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
        }

		public void surfaceCreated(SurfaceHolder holder) {
			camera = Camera.open();
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//get
    		Camera.Parameters param = camera.getParameters();
    		
    		//get picture format
    		Log.d("camera", "format= " + param.getPictureFormat ());
    		
    		//get supported picture format
    		
    		List<Integer> pictureformatlist = param.getSupportedPictureFormats ();
    		for (int i=0;i < pictureformatlist.size();i++) {
    			Log.d("camera", "format " + i + " = " + pictureformatlist.get(i));
    		}
    		
    		//get picture size
    		Log.d("camera", "width= " + param.getPictureSize ().width + " height= " + param.getPictureSize ().height);
    		
    		//get supported picture size
    		
    		List<Camera.Size> picturesizelist = param.getSupportedPictureSizes ();
    		for (int i=0;i < picturesizelist.size();i++) {
    			Log.d("camera", i + " width = " + picturesizelist.get(i).width + " height = "  + picturesizelist.get(i).height);
    		}
    		
    		Log.d("camera", "jpeg quality = " + param.getJpegQuality ());
    		
    		param.setPictureSize(2048, 1536);
    		camera.setParameters(param);
			
			camera.startPreview();

			
		}
		protected void setPictureFormat(int format) {
			Camera.Parameters params = camera.getParameters();
			List<Integer> supported = params.getSupportedPictureFormats();
			if (supported != null) {
				for (int f : supported) {
					if (f == format) {
						params.setPreviewFormat(format);
						camera.setParameters(params);
						break;
					}
				}
			}
		}
		
		protected void setPreviewSize(int width, int height) {
			Camera.Parameters params = camera.getParameters();
			List<Camera.Size> supported = params.getSupportedPreviewSizes();
			if (supported != null) {
				for (Camera.Size size : supported) {
					if (size.width <= width && size.height <= height) {
						params.setPreviewSize(size.width, size.height);
						camera.setParameters(params);
						break;
					}
				}
			}
		}
		public void configure(int format, int width, int height) {
			camera.stopPreview();
			setPictureFormat(format);
			setPreviewSize(width, height);
			camera.startPreview();
		}
    }    
    
}