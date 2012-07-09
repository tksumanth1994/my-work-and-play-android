package com.RecordLocalSocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Finds SPS & PPS parameters in mp4 file
 */
public class MP4Config {

	private final RandomAccessFile fis;
	private final StsdBox stsdBox; 
	private final MP4Parser mp4Parser;
	
	/**
	 * Finds sps & pps parameters inside a .mp4
	 * @param path Path to the file to analyze
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public MP4Config (String path) throws IOException, FileNotFoundException {
		
		// We open the mp4 file
		File file = new File(path);
		fis = new RandomAccessFile(file, "r");

		// We parse it
		mp4Parser = new MP4Parser(fis);
		
		// We find the stsdBox
		stsdBox = mp4Parser.getStsdBox();
		
		// We're done !
		fis.close();
	}
	
	public String getProfileLevel() {
		return stsdBox.getProfileLevel();
	}
	
	public String getB64PPS() {
		return stsdBox.getB64PPS();
	}
	
	public String getB64SPS() {
		return stsdBox.getB64SPS();
	}
	public byte[] getSPS() {
		return stsdBox.getSPS();
	}
	public byte[] getPPS() {
		return stsdBox.getPPS();
	}
}