package com.kuba.model.player;


import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.ai.Evaluator;
import com.kuba.model.player.ai.MoveStrategy;
import com.kuba.model.player.ai.StandardEvaluator;


public class MiniMax implements MoveStrategy {

    private final Evaluator evaluator;
    private final int depthSearch;
    private final Joueur aiPlayer;
    private final Joueur opponent;
    private Mouvement bestMove;

    public MiniMax(int depthSearch, Joueur aiPlayer, Joueur opponent) {
        this.evaluator = new StandardEvaluator();
        this.depthSearch = depthSearch;
        this.aiPlayer = aiPlayer;
        this.opponent = opponent;
    }
    @Override
    public Mouvement execute(Board board) {
        int sr1 = aiPlayer.getNbBilleRougeCaptured(), sa1 = aiPlayer.getNbAdversaireCaptured();
        int sr2 = opponent.getNbBilleRougeCaptured(), sa2 = opponent.getNbAdversaireCaptured();
        max(board, depthSearch, Integer.MIN_VALUE, Integer.MAX_VALUE);
        aiPlayer.setNbRougesCaptured(sr1);
        aiPlayer.setNbAdversaireCaptured(sa1);
        opponent.setNbAdversaireCaptured(sa2);
        opponent.setNbRougesCaptured(sr2);
        return bestMove;
    }

    public int min(Board board, int depth, int alpha, int beta) {
        if(depth == 0 || board.gameOver(aiPlayer, opponent)) {
            return this.evaluator.evaluate(aiPlayer, opponent);
        }

        for(Mouvement move : board.getAllPossibleMoves(opponent)) {
            Board tmp = board.copyBoard();
            int tmp1 = opponent.getNbBilleRougeCaptured(), tmp2 = opponent.getNbAdversaireCaptured();
            opponent.move(tmp, move);
            int currentValue = max(tmp, depth-1, alpha, beta);
            opponent.setNbRougesCaptured(tmp1);
            opponent.setNbAdversaireCaptured(tmp2);
            if(currentValue < beta) {
                beta = currentValue;
            }
            if(alpha > beta) {
                return beta;
            }
        }

        return beta;
    }

    public int max(Board board, int depth, int alpha, int beta) {
        if(depth == 0 || board.gameOver(aiPlayer, opponent)) {
            return this.evaluator.evaluate(aiPlayer, opponent);
        }

        for(Mouvement move : board.getAllPossibleMoves(aiPlayer)) {
            Board tmp = board.copyBoard();
            int tmp1 = aiPlayer.getNbBilleRougeCaptured(), tmp2 = aiPlayer.getNbAdversaireCaptured();
            aiPlayer.move(tmp, move);
            int currentValue = min(tmp, depth-1, alpha, beta);
            aiPlayer.setNbRougesCaptured(tmp1);
            aiPlayer.setNbAdversaireCaptured(tmp2);
            if(currentValue > alpha) {
                alpha = currentValue;
                if(depth == depthSearch)
                    bestMove = move;
            }
            if(beta < alpha) {
                return alpha;
            }
        }

        return alpha;
    }
}
