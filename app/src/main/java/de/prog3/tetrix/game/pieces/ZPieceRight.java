//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tetrix.game.pieces;

import android.graphics.Bitmap;

import de.prog3.tetrix.game.Abstract.AbstractPiece;

public class ZPieceRight extends AbstractPiece {
    public ZPieceRight(Bitmap image,Bitmap imagePre, Bitmap nextPicture) {
        super(3, image,imagePre,nextPicture);

        this.blocksBase[0][0] = true;
        this.blocksBase[1][0] = true;
        this.blocksBase[1][1] = true;
        this.blocksBase[2][1] = true;
        copyInblockBase();
    }
}
