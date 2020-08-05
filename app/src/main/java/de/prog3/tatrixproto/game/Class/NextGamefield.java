package de.prog3.tatrixproto.game.Class;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Abstract.AbstractPiece;
import de.prog3.tatrixproto.game.pieces.EmptyPiece;

public class NextGamefield extends View {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;
    private AbstractPiece nextPiece;
    private ActivePiece activ;

    Block grid[][] = new Block[WIDTH][HEIGHT];
    public NextGamefield(Context context) {
        super(context);
        for (int i = 0; i < grid.length; i++){
            for (int k = 0; k < grid[i].length; k++){
                grid[i][k] = new Block();
            }
        }
        activ = new ActivePiece(grid);
        activ.addPiece(new EmptyPiece(BitmapFactory.decodeResource(context.getResources(),R.drawable.square_white)));
    }
    public void addPiece(AbstractPiece piece) {
        for (int i = 0; i < WIDTH;i++){
            for (int k = 0; k < HEIGHT;k++){
                grid[i][k].clear();
            }
        }
        this.nextPiece = piece;
        activ.addPiece(piece,0,0);
        activ.addToGrid();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int borderoffset = 0;
        int x = borderoffset;
        int y = borderoffset;
        int blockSize;

        int width = canvas.getWidth() - borderoffset * 2;
        int height = canvas.getHeight() - borderoffset * 2;
        if ((width / WIDTH) * HEIGHT > height) {
            blockSize = height / HEIGHT;
            // Spielfeld ist breiter als hoch
        } else {
            blockSize = width / WIDTH;
            // Spielfeld ist höher als breit
        }
        x = (width - blockSize * WIDTH) / 2; // centrieren

        // draw blocks
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k].draw(canvas, x + (i * blockSize), y + (k * blockSize), blockSize,null);
            }
        }
    }
}
