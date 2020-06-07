package de.prog3.tatrixproto.game.interfaces;

public interface IPiece {
    public int getColor();

    public boolean canMoveLeft();
    public boolean canMoveRight();
    public boolean canMoveDown();
    public void movePieceLeft();
    public void movePieceRight();
    public boolean movePieceDown();

    public boolean canRotate(boolean pre[][]);
    public void rotatePiece();

    public boolean addToGrid();
}
