package controller;
import model.plateau.Board;
import model.mouvement.*;
import view.BoardView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import model.Joueur;
import model.Couleur;
import javax.swing.*;

public class BoardController {
    private Board boardModel;
    private boolean controllPlayer;
    private Position coordinates;
    private Position coordinates2;
    
    private int startX;
    private int startY;
    private boolean verif;

    private ArrayList<Joueur> joueurs;
    private Joueur currentJoueur;

    // Les éléments de controlle
    private KeyHandler key;
    private MouseAdapter mouse;

    public BoardController(Board board){
        this.boardModel = board;
        key = new KeyHandler();
        initJoueur();
    }

    public void initJoueur(){
        joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Joueur 1",Couleur.BLANC,8*boardModel.getN()-8));
        joueurs.add(new Joueur("Joueur 2",Couleur.NOIR,8*boardModel.getN()-8));
        currentJoueur = joueurs.get(0);
    }

    public JPanel getObserver(){
        return (JPanel)boardModel.elementObs.get(0);
    } 

    public void testEnterPanel(){
        mouse = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if(getObserver().contains(e.getPoint())){
                    coordinates = positionConvert(e.getPoint()); 
                    verif=true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {   
                if(getObserver().contains(e.getPoint())){
                   coordinates2 = positionConvert(e.getPoint());
                    if(coordinates!=null){
                    Direction d = coordinates.nextDir(coordinates2);
                    try{
                     if(d!=null && coordinates!=null){ 
                        boardModel.update(coordinates, d, currentJoueur);
                        controllPlayer=boardModel.move;
                        if(controllPlayer){
                            changePlayer();
                        }
                    }
                    }
                    catch(Exception exception){
                        System.out.print("_");
                    }
                }
            }
            }
        };

        this.getObserver().addMouseListener(mouse);
    }

    public Position positionConvert(Point a){
        // Calcul selon la taille des billes
        int x = (int)a.getX()/BoardView.billeWidth;
        int y = (int)a.getY()/BoardView.billeWidth;
        return new Position(y,x);
    }

    public void changePlayer(){
        if(joueurs.get(0).equals(currentJoueur)){
            currentJoueur = joueurs.get(1);
        }else{
            currentJoueur = joueurs.get(0);
        }
    }

    public void deplacement(Direction d){
        try{
            if(d!=null && coordinates!=null){ 
                boardModel.update(coordinates, d, currentJoueur);
                controllPlayer=boardModel.move;
                if(controllPlayer){
                    changePlayer();
                }
                else{
                    coordinates=coordinates.next(d);
                }
            }
        }
        catch(Exception e){
            System.out.println("--------------------------");
        }
    }

    public class KeyHandler implements KeyListener{
		@Override
	    public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {
            try{
            Direction d;
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP :  
                d=Direction.NORD; 
                deplacement(d);
			break;
			case KeyEvent.VK_LEFT :  
                d= Direction.OUEST;
                deplacement(d);
                break;
			case KeyEvent.VK_RIGHT:  
			    d= Direction.EST;
                deplacement(d);
			    break;
			case KeyEvent.VK_DOWN :  
                d= Direction.SUD;
                deplacement(d); 
			    break;
            }
            }catch(Exception ex){
                System.out.println("^");
         	}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
    }

    public void removeKey(BoardView bv){
        bv.removeKeyListener(key);
    }
    public void removeMouse(BoardView bv){
        bv.removeMouseListener(mouse);
    }

    public KeyHandler getKey(){
        return key;
    }

}
