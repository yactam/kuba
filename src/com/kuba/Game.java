package com.kuba;

import com.kuba.model.player.Joueur;
import com.kuba.vue.*;

import java.awt.*;
import javax.swing.*;

import static com.kuba.vue.GameView.screenSize;

public class Game extends JFrame {
    private final MenuView menu = new MenuView(this);

    public Game() {
        setSize(screenSize);
        setContentPane(menu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kuba: The Game");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void moveToMenu(){
        setContentPane(menu);
        invalidate();
        validate();
    }

    public void moveToBoard(int n, Joueur j1, Joueur j2){
        GameView gameView = new GameView(this, n, j1, j2);
        setContentPane(gameView);
        invalidate();
        validate();
    }
}
