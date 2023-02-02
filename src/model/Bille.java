package model;

import model.mouvement.*;;

public class Bille {
    private Position pos;
    private final Couleur color;

    public Bille(Couleur c, Position p){
        color = c;
        pos = p;
    }

    public Couleur getColor() { return color; }
    public Position getPos() { return pos; }

    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "B";
    }

}
