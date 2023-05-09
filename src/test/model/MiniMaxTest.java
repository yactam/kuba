package test.model;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.MiniMax;
import com.kuba.vue.BoardView;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MiniMaxTest {

   @Test
    public void testCase1() {
        Board board = new Board(2);
        BoardView boardView = new BoardView(board);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardView);
        frame.setVisible(true);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }
        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);
        board.board(0, 6).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));

        MiniMax miniMax = new MiniMax(3, j1, j2);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(3, 6), Direction.NORD));
    }

    @Test
    public void testCase2() {
        Board board = new Board(2);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }
        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);
        board.board(0, 6).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));

        MiniMax miniMax = new MiniMax(10, j2, j1);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(0, 6), Direction.OUEST));
    }

    @Test
    public void testCase3() {
        Board board = new Board(2);
        board.initBoard();
        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);

        MiniMax miniMax = new MiniMax(4, j1, j2);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(0, 0), Direction.SUD));
    }

    @Test
    public void testCase4() {
        Board board = new Board(2);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }
        board.board(0, 6).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));
        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);

        MiniMax miniMax = new MiniMax(10, j2, j1);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(0, 6), Direction.OUEST));
    }

    @Test
    public void testCase5() {
        Board board = new Board(2);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }
        board.board(0, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));
        board.board(2, 2).setBille(new Bille(Couleur.BLANC));
        board.board(3, 2).setBille(new Bille(Couleur.BLANC));
        board.board(6, 6).setBille(new Bille(Couleur.NOIR));

        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);

        MiniMax miniMax = new MiniMax(5, j1, j2);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(3, 6), Direction.NORD));
    }

    @Test
    public void testCase6() {
        Board board = new Board(2);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }

        board.board(0, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(4, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(5, 6).setBille(new Bille(Couleur.BLANC));
        board.board(0, 5).setBille(new Bille(Couleur.NOIR));
        board.board(5, 1).setBille(new Bille(Couleur.BLANC));
        board.board(0, 0).setBille(new Bille(Couleur.BLANC));
        board.board(3, 3).setBille(new Bille(Couleur.NOIR));
        board.board(4, 3).setBille(new Bille(Couleur.NOIR));
        board.board(6, 4).setBille(new Bille(Couleur.NOIR));

        Joueur j1 = new Joueur("1", Couleur.BLANC);
        Joueur j2 = new Joueur("2", Couleur.NOIR);

        MiniMax miniMax = new MiniMax(5, j1, j2);
        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(5, 6), Direction.NORD));
    }

    @Test
    public void testCase7() {
        Board board = new Board(2);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                board.initCell(i, j);
            }
        }

        board.board(0, 1).setBille(new Bille(Couleur.ROUGE));
        board.board(0, 2).setBille(new Bille(Couleur.BLANC));
        board.board(0, 3).setBille(new Bille(Couleur.BLANC));
        board.board(0, 6).setBille(new Bille(Couleur.BLANC));
        board.board(1, 0).setBille(new Bille(Couleur.BLANC));
        board.board(1, 1).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.BLANC));
        board.board(2, 0).setBille(new Bille(Couleur.NOIR));
        board.board(2, 1).setBille(new Bille(Couleur.NOIR));
        board.board(2, 2).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 4).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 0).setBille(new Bille(Couleur.NOIR));
        board.board(3, 2).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 4).setBille(new Bille(Couleur.ROUGE));
        board.board(4, 2).setBille(new Bille(Couleur.ROUGE));
        board.board(4, 4).setBille(new Bille(Couleur.ROUGE));
        board.board(5, 6).setBille(new Bille(Couleur.BLANC));
        board.board(6, 4).setBille(new Bille(Couleur.BLANC));
        board.board(6, 6).setBille(new Bille(Couleur.BLANC));

        Joueur j1 = new Joueur("1", Couleur.NOIR);
        Joueur j2 = new Joueur("2", Couleur.BLANC);

        MiniMax miniMax = new MiniMax(3, j1, j2);

        Mouvement mouvement = miniMax.execute(board);
        assertEquals(mouvement, new Mouvement(new Position(2, 1), Direction.NORD));
    }

}