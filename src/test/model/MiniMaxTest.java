package test.model;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.ai.MiniMax;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiniMaxTest {

    @Test
    public void testCase1() {
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

        MiniMax miniMax = new MiniMax(4);
        Mouvement mouvement = miniMax.execute(board, Couleur.BLANC);
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
        board.board(0, 6).setBille(new Bille(Couleur.NOIR));
        board.board(1, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(2, 6).setBille(new Bille(Couleur.ROUGE));
        board.board(3, 6).setBille(new Bille(Couleur.BLANC));

        MiniMax miniMax = new MiniMax(4);
        Mouvement mouvement = miniMax.execute(board, Couleur.NOIR);
        assertEquals(mouvement, new Mouvement(new Position(0, 6), Direction.SUD));
    }

    @Test
    public void testCase3() {
        Board board = new Board(2);
        board.initBoard();

        MiniMax miniMax = new MiniMax(4);
        Mouvement mouvement = miniMax.execute(board, Couleur.BLANC);
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

        MiniMax miniMax = new MiniMax(4);
        Mouvement mouvement = miniMax.execute(board, Couleur.NOIR);
        assertEquals(mouvement, new Mouvement(new Position(0, 6), Direction.SUD));
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

        MiniMax miniMax = new MiniMax(4);
        Mouvement mouvement = miniMax.execute(board, Couleur.BLANC);
        assertEquals(mouvement, new Mouvement(new Position(3, 6), Direction.NORD));
    }

}