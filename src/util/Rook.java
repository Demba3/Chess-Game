package util;

public class Rook extends Piece {
    public Rook(Boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Rok";
    }
}
