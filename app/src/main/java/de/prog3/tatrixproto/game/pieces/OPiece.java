package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class OPiece extends AbstractPiece {

    public OPiece(Bitmap image, boolean colorOn) {
        super(4, image,Color.parseColor("#00ffff"),colorOn);

        this.blocks[1][1] = true;
        this.blocks[1][2] = true;
        this.blocks[2][1] = true;
        this.blocks[2][2] = true;

    }
}
