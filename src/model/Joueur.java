package model;

import model.plateau.*;
import observerpattern.Observer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.mouvement.*;

public class Joueur implements Observer{

    private Board board;
    private final String nom;
    private Score score;
    private final Couleur couleur;
    private Cell chosedCell;


    public Joueur(String nom, Couleur couleur, int nbBille) {
        this.nom = nom;
        this.score = new Score(0, nbBille);
        this.couleur = couleur;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void capturerBilleRouge() {
        score.updateRouges();
    }

    public void capturerBilleAdversaire() {
        this.score.updateAdversaire();
    }

    @Override
    public String toString() {
        return "Joueur : " + this.nom + " Nombre de billes adversaires capturées : " + this.score.getAdversaire() + " Nombre de billes rouge capturé : " + this.score.getRouges() + " Couleur : " + couleur.toString();
    }

    public String getNom() {
        return nom;
    }

    public Couleur getCouleur() {
        return couleur;
    }
    public int getNbAdversaireCapture() {
        return score.getAdversaire();
    }

    public int getNbBilleRougeCapturer() {
        return score.getRouges();
    }

    public void move(Board board, Position pos, Direction dir){
        board.update(pos, dir, this);
    }

    @Override
    public void update(Object obj) {
        int code = (int) obj;
        if (chosedCell != null){
            System.out.println("code : "+code);
            switch(code){
                case 37:
                    move(board, chosedCell.getPos(), Direction.OUEST);
                break;
                case 38:
                    move(board, chosedCell.getPos(), Direction.NORD);
                break;
                case 39:
                    move(board, chosedCell.getPos(), Direction.EST);
                break;
                case 40:
                    move(board, chosedCell.getPos(), Direction.SUD);
                break;
                default:return;
            }
        }
        chosedCell = null;
    }

    @Override
    public void notifySubject(Observer b) {
        return;
    }

    public void notify(Cell c){
        chosedCell = c;
    }

}