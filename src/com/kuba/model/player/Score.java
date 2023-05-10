package com.kuba.model.player;

public class Score {

    private int nbBillesRouges;
    private int nbBillesAdversaire;

    public Score(int nbBillesRouges, int nbBillesAdversaire) {
        this.nbBillesRouges = nbBillesRouges;
        this.nbBillesAdversaire = nbBillesAdversaire;
    }

    public void updateRouges() {
        nbBillesRouges++;
    }

    public void updateAdversaire() {
        nbBillesAdversaire++;
    }

    public int getRouges() {
        return nbBillesRouges;
    }
    void setRouges(int r) {
        this.nbBillesRouges = r;
    }

    public int getAdversaire() {
        return nbBillesAdversaire;
    }
    void setAdversaire(int a) {
        this.nbBillesAdversaire = a;
    }
}
