//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class LongPiece extends AbstractPiece {

    public LongPiece(Bitmap image,Bitmap imagePre) {
        super(4, image,imagePre);

        this.blocksBase[0][1] = true;
        this.blocksBase[1][1] = true;
        this.blocksBase[2][1] = true;
        this.blocksBase[3][1] = true;
        copyInblockBase();
    }
}
