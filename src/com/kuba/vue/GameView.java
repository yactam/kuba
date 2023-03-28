package com.kuba.vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;

public class GameView extends JPanel {
    private PlayerView p1, p2;
    private BoardView boardView;
    private JButton leave, confirm, undo, redo;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public GameView(int N, Joueur j1, Joueur j2) {
        setLayout(null);
        setSize(screenSize);
        setPreferredSize(screenSize);
        setBackground(new Color(0,0,0,0));
        Board plateau = new Board(N);
        boardView = new BoardView(plateau);
        new GameController(plateau, j1, j2);
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

            } 
        });

        boardView.setLocation(0, 0);
        //boardView.setSize(new Dimension((int) (0.8 * getWidth()),getHeight()));
        //boardView.setPreferredSize(new Dimension((int) (0.8 * getWidth()),getHeight()));
        //boardView.setBounds(0, 0, (int) (0.8 * getWidth()),getHeight());
        p1.setBounds(648, 123, 275, 165);
        p2.setBounds(648, 287, 275, 165);
        undo.setBounds(680, 475, 55, 55);
        redo.setBounds(840, 475, 55, 55);
        confirm.setBounds(760, 475, 55, 55);
        leave.setBounds(720, 550, 55, 55);

        add(boardView);
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
