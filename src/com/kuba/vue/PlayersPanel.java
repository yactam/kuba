package com.kuba.vue;

import com.kuba.Game;
import com.kuba.model.player.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayersPanel extends Background {

    private final PlayerView p1, p2;
    private final JLabel errors;
    private JPanel buttons;
    private JButton exit, abandonner, recommencer, mute, menu;

    public PlayersPanel(Joueur j1, Joueur j2, int WIDTH, int HEIGHT) {
        super("src/resources/panel.png", new Dimension((int) (0.3 * WIDTH), (int) (0.9 * HEIGHT)));
        setLayout(null);

        p1 = new PlayerView(j1);
        p2 = new PlayerView(j2);
        errors = new JLabel("");
        initButtons();

        p1.setBounds((int)(0.027*getWidth()), (int)(0.014*getHeight()), (int)(0.951*getWidth()), (int)(0.24*getHeight()));
        p2.setBounds((int)(0.027*getWidth()), (int)(0.254*getHeight()), (int)(0.951*getWidth()), (int)(0.24*getHeight()));
        errors.setBounds((int)(0.027*getWidth()), (int)(0.496*getHeight()), (int)(0.951*getWidth()), (int)(0.24*getHeight()));
        buttons.setBounds((int)(0.028*getWidth()), (int)(0.738*getHeight()), (int)(0.95*getWidth()), (int)(0.24*getHeight()));
        add(p1);
        add(p2);
        add(errors);
        add(buttons);
        style();
        new ButtonsController();
    }

    private void initButtons() {
        buttons = new JPanel(new GridBagLayout());
        buttons.setBackground(new Color(0,0,0,0));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        abandonner = new JButton(new ImageIcon("src/resources/end.png"));
        exit = new JButton(new ImageIcon("src/resources/return.png"));
        mute = new JButton(new ImageIcon("src/resources/mute.png"));
        recommencer = new JButton(new ImageIcon("src/resources/restart.png"));
        menu = new JButton(new ImageIcon("src/resources/exit.png"));
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttons.add(abandonner, constraints);
        constraints.gridx = 1;
        buttons.add(recommencer, constraints);
        constraints.gridx = 2;
        buttons.add(menu, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        buttons.add(mute, constraints);
        constraints.gridx = 2;
        buttons.add(exit, constraints);
    }

    private void style() {
        setCurrent(1);
        errors.setHorizontalAlignment(JLabel.CENTER);
        Font f2 = PlayerView.f1.deriveFont((float) (0.021 * GameView.WIDTH));
        errors.setFont(f2);
        errors.setForeground(new Color(255, 120, 0));
        abandonner.setBorderPainted(false);
        recommencer.setBorderPainted(false);
        menu.setBorderPainted(false);
        mute.setBorderPainted(false);
        exit.setBorderPainted(false);
        abandonner.setBackground(new Color(0,0,0,0));
        recommencer.setBackground(new Color(0,0,0,0));
        menu.setBackground(new Color(0,0,0,0));
        mute.setBackground(new Color(0,0,0,0));
        exit.setBackground(new Color(0,0,0,0));
    }

    public void setCurrent(int player) {
        if(player == 1) {
            p1.recolor();
            p2.uncolor();
        } else {
            p2.recolor();
            p1.uncolor();
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

    public void unmute(boolean isPlaying){
        if (isPlaying) mute.setIcon(new ImageIcon("src/resources/unmute.png"));
        else mute.setIcon(new ImageIcon("src/resources/mute.png"));
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

    public void menuAddListener(ActionListener e){
        menu.addActionListener(e);
    }

    private class ButtonsController {

        ButtonsController() {
            exit.addActionListener(e -> {
                System.exit(0);
            });
        }
    }
}
