package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.plateau.Board;
import model.Joueur;

public class MatchView extends JPanel{
    Joueur j1, j2;
    PlayerView p1, p2;
    Board plateau;

    public MatchView(int N, Joueur j1, Joueur j2){
        plateau = new Board(N);
        j1.setBoard(plateau);
        j2.setBoard(plateau);
        plateau.initBoard();
        System.out.println(plateau);
        plateau.addJoueur(j1);
        plateau.addJoueur(j2);
        plateau.setBorder(new EmptyBorder(0, 0, getHeight(), getHeight()));
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        JPanel players = new JPanel(new GridLayout(2,1));
        players.add(p1);
        players.add(p2);
        this.add(plateau, BorderLayout.WEST);
        this.add(players);
    }

    public void keyPressed(int code){
        plateau.keyPressed(code);
    }

    public void update(){
        p1.update();
        p2.update();
    }
    
}
