package com.DisplayOrientation;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class DisplayOrientationActivity extends Activity {
    /** Called when the activity is first created. */
    Camera _camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }
    
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        
        android.hardware.Camera.getCameraInfo(cameraId, info);
        
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        
		Log.d("camera", "result= " + result);
		camera.stopPreview();
        camera.setDisplayOrientation(result);
        camera.startPreview();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
		Log.d("camera", "event= " + event);
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		setCameraDisplayOrientation(this, 0, _camera);
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
