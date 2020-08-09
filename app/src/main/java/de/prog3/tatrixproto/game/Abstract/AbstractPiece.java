//Projektarbeit Prog3: Tetris
//von Nelson Morais (879551) & Marcel Sauer (886022) geschrieben
package de.prog3.tatrixproto.game.Abstract;

import android.graphics.Bitmap;

public abstract class AbstractPiece {
    protected boolean blocksBase[][];
    protected boolean blocks[][];
    protected Bitmap image;
    protected Bitmap imagePre;
    protected boolean pre[][];
    protected int sizeD2;
    public AbstractPiece(int sizeD2,Bitmap image,Bitmap imagePre){
        blocks = new boolean[sizeD2][sizeD2];
        pre = new boolean[sizeD2][sizeD2];
        blocksBase = new boolean[sizeD2][sizeD2];
        this.sizeD2 =sizeD2;
        this.image = image;
        this.imagePre=imagePre;
    }

    public boolean[][] getBlocks() {
        return blocks;
    }
    public Bitmap getImagePre(){return imagePre;}

    public void setImagePre(Bitmap imagePre) {
        this.imagePre = imagePre;
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
    private void reset() {
        for (int i = 0; i < blocksBase.length;i++){
            for (int k = 0; k < blocksBase[i].length; k++){
                blocks[i][k] = blocksBase[i][k];
            }
        }
    }
    public AbstractPiece getPiece(){reset(); return this; }
}
