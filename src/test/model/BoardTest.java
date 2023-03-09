package test.model;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void initialState() {
        Board board = new Board(2);
        board.initBoard();
        assertEquals(board.currentPlayer(), Couleur.BLANC);
        assertFalse(board.gameOver());
        assertEquals(board.getAllPossibleMoves(Couleur.BLANC, Couleur.NOIR).size(), 16);
        assertEquals(board.getAllPossibleMoves(Couleur.BLANC).size(), 8);
        assertEquals(board.getAllPossibleMoves(Couleur.NOIR).size(), 8);
    }

    @Test
    public void hashTest1() {
        Board board1 = new Board(1);
        board1.initBoard();
        int hash1 = board1.hashCode();

        Board board2 = new Board(1);
        board2.initBoard();
        int hash2 = board2.hashCode();


        assertEquals(hash1, hash2);
    }

}