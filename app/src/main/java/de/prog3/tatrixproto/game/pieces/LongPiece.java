package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class LongPiece extends AbstractPiece {

    public LongPiece(Bitmap image) {
        super(4, image);

        this.blocks[0][1] = true;
        this.blocks[1][1] = true;
        this.blocks[2][1] = true;
        this.blocks[3][1] = true;
    }
}
