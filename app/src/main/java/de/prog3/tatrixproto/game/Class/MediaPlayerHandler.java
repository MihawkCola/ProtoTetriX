package de.prog3.tatrixproto.game.Class;

import android.app.Activity;
import android.media.MediaPlayer;

import java.io.IOException;


public class MediaPlayerHandler {

    private MediaPlayer mp;
    private boolean active;


    public MediaPlayerHandler(Activity activity, int uri) {
        this.mp = MediaPlayer.create(activity, uri);


    }


    public void play() {
        if (SettingsHandler.isSoundON()) {
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer player) {
                player.start();
                }
            });
        }
    }

    public void resume() {
        if (SettingsHandler.isSoundON()) {
            this.mp.start();
            this.mp.setLooping(true);

        }
    }

    public void stop() {
        mp.stop();
    }

    public void pause() {

        mp.pause();
    }
}
//        mediaPlayer = MediaPlayer.create(this, R.raw.tetrix_soundtrack);
//                mediaPlayer.start();
//                mediaPlayer.setLooping(true);
