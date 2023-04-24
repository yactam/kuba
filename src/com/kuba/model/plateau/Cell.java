package com.kuba.model.plateau;


public class Cell extends JLabel implements Cloneable, MouseListener, Observer{

    private Board board;
    private Bille bille;
    private int x,y;

    public Cell(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
        addMouseListener(this);
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
}
