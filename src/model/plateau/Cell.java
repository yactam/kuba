package model.plateau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import model.Bille;

class Cell extends JLabel implements Cloneable {

    private Bille bille;

    void setBille(Bille bille) {
        setBackground(Color.LIGHT_GRAY);
        this.bille = bille;
        setPreferredSize(new Dimension(Bille.width, Bille.width));
    }

    public Bille getBille() {
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

    @Override
    public void paintComponent(Graphics g) {
        if (!estVide()){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(bille.image(), 0, 0, Bille.width, Bille.width, null);
        }
    }

}
