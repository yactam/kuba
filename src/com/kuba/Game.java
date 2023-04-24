package com.kuba;
import java.awt.*;
import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BoardView;
import com.kuba.vue.GameView;

import javax.swing.*;

public class Game extends JFrame {

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int HIEGHT = screenSize.height, WIDTH = screenSize.width;;

    public Game(int n, Joueur j1, Joueur j2) {

        GameView gameView = new GameView(n, j1, j2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(" Board Affichage ");
        this.setSize(screenSize);
        add(gameView);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
