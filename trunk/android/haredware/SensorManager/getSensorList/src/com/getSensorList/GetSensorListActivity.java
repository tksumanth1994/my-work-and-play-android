package com.getSensorList;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class GetSensorListActivity extends Activity {
    /** Called when the activity is first created. */
	SensorManager mSensorManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        
        for( Sensor s : sensorList) {
        	Log.d("sensor", "Name=" + s.getName());
        	Log.d("sensor", "Vendor=" + s.getVendor());
        	Log.d("sensor", "Version=" + s.getVersion());
        	Log.d("sensor", "MaximumRange=" + s.getMaximumRange());
        	Log.d("sensor", "MinDelay=" + s.getMinDelay());
        	Log.d("sensor", "Power=" + s.getPower());
        	Log.d("sensor", "Type=" + s.getType());
        }
    }
}