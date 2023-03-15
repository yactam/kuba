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
    JComboBox<String> boardSizes;
    JTextField playerOne = new JTextField("Joueur 1");
    JTextField playerTwo = new JTextField("Joueur 2");
    JButton start;
    JPanel menu, match;
    JLabel title, background;
    
    public View(){
        setSize(1200,900);
        setTitle("Kuba");
        setLayout(null);
        accessor = this;
        menu = new JPanel(null);
        menu.setSize(1200,900);
        
        ImageIcon img1 = new ImageIcon("../resources/main_title.png");
        title = new JLabel(img1, JLabel.CENTER);
        ImageIcon img2 = new ImageIcon("../resources/background.png");
        background = new JLabel(img2, JLabel.CENTER);

        String[] choices = {"3x3", "7x7", "11x11", "15x15", "19x19"};
        boardSizes = new JComboBox<String>(choices);
        boardSizes.setSelectedIndex(1);
        
        start = new JButton("Lancer");
        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int N = boardSizes.getSelectedIndex()+1;
                match = new MatchView(N,
                new Joueur(playerOne.getText(), Couleur.BLANC, N*N*2),
                new Joueur(playerTwo.getText(), Couleur.NOIR, N*N*2));
                JFrame frame = View.accessor;
                frame.remove(match);
                frame.add(match);
            } 
            
        });

        title.setBounds(0,0,1200,900);
        background.setBounds(0,0,1200,900);

        playerOne.setBounds(180,200,200,50);
        playerTwo.setBounds(820,200,200,50);
        start.setBounds(500,325,200,100);
        boardSizes.setBounds(560,250,80,50);
        
        menu.add(title);
        menu.add(playerOne);
        menu.add(boardSizes);
        menu.add(playerTwo);
        menu.add(start);

        setContentPane(menu);

    }

    public void restart(){
        this.remove(match);
        this.add(menu);
    }
}
