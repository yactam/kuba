package com.kuba.model.player.ai;


import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;


public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int depthSearch;
    private Joueur currentPlayer;
    private Joueur opponent;
    private Mouvement bestMove;

    public MiniMax(int depthSearch, Joueur currentPlayer, Joueur opponent) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.depthSearch = depthSearch;
        this.currentPlayer = currentPlayer;
        this.opponent = opponent;
    }
    @Override
    public Mouvement execute(Board board) {
        int sr1 = currentPlayer.getNbBilleRougeCapturee(), sa1 = currentPlayer.getNbAdversaireCapturee();
        int sr2 = opponent.getNbBilleRougeCapturee(), sa2 = opponent.getNbAdversaireCapturee();
        max(board, currentPlayer, depthSearch, Integer.MIN_VALUE, Integer.MAX_VALUE);
        currentPlayer.setNbRougesCapturee(sr1);
        currentPlayer.setNbAdversaireCapturee(sa1);
        opponent.setNbAdversaireCapturee(sa2);
        opponent.setNbRougesCapturee(sr2);
        return bestMove;
    }

    public int min(Board board, Joueur joueur, int depth, int alpha, int beta) {

        if(depth == 0 || gameOver(board, joueur, opponent)) {
            if(gameOver(board, joueur, opponent)) {
                System.out.println("game over");
                if(getWinner(board, joueur, opponent).equals(joueur))
                    return Integer.MIN_VALUE;
                else
                    return Integer.MAX_VALUE;
            }
            return this.boardEvaluator.evaluate(board, opponent, joueur);
        }

        for(Mouvement move : board.getAllPossibleMoves(joueur)) {
            Board tmp = board.copyBoard();
            tmp.update(move, joueur);
            switchPlayers();
            int currentValue = max(tmp, currentPlayer, depth-1, alpha, beta);
            switchPlayers();
            if(currentValue <= beta) {
                beta = currentValue;
            }
            if(alpha >= beta) {
                return beta;
            }
        }

        return beta;
    }

    public int max(Board board, Joueur joueur, int depth, int alpha, int beta) {
        if(depth == 0 || gameOver(board, joueur, opponent)) {
            if(gameOver(board, joueur, opponent)) {
                System.out.println("game over");
                if(getWinner(board, joueur, opponent).equals(joueur))
                    return Integer.MAX_VALUE;
                else
                    return Integer.MIN_VALUE;
            }
            return this.boardEvaluator.evaluate(board, joueur, opponent);
        }

        for(Mouvement move : board.getAllPossibleMoves(joueur)) {
            Board tmp = board.copyBoard();
            tmp.update(move, joueur);
            switchPlayers();
            int currentValue = min(tmp, currentPlayer, depth-1, alpha, beta);
            switchPlayers();
            if(currentValue >= alpha) {
                alpha = currentValue;
                if(depth == depthSearch)
                    bestMove = move;
            }
            if(alpha >= beta) {
                return alpha;
            }
        }

        return alpha;
    }

    private void switchPlayers() {
        Joueur tmp = currentPlayer;
        currentPlayer = opponent;
        opponent = tmp;
    }

    private boolean gameOver(Board board, Joueur player, Joueur opponent) {
        int n = (board.size() + 1) / 4;
        int nbBillesRouges = 8 * n * n - 12 * n + 5;
        if(player.getNbBilleRougeCapturee() > nbBillesRouges/2) return true;
        if(opponent.getNbBilleRougeCapturee() > nbBillesRouges/2) return true;
        if(player.getNbAdversaireCapturee() == 2 * n * n) return true;
        if(opponent.getNbAdversaireCapturee() == 2 * n * n) return true;
        return false;
    }

    private Joueur getWinner(Board board, Joueur player, Joueur opponent) {
        int n = (board.size() + 1) / 4;
        int nbBillesRouges = 8 * n * n - 12 * n + 5;
        if(player.getNbBilleRougeCapturee() > nbBillesRouges/2) return player;
        if(opponent.getNbBilleRougeCapturee() > nbBillesRouges/2) return opponent;
        if(player.getNbAdversaireCapturee() == 2 * n * n) return player;
        if(opponent.getNbAdversaireCapturee() == 2 * n * n) return opponent;
        return null;
    }
}
