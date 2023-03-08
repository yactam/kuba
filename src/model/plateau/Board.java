package model.plateau;

import java.util.*;
import observerpattern.*;
import observerpattern.Observer;
import model.Bille;
import model.Couleur;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Board extends JPanel implements SubjectObserver {
    private final Cell[][] board;
    private static Long[][] keys;
    private final int n;
    private final Set<Integer> treated_confs;
    private ArrayList<Observer> elementObs;
    private static int size = 50;

    public Board(int n) {
        setBackground(Color.red);
        this.treated_confs = new HashSet<>();
        this.n = n;
        int k = 4 * n - 1;
        board = new Cell[k][k];
        keys  = new Long[3][k*k];
        elementObs = new ArrayList<>();
        initKeys();
        setPreferredSize(new Dimension(k*size, k*size));
        setLayout(new GridLayout(k, k));
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

    public boolean estVide(Position position) {
        return board(position).estVide();
    }

    private void updatePosition(Position prev, Position next) {
        Bille bille = board(prev).getBille();
        board(prev).clear();
        board(next).setBille(bille);
    }




    public void update(Position pos, Direction dir, Joueur joueur) {
        // Le joueur ne peut bouger que les billes de sa propre couleur
        if(!ColorAt(pos).equals(joueur.getCouleur())) {
            System.out.println("Le joueur ne peut bouger que les billes de sa propre couleur");
            return;
        }

        // Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger
        if(estDansLimite(pos.prev(dir)) && !estVide(pos.prev(dir))) {
            System.out.println(pos.prev(dir));
            System.out.println("Mouvement pas valide il y a une bille avant la bille que le joueur veut bouger");
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
            treated_confs.add(hash_code);
        }

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
    public Cell[][] getCellBoard(){
    	return this.board;
    }

    @Override
    public void addObserver(Observer o) {
        elementObs.add(o);
    }

    @Override
    public void notifyObservers(){
        for(Observer o : elementObs) {
            o.update(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Board)) return false;
        return o.hashCode() == this.hashCode();
    }

    public void save(String path){
        try{
            FileOutputStream fileOutputStream
                = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream 
            = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Board load(String path){
        try{
            FileInputStream fileInputStream
                = new FileInputStream(path);
            ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
            Board b = (Board) objectInputStream.readObject();
            objectInputStream.close();
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(!board(j, i).estVide()) {
                    add(board(i, j).getBille());
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