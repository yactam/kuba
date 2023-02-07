package model.plateau;

import model.Bille;
import model.Couleur;

public class Board {

    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;

    public Board(int n) {
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[k][k];
    }

    public void initBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
        initWhite();
        initBlack();
        initRed();
    }

    private void initWhite() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j].setBille(new Bille(Couleur.White));
                board[board.length-1 - i][board.length-1 - j].setBille(new Bille(Couleur.White));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board[i][j].setBille(new Bille(Couleur.Black));
                board[j][i].setBille(new Bille(Couleur.Black));
            }
        }
    }

    private void initRed() {
        int count = 1, spaces = 1, k = 4 * n - 1;
        for(int i = 1; i < k-1; i++) {
            if(i < k/2) {
                spaces = (k/2) - i + 1;
            } else {
                spaces = i + 1 - (k/2);
            }
            for(int j = 0; j < count; j++) {
                board[i][j+spaces].setBille(new Bille(Couleur.Red));
            }
            if(i < k/2) {
                count += 2;
            } else {
                count -= 2;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder plateau = new StringBuilder();
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                plateau.append(cell.toString()).append(" ");
            }
            plateau.append("\n");
        }
        return plateau.toString();
    }
}
