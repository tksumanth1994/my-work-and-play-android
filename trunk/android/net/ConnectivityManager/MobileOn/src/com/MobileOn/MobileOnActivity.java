package com.MobileOn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MobileOnActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
