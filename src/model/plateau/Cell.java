package model.plateau;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import model.Bille;
import model.Joueur;
import model.mouvement.Position;
import observerpattern.Observer;

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

    void setBille(Bille bille) {
        this.bille = bille;
    }

    public Bille getBille() {
        if (bille != null) return (Bille) bille.clone();
        else return null;
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
    public void mouseClicked(MouseEvent e) {
       return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        notifySubject(board.getJoueur());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    @Override
    public void update(Object obj) {
        return;
    }

    @Override
    public void notifySubject(Observer ob) {
        Joueur joueur = (Joueur) ob;
        joueur.notify(this);
    }

    public Position getPos(){
        return new Position(x, y);
    }

}