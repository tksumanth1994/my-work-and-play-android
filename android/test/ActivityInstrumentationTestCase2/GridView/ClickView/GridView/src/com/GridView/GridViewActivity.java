package com.GridView;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	private GridView mGridView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGridView = (GridView)findViewById(R.id.gridView1);
        mGridView.setAdapter(new ImageAdapter());
        
    }
    
    public class ImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
	    	Cursor c = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
			Log.d("GridViewActivity", "getCount ="+ c.getCount());
			return c.getCount();
		}

		@Override
		public Object getItem(int arg0) {
			Log.d("GridViewActivity", "getItem");
			return null;
		}

		@Override
		public long getItemId(int position) {
			Log.d("GridViewActivity", "getItemId");
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	ImageView imageView;
	    	
	    	if (convertView == null) {
	    		imageView = new ImageView(mGridView.getContext());
	    		imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
	    	} else {
	    		imageView = (ImageView)convertView;
	    	}
	    	
	    	Cursor c = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
	    	c.moveToPosition(position);
	    	int dataIndex = c.getColumnIndex(MediaStore.Video.Media.DATA);
	    	String path = c.getString(dataIndex);
	    	Bitmap bmp = ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND );
	    	imageView.setImageBitmap(bmp);
	    	
	    	class OnClickListenerDetectPosition implements OnClickListener {

	    		private int mPosition = 0;
	    		public OnClickListenerDetectPosition(int position ) {
	    			mPosition = position;
	    		}
				@Override
				public void onClick(View v) {
					Log.d("GridViewActivity", "onClick = " + mPosition);
				}
	    		
	    	}
	    	
			OnClickListener onclikclistener = new OnClickListenerDetectPosition(position);
    		imageView.setOnClickListener(onclikclistener);
	    	
			Log.d("GridViewActivity", "getView");
	    	return (View)imageView;
	    	
	    	
		}
    	
    }
    
}