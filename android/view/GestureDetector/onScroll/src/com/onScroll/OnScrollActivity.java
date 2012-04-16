package com.onScroll;


import android.app.Activity;
import android.os.Bundle;

public class OnScrollActivity extends Activity  {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GestureDetectorView view = new GestureDetectorView(this);
        setContentView(view);
        
    }
   
}