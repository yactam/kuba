package model.player.ai;

import model.mouvement.Mouvement;
import model.plateau.Board;
import model.player.Joueur;

public interface MoveStrategy {

    Mouvement execute(Board board, Joueur joueur);
}
