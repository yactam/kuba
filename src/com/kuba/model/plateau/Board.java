package com.kuba.model.plateau;

import com.kuba.model.mouvement.MoveStatus;
import com.kuba.observerpattern.*;
import com.kuba.observerpattern.Observable;
import com.kuba.observerpattern.Observer;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Position;
import com.kuba.model.player.Joueur;

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
    }

    public void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
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
        int count = 1, spaces, k = 4 * n - 1;
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

    // TODO remove after testing, c'est juste une methode pour pouvoir initialiser les tests
    public void initCell(int i, int j) {
        board[i][j] = new Cell();
    }

    public Cell board(int i, int j) {
        return board[i][j];
    }

    public Couleur ColorAt(Position pos) {
        return board(pos).getBille().getColor();
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
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE,
                    "Le joueur ne peut bouger que les billes de sa propre couleur");
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        if (estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
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
            return new MoveStatus(MoveStatus.Status.INVALID_MOVE, "KO");
        } else {
            transitionBoard.treated_configs.add(hash_code);
            if (execute) {
                this.board = transitionBoard.board;
                this.treated_configs = transitionBoard.treated_configs;
            }
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
                res.board[i][j] = new Cell();
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
            return false;
        }
        return true;
    }

    private boolean estDansLimite(Position position) {
        return estDansLimite(position.getI(), position.getJ());
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
                    zobristHash ^= keys[2 - board(i,j).getBille().getColor().ordinal()][i + board[i].length * j];
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

    public boolean gameOver(Joueur player, Joueur opponent) {
        int n = (this.size() + 1) / 4;
        int nbBillesRouges = 8 * n * n - 12 * n + 5;
        if(player.getNbBilleRougeCaptured() > nbBillesRouges/2) return true;
        if(opponent.getNbBilleRougeCaptured() > nbBillesRouges/2) return true;
        if(player.getNbAdversaireCaptured() == 2 * n * n) return true;
        if(opponent.getNbAdversaireCaptured() == 2 * n * n) return true;
        return false;
    }

    public Joueur getWinner(Joueur player, Joueur opponent) {
        int n = (this.size() + 1) / 4;
        int nbBillesRouges = 8 * n * n - 12 * n + 5;
        if(player.getNbBilleRougeCaptured() > nbBillesRouges/2) return player;
        if(opponent.getNbBilleRougeCaptured() > nbBillesRouges/2) return opponent;
        if(player.getNbAdversaireCaptured() == 2 * n * n) return player;
        if(opponent.getNbAdversaireCaptured() == 2 * n * n) return opponent;
        return null;
    }

    public Collection<Mouvement> getAllPossibleMoves(Joueur... joueurs) {
        List<Mouvement> allMoves = new ArrayList<>();
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (!board(i, j).estVide() && board(i, j).getBille().getColor() == joueur.getCouleur()) {
                            for(Direction dir : Direction.values()) {
                                Mouvement mouvement = new Mouvement(new Position(i, j), dir);
                                boolean isLegal = this.update(mouvement, joueur, false).isLegal();
                                if (isLegal) allMoves.add(mouvement);
                            }
                    }
                }
            }
        }
        return allMoves;
    }
}
