package com.kuba.model.player.ai;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;

public class StandardEvaluator implements Evaluator {
    /**
     * a method used to evaluate the actual board configuration used in the minimax algorithme in the base case.
     * The method take in charge:
     *          - The number of the current player discs in the board
     *          - The number of the opponent discs in the board
     * @param aiPlayer the player who try to find the best move
     * @param opponent the opponent
     * @return a positive number if the actual board config is in favor of the ai player
     * and return a negative number if it's not, 0 means equality for the two players
     */
    @Override
    public int evaluate(Joueur aiPlayer, Joueur opponent) {
        return aiPlayer.getScore() - opponent.getScore();
    }

}
