package de.prog3.tatrixproto.game.Abstract;

import android.graphics.Color;

import de.prog3.tatrixproto.game.Class.Block;
import de.prog3.tatrixproto.game.interfaces.IPiece;


public abstract class Piece4x4 implements IPiece {
    protected int x;
    protected int y;

    protected int color;

    /** The blocks inside te 4x4 Area that are blocked by this piece */
    protected boolean blocks[][];

    /** reference t the parent grid */
    protected Block[][] grid;

    public Piece4x4(Block[][] grid) {
        this.blocks = new boolean[4][4];
        this.grid = grid;
        x = 0;
        y = -1;

        this.color = Color.parseColor("#ff0000");

    }


    public boolean canMoveLeft() {
        return false;
    }
    public boolean canMoveRight() {
        return false;
    }


    public boolean canMoveDown() {
        for (int i = 0; i <4;i++){
            for (int k = 0; k <4;k++){
                if (y+k < 0) {
                    continue;
                }
                if(blocks[i][k]) {
                    // Unterkante des Spielfelds erreicht?
                    if (y+k+1 >= grid[0].length) {
                        return false;
                    }

                    boolean isSelfBelow = false;
                    if (k+1 < 4) {
                        isSelfBelow = this.blocks[i][k+1];
                    }
                    Block blockBelow = grid[x+i][y+k+1];
                    if(!isSelfBelow && blockBelow.isActive()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void movePieceLeft() {
    }
    public void movePieceRight() {
    }

    /**
     * Add this piece to the grid
     */
    public boolean addToGrid() {
        updateGrid(this);
        return true;
    }
    /**
     * Remove this piece from the grid
     */
    private void removeFromGrid() {
        updateGrid(null);
    }
    private void updateGrid(IPiece piece){
        for (int i = 0; i <4;i++) {
            for (int k = 0; k < 4; k++) {
                if (blocks[i][k]) {
                    if (y+k >= 0) {
                        grid[x + i][y + k].setPiece(piece);
                    }
                }
            }
        }
    }


    public boolean movePieceDown() {
        if (canMoveDown()) {
            removeFromGrid();
            y++;
            addToGrid();
            return true;
        }
        return false;
    }


    public boolean canRotateLeft() {
        return false;
    }


    public boolean canRotateRight() {
        return false;
    }


    public void rotateLeft() {
    }


    public void rotateRight() {
    }

    public int getColor() {
        return this.color;
    }
}
