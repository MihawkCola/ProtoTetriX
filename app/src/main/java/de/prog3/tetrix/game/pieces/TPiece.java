//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tetrix.game.pieces;

import android.graphics.Bitmap;


import de.prog3.tetrix.game.Abstract.AbstractPiece;

public class TPiece extends AbstractPiece {

    public TPiece(Bitmap image,Bitmap imagePre, Bitmap nextPicture) {
        super(3, image,imagePre,nextPicture);

        this.blocksBase[1][0] = true;
        this.blocksBase[0][1] = true;
        this.blocksBase[1][1] = true;
        this.blocksBase[2][1] = true;
        copyInblockBase();
    }
}
