package de.prog3.tatrixproto.game.Class;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;


public class Block {
    protected AbstractPiece piece;

    public Block() {
        this.piece = null;
    }

    public AbstractPiece getPiece() {
        return piece;
    }

    public boolean isActive() {
        return piece != null;
    }

    public void setPiece(AbstractPiece piece) {
        this.piece = piece;
    }
    public void draw(Canvas canvas, int x , int y, int size,Paint paint){
        if(piece != null){
            if (piece.getImage().getWidth() != size) {
                piece.setImage(Bitmap.createScaledBitmap(piece.getImage(), size,size,false));
            }
            canvas.drawBitmap(piece.getImage(), x, y, paint);
        }

    }
    public void clear(){
        piece = null;
    };
}
