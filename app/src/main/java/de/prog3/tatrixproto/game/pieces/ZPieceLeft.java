package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class ZPieceLeft extends AbstractPiece {
    public ZPieceLeft(Bitmap image, boolean colorOn) {
        super(3, image,Color.parseColor("#00ff00"),colorOn);

        this.blocks[1][0] = true;
        this.blocks[2][0] = true;
        this.blocks[0][1] = true;
        this.blocks[1][1] = true;
    }
}
