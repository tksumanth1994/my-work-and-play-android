package com.MailSned;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MailSendActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String to = new String("xxxx@gmail.com");
        String title = new String("Hello");
        String text = new String("test");
        
        Intent in = new Intent();
        in.setAction(Intent.ACTION_SENDTO);
        in.setData(Uri.parse("mailto:" + to));
        in.putExtra(Intent.EXTRA_SUBJECT, title);
        in.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(in);
    }
}