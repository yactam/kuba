package com.kuba;

import com.kuba.model.player.Joueur;
import com.kuba.vue.*;
import java.io.ObjectOutputStream;
import java.awt.*;
import javax.swing.*;

public class Game extends JFrame {
    public final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    public MenuView menu = new MenuView(this);
    public GameView board;

    public Game() {
        setSize(screensize);
        setContentPane(menu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kuba: The Game");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Game(int n, Joueur j1, Joueur j2,boolean online,ObjectOutputStream out,Joueur j) {
        board = new GameView(this,n, j1, j2,online,out,j);
        setContentPane(board);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setTitle("Kuba: The Game");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public GameView getGameView() {
        return this.board;
    }

    public void moveToMenu(){
        setContentPane(menu);
        invalidate();
        validate();
    }

    public void moveToBoard(int n, Joueur j1, Joueur j2){     
        board = new GameView(this, n, j1, j2);
        setContentPane(board);
        invalidate();
        validate();
    }

}
