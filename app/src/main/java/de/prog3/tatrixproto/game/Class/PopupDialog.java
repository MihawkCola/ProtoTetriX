package de.prog3.tatrixproto.game.Class;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import de.prog3.tatrixproto.MainActivity;
import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.GameActivity;
import de.prog3.tatrixproto.game.db.DatabaseHelper;


public class PopupDialog extends Dialog {

    private Gamefield gamefield;
    private GameActivity gameactivity;
    private DatabaseHelper mydb;
    private Button submitButton, shareButton, restartButton, backButton;
    private TextView nicknameInput, firstplacename, firstplacescore,secondplacename, secondplacescore,thirdplacename,thirdplacescore, yourscore;
    private String score;
    private TextView finalscore;

    public PopupDialog(Context context, Gamefield gamefield, DatabaseHelper mydb, GameActivity gameActivity) {
        super(context);
        this.gameactivity = gameActivity;
        this.gamefield = gamefield;
        this.mydb = mydb;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        score = String.valueOf(gamefield.getScoreInt());

        //Buttons
        submitButton = findViewById(R.id.popup_submit);
        shareButton = findViewById(R.id.popup_share);
        restartButton = findViewById(R.id.popup_restart);
        backButton = findViewById(R.id.popup_back);
        nicknameInput = findViewById(R.id.nicknameInput);
        firstplacename = findViewById(R.id.firstplacename);
        firstplacescore = findViewById(R.id.firstplacescore);
        secondplacename = findViewById(R.id.secondplacename);
        secondplacescore = findViewById(R.id.secondplacescore);
        thirdplacename = findViewById(R.id.thirdplacename);
        thirdplacescore = findViewById(R.id.thirdplacescore);
        yourscore = findViewById(R.id.yourScore);

        if(gamefield.getScoreInt()>mydb.getHighScore()){
            yourscore.setText("NEW HIGHSCORE:");
        }else{
            yourscore.setText("YOUR SCORE:");
        }

        finalscore = findViewById(R.id.finalScore);
        finalscore.setText(score);

        getTop3();


        //SHARE BUTTON
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "My Score on Tetrix is: " + score);
                gameactivity.startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });

        //BACK BUTTON
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent(getContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gameactivity.startActivity(i);

            }
        });

        //SUBMIT BUTTON
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submitButton.setEnabled(false);
                nicknameInput.setEnabled(false);
                insetToDb();
                getTop3();
                nicknameInput.setText(null);
            }
        });


        //RESTART BUTTON
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamefield.reset();
                gameactivity.restart();
                submitButton.setEnabled(true);
                nicknameInput.setText(null);
                nicknameInput.setEnabled(true);
                nicknameInput.clearFocus();
                dismiss();
            }
        });
    }



    private void insetToDb(){
        boolean isInserted =  mydb.insertData(nicknameInput.getText().toString(),score);
        if(isInserted){
            Toast.makeText(getContext(), "Score saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Couldn't save score", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTop3(){
        firstplacescore.setText(mydb.getScore(0));
        firstplacename.setText(mydb.getName(0));
        secondplacescore.setText(mydb.getScore(1));
        secondplacename.setText(mydb.getName(1));
        thirdplacescore.setText(mydb.getScore(2));
        thirdplacename.setText(mydb.getName(2));
    }

    //Hide Keyboard on touch outside its scope.
    //Source: https://stackoverflow.com/a/54308582/1375582 by sumit sonawane
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) gameactivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
