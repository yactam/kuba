package model;

import javax.swing.text.Position;

public class Bille {
    private Position pos;
    private final Couleur color;

    public Bille(Couleur c, Position p){
        color = c;
        pos = p;
    }

    public Couleur getColor() { return color; }
    public Position getPos() { return pos; }


}