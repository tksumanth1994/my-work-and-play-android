package com.caller;

import com.caller.R.id;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CallerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b= (Button)findViewById(id.button1);
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:117"));
		        startActivity(intent);
			}
		});
    }
}