package model.plateau;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import model.Bille;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import observerpattern.Observer;

public class Cell extends JLabel implements Cloneable, MouseMotionListener, MouseListener, Observer{

    private Board board;
    private Joueur currentJoueur;
    private Bille bille;
    private int i=0,j=0;
    private int clickedX, clickedY;
    private int x,y;

    public Cell(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setJoueur(Joueur joueur){
        this.currentJoueur = joueur;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Direction getMoveDirection(){
        if (i>0) return Direction.EST;
        else if (i<0) return Direction.OUEST;
        else if (j>0) return Direction.SUD;
        else return Direction.NORD;
    }

    void setBille(Bille bille) {
        setBackground(Color.red);
        this.bille = bille;
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
            graphics2D.drawImage(bille.image(), i, j,
                                                 Bille.width, Bille.width, null);
        }
    }
   

    @Override
    public void mouseDragged(MouseEvent e) {
        this.i = (e.getX() - clickedX);
        this.j = (e.getY() - clickedY);
        repaint();
        notifySubject(currentJoueur);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickedX = e.getX();
        clickedY = e.getY();
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
    public void update() {
        this.i = 0;
        this.j = 0;    
    }

    @Override
    public void notifySubject(Observer ob) {
        Joueur joueur = (Joueur) ob;
        joueur.notify(this, board);
    }

    public Position getPos(){
        return new Position(i, j);
    }

}
