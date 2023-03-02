package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import model.Couleur;
import model.plateau.Board;
import model.Joueur;

public class View extends JFrame {
    static View accessor;
    //BufferedImage background;
    JComboBox<Integer> boardSizes;
    JTextField playerOne = new JTextField("Joueur 1");
    JTextField playerTwo = new JTextField("Joueur 2");
    JButton start;
    
    public View(){
        this.setSize(1200,900);
        this.setTitle("Kuba");
        accessor = this;
        JPanel menu = new JPanel(null);
        
        Integer[] choices = {1, 2, 3, 4, 5};
        boardSizes = new JComboBox<Integer>(choices);
        boardSizes.setSelectedIndex(1);
        
        start = new JButton("Lancer");
        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int N = boardSizes.getSelectedIndex()+1;
                JPanel match = new MatchView(N,
                new Joueur(playerOne.getText(), Couleur.BLANC, N*N*2),
                new Joueur(playerTwo.getText(), Couleur.NOIR, N*N*2));
                JFrame frame = View.accessor;
                frame.setContentPane(match);   
                frame.invalidate();
                frame.validate();   
            } 
            
        });

        playerOne.setBounds(180,200,200,50);
        playerTwo.setBounds(820,200,200,50);
        start.setBounds(500,325,200,100);
        boardSizes.setBounds(560,250,80,50);
        
        menu.add(playerOne);
        menu.add(boardSizes);
        menu.add(playerTwo);
        menu.add(start);

        this.setContentPane(menu);
    }
}
