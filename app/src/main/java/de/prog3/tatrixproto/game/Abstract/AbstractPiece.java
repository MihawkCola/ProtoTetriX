package de.prog3.tatrixproto.game.Abstract;

import android.graphics.Bitmap;

public abstract class AbstractPiece {
    protected boolean blocks[][];
    protected Bitmap image;
    protected boolean pre[][];
    protected int sizeD2;
    public AbstractPiece(int sizeD2,Bitmap image){
        blocks = new boolean[sizeD2][sizeD2];
        pre = new boolean[sizeD2][sizeD2];
        this.sizeD2 =sizeD2;
        this.image = image;
    }

    public boolean[][] getBlocks() {
        return blocks;
    }

    public boolean[][] getPre() {
        return pre;
    }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {this.image=image;}

    public int getSizeD2() {
        return sizeD2;
    }
}
