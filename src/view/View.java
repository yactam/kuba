package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import model.Couleur;
import model.Joueur;

public class View extends JFrame {
    static View accessor;
    JComboBox<String> boardSizes;
    JTextField playerOne = new JTextField("Joueur 1");
    JTextField playerTwo = new JTextField("Joueur 2");
    JCheckBox botOne, botTwo;
    JButton start;
    JLabel menu, game;
    JPanel match;
    
    public View() throws IOException {
        setSize(1000,735);
        setTitle("Kuba");
        setLayout(null);
        
        accessor = this;
        menu = new JLabel("",new ImageIcon("src/resources/main_title.png"), JLabel.CENTER);
        game = new JLabel("",new ImageIcon("src/resources/background.png"), JLabel.CENTER);
        

        String[] choices = {"3x3", "7x7", "11x11", "15x15", "19x19"};
        boardSizes = new JComboBox<String>(choices);
        boardSizes.setSelectedIndex(1);

        botOne = new JCheckBox("Utiliser un bot");
        botOne.setForeground(Color.WHITE);
        botOne.setBackground(new Color(0,0,0,100));
        botTwo = new JCheckBox("Utiliser un bot");
        botTwo.setForeground(Color.WHITE);
        botTwo.setBackground(new Color(0,0,0,100));

        playerOne.setBackground(new Color(255,255,255,70));
        playerOne.setFont(new Font("Arial", Font.BOLD, 18));
        playerTwo.setBackground(new Color(255,255,255,70));
        playerTwo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel text = new JLabel("Taille du plateau");
        text.setFont(new Font("Arial", Font.BOLD, 18));
        text.setForeground(Color.white);
        text.setBackground(new Color(0,0,0,70));
;        
        start = new JButton("Lancer");
        start.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int N = boardSizes.getSelectedIndex()+1;
                match = new MatchView(N,
                new Joueur(playerOne.getText(), Couleur.BLANC, N*N*2),
                new Joueur(playerTwo.getText(), Couleur.NOIR, N*N*2));
                JFrame frame = View.accessor;
                match.setBounds(50,50,930,630);
                game.add(match);
                frame.setContentPane(game);
                frame.invalidate();
                frame.validate();
            } 
            
        });

        playerOne.setBounds(125,375,200,50);
        playerTwo.setBounds(675,375,200,50);
        botOne.setBounds(130, 430, 130, 30);
        botTwo.setBounds(680, 430, 130, 30);
        start.setBounds(445,575,130,60);
        text.setBounds(375, 510, 175, 30);
        boardSizes.setBounds(575,500,80,50);
        
        menu.add(playerOne);
        menu.add(boardSizes);
        menu.add(playerTwo);
        menu.add(start);
        menu.add(botOne);
        menu.add(botTwo);
        menu.add(text);

        setContentPane(menu);

    }

    public void restart(){
        this.setContentPane(menu);
        game = new JLabel("",new ImageIcon("src/resources/background.png"), JLabel.CENTER);
        match = null;
    }
}
