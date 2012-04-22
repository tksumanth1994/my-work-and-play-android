package com.webview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WebviewActivity extends Activity {
    /** Called when the activity is first created. */
	InputMethodManager mI;
	Context mC;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mC = this;
        setContentView(R.layout.main);
        
        WebView w = (WebView)findViewById(R.id.webView1);
        w.setWebViewClient(new WebViewClient());
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setBuiltInZoomControls(true);
        w.setWebViewClient(new webclient());
        w.loadUrl("http://www.google.co.jp");

        mI = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);  
        
        Button b = (Button)findViewById(R.id.button1);
        
        b.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        EditText e = (EditText)findViewById(R.id.editText1);
		        if (e.isFocused()) {
				
		        	WebView w = (WebView)findViewById(R.id.webView1);
		        
					mI.hideSoftInputFromWindow(w.getWindowToken(), 0);
					TextView t = (TextView)findViewById(R.id.editText1);
				
					if (!t.getText().toString().matches(".")){
						w.loadUrl("http://www.google.co.jp/search?q=" + t.getText().toString());
					} else if (!t.getText().toString().startsWith("http://")) {
			        	w.loadUrl("http://" + t.getText().toString());
					} else {
						w.loadUrl(t.getText().toString());
					}
		        }
			}
		});
        EditText e = (EditText)findViewById(R.id.editText1);
        e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
			        EditText e = (EditText)findViewById(R.id.editText1);
					e.selectAll();
				}
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
    public class webclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            setProgressBarIndeterminateVisibility(false);
	        WebView w = (WebView)findViewById(R.id.webView1);
			TextView t = (TextView)findViewById(R.id.editText1);
			String u = w.getUrl();
			t.setText(u);
			w.requestFocus();
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            setProgressBarIndeterminateVisibility(true);
        } 
    }
    

}