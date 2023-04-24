package com.kuba.model.player;

import com.kuba.model.plateau.Couleur;
import com.kuba.model.plateau.*;
import com.kuba.model.mouvement.*;

public class Joueur {

    private final String nom;
    private Score score;
    private final Couleur couleur;


    public Joueur(String nom, Couleur couleur) {
        this.nom = nom;
        this.score = new Score(0, 0);
        this.couleur = couleur;
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
    public int getNbAdversaireCapturee() {
        return score.getAdversaire();
    }
    public void setNbAdversaireCapturee(int a) {
        score.setAdversaire(a);
    }

    public void setNbRougesCapturee(int r) {
        score.setRouges(r);
    }

    public int getNbBilleRougeCapturee() {
        return score.getRouges();
    }

    public int getScore() {
        return getNbBilleRougeCapturee() + getNbAdversaireCapturee();
    }

    public MoveStatus move(Board board, Position pos, Direction dir){
        return board.update(new Mouvement(pos, dir), this);
    }

    public MoveStatus move(Board board, Mouvement mouvement) {
        return board.update(mouvement, this);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Joueur joueur)) return false;
        return this.nom.equals(joueur.nom) && this.getNbBilleRougeCapturee() == joueur.getNbBilleRougeCapturee() && this.getNbAdversaireCapturee() == joueur.getNbAdversaireCapturee();
    }
}

