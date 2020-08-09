//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class OnePiece extends AbstractPiece {

    public OnePiece(Bitmap image, Bitmap imagePre) {
            super(1, image,imagePre);
            this.blocksBase[0][0]=true;
            copyInblockBase();
    }
}
