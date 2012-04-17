package com.ScaleImage;

import android.app.Activity;
import android.os.Bundle;

public class ScaleImageActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScaleGestureDetectorView view = new ScaleGestureDetectorView(this);
        setContentView(view);
   }
}