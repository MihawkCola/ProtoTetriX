//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class LPieceRight extends AbstractPiece {

    public LPieceRight(Bitmap image,Bitmap imagePre) {
        super(3, image,imagePre);

        this.blocksBase[0][1] = true;
        this.blocksBase[1][1] = true;
        this.blocksBase[2][1] = true;
        this.blocksBase[2][0] = true;
        copyInblockBase();
    }
}
