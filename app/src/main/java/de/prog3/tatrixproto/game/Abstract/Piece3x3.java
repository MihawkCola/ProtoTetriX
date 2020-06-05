package de.prog3.tatrixproto.game.Abstract;

import android.graphics.Color;

import de.prog3.tatrixproto.game.Class.Block;
import de.prog3.tatrixproto.game.interfaces.IPiece;


public abstract class Piece3x3 implements IPiece {
    private int x;
    private int y;

    protected int color;

    /** The blocks inside te 3x3 Area that are blocked by this piece */
    protected boolean blocks[][];

    /** reference t the parent grid */
    private Block[][] grid;

    public  Piece3x3(Block[][] grid,int x) {
        this.blocks = new boolean[3][3];
        this.grid = grid;
        this.x = x;
        y = 0;

        this.color = Color.parseColor("#ff0000");

    }


    public boolean canMoveLeft() {
        for (int i = 0; i <3;i++) {
            for (int k = 0; k < 3; k++) {
                if(blocks[i][k]){
                    if(i+x-1<0){
                        return false;
                    }

                    boolean isSelfLeft = false;
                    if (i-1 >= 0) {
                        isSelfLeft = this.blocks[i-1][k];
                    }
                    Block blockLeft = grid[x+i-1][y+k];
                    if(!isSelfLeft && blockLeft.isActive()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean canMoveRight() {
        for (int i = 0; i <3;i++) {
            for (int k = 0; k < 3; k++) {
                if(blocks[i][k]){
                    if(i+x+1 >= grid.length){
                        return false;
                    }

                    boolean isSelfLeft = false;
                    if (i+1 <3) {
                        isSelfLeft = this.blocks[i+1][k];
                    }
                    Block blockLeft = grid[x+i+1][y+k];
                    if(!isSelfLeft && blockLeft.isActive()){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean canMoveDown() {
        for (int i = 0; i <3;i++){
            for (int k = 0; k <3;k++){
                if(blocks[i][k]) {
                    // Unterkante des Spielfelds erreicht?
                    if (y+k+1 >= grid[0].length) {
                        return false;
                    }

                    boolean isSelfBelow = false;
                    if (k+1 < 3) {
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
        if (!canMoveLeft()){
            return;
        }
        removeFromGrid();
        x--;
        addToGrid();
    }
    public void movePieceRight() {
        if (!canMoveRight()){
            return;
        }
        removeFromGrid();
        x++;
        addToGrid();
    }

    /**
     * Add this piece to the grid
     */
    public boolean addToGrid() {
        for (int i = 0; i <3;i++) {
            for (int k = 0; k < 3; k++) {
                if(blocks[i][k]) {
                    if (x+i >= 0 && x+i < grid.length
                            && y+k >=0 && y+k < grid[0].length) {
                        Block blockBelow = grid[x + i][y + k];
                        if (blockBelow.isActive()) {
                            return false;
                        }
                    }
                }
            }
        }

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
        for (int i = 0; i <3;i++) {
            for (int k = 0; k < 3; k++) {
                if (blocks[i][k]) {
                    grid[x + i][y + k].setPiece(piece);
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
