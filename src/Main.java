
import model.plateau.Couleur;
import model.player.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import model.plateau.Board;
import vue.BoardView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);
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

        Joueur joueur = new Joueur("EMMA", Couleur.BLANC, 18);

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
        System.out.println(board.hashCode());



    }
}