package com.WebAccessTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class WebAccessTimeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    protected  void onResume() {
    	super.onResume();

        WebView w = (WebView)findViewById(R.id.webView1);
        w.loadUrl("");

        //get clock
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy'Å@/'MM'/'dd'/ 'HH':'mm':'ss':'");  
        Date  date = new Date();  
        Log.d("date : ", sdf.format(date));      

        //
        SharedPreferences pref = getSharedPreferences("pref", MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);

        String votetime = pref.getString("votetime", sdf.format(date));
        
        TextView prevvotetime = (TextView)findViewById(R.id.text1);
        
        prevvotetime.setText(votetime);
        
        Button votebutton = (Button)findViewById(R.id.button1);
        
        votebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
		        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy'Å@/'MM'/'dd'/ 'HH':'mm':'ss':'");  
		        Date  date = new Date(); 
		        
		        SharedPreferences pref = getSharedPreferences("pref", MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
		        Editor e = pref.edit();
		        e.putString("votetime", sdf.format(date));
		        e.commit();

		        
		        TextView prevvotetime = (TextView)findViewById(R.id.text1);
		        
		        prevvotetime.setText(sdf.format(date));
		        
		        WebView w = (WebView)findViewById(R.id.webView1);
		        w.setWebViewClient(new WebViewClient());
		        w.getSettings().setJavaScriptEnabled(true);
		        w.getSettings().setBuiltInZoomControls(true);
		        w.loadUrl("http://naochiaki.biz/android/WebAccessTime.html");
		        
			}
        	
        });
        
    }
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
	        WebView w = (WebView)findViewById(R.id.webView1);
            w.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }    

}