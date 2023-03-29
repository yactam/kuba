package com.kuba.model.plateau;

public class Cell implements Cloneable{

    private Bille bille;
    private int x,y;
    private Board board;

    public Cell(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

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
        c.bille = (bille != null) ? (Bille) bille.clone() : null;
        return c;
    }

    public boolean contains(int x, int y){
        if (estVide()) return false;
        if (x >= bille.getX() && x <= bille.getX()+Bille.width
                && y >= bille.getY() && y <= bille.getY()+Bille.width){
            return true;
        }
        return false;
    }

}
