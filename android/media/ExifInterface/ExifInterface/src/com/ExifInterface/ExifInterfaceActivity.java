package com.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class ExifInterfaceActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ExifInterface exif = null;
        
        try {
			exif = new ExifInterface("/sdcard/test.jpg");
			Log.d("test", "ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("test", "IOException");
			return;
		}
        double altitude = exif.getAltitude(1.0);
        
		Log.d("test", "Altitude=" + altitude);
        
		Log.d("test", "FNumber=" + exif.getAttribute("FNumber"));
		
		Log.d("test", "DateTime=" + exif.getAttribute("DateTime"));
		
		Log.d("test", "ExposureTime=" + exif.getAttribute("ExposureTime"));
		
		Log.d("test", "Flash=" + exif.getAttributeInt("Flash", 0));
		
		Log.d("test", "FocalLenght=" + exif.getAttributeDouble("FocalLenght", 0));
		
		Log.d("test", "GPSAltitude=" + exif.getAttributeDouble("GPSAltitude", 0));
		
		Log.d("test", "GPSAltitudeRef=" + exif.getAttributeInt("GPSAltitudeRef", 0));
		
		Log.d("test", "GPSDateStamp=" + exif.getAttribute("GPSDateStamp"));
		
		Log.d("test", "GPSLatitude=" + exif.getAttribute("GPSLatitude"));
		
		Log.d("test", "GPSLatitudeRef=" + exif.getAttribute("GPSLatitudeRef"));
		
		Log.d("test", "GPSLongitude=" + exif.getAttribute("GPSLongitude"));
		
		Log.d("test", "GPSLongitudeRef=" + exif.getAttribute("GPSLongitudeRef"));
		
		Log.d("test", "GPSProcessingMethod=" + exif.getAttribute("GPSProcessingMethod"));
		
		Log.d("test", "GPSTimeStamp=" + exif.getAttribute("GPSTimeStamp"));
		
		Log.d("test", "ImageLength=" + exif.getAttributeInt("ImageLength", 0));
		
		Log.d("test", "ImageWidth=" + exif.getAttributeInt("ImageWidth", 0));
		
		Log.d("test", "ISOSpeedRatings=" + exif.getAttribute("ISOSpeedRatings"));
		
		Log.d("test", "Make=" + exif.getAttribute("Make"));
		
		Log.d("test", "Model=" + exif.getAttribute("Model"));
		
		Log.d("test", "Orientation=" + exif.getAttributeInt("Orientation", 0));
		
		Log.d("test", "WhiteBalance=" + exif.getAttribute("WhiteBalance"));
		
		Log.d("test", "hasThumbnail=" + exif.hasThumbnail());
		
		if (exif.hasThumbnail()) {
			outputFile(exif.getThumbnail());
		}
		
    }
	private void outputFile(byte[] data) {
		File recFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cap.jpg");
        try {
        	recFile.delete();
            recFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("camera", "error1");
        }
        
        FileOutputStream out = null;
        try {
            out =  new FileOutputStream(recFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("camera", "error2");
        }
        Log.d("camera", "start write " + data.length);
       	try {
			out.write(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
	        Log.d("camera", "err ");
			e1.printStackTrace();
		}
        Log.d("camera", "stop write " + data.length);
        
        
        try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }      		
    
}