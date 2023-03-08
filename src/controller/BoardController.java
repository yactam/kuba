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
import javax.swing.JFrame;

public class BoardController extends JFrame{
    private Board boardModel;
    private BoardView boardView;
    private boolean in , controllPlayer;
    private Position coordinates;
    private Position coordinates2;
    private ArrayList<Joueur> joueurs;
    private Joueur currentJoueur;
  
    public BoardController(Board board){
        this.boardModel = board;
        this.boardView = new BoardView(boardModel);
        initController();
        link();
        initJoueur();
        testEnterPanel();
    }

    public void initJoueur(){
        joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Joueur 1",Couleur.BLANC,8*boardModel.getN()-8));
        joueurs.add(new Joueur("Joueur 2",Couleur.NOIR,8*boardModel.getN()-8));
        currentJoueur = joueurs.get(0);
    }

    public void initController(){
        this.setTitle("Plateau Kuba");
        this.add(boardView);
        this.pack();
        this.show();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void link(){
        boardModel.addObserver(boardView);
        boardModel.initBoard();
    }

    public void testEnterPanel(){
        boardView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boardView.contains(e.getPoint())) {
                    in=true;
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                in=false;
            }
        
            @Override
            public void mouseClicked(MouseEvent e) {
                if(in){
                    coordinates = positionConvert(e.getPoint()); 
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if(in){
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
