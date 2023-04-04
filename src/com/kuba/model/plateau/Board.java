package com.kuba.model.plateau;

import com.kuba.model.mouvement.MoveStatus;
import com.kuba.observerpattern.*;
import com.kuba.observerpattern.Observable;
import com.kuba.observerpattern.Observer;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Position;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BilleAnimateView;
import com.kuba.vue.BoardView;

import java.util.*;

public class Board implements Observable<Data>, Data {
    private Cell[][] board;
    public static Long[][] keys;
    private final int n;
    private Set<Integer> treated_configs;
    private final ArrayList<Observer<Data>> elementObs;

    public Board(int n) {
        this.treated_configs = new HashSet<>();
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        elementObs = new ArrayList<>();
        if (keys == null)
            initKeys();
        BilleAnimateView.width = (BoardView.HEIGHT / k);
        initBoard();
    }

    private void initKeys() {
        int k = this.size();
        keys = new Long[3][k * k];
        Random random = new Random();
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                keys[i][j] = random.nextLong();
            }
        }
        this.notifyObservers();
    }

    public void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(this, i, j);
            }
        }
        initWhite();
        initBlack();
        initRed();
    }

    private void initWhite() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board(i, j).setBille(new Bille(Couleur.BLANC));
                board(board.length - 1 - i, board.length - 1 - j)
                        .setBille(new Bille(Couleur.BLANC));
            }
        }
    }

    private void initBlack() {
        for (int i = 0; i < n; i++) {
            for (int j = board[i].length - 1; j >= board[i].length - n; j--) {
                board(i, j).setBille(new Bille(Couleur.NOIR));
                board(j, i).setBille(new Bille(Couleur.NOIR));
            }
        }
    }

    private void initRed() {
        int count = 1, spaces = 1, k = 4 * n - 1;
        for (int i = 1; i < k - 1; i++) {
            if (i < k / 2) {
                spaces = (k / 2) - i + 1;
            } else {
                spaces = i + 1 - (k / 2);
            }
            for (int j = 0; j < count; j++) {
                board(i, j + spaces).setBille(new Bille(Couleur.ROUGE));
            }
            if (i < k / 2) {
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

    // TODO remove after testing
    public void initCell(int i, int j) {
        board[i][j] = new Cell(this, i, j);
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

    public MoveStatus update(Mouvement mouvement, Joueur joueur, boolean execute) {
        Position pos = mouvement.getPosition();
        Direction dir = mouvement.getDirection();
        if (!estDansLimite(pos) || estVide(pos))
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE, "Position non valide");
        // Le joueur ne peut bouger que les billes de sa propre couleur
        if (!ColorAt(pos).equals(joueur.getCouleur())) {
            // System.out.println("Le joueur ne peut bouger que les billes de sa propre
            // couleur");
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE,
                    "Le joueur ne peut bouger que les billes de sa propre couleur");
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut
        // bouger
        if (estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
            // System.out.println(pos.prev(dir));
            // System.out.println("Mouvement pas valide il y a une bille avant la bille que
            // le joueur veut bouger");
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE,
                    "Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger");
        }

        Position next = pos.next(dir); // Trouver la limite
        while (estDansLimite(next) && !estVide(next)) { // avec next tant que c'est possible
            next = next.next(dir);
        }
        Board transitionBoard = this.copyBoard();
        boolean move_out = false;
        if (!transitionBoard.estDansLimite(next)) { // là, il faut sortir les billes
            Position limit = next.prev(dir); // La limite du tableau
            boolean legal = transitionBoard.moveOut(limit, joueur, execute);
            if (!legal)
                return new MoveStatus(MoveStatus.Status.INVALID_MOVE,
                        "Le joueur ne peut pas sortir les billes de sa propre couleur");
            next = next.prev(dir);
            move_out = true;
        }

        while (!next.equals(pos) && transitionBoard.board(next).estVide()) { // Décaler les pions
            Position prev = next.prev(dir);
            transitionBoard.updatePosition(prev, next);
            next = next.prev(dir);
        }

        int hash_code = transitionBoard.hashCode(); // KO
        if (isTreated(hash_code)) {
            // System.out.println("KO");
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE, "KO");
        } else {
            
            transitionBoard.treated_configs.add(hash_code);
            if (execute) {
                this.board = transitionBoard.board;
                this.treated_configs = transitionBoard.treated_configs;
            }
            notifyObservers();
            if (move_out)
                return new MoveStatus(MoveStatus.Status.MOVE_OUT, "");
            return new MoveStatus(MoveStatus.Status.BASIC_MOVE, "");
        }
    }

    public MoveStatus update(Mouvement mouvement, Joueur joueur) {
        return update(mouvement, joueur, true);
    }

    public Board copyBoard() {
        Board res = new Board(this.n);
        res.treated_configs = new HashSet<>(treated_configs);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                res.board[i][j] = new Cell(this, i, j);
                if (board(i, j).estVide())
                    res.board(i, j).clear();
                else
                    res.board(i, j).setBille(board[i][j].getBille());
            }
        }
        return res;
    }

    private boolean moveOut(Position limit, Joueur joueur, boolean execute) {
        if (!ColorAt(limit).equals(joueur.getCouleur())) {
            if (ColorAt(limit).equals(Couleur.ROUGE) && execute) {
                joueur.capturerBilleRouge();
            } else if (execute) {
                joueur.capturerBilleAdversaire();
            }
            board(limit).clear();
        } else {
            // System.out.println("Le joueur ne peut pas sortir les billes de sa propre
            // couleur");
            return false;
        }
        return true;
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private boolean estDansLimite(int i, int j) {
        return i >= 0 && i < board.length && j >= 0 && j < board[i].length;
    }

    private boolean isTreated(int conf) {
        return treated_configs.contains(conf);
    }

    public void addObserver(Observer<Data> o) {
        elementObs.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Data> o : elementObs) {
            o.update(this);
        }
    }

    public Data getData() {
        return this;
    }

    @Override
    public boolean libre(int i, int j) {
        return board[i][j].estVide();
    }

    @Override
    public Bille obtenirBille(int i, int j) {
        return board[i][j].getBille();
    }

    public Observer<Data> getObserver() {
        return elementObs.get(0);
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
        int zobristHash = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board(i, j).estVide()) {
                    switch (board[i][j].getBille().getColor()) {
                        case ROUGE -> zobristHash ^= keys[0][i + board[i].length * j];
                        case NOIR -> zobristHash ^= keys[1][i + board[i].length * j];
                        case BLANC -> zobristHash ^= keys[2][i + board[i].length * j];
                    }
                }
            }
        }
        return zobristHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Board b))
            return false;
        return b.hashCode() == this.hashCode();
    }

    public boolean gameOver() {
        int red = 0, black = 0, white = 0;
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (!cell.estVide()) {
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

    public Collection<Mouvement> getAllPossibleMoves(Joueur... joueurs) {
        List<Mouvement> allMoves = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (!board(i, j).estVide()) {
                        Couleur c = board(i, j).getBille().getColor();
                        if (joueur.getCouleur() == c) {
                            Mouvement mouvement1 = new Mouvement(new Position(i, j), Direction.EST);
                            Mouvement mouvement2 = new Mouvement(new Position(i, j), Direction.OUEST);
                            Mouvement mouvement3 = new Mouvement(new Position(i, j), Direction.NORD);
                            Mouvement mouvement4 = new Mouvement(new Position(i, j), Direction.SUD);
                            boolean isLegal = this.update(mouvement1, joueur, false).isLegal();
                            if (isLegal)
                                allMoves.add(mouvement1);
                            isLegal = this.update(mouvement2, joueur, false).isLegal();
                            if (isLegal)
                                allMoves.add(mouvement2);
                            isLegal = this.update(mouvement3, joueur, false).isLegal();
                            if (isLegal)
                                allMoves.add(mouvement3);
                            isLegal = this.update(mouvement4, joueur, false).isLegal();
                            if (isLegal)
                                allMoves.add(mouvement4);
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    public boolean isFrontier(int i, int j) {
        assert (!board(i, j).estVide() && board(i, j).getBille().getColor() != Couleur.ROUGE);
        if (estDansLimite(i - 1, j) && board(i - 1, j).estVide())
            return true;
        if (estDansLimite(i, j - 1) && board(i, j - 1).estVide())
            return true;
        if (estDansLimite(i + 1, j) && board(i + 1, j).estVide())
            return true;
        if (estDansLimite(i, j + 1) && board(i, j + 1).estVide())
            return true;
        return false;
    }

    public boolean inFrontOfRed(int i, int j) {
        assert (!board(i, j).estVide() && board(i, j).getBille().getColor() != Couleur.ROUGE);
        if (estDansLimite(i - 1, j) && !board(i - 1, j).estVide()
                && board(i - 1, j).getBille().getColor() == Couleur.ROUGE)
            return true;
        if (estDansLimite(i, j - 1) && !board(i, j - 1).estVide()
                && board(i, j - 1).getBille().getColor() == Couleur.ROUGE)
            return true;
        if (estDansLimite(i + 1, j) && !board(i + 1, j).estVide()
                && board(i + 1, j).getBille().getColor() == Couleur.ROUGE)
            return true;
        if (estDansLimite(i, j + 1) && !board(i, j + 1).estVide()
                && board(i, j + 1).getBille().getColor() == Couleur.ROUGE)
            return true;
        return false;
    }
}
