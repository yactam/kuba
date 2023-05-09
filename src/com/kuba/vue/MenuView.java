package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.IA;

import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*;


public class MenuView extends JPanel {
    Game game;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel background = new Background("src/resources/images/main_title.png", screenSize);
    String[] choices = {" 3x3 ", " 7x7 ", "11x11", "15x15", "19x19"};
    JTextField playerOne, playerTwo;
    JCheckBox botOne;
    JLabel text;
    JComboBox<String> boardSizes;
    JButton start, exit;
    public MenuView(Game g) {
        game = g;
        setPreferredSize(screenSize);
        setSize(screenSize);
        setLayout(null);

        initPlayers();
        initBot();
        initButtons();

        background.add(playerOne);
        background.add(playerTwo);
        background.add(botOne);
        background.add(boardSizes);
        background.add(start);
        background.add(exit);
        add(background);
        new MenuController();
    }

    private void initPlayers() {
        playerOne = new JTextField("Joueur 1");
        playerTwo = new JTextField("Joueur 2");
        stylePlayers();
    }

    private void stylePlayers() {
        // Réglage du TextField du joueur 1
        playerOne.setFont(new Font("Arial", Font.BOLD, (int)(getWidth()*0.014)+(int)(getHeight()*0.007)));
        playerOne.setBackground(new Color(0,0,0,40));
        playerOne.setBounds((int)(getWidth()*0.23), (int)(getHeight()*0.486), 
                            (int)(getWidth()*0.175), (int)(getHeight()*0.054));
        playerOne.setBorder(BorderFactory.createEmptyBorder());

        // Réglage du TextField du joueur 2
        playerTwo.setFont(new Font("Arial", Font.BOLD, (int)(getWidth()*0.014)+(int)(getHeight()*0.007)));
        playerTwo.setBackground(new Color(0,0,0,40));
        playerTwo.setBounds((int)(getWidth()*0.661), (int)(getHeight()*0.486), 
                            (int)(getWidth()*0.175), (int)(getHeight()*0.054));
        playerTwo.setBorder(BorderFactory.createEmptyBorder());
    }



    private void initBot() {
        botOne = new JCheckBox();
        botOne.setBackground(new Color(0,0,0,0));
        botOne.setBounds((int) (0.683 * getWidth()), (int) (0.561 * getHeight()), 
                         (int) (0.031*getWidth()), (int) (0.031*getWidth()));
    }

    private void initButtons() {
        boardSizes = new JComboBox<>(choices);
        start = new JButton(new ImageIcon("src/resources/images/launch.png"));
        exit = new JButton(new ImageIcon("src/resources/images/return.png"));
        boardSizes.setSelectedIndex(1);

        styleButtons();
    }

    private void styleButtons() {
        start.setFont(new Font("Arial", Font.BOLD, (int) (0.017*getWidth())));
        boardSizes.setBounds((int)(0.573*getWidth()), (int)(0.654*getHeight()), (int)(0.056*getWidth()), (int)(0.066*getHeight()));
        start.setBounds((int) (0.465 * getWidth()), (int) (0.814 * getHeight()), 130, 60);
        exit.setBounds((int) (0.92 * getWidth()), (int) (0.036 * getHeight()), 50, 50);

        start.setBorderPainted(false);
        start.setBackground(new Color(0,0,0,0));
        exit.setBorderPainted(false);
        exit.setBackground(new Color(0,0,0,0));
    }

    private class MenuController extends MouseAdapter {

        public MenuController() {
            start.addActionListener(e -> {
                int i = boardSizes.getSelectedIndex()+1;
                Joueur j1 = new Joueur(playerOne.getText(), Couleur.BLANC);
                Joueur j2 = (botOne.isSelected()) ? new IA(Couleur.NOIR, j1) : new Joueur(playerTwo.getText(), Couleur.NOIR);
                game.moveToBoard(i, j1, j2);
            });

            exit.addActionListener(e -> {
                System.exit(0);
            });
        }
    }
}
