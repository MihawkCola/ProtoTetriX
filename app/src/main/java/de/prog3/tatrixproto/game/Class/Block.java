//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.Class;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;


public class Block {
    protected AbstractPiece piece;
    protected boolean isPrediction;

    public Block() {
        this.piece = null;
    }

    public AbstractPiece getPiece() {
        return piece;
    }

    public boolean isActive() {
        return piece != null & !isPrediction ;
    }
    public void setPiece(AbstractPiece piece,boolean p) {
        this.piece = piece;
        this.isPrediction= p;
    }
    public void draw(Canvas canvas, int x , int y, int size){
        if(piece != null){
            if (piece.getImage().getWidth() != size) {
                piece.setImage(Bitmap.createScaledBitmap(piece.getImage(), size,size,false));
            }
            if (piece.getImagePre().getWidth() != size) {
                piece.setImagePre(Bitmap.createScaledBitmap(piece.getImagePre(), size,size,false));
            }
            if (isPrediction){
                canvas.drawBitmap(piece.getImagePre(), x, y, null);
            }else {
                canvas.drawBitmap(piece.getImage(), x, y, null);
            }
        }

    }
    public void clear(){
        piece = null;
        this.isPrediction = false;
    };
}
