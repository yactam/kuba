package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.player.Joueur;

import javax.swing.*;
import java.awt.*;

public class PlayersPanel extends JPanel {

    private PlayerView p1, p2;
    private JLabel errors;
    private JButton exit;

    public PlayersPanel(Joueur j1, Joueur j2) {
        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        errors = new JLabel();
        exit = new JButton(new ImageIcon("/resources/exit.png"));

        setSize(new Dimension((int) (0.3 * Game.WIDTH), (int) (0.9 * Game.HEIGHT)));
        setPreferredSize(new Dimension((int) (0.3 * Game.WIDTH), (int) (0.9 * Game.HEIGHT)));
        setLayout(new GridLayout(4, 1));
        add(p1);
        add(p2);
        add(errors);
        add(exit);
    }

    public void showError(String message) {
        errors.setText(message);
    }

    public void cleanError() {
        errors.setText("");
    }
}
