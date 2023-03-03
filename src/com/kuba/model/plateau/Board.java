package model.plateau;

import model.mouvement.Mouvement;
import model.player.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;

import java.util.*;


public class Board {
    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;
    private final Set<Integer> treated_configs;
    private Couleur currentPlayer = Couleur.BLANC;

    public Board(int n) {
        this.treated_configs = new HashSet<>();
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

    public int size() {
        return board.length;
    }
    private Cell board(Position pos) {
        return board[pos.getI()][pos.getJ()];
    }

    public Cell board(int i, int j) {
        return board[i][j];
    }

    public Couleur ColorAt(Position pos) {
        return board(pos).getBille().getColor();
    }

    public Couleur ColorAt(int i, int j) {
        return board(i, j).getBille().getColor();
    }

    private boolean estVide(Position position) {
        return board(position).estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board(prev).getBille();
        board(prev).clear();
        board(next).setBille(bille);
    }

    public Board update(Mouvement mouvement, Couleur joueur) {
        Position pos = mouvement.getPosition();
        Direction dir = mouvement.getDirection();
        // Le joueur ne peut bouger que les billes de sa propre couleur
        if(!ColorAt(pos).equals(joueur)) {
            //System.out.println("Le joueur ne peut bouger que les billes de sa propre couleur");
            return this;
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        if(estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
            //System.out.println(pos.prev(dir));
            //System.out.println("Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger");
            return this;
        }

        Position next = pos.next(dir); // Trouver la limite
        while(estDansLimite(next) && !estVide(next)) { // avec next tant que c'est possible
            next = next.next(dir);
        }
        Board transitionBoard = this.copyBoard();
        Position fin = next;  // Pour revenir en arriere si le mouvement n'est pas valid (un curseur backup)
        if(!estDansLimite(next)) { // là, il faut sortir les billes
            Position limit = next.prev(dir); // La limite du tableau
            transitionBoard.moveOut(limit, joueur);
            fin = limit;
            next = next.prev(dir);
        }

        while (!next.equals(pos) && board(next).estVide()) { // Décaler les pions
            Position prev = next.prev(dir);
            transitionBoard.updatePosition(prev, next);
            next = next.prev(dir);
        }

        int hash_code = hashCode(); // KO
        if(isTreated(hash_code)) {
            return this;
        } else {
            treated_configs.add(hash_code);
            currentPlayer = currentPlayer.opposite();
            return transitionBoard;
        }
    }

    private Board copyBoard() {
        Board res = new Board(this.n);
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                res.board[i][j] = new Cell();
                if(board(i, j).estVide()) res.board(i, j).clear();
                else res.board(i, j).setBille(board[i][j].getBille());
            }
        }
        return res;
    }

    private void moveOut(Position limit, Couleur joueur) {
        if(!ColorAt(limit).equals(joueur)) {
            if(ColorAt(limit).equals(Couleur.ROUGE)) {
                //TODO notify capture billes rouges
            } else {
                //TODO notify capture billes de l'adversaire
            }
            board(limit).clear();
        } else {
            //System.out.println("Le joueur ne peut pas sortir les billes de sa propre couleur");
        }
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private boolean isTreated(int conf){
        return treated_configs.contains(conf);
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

    public boolean gameOver() {
        int red = 0, black = 0, white = 0;
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if(!cell.estVide()) {
                    switch (cell.getBille().getColor()) {
                        case BLANC -> white++;
                        case NOIR -> black++;
                        case ROUGE -> red++;
                    }
                }
            }
        }
        return red == 0 || black == 0 || white == 0;
    }

    public Collection<Mouvement> getAllPossibleMoves() {
        List<Mouvement> allMoves = new ArrayList<>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(!board(i, j).estVide()) {
                    Couleur couleur = board(i, j).getBille().getColor();
                    if(couleur != Couleur.ROUGE) {
                        Mouvement mouvement1 = new Mouvement(new Position(i, j), Direction.EST);
                        Mouvement mouvement2 = new Mouvement(new Position(i, j), Direction.OUEST);
                        Mouvement mouvement3 = new Mouvement(new Position(i, j), Direction.NORD);
                        Mouvement mouvement4 = new Mouvement(new Position(i, j), Direction.SUD);
                        Board transitionBoard = this.update(mouvement1, couleur);
                        if(!transitionBoard.equals(this)) allMoves.add(mouvement1);
                        transitionBoard = this.update(mouvement2, couleur);
                        if(!transitionBoard.equals(this)) allMoves.add(mouvement2);
                        transitionBoard = this.update(mouvement3, couleur);
                        if(!transitionBoard.equals(this)) allMoves.add(mouvement3);
                        transitionBoard = this.update(mouvement4, couleur);
                        if(!transitionBoard.equals(this)) allMoves.add(mouvement4);
                    }
                }
            }
        }
        return allMoves;
    }

    public Couleur currentPlayer() {
        return currentPlayer;
    }
}
