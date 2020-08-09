//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;


import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class OPiece extends AbstractPiece {

    public OPiece(Bitmap image,Bitmap imagePre) {
        super(4, image,imagePre);

        this.blocksBase[1][1] = true;
        this.blocksBase[1][2] = true;
        this.blocksBase[2][1] = true;
        this.blocksBase[2][2] = true;
        copyInblockBase();
    }
}
