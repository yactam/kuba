
import model.Couleur;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import model.plateau.Board;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);
        //Board board = Board.load("./board1");

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Joueur joueur = new Joueur("EMMA", Couleur.BLANC, 18);

        joueur.move(board ,new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(1, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(2, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(3, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(4, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(5, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 2), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(7, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(8, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(9, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 3), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 4), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 5), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 6), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 7), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 8), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 9), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(6, 10), Direction.EST);
        System.out.println(board);
        System.out.println(board.hashCode());
        board.save("./board1");

    }
}