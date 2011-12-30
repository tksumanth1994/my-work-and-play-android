package com.Button;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ButtonActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.button1);
        
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("test", "button1");
			}
		});
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "1");
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "2");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case Menu.FIRST:
        	Log.d("test", "pushed 1");
            break;
        case Menu.FIRST + 1:
        	Log.d("test", "pushed 2");
            break;
        default:
            break;
        }
        return true;
    }   
}