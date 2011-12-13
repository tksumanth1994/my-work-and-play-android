package com.SemaphoreAllowSample;

import java.util.concurrent.Semaphore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SemaphoreAllowSampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Semaphore sem = new Semaphore(1); //allow  only 1 member .
        Log.d("test", "constructure");
        sem.drainPermits(); // No permit.
        Log.d("test", "drainPermits");
        
        sem.release(); //release semaphore and 1 permit.
        
        Log.d("test", "release");
        
        try {
			sem.acquire(); //require permit.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Log.d("test", "acquire"); //allowed
        
    }
}