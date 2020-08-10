//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben


package de.prog3.tatrixproto.game;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;
import de.prog3.tatrixproto.game.Class.MediaPlayerHandler;
import de.prog3.tatrixproto.game.Class.NextGamefield;
import de.prog3.tatrixproto.game.Class.SettingsHandler;
import de.prog3.tatrixproto.game.db.DatabaseHelper;


public class GameActivity extends AppCompatActivity {
    public double speed = 1;
    public double normalSpeed = speed;
    public double speedFactor;
    public int boostetSpeed = 20;
    public int levelPoint;
    private int levelUP;

    private int musicUri = R.raw.tetrix_soundtrack;
    private int effectUri = R.raw.tetrix_effect;

    private Gamefield gamefield;
    private Handler handler = new Handler();
    private PausedDialog pausedDialog;
    private DatabaseHelper mydb;
    private NextGamefield nextField;
    private PopupDialog popupDialog;



    private MediaPlayerHandler musicMp;
    private MediaPlayerHandler effectMp;

    private boolean stop;
    private Boolean isPlaying = true;

    private ImageButton buttonL, buttonR, buttonD, buttonRot, soundButton;
    private TextView score;
    private TextView highscore;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.levelUP = 0;
        speedFactor = 1;
        levelPoint = 500;
        score = findViewById(R.id.Score);


        nextField = new NextGamefield(this);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.NextField);
        layout2.addView(nextField);

        // Hide the status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonL = findViewById(R.id.Button_Left);
        buttonR = findViewById(R.id.Button_Right);
        buttonD = findViewById(R.id.Button_Down);
        buttonRot = findViewById(R.id.Button_Rotation);
        highscore = findViewById(R.id.HighScore);

        musicMp = new MediaPlayerHandler(this,musicUri);
        effectMp = new MediaPlayerHandler(this,effectUri);


        musicMp.play();



        gamefield = new Gamefield(this, nextField);
        mydb = new DatabaseHelper(this);
        pausedDialog = new PausedDialog(this,gamefield,this);
        popupDialog = new PopupDialog(this,gamefield,mydb,this);

        highscore.setText(String.format("%06d", mydb.getHighScore()));

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.game);
        layout1.addView(gamefield);


        final Runnable nextFrameRunnable = new Runnable() {
            @Override
            public void run() {
                if (!stop) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (gamefield.nextFrame()) {
                                levelCheck();
                                score.setText(gamefield.getScore());
                                Log.d("Game","Speed: "+speed);
                            } else {
                                stop=true;
                                levelUP = 0;
                                speed = 1;
                                normalSpeed=1;
//                                mediaPlayer.seekTo(0);
                                endGame();
                            }
                        }
                    });
                }
                gamefield.postDelayed(this, 1000 / (long)speed);
            }
        };

        final Runnable FPS = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!stop) {
                            gamefield.invalidate();
                            nextField.invalidate();
                        }
                    }
                });
                gamefield.postDelayed(this, 1000 / 60);
            }
        };
        buttonD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                vibrate();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gamefield.removeCallbacks(nextFrameRunnable);
                    speed = boostetSpeed;
                    levelCheck();
                    gamefield.post(nextFrameRunnable);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gamefield.removeCallbacks(nextFrameRunnable);
                    speed = normalSpeed;
                    levelCheck();
                    gamefield.post(nextFrameRunnable);
                }
                return false;
            }
        });
        buttonRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                if (!gamefield.isFinished) {
                    gamefield.rotate();
                }
            }
        });
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                if (!gamefield.isFinished) {
                    gamefield.moveRight();
                }
            }
        });

        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                if (!gamefield.isFinished) {
                    gamefield.moveLeft();
                }
            }
        });


        gamefield.post(nextFrameRunnable);
        gamefield.post(FPS);


        soundButton = findViewById(R.id.Button_Sound);

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SettingsHandler.setSoundON();
//                musicMp.resume();
                onPause();
                pausedDialog.show();
            }
        });



    }


    public void levelCheck(){
        int tmp = levelUP;
        levelUP= gamefield.getScoreInt()/levelPoint;
        if(tmp<levelUP){
            speed= speedFactor*levelUP;
            normalSpeed = speed;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop = true;
        musicMp.pause();
    }

    @Override
    protected void onResume() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stop = false;
        musicMp.resume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //End Game popup
    //TODO MediaPlayer settings file.
    private void endGame() {
//        handleSound();
        popupDialog.show();
    }

    public void resume(){
        highscore.setText(String.format("%06d", mydb.getHighScore()));
        onResume();
    }


    //Sound Handler Function
//    private void turnSound() {
//        soundButton = findViewById(R.id.Button_Sound);
//        soundButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    soundButton.setSelected(true);
//                } else {
//                    mediaPlayer.start();
//                    soundButton.setSelected(false);
//                }
//            }
//        });
//    }

//    private void handleSound(){
//        if (mediaPlayer.isPlaying()) {
//            isPlaying = true;
//        } else {
//            isPlaying = false;
//        }
//        mediaPlayer.pause();
//    }


    //Handle Vibration with SDK < 26 and SDK >= 26
    private void vibrate(){
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(5);
        }
    }


}


