package test.model;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import org.junit.jupiter.api.Test;

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

}