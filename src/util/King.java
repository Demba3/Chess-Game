package util;


public class King extends Piece {
    public King(boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Kng";
    }
}
