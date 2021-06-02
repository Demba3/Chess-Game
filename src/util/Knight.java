package util;

public class Knight extends Piece {
    public Knight(boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Knt";
    }
}
