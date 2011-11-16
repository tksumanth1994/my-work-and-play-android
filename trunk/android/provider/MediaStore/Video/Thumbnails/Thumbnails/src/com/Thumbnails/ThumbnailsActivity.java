package com.Thumbnails;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class ThumbnailsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TableLayout tablelayout  = new TableLayout(this);
        
        setContentView(tablelayout);
        
        ContentResolver contentresolver = getContentResolver();
        
        Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        
        cursor.moveToFirst();
        
        for (int i = 0;i < cursor.getCount() ; i++ ){
        	long id = cursor.getLong(cursor.getColumnIndexOrThrow("_ID"));
        	
        	Bitmap bmp = MediaStore.Video.Thumbnails.getThumbnail(contentresolver, id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
        
        	ImageView image = new ImageView(this);
    		image.setImageBitmap(bmp);
    		tablelayout.addView(image, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
    		
    		cursor.moveToNext();
        }
        
        
    }
    
    
}