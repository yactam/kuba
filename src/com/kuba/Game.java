package com.kuba;
import java.awt.*;
import com.kuba.controller.GameController;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BoardView;
import javax.swing.*;
import com.kuba.controller.Son;
public class Game extends JFrame {
    
    public Game(int n, Joueur j1, Joueur j2,Son son) {
        Board board = new Board(n);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        BoardView boardView = new BoardView(board);
        new GameController(board, j1, j2,son);
        

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(" Board Affichage ");
        this.setSize(screenSize);
        add(boardView);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
