package model;

public class Bille {
    private Position pos;
    private Couleur color;

    public Bille(Couleur c, Position p){
        color = c;
        pos = p;
    }

    public Couleur getColor() { return color; }
    public Position getPos() { return pos; }


}