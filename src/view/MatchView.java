package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.plateau.Board;
import model.Joueur;

public class MatchView extends JPanel {
    Joueur j1, j2;
    PlayerView p1, p2;
    Board plateau;
    JButton leave, confirm, undo, redo;

    public MatchView(int N, Joueur j1, Joueur j2) {
        setLayout(null);
        setBackground(new Color(0,0,0,0));
        plateau = new Board(N);
        plateau.initBoard();
        //plateau.setBorder(new EmptyBorder(0, 0, getHeight(), getHeight()));
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        undo = new JButton(new ImageIcon("src/resources/undo.png"));
        undo.setBackground(new Color(0,0,0,0));
        undo.setBorderPainted(false);
        redo = new JButton(new ImageIcon("src/resources/redo.png"));
        redo.setBackground(new Color(0,0,0,0));
        redo.setBorderPainted(false);
        confirm = new JButton(new ImageIcon("src/resources/confirm.png"));
        confirm.setBackground(new Color(0,0,0,0));
        confirm.setBorderPainted(false);
        leave = new JButton(new ImageIcon("src/resources/return.png"));
        leave.setBackground(new Color(0, 0, 0, 0));
        leave.setBorderPainted(false);
        leave.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View.accessor.restart();
            } 
        });

        plateau.setBounds(0,0,598,598);
        p1.setBounds(648, 123, 275, 165);
        p2.setBounds(648, 287, 275, 165);
        undo.setBounds(680, 475, 55, 55);
        redo.setBounds(840, 475, 55, 55);
        confirm.setBounds(760, 475, 55, 55);
        leave.setBounds(720, 550, 55, 55);

        add(plateau);
        add(p1);
        add(p2);
        add(leave);
        add(confirm);
        add(undo);
        add(redo);
        p1.setBackground(new Color(15,5,0,50));
    }

    public void update(){
        p1.update();
        p2.update();
    }
    
}
