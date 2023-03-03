package test.model;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.ai.MiniMax;
import com.kuba.model.player.ai.StandardBoardEvaluator;
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

}