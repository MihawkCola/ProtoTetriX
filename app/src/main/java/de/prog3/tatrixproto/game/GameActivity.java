package de.prog3.tatrixproto.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;
import de.prog3.tatrixproto.game.db.DatabaseHelper;


public class GameActivity extends AppCompatActivity {
    public int speed = 1;
    public int boostetSpeed = 20;
    public int normalSpeed = speed;
    private GameView gameview;
    private Handler handler = new Handler();
    private Boolean isPlaying = true;
    private DatabaseHelper mydb;

    private ImageButton buttonL, buttonR, buttonD, buttonRot, soundButton;
    private TextView score;
    private TextView highscore;

    MediaPlayer mediaPlayer;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.Score);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        buttonL = findViewById(R.id.Button_Left);
        buttonR = findViewById(R.id.Button_Right);
        buttonD = findViewById(R.id.Button_Down);
        buttonRot = findViewById(R.id.Button_Rotation);
        highscore = findViewById(R.id.HighScore);

        mydb = new DatabaseHelper(this);
        highscore.setText(String.format("%06d",mydb.getHighScore()));
        gameview = new GameView(this);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.game);
        layout1.addView(gameview);

        final Runnable nextFrameRunnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(gameview.nextFrame()){
                        score.setText(gameview.onTextScore());
                        }else {
                            mydb.insertData(gameview.onTextScore());
                            score.setText("ENDE"); //TODO END Screen DB Score
                        }

                    }
                });
                gameview.postDelayed(this, 1000 / speed);
            }
        };

        final Runnable FPS = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameview.invalidate();
                    }
                });
                gameview.postDelayed(this, 1000 / 60);
            }
        };
        buttonD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameview.removeCallbacks(nextFrameRunnable);
                    speed = boostetSpeed;
                    gameview.post(nextFrameRunnable);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gameview.removeCallbacks(nextFrameRunnable);
                    speed = normalSpeed;
                    gameview.post(nextFrameRunnable);
                }
                return false;
            }
        });
        buttonRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameview.onButtonRotateClicked();
            }
        });
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameview.onButtonRightClicked();
            }
        });
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameview.onButtonLeftClicked();
            }
        });


        gameview.post(nextFrameRunnable);
        gameview.post(FPS);


        mediaPlayer = MediaPlayer.create(this, R.raw.tetrix_soundtrack);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        turnSound();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            isPlaying = true;
        } else {
            isPlaying = false;
        }
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            mediaPlayer.start();
            soundButton.setSelected(false);
            mediaPlayer.setLooping(true);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onStop() {
        super.onStop();

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
}