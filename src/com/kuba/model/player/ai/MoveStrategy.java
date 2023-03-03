package com.kuba.model.player.ai;

import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;


public interface MoveStrategy {

    Mouvement execute(Board board, Couleur joueur);
}
