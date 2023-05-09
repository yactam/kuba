package com.kuba.vue;

import java.awt.*;
import java.awt.event.*;
import com.kuba.controller.GameController;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.Game;

public class GameView extends Background {
    private final BoardView boardView;
    private final PlayersPanel playersPanel;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int HEIGHT = screenSize.height, WIDTH = screenSize.width;

    public GameView(Game g, int n, Joueur j1, Joueur j2) {
        super("src/resources/images/table.jpg", new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(0,0,0,0));
        Board plateau = new Board(n);
        boardView = new BoardView(plateau);
        playersPanel = new PlayersPanel(j1, j2, WIDTH, HEIGHT);

        boardView.setLocation((int) (0.1 * WIDTH), (int) (0.05 * HEIGHT));
        playersPanel.setLocation((int) (0.65 * WIDTH), (int) (0.05 * HEIGHT));

        add(boardView);
        add(playersPanel);

        new GameController(g, plateau, this, j1, j2);
    }

    public void showError(String message) {
        playersPanel.showError(message);
    }

    public void cleanError() {
        playersPanel.cleanError();
    }
    public void changePlayer(int player) {
        playersPanel.setCurrent(player);
    }

    public void updateScore() {
        playersPanel.update();
    }

    public void recommencer(ActionListener actionListener) {
        playersPanel.recommencerAddListener(actionListener);
    }

    public void abandonner(ActionListener actionListener) {
        playersPanel.abandonnerAddListener(actionListener);
    }

    public void mute(ActionListener actionListener) {
        playersPanel.muteAddListener(actionListener);
    }

    public void menu(ActionListener actionListener) {
        playersPanel.menuAddListener(actionListener);
    }

    public void unmute(boolean isPlaying){
        playersPanel.unmute(isPlaying);
    }

    public void startAnimation(Position from, Direction direction) {
        boardView.startAnimation(from, direction);
    }

    public void enableController(boolean enable) {
        boardView.setFocusable(enable);
        if(enable) {
            boardView.requestFocusInWindow();
            boardView.requestFocus();
        }
    }
}
