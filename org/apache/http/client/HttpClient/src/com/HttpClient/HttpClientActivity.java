package com.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HttpClientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String url = "http://www.yahoo.co.jp";
        HttpClient hc = new DefaultHttpClient();
        
        HttpGet get = new HttpGet(url);
        
        try {
            HttpResponse res;
			res = hc.execute(get);
			if (res.getStatusLine().getStatusCode() == 200) {
				//It is ok.
				InputStream input = res.getEntity().getContent();
				
				BufferedReader rBuffer = new BufferedReader(new InputStreamReader(input, "UTF-8"));
	        	String readLine;
	        	
	        	try {
					while((readLine = rBuffer.readLine()) != null) {
						Log.d("HttpClientActivity", readLine);
					}
				} catch (IOException e) {
					Log.d("HttpClientActivity", " read IOException");
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
			Log.d("HttpClientActivity", "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("HttpClientActivity", "IOException");
			e.printStackTrace();
		}
    }
}