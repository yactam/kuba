package test.model;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void initialState() {
        Board board = new Board(2);
        Joueur j1 = new Joueur("Blanc", Couleur.BLANC);
        Joueur j2 = new Joueur("Noir", Couleur.NOIR);
        board.initBoard();
        assertFalse(board.gameOver(j1, j2));
        assertEquals(board.getAllPossibleMoves(j1, j2).size(), 16);
        assertEquals(board.getAllPossibleMoves(j1).size(), 8);
        assertEquals(board.getAllPossibleMoves(j2).size(), 8);
        assertEquals(j1.getScore(), 0);
        assertEquals(j2.getScore(), 0);
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