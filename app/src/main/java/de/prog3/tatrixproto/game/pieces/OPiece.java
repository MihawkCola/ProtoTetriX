package de.prog3.tatrixproto.game.pieces;

import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.Piece4x4;
import de.prog3.tatrixproto.game.Class.Block;


public class OPiece extends Piece4x4 {

    public OPiece(Block[][] grid) {
        super(grid);
        this.color = Color.parseColor("#00ffff");

        this.blocks[1][1] = true;
        this.blocks[1][2] = true;
        this.blocks[2][1] = true;
        this.blocks[2][2] = true;

        this.x = -1;
    }



    @Override
    public boolean canRotate(boolean[][] pre) {
        return false;
    }

    @Override
    public void rotatePiece() {

    }
}
