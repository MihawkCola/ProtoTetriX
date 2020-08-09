package de.prog3.tatrixproto.game;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;

public class PausedDialog extends Dialog {

    private PausedDialog himself;
    private GameActivity gameActivity;
    private Gamefield gamefield;

    private Button restartButton, resumeButton;
    private Switch audioSwitch;

    private MediaPlayer mp;


    PausedDialog(Context context, MediaPlayer mp, Gamefield gamefield, GameActivity gameActivity){
        super(context);
        this.mp = mp;
        this.gameActivity = gameActivity;
        this.gamefield = gamefield;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        himself =this;


        himself.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        restartButton = findViewById(R.id.popup_restart);
        resumeButton = findViewById(R.id.popup_resume);
        audioSwitch = findViewById(R.id.switch_sound);

        //Resume Button
        resumeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                gameActivity.restart();
                himself.dismiss();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamefield.reset();
                gameActivity.restart();
                dismiss();
            }
        });


    }

    }

