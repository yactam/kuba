package model.player.ai;

import model.mouvement.Mouvement;
import model.plateau.Board;
import model.plateau.Couleur;


public interface MoveStrategy {

    Mouvement execute(Board board, Couleur joueur);
}
