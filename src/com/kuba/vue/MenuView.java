package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.IA;

import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*;


public class MenuView extends JPanel {
    Game game;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel background = new Background("src/resources/main_title.png", screenSize);
    String[] choices = {"3", "7", "11", "15", "19"};
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
        playerOne.setBounds((int)(getWidth()*0.228), (int)(getHeight()*0.486), 
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
        botOne.setBounds((int) (0.677 * getWidth()), (int) (0.574 * getHeight()), 
                         (int) (0.02*getWidth()), (int) (0.02*getWidth()));
    }

    private void initButtons() {
        boardSizes = new JComboBox<>(choices);
        start = new JButton(new ImageIcon("src/resources/launch.png"));
        exit = new JButton(new ImageIcon("src/resources/return.png"));
        boardSizes.setSelectedIndex(1);

        styleButtons();
    }

    private void styleButtons() {
        start.setFont(new Font("Arial", Font.BOLD, (int) (0.015*getWidth())));
        boardSizes.setBounds((int) (0.55 * getWidth()), (int) (0.6 * getHeight()), (int) (0.05*getWidth()), (int) (0.03*getHeight()));
        start.setBounds((int) (0.465 * getWidth()), (int) (0.7 * getHeight()), (int) (0.1*getWidth()), (int) (0.06*getHeight()));
        exit.setBounds((int) (0.465 * getWidth()), (int) (0.8 * getHeight()), (int) (0.1*getWidth()), (int) (0.06*getHeight()));

        start.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        start.setOpaque(false);
        exit.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        exit.setOpaque(false);
    }

    private class MenuController extends MouseAdapter {

        public MenuController() {
            start.addActionListener(e -> {
                int i = boardSizes.getSelectedIndex();
                int t = (Integer.parseInt(choices[i]) + 1) / 4;
                Joueur j1 = new Joueur(playerOne.getText(), Couleur.BLANC);
                Joueur j2 = (botOne.isSelected()) ? new IA(Couleur.NOIR, j1) : new Joueur(playerTwo.getText(), Couleur.NOIR);
                game.moveToBoard(t, j1, j2);
            });

            exit.addActionListener(e -> {
                System.exit(0);
            });
        }

    }
}
