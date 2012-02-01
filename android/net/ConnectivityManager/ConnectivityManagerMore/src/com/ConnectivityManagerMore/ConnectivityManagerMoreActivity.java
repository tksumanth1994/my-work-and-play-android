package com.ConnectivityManagerMore;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class ConnectivityManagerMoreActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        
        Log.d("net", "getTypeName = " + ni.getTypeName());

        int pre = cm.getNetworkPreference();
        
        Log.d("net", "getNetworkPreference = " + pre);
        
        cm.setNetworkPreference(ConnectivityManager.TYPE_MOBILE);
        //cm.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
        
        Log.d("net", "getNetworkPreference = " + pre);
        
        boolean valid = ConnectivityManager.isNetworkTypeValid(ConnectivityManager.TYPE_MOBILE);
        
        Log.d("net", "TYPE_MOBILE isNetworkTypeValid = " + valid);
        
        valid = ConnectivityManager.isNetworkTypeValid(ConnectivityManager.TYPE_WIMAX);
        
        Log.d("net", "TYPE_WIMAX isNetworkTypeValid = " + valid);
        
    }
}