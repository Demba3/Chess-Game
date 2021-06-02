package util;

public class Piece {
    protected boolean white;
    private boolean dummy;

    public Piece(boolean white, boolean dummy) {
        this.white = white;
        this.dummy = dummy;
    }

    public String toString() {
        return (white ? "W Pce" : "B Pce");
    }

    public boolean isEmpty() {
        return dummy;
    }
}
