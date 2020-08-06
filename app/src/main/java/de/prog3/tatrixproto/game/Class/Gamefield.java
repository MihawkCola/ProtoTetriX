//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import de.prog3.tatrixproto.R;
import de.prog3.tatrixproto.game.Abstract.AbstractPiece;
import de.prog3.tatrixproto.game.pieces.LPieceLeft;
import de.prog3.tatrixproto.game.pieces.LPieceRight;
import de.prog3.tatrixproto.game.pieces.LongPiece;
import de.prog3.tatrixproto.game.pieces.OPiece;
import de.prog3.tatrixproto.game.pieces.TPiece;
import de.prog3.tatrixproto.game.pieces.ZPieceLeft;
import de.prog3.tatrixproto.game.pieces.ZPieceRight;


public class Gamefield extends View {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    public int score;
    private int bonusPunkte;
    private ArrayList<AbstractPiece> list;
    private AbstractPiece nextPiece;

    NextGamefield nextField;
    // Tetris Grid 10x21
    Block grid[][] = new Block[WIDTH][HEIGHT];
    ActivePiece activePiece;

    Bitmap gamefieldBackground;

    public boolean isFinished = false;

    public Gamefield(Context context, NextGamefield nextField) {
        super(context);

        this.nextField = nextField;
        gamefieldBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.gamefield);
        score = 0;
        bonusPunkte = 50;

        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k] = new Block();
            }
        }

        list = new ArrayList<AbstractPiece>();
        list.add(new LPieceLeft(BitmapFactory.decodeResource(context.getResources(), R.drawable.syellow)));
        list.add(new LongPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sblue)));
        list.add(new LPieceRight(BitmapFactory.decodeResource(context.getResources(), R.drawable.scyan)));
        list.add(new OPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sgreen)));
        list.add(new TPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sorange)));
        list.add(new ZPieceLeft(BitmapFactory.decodeResource(context.getResources(), R.drawable.spurple)));
        list.add(new ZPieceRight(BitmapFactory.decodeResource(context.getResources(), R.drawable.sred)));

        activePiece = new ActivePiece(grid);

        createRandomNextPiece();
        activePiece.addPiece(nextPiece.getPiece());
        createRandomNextPiece();
        activePiece.addToGrid();
    }

    public void createRandomNextPiece() {
        int k = ThreadLocalRandom.current().nextInt(0, list.size());
        nextPiece = list.get(k);
        nextField.addPiece(nextPiece.getPiece());
    }


    public void moveLeft() {
        activePiece.movePieceLeft();
    }

    public void moveRight() {
        activePiece.movePieceRight();
    }

    public void rotate() {
        activePiece.rotatePiece();
    }

    public boolean nextFrame() {
        if (isFinished) return false;

        boolean hasMovedDown = activePiece.movePieceDown();
        if (!hasMovedDown) {
            int scoreCount = checkLine();
            if (1 < scoreCount) {
                scoreCount--;
                score = score + scoreCount * bonusPunkte;
            }
            activePiece.resetP();
            activePiece.addPiece(nextPiece.getPiece());
            createRandomNextPiece();
            boolean addedSuccessfully = activePiece.addToGrid();
            if (!addedSuccessfully) {
                //TODO: SPIEL ABBRUCH
                isFinished = true;
            }
        }
        return true;
    }

    private int checkLine() {
        int scoreCount = 0;
        for (int k = HEIGHT - 1; k >= 0; k--) {
            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][k].isActive()) {
                    count++;
                }
            }
            if (count == WIDTH) {
                score = score + 100;
                removeGridLine(k);
                moveGridDown(k);
                scoreCount++;
                k++;
            }
        }
        return scoreCount;
    }

    private void moveGridDown(int y) {
        y--;
        for (int k = y; k >= 0; k--) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][k].isActive()) {
                    grid[i][k + 1].setPiece(grid[i][k].getPiece());
                    grid[i][k].setPiece(null);
                }
            }
        }
    }

    private void removeGridLine(int y) {
        for (int i = 0; i < grid.length; i++) {
            grid[i][y].setPiece(null);
        }
    }

    public String getScore() {
        return String.format("%06d", score);
    }

    public int getScoreInt() {
        return score;
    }

    public void reset() {
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k].clear();
            }
        }
        this.score = 0;
        this.isFinished = false;
        nextField.clear();
        nextPiece = null;
        activePiece.clear();
        activePiece.resetP();
        createRandomNextPiece();
        activePiece.addPiece(nextPiece.getPiece());
        createRandomNextPiece();
        activePiece.addToGrid();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int borderoffset = 4;
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

        // draw gamefield background
        if (gamefieldBackground.getWidth() != blockSize * WIDTH + borderoffset * 2) {
            gamefieldBackground = Bitmap.createScaledBitmap(
                    gamefieldBackground,
                    blockSize * WIDTH + borderoffset * 2,
                    blockSize * HEIGHT + borderoffset * 2,
                    false
            );
        }
        canvas.drawBitmap(gamefieldBackground, x - borderoffset, y - borderoffset, null);

        // draw blocks
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k].draw(canvas, x + (i * blockSize), y + (k * blockSize), blockSize, null);
            }
        }
    }
}
