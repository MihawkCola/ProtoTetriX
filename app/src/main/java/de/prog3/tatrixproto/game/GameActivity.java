package de.prog3.tatrixproto.game;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import de.prog3.tatrixproto.R;


public class GameActivity extends AppCompatActivity {
    public int speed = 6;
    private GameView gameview;
    private Handler handler = new Handler();

    private ImageButton buttonL, buttonR, buttonD,buttonRot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        buttonL = findViewById(R.id.Butten_Left);
        buttonR = findViewById(R.id.Butten_Right);
        buttonD = findViewById(R.id.Butten_Down);
        buttonRot = findViewById(R.id.Butten_Rotation);
        buttonD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    speed=20;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    speed=6;
                }
                return false;
            }
        });
        buttonRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameview.onButtonRightClicked();
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

        gameview = new GameView(this);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.game);
        layout1.addView(gameview);

        final Runnable nextFrameRunnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameview.nextFrame();
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

        gameview.post(nextFrameRunnable);
        gameview.post(FPS);


    }
}