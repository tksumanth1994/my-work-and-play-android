package com.ConnectivityManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class ConnectivityManagerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Log.d("cm", "TYPE_MOBILE isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_MOBILE isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_MOBILE isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_MOBILE isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_MOBILE isRoaming=" + ni.isRoaming());
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        
        Log.d("cm", "TYPE_WIFI isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_WIFI isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_WIFI isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_WIFI isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_WIFI isRoaming=" + ni.isRoaming());
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_DUN);
        if (ni!=null) {
        Log.d("cm", "TYPE_MOBILE_DUN isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_MOBILE_DUN isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_MOBILE_DUN isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_MOBILE_DUN isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_MOBILE_DUN isRoaming=" + ni.isRoaming());
        }
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_HIPRI);
        if (ni!=null) {
        Log.d("cm", "TYPE_MOBILE_HIPRI isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_MOBILE_HIPRI isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_MOBILE_HIPRI isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_MOBILE_HIPRI isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_MOBILE_HIPRI isRoaming=" + ni.isRoaming());
        }
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_MMS);
        if (ni!=null) {
        Log.d("cm", "TYPE_MOBILE_MMS isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_MOBILE_MMS isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_MOBILE_MMS isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_MOBILE_MMS isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_MOBILE_MMS isRoaming=" + ni.isRoaming());
        }
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_SUPL);
        if (ni!=null) {
        Log.d("cm", "TYPE_MOBILE_SUPL isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_MOBILE_SUPL isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_MOBILE_SUPL isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_MOBILE_SUPL isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_MOBILE_SUPL isRoaming=" + ni.isRoaming());
        }
        
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        if (ni!=null) {
        Log.d("cm", "TYPE_WIMAX isAvailable=" + ni.isAvailable());
        Log.d("cm", "TYPE_WIMAX isConnected=" + ni.isConnected());
        Log.d("cm", "TYPE_WIMAX isConnectedOrdConnecting=" + ni.isConnectedOrConnecting());
        Log.d("cm", "TYPE_WIMAX isFailover=" + ni.isFailover());
        Log.d("cm", "TYPE_WfIMAX isRoaming=" + ni.isRoaming());
        }
        
        
        
        //cm.stopUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "aa");
        setMobileDataEnabled(true);
    }
    
    public void setMobileDataEnabled(boolean toBeEnabled) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        Class<?> clazz = null;
        try {
            clazz = Class.forName(cm.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Method method = null;
        try {
            Method[] available_methods = clazz.getDeclaredMethods();
            for (Method m : available_methods) {
                if (m.getName().contains("setMobileDataEnabled")) {
                    method = m;
                    break;
                }
            }
            method.invoke(cm, toBeEnabled);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }}
