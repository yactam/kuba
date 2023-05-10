package com.kuba.model.plateau;


public class Cell implements Cloneable {

    private Bille bille;

    public void setBille(Bille bille) {
        this.bille = bille;
    }

    public Bille getBille() throws NullPointerException{
        return bille;
    }

    public boolean estVide() {
        return bille == null;
    }

    void clear() {
        bille = null;
    }

    @Override
    public String toString() {
        if(estVide()) return "_";
        return bille.toString();
    }

    @Override
    public Object clone(){
        Cell c = null;
        try{
            c = (Cell)super.clone();
        }catch(Exception e){
            e.printStackTrace();
        }
        assert c != null;
        c.bille = (bille != null) ? bille.copy() : null;
        return c;
    }
}
