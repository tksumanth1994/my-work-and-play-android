package com.ThumbnailUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class ThumbnailUtilsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TableLayout tablelayout  = new TableLayout(this);
        
        setContentView(tablelayout);
                
        Bitmap bmp = ThumbnailUtils.createVideoThumbnail("/sdcard/sample.3gp",MediaStore.Video.Thumbnails.MINI_KIND );
        
        ImageView image = new ImageView(this);
        image.setImageBitmap(bmp);
        tablelayout.addView(image, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                
    }
}