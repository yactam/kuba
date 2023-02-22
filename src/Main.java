import model.mouvement.Direction;
import model.mouvement.Position;
import model.plateau.Board;
import view.BoardView;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());

        board.move(new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 8), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 9), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        board.initBoard();
        System.out.println(board.hashCode());

    }
}