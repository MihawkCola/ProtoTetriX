package de.prog3.tatrixproto.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;

import de.prog3.tatrixproto.game.Class.Gamefield;


public class GameView extends View {
    private Gamefield gamefield;


    public GameView(Context context) {
        super(context);


        gamefield = new Gamefield(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gamefield.draw(canvas);

       // playerBlock.onDraw(canvas);

    }

    /**
     * Calculates the next frame and renders it
     */
    public boolean nextFrame() {
        //test
        // calculate next frame
        return gamefield.nextFrame();

        // invalidate the view to draw it again
        //this.invalidate();
    }
    public int getScore(){return gamefield.score;}
    public String onTextScore(){return gamefield.getScore();}
    public void onButtonLeftClicked(){
        if (!gamefield.isFinished){gamefield.moveLeft();}
    }
    public void onButtonRightClicked(){
        if (!gamefield.isFinished){gamefield.moveRight();}
    }
    public void onButtonRotateClicked(){
        if (!gamefield.isFinished){gamefield.rotate();}
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}


