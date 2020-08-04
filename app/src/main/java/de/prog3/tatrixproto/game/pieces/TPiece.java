package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class TPiece extends AbstractPiece {

    public TPiece(Bitmap image, boolean colorOn) {
        super(3, image,Color.parseColor("#ff00ff"),colorOn);

        this.blocks[1][0] = true;
        this.blocks[0][1] = true;
        this.blocks[1][1] = true;
        this.blocks[2][1] = true;
    }
}
