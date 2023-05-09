package com.kuba.model.player;

import com.kuba.model.plateau.Couleur;
import com.kuba.model.plateau.*;
import com.kuba.model.mouvement.*;

public class Joueur {

    private final String nom;
    private final Score score;
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
        score.updateAdversaire();
    }

    @Override
    public String toString() {
        return "Joueur : " + this.nom + " Nombre de billes adversaires capturées : "
                + score.getAdversaire() + " Nombre de billes rouge capturé : "
                + score.getRouges() + " Couleur : " + couleur.toString();
    }

    public String getNom() {
        return nom;
    }
    public Couleur getCouleur() {
        return couleur;
    }
    public int getNbAdversaireCaptured() {
        return score.getAdversaire();
    }
    void setNbAdversaireCaptured(int a) {
        score.setAdversaire(a);
    }

    void setNbRougesCaptured(int r) {
        score.setRouges(r);
    }

    public int getNbBilleRougeCaptured() {
        return score.getRouges();
    }

    public int getScore() {
        return 2 * getNbBilleRougeCaptured() + getNbAdversaireCaptured();
    }
    public void resetScore() {
        this.setNbAdversaireCaptured(0);
        this.setNbRougesCaptured(0);
    }

    public MoveStatus move(Board board, Mouvement mouvement) {
        return board.update(mouvement, this);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Joueur joueur)) return false;
        return this.nom.equals(joueur.nom) && this.getNbBilleRougeCaptured() == joueur.getNbBilleRougeCaptured() && this.getNbAdversaireCaptured() == joueur.getNbAdversaireCaptured();
    }
}

