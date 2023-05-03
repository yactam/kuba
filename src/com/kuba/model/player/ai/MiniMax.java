package com.kuba.model.player.ai;


import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;


public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int depthSearch;
    private final Joueur aiPlayer;
    private final Joueur opponent;
    private Mouvement bestMove;

    public MiniMax(int depthSearch, Joueur aiPlayer, Joueur opponent) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.depthSearch = depthSearch;
        this.aiPlayer = aiPlayer;
        this.opponent = opponent;
    }
    @Override
    public Mouvement execute(Board board) {
        int sr1 = aiPlayer.getNbBilleRougeCapturee(), sa1 = aiPlayer.getNbAdversaireCapturee();
        int sr2 = opponent.getNbBilleRougeCapturee(), sa2 = opponent.getNbAdversaireCapturee();
        max(board, depthSearch, Integer.MIN_VALUE, Integer.MAX_VALUE);
        aiPlayer.setNbRougesCapturee(sr1);
        aiPlayer.setNbAdversaireCapturee(sa1);
        opponent.setNbAdversaireCapturee(sa2);
        opponent.setNbRougesCapturee(sr2);
        return bestMove;
    }

    public int min(Board board, int depth, int alpha, int beta) {
        if(depth == 0 || board.gameOver(aiPlayer, opponent)) {
            return this.boardEvaluator.evaluate(board, aiPlayer, opponent);
        }

        for(Mouvement move : board.getAllPossibleMoves(opponent)) {
            Board tmp = board.copyBoard();
            int tmp1 = opponent.getNbBilleRougeCapturee(), tmp2 = opponent.getNbAdversaireCapturee();
            opponent.move(tmp, move);
            int currentValue = max(tmp, depth-1, alpha, beta);
            opponent.setNbRougesCapturee(tmp1);
            opponent.setNbAdversaireCapturee(tmp2);
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
            return this.boardEvaluator.evaluate(board, aiPlayer, opponent);
        }

        for(Mouvement move : board.getAllPossibleMoves(aiPlayer)) {
            Board tmp = board.copyBoard();
            int tmp1 = aiPlayer.getNbBilleRougeCapturee(), tmp2 = aiPlayer.getNbAdversaireCapturee();
            aiPlayer.move(tmp, move);
            int currentValue = min(tmp, depth-1, alpha, beta);
            aiPlayer.setNbRougesCapturee(tmp1);
            aiPlayer.setNbAdversaireCapturee(tmp2);
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
