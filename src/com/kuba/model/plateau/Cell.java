package com.kuba.model.plateau;

public class Cell implements Cloneable{

    private Bille bille;
    private int x,y;
    private Board board;

    public void setBille(Bille bille) {
        if(this.bille == null) this.bille = new Bille(bille.getColor());
        else this.bille = bille;
    }

    public Bille getBille() throws NullPointerException{
        return (Bille) bille.clone();
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
        c.bille = (bille != null) ? (Bille) bille.clone() : null;
        return c;
    }

}
