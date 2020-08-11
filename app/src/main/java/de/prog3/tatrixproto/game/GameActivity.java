//Projektarbeit Prog3: Tetris
//Autor: Nelson Morais (879551) & Marcel Sauer (886022)


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

import java.util.Objects;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;
import de.prog3.tatrixproto.game.Class.MediaPlayerHandler;
import de.prog3.tatrixproto.game.Class.NextGamefield;
import de.prog3.tatrixproto.game.Class.SettingsHandler;
import de.prog3.tatrixproto.game.db.DatabaseHandler;


public class GameActivity extends AppCompatActivity {
    public double speed = 1;
    public double normalSpeed = speed;
    public double speedFactor;
    public int boostedSpeed = 20;
    public int levelPoint;
    private int levelUP;
    private long lastTouch = -1;

    private int musicUri = R.raw.tetrix_soundtrack;
    private int effectUri = R.raw.tetrix_effect;

    private Gamefield gamefield;
    private Handler handler = new Handler();
    private PausedDialog pausedDialog;
    private DatabaseHandler mydb;
    private NextGamefield nextField;
    private GameoverDialog gameoverDialog;


    private MediaPlayerHandler musicMp;
    private MediaPlayerHandler effectMp;

    private boolean stop;

    private ImageButton buttonL, buttonR, buttonD, buttonRot, pauseButton;
    private TextView score;
    private TextView highscore;


    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.levelUP = 0;
        speedFactor = 1;
        levelPoint = 1000;



        nextField = new NextGamefield(this);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.NextField);
        layout2.addView(nextField);

        // Hide the status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Buttons, Textviews & MediaPlayers
        buttonL = findViewById(R.id.Button_Left);
        buttonR = findViewById(R.id.Button_Right);
        buttonD = findViewById(R.id.Button_Down);
        buttonRot = findViewById(R.id.Button_Rotation);
        pauseButton = findViewById(R.id.Button_Pause);
        highscore = findViewById(R.id.HighScore);
        score = findViewById(R.id.Score);
        musicMp = new MediaPlayerHandler(this, musicUri, "music");
        effectMp = new MediaPlayerHandler(this, effectUri, "effect");


        //Classes
        gamefield = new Gamefield(this, nextField);
        mydb = new DatabaseHandler(this);
        pausedDialog = new PausedDialog(this, gamefield, this);
        gameoverDialog = new GameoverDialog(this, gamefield, mydb, this);

        highscore.setText(String.format("%06d", mydb.getHighScore()));

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.game);
        layout1.addView(gamefield);

        //Logik Thread
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
                                if (gamefield.getLineCleared()) {
                                    effectMp.play();
                                    vibrate(150);
                                    gamefield.resetLineCleared();
                                }
                                Log.d("Game", "Speed: " + speed);
                            } else {
                                stop = true;
                                levelUP = 0;
                                speed = 1;
                                normalSpeed = 1;
                                endGame();
                            }
                        }
                    });
                }
                gamefield.postDelayed(this, 1000 / (long) speed);
            }
        };

        //Refresh Rate Thread
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

        //Button Down
        buttonD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                vibrate(10);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    long currentTouch = System.currentTimeMillis();
                    if (currentTouch - lastTouch <150) {
                    //TODO: INSTANT DOWN !

                    } else {
                        gamefield.removeCallbacks(nextFrameRunnable);
                        speed = boostedSpeed;
                        levelCheck();
                        gamefield.post(nextFrameRunnable);
                        lastTouch = currentTouch;
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gamefield.removeCallbacks(nextFrameRunnable);
                    speed = normalSpeed;
                    levelCheck();
                    gamefield.post(nextFrameRunnable);

                }
                return false;
            }
        });

        //Button Rotate
        buttonRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(10);
                if (!gamefield.isFinished) {
                    gamefield.rotate();
                }
            }
        });

        //Button Right
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(10);
                if (!gamefield.isFinished) {
                    gamefield.moveRight();
                }
            }
        });

        //Button Left
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(10);
                if (!gamefield.isFinished) {
                    gamefield.moveLeft();
                }
            }
        });

        //Pause Button
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                pausedDialog.show();
            }
        });

        gamefield.post(nextFrameRunnable);
        gamefield.post(FPS);

        musicMp.play();

    }


    public void levelCheck() {
        int tmp = levelUP;
        levelUP = gamefield.getScoreInt() / levelPoint;
        if (tmp < levelUP) {
            speed = speedFactor * levelUP;
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
        musicMp.resumeMusic();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //End Game popup
    //TODO MediaPlayer settings file.
    private void endGame() {
        musicMp.pause();
        gameoverDialog.show();
    }

    @SuppressLint("DefaultLocale")
    public void restart() {
        highscore.setText(String.format("%06d", mydb.getHighScore()));
        musicMp.play();
        onResume();
    }

    public void resume() {

        onResume();
    }

    //Handle Vibration with SDK < 26 (Deprecated API) and SDK >= 26
    private void vibrate(int n) {

        if (SettingsHandler.isVibrationON()) {
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) Objects.requireNonNull(getSystemService(VIBRATOR_SERVICE))).vibrate(VibrationEffect.createOneShot(n, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //Deprecated API.
                ((Vibrator) Objects.requireNonNull(getSystemService(VIBRATOR_SERVICE))).vibrate(n);
            }
        }
    }


}


