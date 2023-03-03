package model.player.ai;

import model.plateau.Board;

public class StandardBoardEvaluator implements BoardEvaluator {
    /**
     * a method used to evaluate the actual board configuration used in the minimax algorithme in the base case.
     * The method take in charge:
     *          - The frontiers vs interiors discs
     *          - Disc positioning (center is good and corners is bad)
     *          - The number of the current player discs in the board
     *          - The number of the opponent discs in the board
     * @param board the actual board configuration
     * @return a positive number if the actual board config is in favor of the current player
     * and return a negative number if it's not, 0 means equality for the two players
     */
    @Override
    public int evaluate(Board board) {
        return 0;
    }
}
