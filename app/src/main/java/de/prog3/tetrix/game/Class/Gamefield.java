//Projektarbeit Prog3: Tetris
//Autor: Nelson Morais (879551) & Marcel Sauer (886022)
package de.prog3.tetrix.game.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import de.prog3.tetrix.R;
import de.prog3.tetrix.game.Abstract.AbstractPiece;

import de.prog3.tetrix.game.pieces.LPieceLeft;
import de.prog3.tetrix.game.pieces.LPieceRight;
import de.prog3.tetrix.game.pieces.LongPiece;
import de.prog3.tetrix.game.pieces.OPiece;
import de.prog3.tetrix.game.pieces.OnePiece;
import de.prog3.tetrix.game.pieces.TPiece;
import de.prog3.tetrix.game.pieces.ZPieceLeft;
import de.prog3.tetrix.game.pieces.ZPieceRight;


public class Gamefield extends View {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 21;
    public int score;
    private int bonusPunkte;
    private int lineScore;
    private ArrayList<AbstractPiece> list;
    private ArrayList<AbstractPiece> listNextPiece;
    private AbstractPiece nextPiece;
    private AbstractPiece OnePiece;
    private AbstractPiece test;

    NextGamefield nextField;
    // Tetris Grid 10x21
    Block grid[][] = new Block[WIDTH][HEIGHT];
    ActivePiece activePiece;

    Bitmap gamefieldBackground;

    public boolean isFinished = false;
    private boolean lineCleared = false;


    public Gamefield(Context context, NextGamefield nextField) {
        super(context);

        this.nextField = nextField;
        gamefieldBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.gamefield);
        score = 0;
        lineScore= 0;
        bonusPunkte = 50;

        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k] = new Block();
            }
        }
        Bitmap prediktion = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_prediction);
        Bitmap animation = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_line);
        test = new OnePiece(animation,animation,animation);
        OnePiece = new OnePiece(animation,animation,animation);

        list = new ArrayList<AbstractPiece>();
        list.add(new LPieceLeft(BitmapFactory.decodeResource(context.getResources(), R.drawable.syellow),prediktion,prediktion));
        list.add(new LongPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sblue),prediktion,prediktion));
        list.add(new LPieceRight(BitmapFactory.decodeResource(context.getResources(), R.drawable.scyan),prediktion,prediktion));
        list.add(new OPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sgreen),prediktion,prediktion));
        list.add(new TPiece(BitmapFactory.decodeResource(context.getResources(), R.drawable.sorange),prediktion,prediktion));
        list.add(new ZPieceLeft(BitmapFactory.decodeResource(context.getResources(), R.drawable.sred),prediktion,prediktion));
        list.add(new ZPieceRight(BitmapFactory.decodeResource(context.getResources(), R.drawable.spurple),prediktion,prediktion));

        activePiece = new ActivePiece(grid);
        createRandomNextPiece();
        activePiece.addPiece(nextPiece.getPiece());
        createRandomNextPiece();
        activePiece.addToGrid();
    }

    public void createRandomNextPiece() {
        int k = ThreadLocalRandom.current().nextInt(0, list.size());
        nextPiece = list.get(k);
        nextField.addPiece(list.get(k));
    }


    public void moveLeft() {
        activePiece.movePieceLeft();
    }

    public void moveRight() {
        activePiece.movePieceRight();
    }
    public void moveInstantDown() { activePiece.moveInstantDown();

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
                isFinished = true;
            }
        }
        if(!activePiece.canNextFrameDown()){
            for (int k = HEIGHT - 1; k >= 0; k--) {
                if (numberInLine(k) == WIDTH) {
                    setLine(k);
                    lineCleared = true;
                }
            }
        }
        return true;
    }

    public int checkLine() {
        int scoreCount = 0;
        for (int k = HEIGHT - 1; k >= 0; k--) {
            if (numberInLine(k) == WIDTH) {
                lineScore++;
                score = score + 100;
                removeGridLine(k);
                moveGridDown(k);
                scoreCount++;
                k++;
            }
        }

        return scoreCount;
    }
    private int numberInLine(int y) {
        int count =0;
        for (Block[] blocks : grid) {
            if (blocks[y].isActive()) {
                count++;
            }
        }
        return count;
    }
    private void setLine(int y) {
        for (Block[] blocks : grid) {
            blocks[y].setPiece(OnePiece, false);
        }
    }

    private void moveGridDown(int y) {
        y--;
        for (int k = y; k >= 0; k--) {
            for (Block[] blocks : grid) {
                if (blocks[k].isActive()) {
                    blocks[k + 1].setPiece(blocks[k].getPiece(), false);
                    blocks[k].clear();
                }
            }
        }
    }

    private void removeGridLine(int y) {
        for (Block[] blocks : grid) {
            blocks[y].clear();
        }
    }

    public String getScore() {
        return String.format("%06d", score);
    }

    public int getScoreInt() {
        return score;
    }
    public int getlineScore() {
        return lineScore;
    }

    public boolean getLineCleared(){
        return lineCleared;
    }
    public void resetLineCleared(){
        lineCleared = false;
    }

    public void reset() {
        for (Block[] blocks : grid) {
            for (Block block : blocks) {
                block.clear();
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
        int blockSize;

        int width = getWidth() - borderoffset * 2;
        int height = getHeight() - borderoffset * 2;
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
        canvas.drawBitmap(gamefieldBackground, x - borderoffset, 0, null);

        // draw blocks
        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[i].length; k++) {
                grid[i][k].draw(canvas, x + (i * blockSize), borderoffset + (k * blockSize), blockSize);

            }
        }
    }
}
