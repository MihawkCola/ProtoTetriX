package de.prog3.tatrixproto.game.Class;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;


public class Block {
    protected Bitmap image;
    protected AbstractPiece piece;

    public Block(Bitmap image) {
        this.image = image;
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
    public void draw(Canvas canvas, int x , int y, int size){
        if (image.getWidth() != size) {
            image = Bitmap.createScaledBitmap(image, size,size,false);
        }

        if(piece != null){
            Paint paint = new Paint();
            if(piece.colorOn) {
                paint.setColorFilter(new PorterDuffColorFilter(piece.getColor(), PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(image, x, y, paint);
            }else {
                if (piece.getImage().getWidth() != size) {
                    piece.setImage(Bitmap.createScaledBitmap(piece.getImage(), size,size,false));
                }
                canvas.drawBitmap(piece.getImage(), x, y, paint);
            }
        }
    }
}
