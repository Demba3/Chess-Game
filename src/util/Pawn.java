package util;

public class Pawn extends Piece {
    public Pawn(boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Pwn";
    }
}
