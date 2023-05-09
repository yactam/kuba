package com.kuba.model.plateau;

import java.io.*;


public class Bille implements Cloneable, Serializable{
    private Couleur color;
    public Bille(Couleur c){
        color = c;
    }
    public Couleur getColor() { return color; }


    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "N";
    }

    @Override
    public Object clone(){
        Bille b = new Bille(color);
        return b;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(color); 
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = (Couleur) in.readObject();
    }


}
