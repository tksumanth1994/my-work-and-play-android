package com.FaceDetector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.util.Log;

public class FaceDetectorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        Bitmap bitmapimage = BitmapFactory.decodeFile("/sdcard/face.jpg");
        
        FaceDetector facedetector = new FaceDetector(bitmapimage.getWidth(), bitmapimage.getHeight(), 10);
        
    	Log.d("test", "getWidth = " + bitmapimage.getWidth());
    	Log.d("test", "getHeight = " + bitmapimage.getHeight());
        Face[] faces = new Face[10];
        int num = facedetector.findFaces(bitmapimage, faces);
        for (int i = 0;i < num;i++) {
        	PointF point = new PointF();
        	Log.d("test", "confidence = " + faces[i].confidence());
        	Log.d("test", "eyesDistance = " + faces[i].eyesDistance());
        	faces[i].getMidPoint(point);
        	Log.d("test", "MidPoint x = " + point.x + " y=" + point.y);
        	Log.d("test", "euler x = " + faces[i].pose(Face.EULER_X));
        	Log.d("test", "euler y = " + faces[i].pose(Face.EULER_Y));
        	Log.d("test", "euler x = " + faces[i].pose(Face.EULER_Z));
        }
        
    }
}