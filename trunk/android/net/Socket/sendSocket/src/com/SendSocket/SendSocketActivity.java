package com.SendSocket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SendSocketActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Socket sk = null;
        BufferedWriter wBuffer = null;
        
        try {
			Log.d("socket", "start");
			String addr = "192.168.1.2";
			sk = new Socket(addr, 5000);
			Log.d("socket", "Socket");
	        wBuffer = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
			Log.d("socket", "BufferedWriter");
	        wBuffer.write("testtesttest");
			Log.d("socket", "write");
	        wBuffer.flush();
			Log.d("socket", "flush");
	        wBuffer.close();
			Log.d("socket", "close");
		} catch (UnknownHostException e) {
			Log.d("socket", "UnknownHostException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("socket", "IOException");
			e.printStackTrace();
		}
        
    }
}