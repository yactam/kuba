package model.plateau;
import model.Bille;

public class Cell implements Cloneable{

    private Bille bille;

    void setBille(Bille bille) {
        this.bille = bille;
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
        c.bille = (bille != null) ? (Bille) bille.clone() : null;
        return c;
    }

}
