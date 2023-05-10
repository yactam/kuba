package com.kuba.model.player.ai;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;


public interface Evaluator {
    int evaluate(Joueur currentPlayer, Joueur opponent);
}
