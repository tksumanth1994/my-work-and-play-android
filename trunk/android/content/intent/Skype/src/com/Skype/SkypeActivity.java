package com.Skype;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class SkypeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent skypeintent = new
        		Intent("android.intent.action.CALL_PRIVILEGED");
        		skypeintent.setClassName("com.skype.raider", "com.skype.raider.Main");
        		skypeintent.setData(Uri.parse("tel:" + "miyazaki.yuusuke1"));
        		startActivity(skypeintent);
        		
    }
}