package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.plateau.Board;
import model.Joueur;

public class MatchView extends JPanel{
    Joueur j1, j2;
    PlayerView p1, p2;
    Board plateau;
    JButton leave;

    public MatchView(int N, Joueur j1, Joueur j2){
        this.setLayout(new BorderLayout());
        plateau = new Board(N);
        plateau.initBoard();
        //plateau.setBorder(new EmptyBorder(0, 0, getHeight(), getHeight()));
        this.add(plateau, BorderLayout.WEST);
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        JPanel players = new JPanel(new BorderLayout());
        p1.setSize(getPreferredSize());
        p2.setSize(getPreferredSize());
        players.add(p1,BorderLayout.NORTH);
        players.add(p2,BorderLayout.CENTER);
        leave = new JButton("Quitter");
        leave.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View.accessor.restart();
            } 
        });
        players.add(leave, BorderLayout.SOUTH);
        this.add(players, BorderLayout.EAST);
    }

    public void update(){
        p1.update();
        p2.update();
    }
    
}
