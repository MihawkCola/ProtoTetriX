//Projektarbeit Prog3: Tetris
//Autor: Nelson Morais (879551) & Marcel Sauer (886022)
package de.prog3.tetrix.game.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import de.prog3.tetrix.R;
import de.prog3.tetrix.game.Abstract.AbstractPiece;
import de.prog3.tetrix.game.pieces.EmptyPiece;

public class NextGamefield extends View {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;
    private ActivePiece active;



    Block grid[][] = new Block[WIDTH][HEIGHT];

    public NextGamefield(Context context) {
        super(context);
        for (int i = 0; i < grid.length; i++){
            for (int k = 0; k < grid[i].length; k++){
                grid[i][k] = new Block();
            }
        }
        active = new ActivePiece(grid);
        //Bitmap prediktion = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_white);
        //active.addPiece(new EmptyPiece(BitmapFactory.decodeResource(context.getResources(),R.drawable.square_white),prediktion));
    }

    public void addPiece(AbstractPiece piece) {
        for (int i = 0; i < WIDTH;i++){
            for (int k = 0; k < HEIGHT;k++){
                grid[i][k].clear();
            }
        }
        active.addPiece(piece,0,0);
        active.addToGrid(false);
        //active.updateGrid(piece,0,0,false);
    }

    public void clear(){
        for (int i = 0; i < WIDTH;i++){
            for (int k = 0; k < HEIGHT;k++){
                grid[i][k].clear();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int x;
        int blockSize;

        int width = getWidth();
        int height = getHeight();
        if ((width / WIDTH) * HEIGHT > height) {
            blockSize = height / HEIGHT;
            // Spielfeld ist breiter als hoch
        } else {
            blockSize = width / WIDTH;
            // Spielfeld ist höher als breit
        }
        x = (width - blockSize * WIDTH) / 2; // zentrieren

        // draw blocks
        /*for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k].draw(canvas, x + (i * blockSize),(k * blockSize), blockSize);
            }
        }*/
    }
}
