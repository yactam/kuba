package com.kuba.model.player;

import com.kuba.model.plateau.Couleur;
import com.kuba.model.plateau.*;
import com.kuba.model.mouvement.*;

public class Joueur {

    private final String nom;
    private Score score;
    private final Couleur couleur;


    public Joueur(String nom, Couleur couleur, int nbBille) {
        this.nom = nom;
        this.score = new Score(0, nbBille);
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
    public int getNbAdversaireCapture() {
        return score.getAdversaire();
    }

    public int getNbBilleRougeCapturer() {
        return score.getRouges();
    }

    public Board move(Board board, Position pos, Direction dir){
        return board.update(new Mouvement(pos, dir), this.couleur);
    }
}

