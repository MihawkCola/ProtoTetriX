package de.prog3.tatrixproto.game;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Class.Gamefield;
import de.prog3.tatrixproto.game.Class.SettingsHandler;

public class PausedDialog extends Dialog {

    private PausedDialog himself;
    private GameActivity gameActivity;
    private Gamefield gamefield;

    private Button restartButton, resumeButton;
    private Switch audioSwitch;





    PausedDialog(Context context, Gamefield gamefield, GameActivity gameActivity){
        super(context);
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

        if(SettingsHandler.isSoundON()){
            audioSwitch.setChecked(true);
        }else{
            audioSwitch.setChecked(false);
        }

        //Resume Button
        resumeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(audioSwitch.isChecked()){
                    SettingsHandler.setSoundON();
                }else{
                    SettingsHandler.setSoundOFF();
                }

                himself.dismiss();
                gameActivity.resume();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamefield.reset();
                gameActivity.resume();
                dismiss();
            }
        });




    }

    }

