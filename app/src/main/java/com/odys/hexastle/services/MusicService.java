package com.odys.hexastle.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.odys.hexastle.R;

public class MusicService extends Service {
    private static MediaPlayer player;

    public static void mute() {
        player.setVolume(0f, 0f);
    }

    public static void amplify() {
        player.setVolume(1f, 1f);
    }

    public static void start() { player.start(); }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        player = MediaPlayer.create(this, R.raw.deguello);

        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);

        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
        player.release();
    }
}
