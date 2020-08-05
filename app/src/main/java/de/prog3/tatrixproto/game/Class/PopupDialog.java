package de.prog3.tatrixproto.game.Class;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import de.prog3.tatrixproto.R;


public class PopupDialog extends Dialog {

    Gamefield gamefield;
    private TextView finalscore;

    public PopupDialog(Context context, Gamefield gamefield) {
        super(context);
        this.gamefield = gamefield;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        finalscore = findViewById(R.id.finalScore);
        finalscore.setText(String.valueOf(gamefield.getScoreInt()));

    }

    public void showPopup(){
        this.show();
    }
}
