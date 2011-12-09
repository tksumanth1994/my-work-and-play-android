package com.ClipPlay2;

import java.io.IOException;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ClipPlay2Activity extends Activity implements SurfaceHolder.Callback {
    /** Called when the activity is first created. */
	SurfaceView _preview  = null;
	SurfaceHolder _holder = null;
	MediaPlayer _mediaplayer = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _preview = (SurfaceView)findViewById(R.id.surfaceView1);
        _holder = _preview.getHolder();
        _holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        _holder.addCallback(this);
    }
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		String path = "/sdcard/sample.mp4";
		_mediaplayer = new MediaPlayer();
		try {
			_mediaplayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_mediaplayer.setDisplay(holder);
		try {
			_mediaplayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("player", "duration = " + _mediaplayer.getDuration());
		Log.d("player", "getVideoHeight = " + _mediaplayer.getVideoHeight());
		Log.d("player", "getVideoWidth = " + _mediaplayer.getVideoWidth());
		Log.d("player", "isLooping=" + _mediaplayer.isLooping());
		Log.d("player", "isPlaying=" + _mediaplayer.isPlaying());

		_mediaplayer.start();
		Log.d("player", "start");
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d("player", "getVideoHeight = " + _mediaplayer.getVideoHeight());
			Log.d("player", "getVideoWidth = " + _mediaplayer.getVideoWidth());
			Log.d("player", "isPlaying=" + _mediaplayer.isPlaying());
			
			break;
		default:
			break;
		}
		return  super.onTouchEvent(event);
		
	}
}