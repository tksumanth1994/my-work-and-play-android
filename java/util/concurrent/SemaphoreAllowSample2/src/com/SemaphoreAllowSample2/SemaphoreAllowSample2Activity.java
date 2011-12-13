package com.SemaphoreAllowSample2;

import java.util.concurrent.Semaphore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SemaphoreAllowSample2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Semaphore sem = new Semaphore(1); //allow  only 1 member .
        
        Log.d("test", "constructure. and first time, 1 member permit");
        
        try {
			sem.acquire(); //require permit.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Log.d("test", "acquire. and now 0 member permit."); //allowed
        
        try {
			sem.acquire(); //require permit.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Log.d("test", "May not reach"); //allowed
    }
}