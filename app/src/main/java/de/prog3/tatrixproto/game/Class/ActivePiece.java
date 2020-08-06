package de.prog3.tatrixproto.game.Class;


import android.util.Log;

import de.prog3.tatrixproto.game.Abstract.AbstractPiece;


public class ActivePiece {
    private int x;
    private int y;
    private int xAusPunkt;
    protected AbstractPiece piece;

    /** reference t the parent grid */
    private Block[][] grid;

    public ActivePiece(Block[][] grid) {
        this.grid = grid;
        this.x = (grid.length/2)-1;
        this.xAusPunkt = x;
        y = 0;
    }
    public void addPiece(AbstractPiece piece){
        this.piece = piece;
        boolean check = true;
        this.x = (grid.length/2)-(int)piece.getSizeD2()/2;

        for (int k = 0; k < piece.getSizeD2();k++) {
            for (int i = 0; i < piece.getSizeD2(); i++) {
                check = check && !piece.getBlocks()[i][k];
            }
            if (check) {
                y--;
            } else {
                break;
            }
        }

        check = true;
        for (int k = piece.getSizeD2()-1; k >=0;k--) {
            for (int i = 0; i < piece.getSizeD2(); i++) {
                if (x+i >= 0 && y+k >=0 ) {
                    if (piece.getBlocks()[i][k] && grid[i+x][k+y].isActive()){
                        y--;
                        i=0;
                        k=piece.getSizeD2()-1;
                    }
                }
            }
        }
    }
    public void addPiece(AbstractPiece piece,int x, int y){
        this.piece = piece;
        this.x = x;
        this.y = y;
    }
    public void resetP(){
        y=0;
        x= xAusPunkt;
    }
    public void clear(){
        piece=null;
    }


    public boolean canMoveLeft() { //L
        x--;
        for (int i = 0; i <piece.getBlocks().length;i++){
            for (int k = 0; k <piece.getBlocks()[0].length;k++){
                if(piece.getBlocks()[i][k]){
                    if(i+x<0){
                        x++;
                        return false;
                    }

                    if(grid[i+x][k+y].isActive()){
                        x++;
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean canMoveRight() { //L
        x++;
        for (int i = 0; i <piece.getBlocks().length;i++){
            for (int k = 0; k <piece.getBlocks()[i].length;k++){
                if(piece.getBlocks()[i][k]){
                    if(i+x >= grid.length){
                        x--;
                        return false;
                    }

                    if(grid[i+x][k+y].isActive()){
                        x--;
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean canMoveDown() {//L
        y++;
        for (int i = 0; i <piece.getBlocks().length;i++){
            for (int k = 0; k <piece.getBlocks()[i].length;k++){
                if(piece.getBlocks()[i][k]) {
                    // Unterkante des Spielfelds erreicht?
                    if (y+k >= grid[0].length) {
                        y--;
                        return false;
                    }
                    // kolision mit einem anderen block
                    if(grid[i+x][k+y].isActive()){
                        y--;
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void movePieceLeft() {//L
        removeFromGrid();
        if (!canMoveLeft()){
            addToGrid();
            return;
        }
        addToGrid();
    }
    public void movePieceRight() {//L
        removeFromGrid();
        if (!canMoveRight()){
            addToGrid();
            return;
        }
        addToGrid();
    }

    /**
     * Add this piece to the grid
     */
    public boolean addToGrid() {
        boolean end = true;
        for (int i = 0; i <piece.getBlocks().length;i++){
            for (int k = 0; k <piece.getBlocks()[i].length;k++){
                if(piece.getBlocks()[i][k]) {
                    if (x+i >= 0 && x+i < grid.length
                            && y+k >=0 && y+k < grid[0].length) {
                        Block blockBelow = grid[x + i][y + k];
                        if (blockBelow.isActive()) {
                            end =false;
                        }
                    }else {end=false;}
                }
            }
        }
        updateGrid(this.piece);
        return end;
    }
    /**
     * Remove this piece from the grid
     */
    public void removeFromGrid() {
        updateGrid(null);
    }
    public void updateGrid(AbstractPiece piece){
        for (int i = 0; i <this.piece.getBlocks().length;i++){
            for (int k = 0; k <this.piece.getBlocks()[i].length;k++){
                if (this.piece.getBlocks()[i][k]) {
                    if (x+i >= 0 && x+i < grid.length
                            && y+k >=0 && y+k < grid[0].length) {
                        grid[x + i][y + k].setPiece(piece);
                    }
                }

            }
        }
    }


    public boolean movePieceDown() {//L
        removeFromGrid();
        if (canMoveDown()) {
            addToGrid();
            return true;
        }
        addToGrid();
        return false;
    }


    public boolean canRotate(boolean pre[][]){
        for (int i = 0; i <piece.getBlocks().length;i++){
            for (int k = 0; k <piece.getBlocks()[i].length;k++){
                if(pre[i][k]) {
                    // Unterkante des Spielfelds erreicht?
                    if (x+i >= 0 && x+i < grid.length
                            && y+k >=0 && y+k < grid[0].length) {
                        Block blockBelow = grid[x+i][y+k];
                        if(blockBelow.isActive()){
                            return false;
                        }

                    }else {return false;}
                }
            }
        }
        return true;
    }
    //Quelle: https://stackoverflow.com/questions/2799755/rotate-array-clockwise
    public void rotatePiece(){
        removeFromGrid();
        int count = 0;
        do {
            count++;
            for (int i = 0; i <piece.getBlocks().length;i++){
                for (int k = 0; k <piece.getBlocks()[i].length;k++){
                    piece.getPre()[k][piece.getBlocks().length-1-i]=piece.getBlocks()[i][k];
                }
            }
        }while (!canRotate(piece.getPre())&&count<5);
        if(count<4){ // Wenn Counter 4 ist hat sich an dem zustand vom Pieces nichts geändert
            this.tiefeCopyArray2(piece.getPre());
        }

        addToGrid();
    }
    public void tiefeCopyArray2(boolean a[][]){
        for (int i = 0; i < a.length;i++){
            for (int k = 0; k < a[0].length; k++){
                this.piece.getBlocks()[i][k] = a[i][k];
            }
        }
    }


    public int getColor() {
        return this.getColor();
    }
}
