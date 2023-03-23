package com.kuba.model.player.ai;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;

public class StandardBoardEvaluator implements BoardEvaluator {
    private static final int BONUS_FRONTIER = 1; // It's good to be free in one side to move
    private static final int BONUS_RED = 2; // It's good to be next to red discs
    private static final int BONUS_PIECE = 30; // It's good to have pieces
    private static final int GET_OUT_RED = 100; // In order to force the AI to get out the red discs
    private static final int BONUS_MOVE = 10; // It's good to have more moves

    /**
     * a method used to evaluate the actual board configuration used in the minimax algorithme in the base case.
     * The method take in charge:
     *          - The frontiers vs interiors discs
     *          - Disc positioning (center is good and corners is bad)
     *          - The number of the current player discs in the board
     *          - The number of the opponent discs in the board
     * @param board the actual board configuration
     * @return a positive number if the actual board config is in favor of the ai player
     * and return a negative number if it's not, 0 means equality for the two players
     */
    @Override
    public int evaluate(Board board, Joueur currentPlayer, Joueur opponent) {
        int res =  score(board, currentPlayer) - score(board, opponent);
        return res + (currentPlayer.getScore() - opponent.getScore());
    }

    private int score(Board board, Joueur joueur) {
        int ret = 0;
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(!board.board(i, j).estVide() && board.board(i, j).getBille().getColor() == joueur.getCouleur()) {
                    ret += BONUS_PIECE;
                    /*if(board.isFrontier(i, j)) {
                        ret += BONUS_FRONTIER;
                    }*/
                    if(board.inFrontOfRed(i, j)) {
                        ret += BONUS_RED;
                    }
                }
                if(!board.board(i, j).estVide() && board.board(i, j).getBille().getColor() == Couleur.ROUGE) {
                    ret -= GET_OUT_RED;
                }
                ret += board.getAllPossibleMoves(joueur).size() * BONUS_MOVE; // More moves is good
            }
        }
        return ret;
    }
}
