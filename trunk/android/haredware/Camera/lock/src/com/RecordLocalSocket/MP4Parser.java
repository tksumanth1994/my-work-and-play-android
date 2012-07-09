package com.RecordLocalSocket;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;


import android.util.Log;


public class MP4Parser {

	private static final String TAG = "MP4Parser";
	
	private HashMap<String, Long> boxes = new HashMap<String, Long>();
	private RandomAccessFile fis;
	private long pos = 0;
	private byte[] buffer = new byte[8];
	
	public MP4Parser(RandomAccessFile fis) throws IOException {
	
		this.fis = fis;
		
		long length = 0;
		try {
			length = fis.length();
		} catch (IOException e) {
			throw new IOException("Wrong size");
		}
		
		if (!parse("",length)) throw new IOException("MP4 Parsing error");
		
	}
	
	public long getBoxPos(String box) throws IOException {
		
		Long r = boxes.get(box);
		if (r==null) throw new IOException("box not found: "+box);
		return boxes.get(box);
	}
	
	public StsdBox getStsdBox() throws IOException {
		try {
			return new StsdBox(fis,getBoxPos("/moov/trak/mdia/minf/stbl/stsd"));
		} catch (IOException e) {
			throw new IOException("Error: stsd box could not be found");
		}
	}
	
	private boolean parse(String path, long len) {
		
		String name="";
		long sum = 0, newlen = 0;

		boxes.put(path, pos-8);
		
		try {

			while (sum<len) {
				
				fis.read(buffer,0,8); sum += 8; pos += 8;
				if (validBoxName()) {
					Log.d("mp4", "buf =" + buffer[0] + buffer[1] + buffer[2] + buffer[3]);
					newlen = ( buffer[3]&0xFF | (buffer[2]&0xFF)<<8 | (buffer[1]&0xFF)<<16 | (buffer[0]&0xFF)<<24 ) - 8;
					if (newlen<0) return false;
					name = new String(buffer,4,4);
					Log.d(TAG,"Atom -> name: "+name+" newlen: "+newlen);
					sum += newlen;
					if (!parse(path+'/'+name,newlen)) return false;
					
				}
				else {
					fis.skipBytes((int) (len-8)); pos += len-8;
					sum += len-8;
				}
				
				
			}
			
		} catch (IOException e) {
			return false;
		}
		return true;
		
	}

	private boolean validBoxName() {
		for (int i=0;i<4;i++) {
			if ((buffer[i+4]<97 || buffer[i+4]>122) && (buffer[i+4]<48 || buffer[i+4]>57) ) return false;
		}
		return true;
	}
	
	
	
}
