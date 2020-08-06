package de.prog3.tatrixproto.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;
import de.prog3.tatrixproto.game.Class.NextGamefield;
import de.prog3.tatrixproto.game.Class.PopupDialog;
import de.prog3.tatrixproto.game.db.DatabaseHelper;


public class GameActivity extends AppCompatActivity {
    public int speed = 1;
    public int boostetSpeed = 20;
    public int normalSpeed = speed;

    private Gamefield gamefield;
    private Handler handler = new Handler();
    private Boolean isPlaying = true;
    private DatabaseHelper mydb;
    NextGamefield nextField;
    PopupDialog popupDialog;
    MediaPlayer mediaPlayer;
    boolean stop;

    private ImageButton buttonL, buttonR, buttonD, buttonRot, soundButton;
    private TextView score;
    private TextView highscore;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

        gamefield = new Gamefield(this, nextField);
        mydb = new DatabaseHelper(this);

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
                                score.setText(gamefield.getScore());
                            } else {
                                stop=true;
                                endGame();
                            }
                        }
                    });
                    gamefield.postDelayed(this, 1000 / speed);
                }
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
                    gamefield.post(nextFrameRunnable);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gamefield.removeCallbacks(nextFrameRunnable);
                    speed = normalSpeed;
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


        mediaPlayer = MediaPlayer.create(this, R.raw.tetrix_soundtrack);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        turnSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop = true;
        handleSound();
    }

    @Override
    protected void onResume() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stop = false;
        super.onResume();
        if (isPlaying) {
            mediaPlayer.start();
            soundButton.setSelected(false);
            mediaPlayer.setLooping(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    //End Game popup
    //TODO MediaPlayer settings file.
    private void endGame() {
        handleSound();
        popupDialog.showPopuo();

    }

    public void restart(){
        onResume();
    }


    //Sound Handler Function
    private void turnSound() {
        soundButton = findViewById(R.id.Button_Sound);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    soundButton.setSelected(true);
                } else {
                    mediaPlayer.start();
                    soundButton.setSelected(false);
                }
            }
        });
    }

    private void handleSound(){
        if (mediaPlayer.isPlaying()) {
            isPlaying = true;
        } else {
            isPlaying = false;
        }
        mediaPlayer.pause();
    }

    //Handle Vibration with SDK < 26 and SDK >= 26

    private void vibrate(){
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(5);
        }
    }


}


