package com.kuba.model.plateau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.kuba.vue.BilleAnimateView;

public class Bille implements Cloneable, Serializable{
    private Couleur color;
    private BilleAnimateView bv;

    public Bille(Couleur c){
        color = c;
    }

    public void setBV(BilleAnimateView bv){
        this.bv=bv;
    }

    public BilleAnimateView getBV(){
        return bv;
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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(color); 
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = (Couleur) in.readObject();
    }


}
