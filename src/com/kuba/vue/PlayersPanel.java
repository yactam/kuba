package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.player.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayersPanel extends JPanel {

    private final PlayerView p1, p2;
    private final JLabel errors;
    private JPanel buttons;
    private JButton exit, abandonner, recommencer, mute;

    public PlayersPanel(Joueur j1, Joueur j2) {
        setSize(new Dimension((int) (0.3 * Game.WIDTH), (int) (0.9 * Game.HEIGHT)));
        setPreferredSize(new Dimension((int) (0.3 * Game.WIDTH), (int) (0.9 * Game.HEIGHT)));
        setLayout(new GridLayout(4, 1));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 6, true));


        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        errors = new JLabel("");
        initButtons();


        add(p1);
        add(p2);
        add(errors);
        add(buttons);

        style();
        new ButtonsController();
    }

    private void initButtons() {
        buttons = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        abandonner = new JButton("Abandonner");
        exit = new JButton("       Exit       ");
        mute = new JButton("      Mute      ");
        recommencer = new JButton("Recommencer");
        buttons.add(abandonner, constraints);
        buttons.add(exit, constraints);
        buttons.add(mute, constraints);
        buttons.add(recommencer, constraints);
    }

    private void style() {
        setCurrent(1);
        errors.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false));
        errors.setHorizontalAlignment(JLabel.CENTER);
        Font f2 = PlayerView.f1.deriveFont((float) (0.014 * GameView.WIDTH));
        errors.setFont(f2);
        errors.setForeground(new Color(255, 0, 0));
    }

    public void setCurrent(int player) {
        if(player == 1) {
            p1.setBackground(new Color(237, 102, 99));
            p1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false));
            p2.setBackground(UIManager.getColor ( "Panel.background" ));
        } else {
            p2.setBackground(new Color(237, 102, 99));
            p2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false));
            p1.setBackground(UIManager.getColor ( "Panel.background" ));
        }
    }

    public void showError(String message) {
        errors.setText(message);
    }

    public void cleanError() {
        errors.setText("");
    }
    public void update() {
        p1.update();
        p2.update();
    }

    public void recommencerAddListener(ActionListener a) {
        recommencer.addActionListener(a);
    }

    public void abandonnerAddListener(ActionListener e) {
        abandonner.addActionListener(e);
    }

    public void muteAddListener(ActionListener e) {
        mute.addActionListener(e);
    }

    private class ButtonsController {

        ButtonsController() {
            exit.addActionListener(e -> {
                System.exit(0);
            });
        }
    }
}
