package com.kuba.model.player.ai;

import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;


public interface BoardEvaluator {
    int evaluate(Board board, Couleur joueur);
}
