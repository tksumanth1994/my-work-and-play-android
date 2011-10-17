package com.Accelerometer;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AccelerometerActivity extends Activity  implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	private TextView _text = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        _text = (TextView)findViewById(R.id.textView1);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    
	@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("test", "onAccuracyChanged name" + sensor.getName() );
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
        Log.d("test", "onSensorChanged:" + event.timestamp + ":" + "x=" + event.values[0] + "y=" + event.values[1] + "z=" + event.values[2]);
        
	}    

}
