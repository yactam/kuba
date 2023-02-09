package model.plateau;

import model.Bille;
import model.Couleur;
import model.mouvement.Direction;
import model.mouvement.Position;
import java.util.*;


public class Board{

    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;
    private final Set<Integer> treated_confs;

    public Board(int n) {
        this.treated_confs = new HashSet<>();
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

    private boolean estVide(Position position) {
        return board[position.getI()][position.getJ()].estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board[prev.getI()][prev.getJ()].getBille();
        board[prev.getI()][prev.getJ()].clear();
        board[next.getI()][next.getJ()].setBille(bille);
    }

    public void move(Position pos, Direction dir) {
        if(!estVide(pos.prev(dir))) return; // Mouvement pas valide
        Position next = pos.next(dir); // Trouver la limite
        while(estDansLimite(next) && !estVide(next)) {
            next = next.next(dir);
        }
        if(!estDansLimite(next)) return;

        while (!next.equals(pos)) { // Décaler les pions
            Position prev = next.prev(dir);
            updatePosition(prev, next);
            next = next.prev(dir);
        }

        int hash_code = hashCode(); // KO
        if(isTreated(hash_code)) {
            updatePosition(next, pos); // Mouvement non valide
        } else {
            treated_confs.add(hash_code);
        }
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private boolean isTreated(int conf){
        return treated_confs.contains(conf);
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
                    switch (board[i][j].getBille().getColor()) {
                        case ROUGE -> zobristHash ^= keys[0][i + board[i].length * j];
                        case NOIR -> zobristHash  ^= keys[1][i + board[i].length * j];
                        case BLANC -> zobristHash ^= keys[2][i + board[i].length * j];
                    }
                }
            }
        }
        return (int) zobristHash;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }
}
