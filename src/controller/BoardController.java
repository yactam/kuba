package controller;
import model.plateau.Board;
import model.mouvement.*;
import view.BoardView;

import java.awt.Color;
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
    private ArrayList<Joueur> joueurs;
    private Joueur currentJoueur;
  
    public BoardController(Board board){
        this.boardModel = board;
        initJoueur();
        testEnterPanel();
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
        this.getObserver().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(getObserver().contains(e.getPoint())){
                    coordinates = positionConvert(e.getPoint()); 
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
        });
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
}
