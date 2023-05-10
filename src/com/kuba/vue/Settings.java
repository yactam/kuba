package com.kuba.vue;

import com.kuba.Game;

import javax.swing.*;
import java.awt.*;

public class Settings extends Background {

    public Settings(Game game) {
        super("src/resources/images/src_resources_settings_page.png", GameView.screenSize);
        JButton home = new JButton(new ImageIcon("src/resources/images/return.png"));
        home.setBounds((int) (0.92 * getWidth()), (int) (0.036 * getHeight()), 50, 50);
        home.setBorderPainted(false);
        home.setBackground(new Color(0,0,0,0));
        add(home);
        home.addActionListener(e -> {
            game.moveToMenu();
        });
    }


}
