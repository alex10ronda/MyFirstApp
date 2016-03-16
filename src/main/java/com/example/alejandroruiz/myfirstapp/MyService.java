package com.example.alejandroruiz.myfirstapp;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service {
    public MyService() {
    }

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public void onCreate(){
        //Uri myUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        player = MediaPlayer.create(this, R.raw.sonido);
        //player.setLooping(false);

    }

    @Override
    public void onDestroy(){
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid){

        player.start();
    }
}
