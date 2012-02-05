package com.ExternalStoragePath;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class ExternalStoragePathActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File file = Environment.getExternalStorageDirectory();
        Log.d("test", file.getPath());
    }
}