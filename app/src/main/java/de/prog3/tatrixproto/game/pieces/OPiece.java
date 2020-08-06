//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.pieces;

import android.graphics.Bitmap;
import android.graphics.Color;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;

public class OPiece extends AbstractPiece {

    public OPiece(Bitmap image) {
        super(4, image);

        this.blocks[1][1] = true;
        this.blocks[1][2] = true;
        this.blocks[2][1] = true;
        this.blocks[2][2] = true;

        this.blocksBase[1][1] = true;
        this.blocksBase[1][2] = true;
        this.blocksBase[2][1] = true;
        this.blocksBase[2][2] = true;

    }
}
