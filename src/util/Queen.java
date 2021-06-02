package util;

public class Queen extends Piece {
    public Queen(boolean white) {
        super(white, false);
    }

    public String toString() {
        return (white ? "W " : "B ") + "Qun";
    }
}
