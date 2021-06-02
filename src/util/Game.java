package util;

public class Game {
    private Piece[][] board;
    private Player[] players;
    private boolean whiteTurn;

    public Game(String player1Name, int player1Rating, String player2Name, int player2Rating) {
        whiteTurn = true;
        players = new Player[2];
        players[0] = new Player(player1Name, player1Rating);
        players[1] = new Player(player2Name, player2Rating);
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {//rank: 8-i
            for (int j = 0; j < 8; j++) {//file: j+'A'
                if (i > 1 && i < 6)//4 middle ranks
                    board[i][j] = new Empty();
                else if (i == 6 || i == 1)//ranks 2 and 7: only pawns
                    board[i][j] = new Pawn(i == 6);
                else if (j == 1 || j == 6)
                    board[i][j] = new Knight(i == 7);
                else if (j == 2 || j == 5) {//ranks 2 and 5: only bishops
                    board[i][j] = new Bishop(i == 7);
                } else if (j == 3) {
                    board[i][j] = new Queen(i == 7);
                } else if (j == 4) {
                    board[i][j] = new King(i == 7);
                } else if (j == 0 || j == 7) {
                    board[i][j] = new Rook(i == 7);
                } else
                    board[i][j] = new Piece(i == 7, false);
            }
        }

    }

    public String toString() {
        String out = players[1] + "\n";
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                out += (((j == 0) ? ((8 - i) + "") : (board[i][j - 1] + "")) + (j == 8 ? '\n' : '\t'));
        out += "\t\tA\t\tB\t\tC\t\tD\t\tE\t\tF\t\tG\t\tH";
        return out + "\n" + players[0];
    }

    public void move(String src, String trg) throws IllegalChessMoveException {
        if (!isLegalMove(src, trg))
            throw new IllegalChessMoveException();
        int srcI = 8 - src.charAt(1) + '0';
        int trgI = 8 - trg.charAt(1) + '0';
        int srcJ = src.charAt(0) - 'a';
        int trgJ = trg.charAt(0) - 'a';
        board[trgI][trgJ] = board[srcI][srcJ];
        board[srcI][srcJ] = new Empty();
        whiteTurn = !whiteTurn;
    }

    public void capture(String src, String trg) throws IllegalChessMoveException {
        if (!isLegalCapture(src, trg))
            throw new IllegalChessMoveException();
        int srcI = 8 - src.charAt(1) + '0';
        int trgI = 8 - trg.charAt(1) + '0';
        int srcJ = src.charAt(0) - 'a';
        int trgJ = trg.charAt(0) - 'a';
        board[trgI][trgJ] = board[srcI][srcJ];
        board[srcI][srcJ] = new Empty();
        whiteTurn = !whiteTurn;

    }

    private boolean isLegalMove(String src, String trg) {
        int srcI = 8 - src.charAt(1) + '0';
        int trgI = 8 - trg.charAt(1) + '0';
        int srcJ = src.charAt(0) - 'a';
        int trgJ = trg.charAt(0) - 'a';
        if (srcI == srcJ && trgI == trgJ)
            return false;
        if (board[srcI][srcJ].isEmpty() || !board[trgI][trgJ].isEmpty())
            return false;
        if (board[srcI][srcJ].white != whiteTurn)//turn violation
            return false;
        Piece picked = board[srcI][srcJ];
        if (picked.toString().contains("Knt")) {//knight
            return Math.abs((srcI - trgI) * (srcJ - trgJ)) == 2;
        } else if (picked.toString().contains("Pwn")) {//pawn
            if (trgJ != srcJ)//not a vertical move
                return false;
            if (picked.white != srcI > trgI)//moving to the wrong direction
                return false;
            if (Math.abs(srcI - trgI) == 1)//moving one square...
                return true;
            if (picked.white && srcI == 6 || !picked.white && srcI == 1)//starting position
                return Math.abs(srcI - trgI) == 2 && board[srcI + (picked.white ? -1 : 1)][srcJ].isEmpty();
            else
                return false;
        } else if (picked.toString().contains("Bsp")) {//bishop
            return isBishopLegal(srcI, srcJ, trgI, trgJ);
        } else if (picked.toString().contains("Qun")) {//queen
            return isBishopLegal(srcI, srcJ, trgI, trgJ) || isRookLegal(srcI, srcJ, trgI, trgJ);
        } else if (picked.toString().contains("Kng")) {//king
            return (srcI - trgI == 1 || srcJ - trgJ == 1);
        } else if (picked.toString().contains("Rok")) {//Rook
            return isRookLegal(srcI, srcJ, trgI, trgJ);
        }
        return true;
    }

    private boolean isLegalCapture(String src, String trg) {
        int srcI = 8 - src.charAt(1) + '0';
        int trgI = 8 - trg.charAt(1) + '0';
        int srcJ = src.charAt(0) - 'a';
        int trgJ = trg.charAt(0) - 'a';
        if (srcI == srcJ && trgI == trgJ)
            return false;
        if (board[srcI][srcJ].isEmpty() || board[trgI][trgJ].isEmpty())
            return false;
        if (board[srcI][srcJ].white != whiteTurn)//turn violation
            return false;
        if (board[srcI][srcJ].white == board[trgI][trgJ].white)//capturing same color
            return false;
        Piece picked = board[srcI][srcJ];
        if (picked.toString().contains("Knt")) {//knight
            return Math.abs((srcI - trgI) * (srcJ - trgJ)) == 2;
        } else if (picked.toString().contains("Pwn")) {//pawn
            if (Math.abs((srcJ - trgJ) * (srcI - trgI)) != 1)
                return false;
            if (picked.white != srcI > trgI)//moving to the wrong direction
                return false;
        } else if (picked.toString().contains("Bsp")) {//bishop
            return isBishopLegal(srcI, srcJ, trgI, trgJ);
        } else if (picked.toString().contains("Qun")) {//queen
            return isBishopLegal(srcI, srcJ, trgI, trgJ) || isRookLegal(srcI, srcJ, trgI, trgJ);
        } else if (picked.toString().contains("Kng")) {//king
            return (srcI - trgI == 1 || srcJ - trgJ == 1);
        } else if (picked.toString().contains("Rok")) {//rook
            return isRookLegal(srcI, srcJ, trgI, trgJ);
        }
        return true;
    }

    public Boolean isBishopLegal(int srcI, int srcJ, int trgI, int trgJ) {
        if (Math.abs(srcI - trgI) == Math.abs(srcJ - trgJ)) {
            if (trgI > srcI && trgJ > srcJ) {
                for (int i = srcI + 1, j = srcJ + 1; i < trgI && j < trgJ; i++, j++) {
                    if (!board[i][j].isEmpty())
                        return false;
                }
            } else if (srcI > trgI && trgJ > srcJ) {
                for (int i = srcI - 1, j = srcJ + 1; i > trgI && j < trgJ;
                     i--, j++) {
                    if (!board[i][j].isEmpty())
                        return false;
                }
            } else if (srcI > trgI && srcJ > trgJ) {
                for (int i = srcI - 1, j = srcJ - 1; i > trgI || j > trgJ; i--, j--) {
                    if (!board[i][j].isEmpty())
                        return false;
                }
            } else if (srcI < trgI && srcJ > trgJ) {
                for (int i = srcI + 1, j = srcJ - 1; i < trgI && j > trgJ; i++, j--) {
                    if (!board[i][j].isEmpty())
                        return false;
                }
            }

            return true;
        }
        return false;
    }

    public boolean isRookLegal(int srcI, int srcJ, int trgI, int trgJ) {
        if (srcJ == trgJ && srcI != trgI || srcI == trgI && srcJ != trgJ) {
            if (srcJ > trgJ) {
                for (int i = srcJ - 1; i > trgJ; i--) {
                    if (!board[srcI][i].isEmpty())
                        return false;
                }
            }
            if (trgJ > srcJ) {
                for (int i = srcJ + 1; i < trgJ; i++) {
                    if (!board[srcI][i].isEmpty())
                        return false;
                }
            }
            if (srcI > trgI) {
                for (int i = srcI - 1; i > trgI; i--) {
                    if (!board[i][srcJ].isEmpty())
                        return false;
                }
            }
            if (trgI > srcI) {
                for (int i = srcI + 1; i < trgI; i++) {
                    if (!board[i][srcJ].isEmpty())
                        return false;
                }
            }
        }
        return true;

    }
}
