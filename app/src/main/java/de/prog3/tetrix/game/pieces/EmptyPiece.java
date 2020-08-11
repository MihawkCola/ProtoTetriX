//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tetrix.game.pieces;

import android.graphics.Bitmap;

import de.prog3.tetrix.game.Abstract.AbstractPiece;

public class EmptyPiece extends AbstractPiece {

    public EmptyPiece(Bitmap image,Bitmap imagePre) {
            super(4, image,imagePre);
    }
}
