package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.IA;

import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*;


public class MenuView extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel background = new Background("src/resources/main_title.png", screenSize);
    String[] choices = {"3", "7", "11", "15", "19"};
    JTextField playerOne, playerTwo;
    JCheckBox botOne;
    JLabel text;
    JComboBox<String> boardSizes;
    JButton start, exit;
    public MenuView() {
        setPreferredSize(screenSize);
        setSize(screenSize);
        setLayout(null);


        initPlayers();
        initBot();
        initButtons();

        background.add(playerOne);
        background.add(playerTwo);
        background.add(botOne);
        background.add(text);
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
        playerOne.setFont(new Font("Arial", Font.BOLD, (int) (0.015*getWidth())));
        playerTwo.setFont(new Font("Arial", Font.BOLD, (int) (0.015*getWidth())));
        playerOne.setBounds((int) (0.2 * getWidth()), (int) (0.4 * getHeight()), (int) (0.07*getWidth()), (int) (0.05*getHeight()));
        playerTwo.setBounds((int) (0.7 * getWidth()), (int) (0.4 * getHeight()), (int) (0.07*getWidth()), (int) (0.05*getHeight()));
    }

    private void initBot() {
        botOne = new JCheckBox("Utiliser un bot");
        styleBot();
    }

    private void styleBot() {
        botOne.setForeground(Color.WHITE);
        botOne.setBackground(new Color(0,0,0,100));
        botOne.setBounds((int) (0.7 * getWidth()), (int) (0.47 * getHeight()), (int) (0.07*getWidth()), (int) (0.03*getHeight()));
    }

    private void initButtons() {
        text = new JLabel("Taille du plateau");
        boardSizes = new JComboBox<>(choices);
        start = new JButton(new ImageIcon("src/resources/start.png"));
        exit = new JButton(new ImageIcon("src/resources/exit.png"));
        boardSizes.setSelectedIndex(1);

        styleButtons();
    }

    private void styleButtons() {
        text.setFont(new Font("Arial", Font.BOLD, (int) (0.015*getWidth())));
        text.setForeground(Color.white);
        text.setBackground(new Color(0,0,0,70));
        start.setFont(new Font("Arial", Font.BOLD, (int) (0.015*getWidth())));
        text.setBounds((int) (0.4 * getWidth()), (int) (0.6 * getHeight()), (int) (0.15*getWidth()), (int) (0.03*getHeight()));
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
                new Game(t, j1, j2);
            });

            exit.addActionListener(e -> {
                System.exit(0);
            });
        }

    }
}
