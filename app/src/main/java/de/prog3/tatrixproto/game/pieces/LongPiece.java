package de.prog3.tatrixproto.game.pieces;

import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.Piece3x3;
import de.prog3.tatrixproto.game.Abstract.Piece4x4;
import de.prog3.tatrixproto.game.Class.Block;

public class LongPiece extends Piece4x4 {

    public LongPiece(Block[][] grid) {
        super(grid);
        this.color = Color.parseColor("#aaaa40");

        this.blocks[0][1] = true;
        this.blocks[1][1] = true;
        this.blocks[2][1] = true;
        this.blocks[3][1] = true;
    }
}
