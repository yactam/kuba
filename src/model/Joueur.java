package model;

import model.plateau.*;
import observerpattern.Observer;
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

    public boolean move(Board board, Position pos, Direction dir){
        return board.update(pos, dir, this);
    }

    @Override
    public void update(Object obj) {
        int code = (int) obj;
        int i = chosedCell.getPos().getI(), j = chosedCell.getPos().getJ();
        if (chosedCell != null){
            System.out.println("moving cell at x="+i+
                        ",y="+j);  
            switch(code){
                case 37:
                    if (move(board, chosedCell.getPos(), Direction.OUEST)) 
                        chosedCell = board.board(i, j-1);
                break;
                case 38:
                    if (move(board, chosedCell.getPos(), Direction.NORD))
                        chosedCell = board.board(i-1, j);
                break;
                case 39:
                    if (move(board, chosedCell.getPos(), Direction.EST))
                        chosedCell = board.board(i, j+1);
                break;
                case 40:
                    if (move(board, chosedCell.getPos(), Direction.SUD))
                        chosedCell = board.board(+1, j);
                break;
                default:return;
            }
        }
    }

    @Override
    public void notifySubject(Observer b) {
        return;
    }

    public void notify(Cell c){
        chosedCell = c;
    }

}