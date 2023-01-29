package model;

public class Bille {

    private final Couleur couleur;

    public Bille(Couleur couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        if(couleur.equals(Couleur.White)) return "W";
        else if (couleur.equals(Couleur.Red)) return "R";
        else return "B";
    }
}
