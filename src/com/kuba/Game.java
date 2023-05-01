package com.kuba;
import java.awt.*;
import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BoardView;
import com.kuba.vue.GameView;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import com.kuba.controller.Son;
public class Game extends JFrame {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int HEIGHT = screenSize.height, WIDTH = screenSize.width;;
    private GameView gameView;
    public Game(int n, Joueur j1, Joueur j2,Son son) { 
        gameView = new GameView(n, j1, j2,son);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(" Board Affichage ");
        this.setSize(screenSize);
        add(gameView);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public Game(int n, Joueur j1, Joueur j2,Son son,boolean online,ObjectOutputStream out,Joueur j) {
        gameView = new GameView(n, j1, j2,son,online,out,j);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setTitle(" Board Affichage ");
        this.setSize(screenSize);
        add(gameView);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);     
    }

    public GameView getGameView() {
        return this.gameView;
    }

}
