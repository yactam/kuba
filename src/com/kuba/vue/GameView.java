package com.kuba.vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.controller.Son;
public class GameView extends JPanel {
    private BoardView boardView;
    private PlayersPanel playersPanel;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int HEIGHT = screenSize.height, WIDTH = screenSize.width;
    private Son son;

    public GameView(int n, Joueur j1, Joueur j2, Son son) {
        this.son = son;
        setLayout(null);
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0,0,0,0));
        Board plateau = new Board(n);
        boardView = new BoardView(plateau);

        new GameController(plateau,boardView, j1, j2,son);

        playersPanel = new PlayersPanel(j1, j2);

        boardView.setLocation((int) (0.1 * WIDTH), (int) (0.05 * HEIGHT));
        playersPanel.setLocation((int) (0.65 * WIDTH), (int) (0.05 * HEIGHT));

        add(boardView);
        add(playersPanel);
    }

    public void showError(String message) {
        playersPanel.showError(message);
    }

    public void cleanError() {
        playersPanel.cleanError();
    }
    
}
