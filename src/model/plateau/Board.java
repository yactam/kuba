package model.plateau;
import observerpattern.Observer;
import observerpattern.Data;
import observerpattern.Observable;
import java.util.*;

import model.Bille;
import model.Couleur;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;


public class Board implements Observable<Data>,Data{
    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;
    private final Set<Integer> treated_confs;
    private ArrayList<Observer<Data>> elementObs;
    private Data dataBoard;
    public boolean move,moveS;

    public Board(int n) {
        this.treated_confs = new HashSet<>();
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        elementObs = new ArrayList<Observer<Data>>();
        dataBoard=this;
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
        this.notifyObservers();
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

    @Override
    public int size() {
        return board.length;
    }

    public Cell board(Position pos) {
        return board[pos.getI()][pos.getJ()];
    }
    
    @Override
    public Cell board(int i, int j) {
        return board[i][j];
    }

    public Couleur ColorAt(Position pos) throws NullPointerException{
        if(pos==null) throw new NullPointerException("Pos est null");
        return board(pos).getBille().getColor();
    }

    public Couleur ColorAt(int i, int j) {
        return board(i, j).getBille().getColor();
    }

    public boolean estVide(Position position) {
        return board(position).estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board(prev).getBille();
        board(prev).clear();
        board(next).setBille(bille);
    }

    public void update(Position pos, Direction dir, Joueur joueur) {
        if(pos==null || dir==null || joueur==null){
            move=false;
            return;
        }
        // Le joueur ne peut bouger que les billes de sa propre couleur
        if(!ColorAt(pos).equals(joueur.getCouleur())) {
            System.out.println("Le joueur ne peut bouger que les billes de sa propre couleur");
            move=false;
            return;
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        if(estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
            System.out.println(pos.prev(dir));
            System.out.println("Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger");
            move=false;
            return;
        }

        Position next = pos.next(dir); // Trouver la limite
        while(estDansLimite(next) && !estVide(next)) { // avec next tant que c'est possible
            next = next.next(dir);
        }
        Position fin = next;  // Pour revenir en arriere si le mouvement n'est pas valid (un curseur backup)
        if(!estDansLimite(next)) { // là, il faut sortir les billes
            Position limit = next.prev(dir); // La limite du tableau
            moveOut(limit, joueur);
            fin = limit;
            next = next.prev(dir);
            moveS=true;
        }
        else{
            moveS=false;
        }

        while (!next.equals(pos) && board(next).estVide()) { // Décaler les pions
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
            if(moveS){move=false;}
            else{move=true;}
            treated_confs.add(hash_code);
        }
        dataBoard=this;
        this.notifyObservers();
    }

    private void moveOut(Position limit, Joueur joueur) {
        if(!ColorAt(limit).equals(joueur.getCouleur())) {
            if(ColorAt(limit).equals(Couleur.ROUGE)) {
                joueur.capturerBilleRouge();
            } else {
                joueur.capturerBilleAdversaire();
            }
            board(limit).clear();
        } else {
            System.out.println("Le joueur ne peut pas sortir les billes de sa propre couleur");
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
                        case ROUGE : zobristHash ^= keys[0][i + board[i].length * j];
                        case NOIR : zobristHash  ^= keys[1][i + board[i].length * j];
                        case BLANC : zobristHash ^= keys[2][i + board[i].length * j];
                    }
                }
            }
        }
        return (int) zobristHash;
    }
    
    public int getN() {
    	return this.n;
    }

    @Override
    public Cell[][] getCellBoard(){
    	return this.board;
    }


    @Override
    public void addObserver(Observer<Data> o) {
        elementObs.add(o);
    }

    @Override
    public void notifyObservers(){
        for(Observer<Data> o : elementObs) {
            o.update(dataBoard);
        }
    }

    public Data getData(){
        return this.dataBoard;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }
}
