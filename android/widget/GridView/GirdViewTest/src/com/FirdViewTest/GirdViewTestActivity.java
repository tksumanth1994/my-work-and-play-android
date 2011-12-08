package com.FirdViewTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GirdViewTestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
	
    public class ImageAdapter extends BaseAdapter {
		private Context _context;
		public ImageAdapter(Context c) {
			_context = c;
			
		}
		
		public  int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public  long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;

			if (convertView == null) {
				imageView = new ImageView(_context);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		        imageView.setPadding(8, 8, 8, 8);	
		    } else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(mThumbIds[position]);
			return imageView;
		}
	    private Integer[] mThumbIds = {
	    		R.drawable.img_01,
	    		R.drawable.img_02,
	    		R.drawable.img_03,
	    		R.drawable.img_04,
	    		R.drawable.img_05,
	    		R.drawable.img_06,
	    		R.drawable.img_07,
	    		R.drawable.img_01,
	    		R.drawable.img_02,
	    		R.drawable.img_03,
	    		R.drawable.img_04,
	    		R.drawable.img_05,
	    		R.drawable.img_06,
	    		R.drawable.img_07,
	    		R.drawable.img_01,
	    };

	}
}
