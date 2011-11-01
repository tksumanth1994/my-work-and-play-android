package com.CamcorderProfile;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.util.Log;

public class CamcorderProfileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        
        Log.d("test","quality= " +  profile.quality);
        Log.d("test","audioBitRate= " +  profile.audioBitRate);
        Log.d("test","audioChannels= " +  profile.audioChannels);
        Log.d("test","audioCodec= " +  profile.audioCodec);
        Log.d("test","audioSampleRate= " +  profile.audioSampleRate);
        Log.d("test","duration= " +  profile.duration);
        Log.d("test","fileformat= " +  profile.fileFormat);
        Log.d("test","videoBitRate= " +  profile.videoBitRate);
        Log.d("test","videocodec= " +  profile.videoCodec);
        Log.d("test","videoFrameHeight= " +  profile.videoFrameHeight);
        Log.d("test","videoFrameRate= " +  profile.videoFrameRate);
        Log.d("test","videoFrameWidth= " +  profile.videoFrameWidth);
        
        profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
        
        Log.d("test","quality= " +  profile.quality);
        Log.d("test","audioBitRate= " +  profile.audioBitRate);
        Log.d("test","audioChannels= " +  profile.audioChannels);
        Log.d("test","audioCodec= " +  profile.audioCodec);
        Log.d("test","audioSampleRate= " +  profile.audioSampleRate);
        Log.d("test","duration= " +  profile.duration);
        Log.d("test","fileformat= " +  profile.fileFormat);
        Log.d("test","videoBitRate= " +  profile.videoBitRate);
        Log.d("test","videocodec= " +  profile.videoCodec);
        Log.d("test","videoFrameHeight= " +  profile.videoFrameHeight);
        Log.d("test","videoFrameRate= " +  profile.videoFrameRate);
        Log.d("test","videoFrameWidth= " +  profile.videoFrameWidth);
        
    }
}