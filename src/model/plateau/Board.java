package model.plateau;

import model.Bille;
import model.Couleur;
import model.mouvement.Position;

public class Board {

    private final Cell[][] board;
    private final int n;

    public Board(int n) {
        this.n = n;
        board = new Cell[4*n-1][4*n-1];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void initBoard() {
        initWhite();
        initBlack();
        initRed();
    }

    private void initWhite() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j].setBille(new Bille(Couleur.BLANC, new Position(i, j)));
                board[board.length-1 - i][board.length-1 - j].setBille(new Bille(Couleur.BLANC, new Position(i, j)));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board[i][j].setBille(new Bille(Couleur.NOIR, new Position(i, j)));
                board[j][i].setBille(new Bille(Couleur.NOIR, new Position(i, j)));
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
                board[i][j+spaces].setBille(new Bille(Couleur.ROUGE, new Position(i, j)));
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
