package util;

public class Bishop extends Piece {
    public Bishop(boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Bsp";
    }
}
