package com.kuba;

import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.MiniMax;
import com.kuba.vue.BoardView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*Board board = new Board(2);
        board.initBoard();
        BoardView boardView = new BoardView(board);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }
        Joueur j1 = new Joueur("1", Couleur.BLANC, 1);
        Joueur j2 = new Joueur("2", Couleur.NOIR, 1);
        board.board(0, 6).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));

        MiniMax miniMax = new MiniMax(1, j1, j2);
        Mouvement mouvement = miniMax.execute(board);
        j1.move(board, mouvement);*/

        Board board = new Board(2);
        BoardView boardView = new BoardView(board);
        board.initBoard();
        board.addObserver(boardView);

        /*Joueur j1 = new Joueur("j1", Couleur.BLANC);
        Joueur j2 = new Joueur("j2" , Couleur.NOIR);

        GameController boardController = new GameController(board, j1, j2);*/


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        /*board.update(new Mouvement(new Position(0, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(0, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(1, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(1, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(0, 2), Direction.NORD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(2, 2), Direction.NORD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(1, 0), Direction.SUD), Couleur.BLANC);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.update(new Mouvement(new Position(1, 2), Direction.SUD), Couleur.NOIR);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());*/

        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);

        MiniMax miniMax = new MiniMax(4, j1, j2); // Blanc
        MiniMax miniMax0 = new MiniMax(4, j2, j1); // Noir

        while(!board.gameOver()) {
            Mouvement m1 = miniMax.execute(board);
            board.update(m1, j1);
            boardView.updateBoard(board);
            System.out.println("Rouges des blancs: " + j1.getNbBilleRougeCapturee() + ", Noirs des blancs: " + j1.getNbAdversaireCapturee());
            int n = (board.size() + 1) / 4;
            int nbBillesRouges = 8 * n * n - 12 * n + 5;
            if(j1.getNbBilleRougeCapturee() > nbBillesRouges/2) break;
            if(j2.getNbBilleRougeCapturee() > nbBillesRouges/2) break;
            if(j1.getNbAdversaireCapturee() == 2 * n * n) break;
            if(j2.getNbAdversaireCapturee() == 2 * n * n) break;
            Mouvement m2 = miniMax0.execute(board);
            board.update(m2, j2);
            boardView.updateBoard(board);
            System.out.println("Rouges des noirs: " + j2.getNbBilleRougeCapturee() + ", Blanches des noirs: " + j2.getNbAdversaireCapturee());
            if(j1.getNbBilleRougeCapturee() > nbBillesRouges/2) break;
            if(j2.getNbBilleRougeCapturee() > nbBillesRouges/2) break;
            if(j1.getNbAdversaireCapturee() == 2 * n * n) break;
            if(j2.getNbAdversaireCapturee() == 2 * n * n) break;
        }

        /*Joueur joueur = new Joueur("EMMA", Couleur.BLANC, 18);

        joueur.move(board ,new Position(0, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(1, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(2, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(3, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(4, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(5, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 2), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(7, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(8, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(9, 2), Direction.SUD);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 3), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 4), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 5), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 6), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 7), Direction.EST);
        boardView.updateBoard(board);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 8), Direction.EST);
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