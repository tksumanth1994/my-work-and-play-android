package com.RecordLocalSocket;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class LockActivity extends Activity {
    /** Called when the activity is first created. */
    Camera _camera;
    MediaRecorder _mediarecorder;
    SurfaceHolder _holder;
    LocalSocketLoop _sl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraPreview(this));
    }
    
    byte[] code = { 0x00, 0x00,0x00,0x01};
    
    public void writeCode(DataOutputStream out) {
    	try {
			out.write(code);
			out.write(_mp.getSPS());
			out.write(code);
			out.write(_mp.getPPS());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //writestate
    //= 0 initialized
    //= 1 get mdat
    //= 2 get size 1byte
    //= 3 get size 2byte
    //= 4 get size 3byte
    //= 5 get size 4byte
    //= 6 get data but no completed.
    //= 1 get data and send data.
    int writeStateToGetSizeCount(int state) {
    	return (state - 1);
    }
    int SizeCountToWriteState(int size) {
    	return size + 1;
    }
    int _writestate = 0;
    byte[] _mdatbuf = { 0x00, 0x00,0x00,0x00};
    byte[] _datasize = { 0x00, 0x00, 0x00,0x00};
	int _sendsize = 0;
    
    public void writeData(DataOutputStream out, byte[] buffer, int size ) {
    	int offset = 0;
    	while(true) {
    		if (_writestate == 0) {
        		for (;offset != size;) {
        			_mdatbuf[3] = buffer[offset];
        			offset++;
        			if ((_mdatbuf[0] == 'm') && (_mdatbuf[1] == 'd') && (_mdatbuf[2] == 'a') && (_mdatbuf[3] == 't')) {
        				_writestate = 1;
        				break;
        			} else {
        				_mdatbuf[0] = _mdatbuf[1];
        				_mdatbuf[1] = _mdatbuf[2];
        				_mdatbuf[2] = _mdatbuf[3];
        			}
        			
        		}
        		if (_writestate != 1) {
        			return;
        		}
    		} 
    		if ((_writestate == 1)||(_writestate == 2) ||(_writestate == 3)||(_writestate == 4)) {
    			int getsize = writeStateToGetSizeCount(_writestate);
       			for (int i = getsize;i<4;i++) {
       				_datasize[i] = buffer[offset];
       				getsize = i + 1;
       				_writestate = SizeCountToWriteState(getsize);
       				offset++;
       				if (offset == size) {
       					//buffer is none, wait buffer and get next buffer.
       					return;
       				} else {
       				
       				}
       			}
       			if (_writestate == 5) {
       				_sendsize = ((_datasize[0] << 24)&0xff000000)|((_datasize[1]<<16)&0xff0000)|((_datasize[2]<<8)&0xff00)|(_datasize[3]&0xff);
       				Log.d("send", "0=" + _datasize[0]);
       				Log.d("send", "1=" + _datasize[1]);
       				Log.d("send", "2=" + _datasize[2]);
       				Log.d("send", "3=" + _datasize[3]);
       				Log.d("send", "_writestate5 Sendsize=" + _sendsize);
       				try {
						out.write(code);
					} catch (IOException e) {
						e.printStackTrace();
					}

       			}
    		}
    		if ((_writestate == 5)|| (_writestate == 6)) {
   				try {
   					if ( (size - offset) - _sendsize >= 0) {
   						Log.d("send", "offset = " + offset );
   						Log.d("send", "size = " + size );
   						Log.d("send", "sendsize = " + _sendsize);
   						
						out.write(buffer, offset, _sendsize);
						offset += _sendsize;
						_writestate = 1;
   					} else if ((size - offset) - _sendsize < 0){
   						out.write(buffer, offset, size - offset);
   						_sendsize -= size - offset;
   						Log.d("send", "xsendsize = " + _sendsize);
   						_writestate = 6;
   						return;
   					}
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    }
    public void sendData() {
		  new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	int j = 0;
			        String hostname = "192.168.1.5";
			        int port = 5000;
					Socket s = null;
					try {
						s = new Socket(InetAddress.getByName(hostname), port);
					} catch (UnknownHostException e3) {
						e3.printStackTrace();
					} catch (IOException e3) {
						e3.printStackTrace();
					}
			    	DataOutputStream out = null;
					try {
						out = new DataOutputStream(s.getOutputStream());
						j++;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					writeCode(out);
					
			    	FileDescriptor fd = _sl.getReceiverFileDescriptor();
			    	FileInputStream fr = new FileInputStream(fd);
			    	
			    	
					byte[] byteBuffer = new byte[10*1024];
					_writestate = 0;
			    	while(isRecording) {
			    		int size = 0;
			    		try {
			    			if (fr.available() != 0) {
			    				size = fr.read(byteBuffer);
								if (size == -1){
									break;
								} else {
									
									writeData(out, byteBuffer, size);
								}
			    				
			    			} else {
			    				// no data
			    			}
			    		} catch (IOException e1) {
					    	Log.d("camera", "close");
			    		}
			    		
			    	}
			    	try {
			    		out.close();
			    		s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	_sl.ReleaseLoop();
			    	Log.d("camera", "close");

			    }
			  }).start();    	
    }
    public void startRecord(FileDescriptor fd, String path) {
		
		_camera.unlock();
		
    	_mediarecorder = new MediaRecorder();
        
		_mediarecorder.setCamera(_camera);

        _mediarecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        _mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        _mediarecorder.setVideoSize(640, 480);
        _mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);


        if (fd != null ) {
        	_mediarecorder.setOutputFile(fd);
        } else {
        	_mediarecorder.setOutputFile(path);
        }
        _mediarecorder.setPreviewDisplay(_holder.getSurface());

		try {
			_mediarecorder.prepare();
			_mediarecorder.start();
		} catch (Exception e) {
			Log.d("camera", "err");
		}
    }
    void stopRecord() {
		_mediarecorder.stop();
		_mediarecorder.reset();
		_mediarecorder.release();
		_camera.lock();
    }
    public synchronized void sleep(long msec)
    {	
    	try
    	{
    		wait(msec);
    	}catch(InterruptedException e){}
    }    
    int counter = 0;
    boolean isRecording = false;
    MP4Config _mp = null;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		if (isRecording == false) {
    			isRecording = true;
				Log.d("camera", "start record");
				
				startRecord(null, Environment.getExternalStorageDirectory() + "/sample.mp4");
				sleep(200);
				stopRecord();
		        try {
		        	_mp = new MP4Config(Environment.getExternalStorageDirectory() + "/sample.mp4");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        _sl = new LocalSocketLoop("sample" + counter);
		        counter++;
		        _sl.InitLoop();
		        
		        startRecord(_sl.getSenderFileDescriptor(), null);
		        sendData();
    			
    			
    		} else {
				Log.d("camera", "stop record");
    			stopRecord();
    			isRecording = false;
    		}
     	}
    	
		return super.onTouchEvent(event); 
	}    
    
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
     
        CameraPreview(Context context) {
            super(context);
            _holder = getHolder();
            _holder.addCallback(this);
            _holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
     
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            _camera.stopPreview();
			try {
				_camera.setPreviewDisplay(holder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_camera.startPreview();
        }
        
        public void surfaceDestroyed(SurfaceHolder holder) {
            _camera.stopPreview();
            _camera.release();
        }

		public void surfaceCreated(SurfaceHolder holder) {
			_camera = Camera.open();
			
			try {
				_camera.setPreviewDisplay(holder);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_camera.startPreview();

			
		}
    }    
    
}