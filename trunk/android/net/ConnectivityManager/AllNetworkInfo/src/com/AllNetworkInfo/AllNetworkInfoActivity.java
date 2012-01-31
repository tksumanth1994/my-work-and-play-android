package com.AllNetworkInfo;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class AllNetworkInfoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        
        NetworkInfo[] ni = cm.getAllNetworkInfo();
        
        for (int i = 0;i< ni.length ; i++) {
        	NetworkInfo.DetailedState dt = ni[i].getDetailedState();
        
        	Log.d("net", "getDetailedState = " + dt);
        	Log.d("net", "getExtraInfo = " + ni[i].getExtraInfo());
        	Log.d("net", "getReason = " + ni[i].getReason());
        
        	NetworkInfo.State st = ni[i].getState();
        
        	Log.d("net", "getState = " + st);
        	Log.d("net", "getSubtype = " + ni[i].getSubtype());
        	Log.d("net", "getSubtypeName = " + ni[i].getSubtypeName());
        	Log.d("net", "getType = " + ni[i].getType());
        	Log.d("net", "getTypeName = " + ni[i].getTypeName());
        	Log.d("net", "isAvailable = " + ni[i].isAvailable());
        	Log.d("net", "isConnected = " + ni[i].isConnected());
        	Log.d("net", "isConnectedOrConnecting  = " + ni[i].isConnectedOrConnecting ());
        	Log.d("net", "isFailover   = " + ni[i].isFailover  ());
        	Log.d("net", "isRoaming   = " + ni[i].isRoaming  ());
        }
     }
}