package com.cabatuan.mymusic;


import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by cobalt on 10/4/15.
 */
public class MusicService extends Service {

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";

    private MediaPlayer player; // media player to play the music



   // This method is called each time a request comes in from the app via an intent.
   // It processes the request by playing or stopping the song as appropriate.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (action.equals(ACTION_PLAY)) {
            String filename = intent.getStringExtra("filename"); // get filename from intent

            // stop any previous playing song
            if (player != null && player.isPlaying()) {
                player.stop();
                player.release();
            }

            try {
                AssetFileDescriptor afd;
                afd = getAssets().openFd(filename);  // open file from assets
                player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.setLooping(true);
                player.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // END ACTION_PLAY

        } else if (action.equals(ACTION_STOP)) {
            // stop any currently playing song
            if (player != null && player.isPlaying()) {
                player.stop();
               // player.release(); // causes IllegalStateException
            }
        }
        return START_NOT_STICKY;
    }



    // Stop service when onDestroy is called
    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Music Service Stopped", Toast.LENGTH_SHORT).show();
        player.stop();
        player.release();
    }




    // This method specifies how our service will deal with binding.
    // Binding is not supported here indicated by returning null.
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
