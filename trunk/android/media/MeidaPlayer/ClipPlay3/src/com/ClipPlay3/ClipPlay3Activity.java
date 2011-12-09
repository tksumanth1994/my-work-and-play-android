package com.ClipPlay3;

import java.io.IOException;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ClipPlay3Activity extends Activity implements SurfaceHolder.Callback, OnCompletionListener, OnErrorListener , OnInfoListener, OnPreparedListener, OnVideoSizeChangedListener  {
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
		_mediaplayer.setOnCompletionListener(this);
		_mediaplayer.setOnErrorListener(this);
		_mediaplayer.setOnInfoListener(this);
		_mediaplayer.setOnPreparedListener(this);
		_mediaplayer.setOnVideoSizeChangedListener(this);
		_mediaplayer.setLooping(true);
		_mediaplayer.start();
		Log.d("player", "start");
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	
	boolean isPause = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d("player", "getAudioSessionId = " + _mediaplayer.getAudioSessionId());
			Log.d("player", "getCurrentPosition = " + _mediaplayer.getCurrentPosition());
			if (isPause == false) {
				isPause = true;
				_mediaplayer.pause();
			} else {
				isPause = false;
				_mediaplayer.start();
			}
			break;
		default:
			break;
		}
		return  super.onTouchEvent(event);
		
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.d("player", "onCompletion");
		
	}
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.d("player", "onError what = " + what + "extra = " + extra);
		return false;
	}
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		Log.d("player", "onInfo what = " + what + "extra = " + extra);
		return false;
	}
	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.d("player", "onVideoSizeChanged width = " + width + " height = " + height);
		
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.d("player", "onPrepared");
	}
}