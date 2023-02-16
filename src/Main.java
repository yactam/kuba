
import model.Couleur;
import model.Joueur;
import model.mouvement.Direction;
import model.mouvement.Position;
import model.plateau.Board;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);

        board.initBoard();
        System.out.println(board);
        System.out.println(board.hashCode());



        Joueur joueur = new Joueur("EMMA", Couleur.BLANC, 18);

        joueur.move(board ,new Position(0, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(1, 2), Direction.SUD);
        System.out.println(board);
        System.out.println(board.hashCode());

        joueur.move(board, new Position(2, 2), Direction.SUD); // Ça bouge pas il faut régler ça
        System.out.println(board);
        System.out.println(board.hashCode());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}