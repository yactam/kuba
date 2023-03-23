package com.kuba.model.player.ai;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;


public interface BoardEvaluator {
    int evaluate(Board board, Joueur currentPlayer, Joueur opponent);
}
