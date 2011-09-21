package com.Preview;

import android.app.Activity;
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

public class PreviewActivity extends Activity {
    /** Called when the activity is first created. */
    Camera camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		Camera.Parameters param = camera.getParameters();
    		Log.d("camera", "now= " + param.getAntibanding ());
    		List< String > Antibandinglist = param.getSupportedAntibanding ();
    		
    		for (int i=0;i < Antibandinglist.size();i++) {
    			Log.d("camera", "type= " + Antibandinglist.get(i));
    		}
    		param.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
    		camera.setParameters(param);
    		
    		Log.d("camera", "next= " + param.getAntibanding());
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
    		//get preview format
    		Log.d("camera", "PreviewFormat=" + param.getPreviewFormat());

    		//get preview supported format
    		List< Integer > previewformatlist = param.getSupportedPreviewFormats ();
    		
    		for (int i=0;i < previewformatlist.size();i++) {
    			Log.d("camera", i + " format = " + previewformatlist.get(i));
    		}
    		
    		//get preview size
    		Log.d("camera", "PreviewSize width=" + param.getPreviewSize().width + " heigth=" + param.getPreviewSize().height);
    		
    		//get preview supported size
    		List< Camera.Size > previewsizelist = param.getSupportedPreviewSizes ();
    		
    		for (int i=0;i < previewsizelist.size();i++) {
    			Log.d("camera", i + " size width = " + previewsizelist.get(i).width + " height = " + previewsizelist.get(i).height);
    		}
    		param.setPreviewSize(1280, 720);
    		
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