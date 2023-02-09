package model;

import model.mouvement.*;;

public class Bille implements Cloneable{

    private Position pos;
    private final Couleur color;

    public Bille(Couleur c, Position p){
        color = c;
        pos = p;
    }

    public void setPosition(Position p){
        this.pos = p;
    }

    public Couleur getColor() { return color; }
    public Position getPos() { return pos; }

    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "N";
    }

    @Override
    public Object clone(){
        Bille b = null;
        try{
            b = (Bille) super.clone();
        }catch(Exception e){
            e.printStackTrace();
        }
        b.pos = (Position) pos.clone();
        return b;
    }

}
