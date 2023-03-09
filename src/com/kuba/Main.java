package com.kuba;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.MiniMax;
import com.kuba.vue.BoardView;

import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(2);
        BoardView boardView = new BoardView(board);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());
        boardView.updateBoard(board);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /*board = board.update(new Mouvement(new Position(0, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(0, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(1, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(1, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(0, 2), Direction.NORD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(2, 2), Direction.NORD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(1, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = board.update(new Mouvement(new Position(1, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());*/


        MiniMax miniMax = new MiniMax(3); // Blanc
        MiniMax miniMax0 = new MiniMax(3); // Noir

        while(!board.gameOver()) {
            Mouvement m1 = miniMax.execute(board, Couleur.BLANC);
            board = board.update(m1, Couleur.BLANC);
            boardView.updateBoard(board);
            Mouvement m2 = miniMax0.execute(board, Couleur.NOIR);
            System.out.println("\t" + m2);
            board = board.update(m2, Couleur.NOIR);
            boardView.updateBoard(board);
        }

        /*Joueur joueur = new Joueur("EMMA", Couleur.BLANC, 18);

        board = joueur.move(board ,new Position(0, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board =joueur.move(board, new Position(1, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(2, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(3, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(4, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(5, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 2), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(7, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(8, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(9, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 3), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 4), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 5), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 6), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 7), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board = joueur.move(board, new Position(6, 8), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 9), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 10), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());*/

        //Controller controller = new Controller(board);



    }
}