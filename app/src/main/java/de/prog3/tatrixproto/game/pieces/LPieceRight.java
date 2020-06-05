package de.prog3.tatrixproto.game.pieces;

import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.Piece3x3;
import de.prog3.tatrixproto.game.Class.Block;


public class LPieceRight extends Piece3x3 {

    public LPieceRight(Block[][] grid, int x) {
        super(grid,x);
        this.color = Color.parseColor("#ff0000");

        this.blocks[0][1] = true;
        this.blocks[1][1] = true;
        this.blocks[2][1] = true;
        this.blocks[2][0] = true;
    }
}
