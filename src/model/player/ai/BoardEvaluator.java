package model.player.ai;

import model.plateau.Board;

public interface BoardEvaluator {
    int evaluate(Board board);
}
