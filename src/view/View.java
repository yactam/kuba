package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import model.plateau.Board;
import model.Joueur;

public class View extends JFrame {
    static View accessor;
    //ufferedImage background;
    JComboBox<Integer> boardSizes;
    JTextField playerOne = new JTextField("Joueur 1");
    JTextField playerTwo = new JTextField("Joueur 2");
    JButton start;
    Joueur j1, j2;
    Board plateau;
    
    public View(){
        this.setSize(1200,900);
        this.setTitle("Kuba");
        accessor = this;
        JPanel menu = new JPanel(null);
        JPanel match = new JPanel(new BorderLayout());
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        /*
        Integer[] choices = {1, 2, 3, 4, 5};
        boardSizes = new JComboBox<Integer>(choices);
        boardSizes.setSelectedIndex(1);
        */
        start = new JButton("Lancer");
        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board board = new Board(2);
                board.initBoard();
                match.add(board, BorderLayout.CENTER);
                //board.addObserver(this);
                JFrame frame = View.accessor;
                frame.setContentPane(match);   
                frame.invalidate();
                frame.validate();   
            } 
            
        });

        playerOne.setBounds(180,200,200,50);
        playerTwo.setBounds(820,200,200,50);
        start.setBounds(500,325,200,100);
        //boardSizes.setBounds(560,250,80,50);
        
        menu.add(playerOne);
        //menu.add(boardSizes);
        menu.add(playerTwo);
        menu.add(start);

        this.setContentPane(menu);
    }
}
