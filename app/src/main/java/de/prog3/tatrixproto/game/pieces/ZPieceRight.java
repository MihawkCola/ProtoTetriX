package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class ZPieceRight extends AbstractPiece {
    public ZPieceRight(Bitmap image, boolean colorOn) {
        super(3, image,Color.parseColor("#0000ff"),colorOn);

        this.blocks[0][0] = true;
        this.blocks[1][0] = true;
        this.blocks[1][1] = true;
        this.blocks[2][1] = true;
    }
}
