package model.plateau;

import model.Bille;
import model.Couleur;
import java.util.Random;

public class Board {

    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;

    public Board(int n) {
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        initKeys();
    }

    private static void initKeys() {
        for(int i = 0; i < keys.length; i++) {
            for(int j = 0; j < keys[i].length; j++) {
                keys[i][j] = new Random().nextLong();
            }
        }
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

    @Override
    public int hashCode() {
        long zobristHash = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board[i][j].estVide()) {
                    switch (board[i][j].getBille().getCouleur()) {
                        case Red -> zobristHash = zobristHash ^ keys[0][i + board[i].length * j];
                        case Black -> zobristHash = zobristHash ^ keys[1][i + board[i].length * j];
                        case White -> zobristHash = zobristHash ^ keys[2][i + board[i].length * j];
                    }

                }
            }
        }
        return (int) zobristHash;
    }
}
