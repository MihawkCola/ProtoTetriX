package de.prog3.tatrixproto.game.Abstract;

import android.graphics.Bitmap;

public abstract class AbstractPiece {
    protected boolean blocks[][];
    protected int color;
    protected Bitmap image;
    protected   boolean pre[][];
    protected int sizeD2;
    public Boolean colorOn;
    public AbstractPiece(int sizeD2,Bitmap image, int color,boolean colorOn){
        blocks = new boolean[sizeD2][sizeD2];
        pre = new boolean[sizeD2][sizeD2];
        this.sizeD2 =sizeD2;
        this.image = image;
        this.color=color;
        this.colorOn=colorOn;
    }
    public int getColor() {
        return color;
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

    }
