package com.HttpGet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HttpGetActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String host ="playandworkandroid.blogspot.com";
        
        Socket sk = null;
        BufferedWriter wBuffer = null;
        BufferedReader rBuffer = null;
        
        try {
			sk = new Socket(host, 80);
			rBuffer = new BufferedReader(new InputStreamReader(sk.getInputStream()));
			wBuffer = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
			
		} catch (UnknownHostException e) {
			Log.d("HttpGetActivity", "UnknownHostException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("HttpGetActivity", "IOException");
			e.printStackTrace();
		}
        
        
        if ((sk != null) && (rBuffer != null) && (wBuffer != null)) {
        	try {
				wBuffer.write("GET / HTTP/1.1\n");
	        	wBuffer.write("host : " + host + "\n");
	        	wBuffer.write("\n");
	        	wBuffer.flush();
			} catch (IOException e) {
				Log.d("HttpGetActivity", " write IOException");
				e.printStackTrace();
			}
        	
        	String readLine;
        	
        	try {
				while((readLine = rBuffer.readLine()) != null) {
					Log.d("HttpGetActivity", readLine);
				}
			} catch (IOException e) {
				Log.d("HttpGetActivity", " read IOException");
				e.printStackTrace();
			}
        	
        }
        
        
        
        
    }
}