package model.plateau;

import model.Bille;
import model.Couleur;
import model.mouvement.Direction;
import model.mouvement.Position;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class Board extends JPanel {
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
        // Panel
        setPreferredSize(new Dimension(Bille.width * k, Bille.width * k));
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
                board(i, j).setBille(new Bille(Couleur.BLANC));
                board(board.length-1 - i, board.length-1 - j).setBille(new Bille(Couleur.BLANC));
            }
        }
    }

    private void initBlack() {
        for(int i = 0; i < n; i++) {
            for(int j = board[i].length-1; j >= board[i].length-n; j--) {
                board(i, j).setBille(new Bille(Couleur.NOIR));
                board(j, i).setBille(new Bille(Couleur.NOIR));
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
                board(i, j+spaces).setBille(new Bille(Couleur.ROUGE));
            }
            if(i < k/2) {
                count += 2;
            } else {
                count -= 2;
            }
        }
    }
    private Cell board(Position pos) {
        return board[pos.getI()][pos.getJ()];
    }

    private Cell board(int i, int j) {
        return board[i][j];
    }

    private boolean estVide(Position position) {
        return board(position).estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board(prev).getBille();
        board(prev).clear();
        board(next).setBille(bille);
    }

    public void move(Position pos, Direction dir) {
        if(estDansLimite(pos.prev(dir)) &&  !estVide(pos.prev(dir))) return; // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        Position next = pos.next(dir); // Trouver la limite
        while(estDansLimite(next) && !estVide(next)) { // avec next tant que c'est possible
            next = next.next(dir);
        }
        if(!estDansLimite(next)) return; // La il faut sortir les billes

        Position fin = next; // Pour revenir en arriere si le mouvement n'est pas valid

        while (!next.equals(pos)) { // Décaler les pions
            Position prev = next.prev(dir);
            updatePosition(prev, next);
            next = next.prev(dir);
        }

        int hash_code = hashCode(); // KO
        if(isTreated(hash_code)) {
            while (!next.equals(fin)) { // Undo move
                Position suiv = next.next(dir);
                updatePosition(suiv, next);
                next = next.next(dir);
            }
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
                if(!board(i, j).estVide()) {
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

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board(j, i).estVide()) {
                    int width = Bille.width;
                    graphics2D.drawImage(board(j, i).getBille().image(), width * i, width * j, width, width, null);
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(i != board.length-1 && j != board[i].length-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * Bille.width + (Bille.width / 2), i * Bille.width + (Bille.width / 2), Bille.width, Bille.width);
                }
            }
        }
    }

}
