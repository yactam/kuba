package com.kuba.model.player.ai;


import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;


public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int depthSearch;

    public MiniMax(int depthSearch) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.depthSearch = depthSearch;
    }
    @Override
    public Mouvement execute(Board board, Couleur joueur) {
        final long startTime = System.currentTimeMillis();
        Mouvement bestMove = null;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int currentValue = 0;
        System.out.println("************************************************************************\n");
        System.out.println(joueur + " is THINKING with depth = " + depthSearch + " ...");
        int i = 0;
        for(Mouvement mouvement : board.getAllPossibleMoves(joueur)) {
            System.out.println("\t testing movement " + (++i));
            Board transitionBoard = board.update(mouvement, joueur);
            currentValue = board.currentPlayer().equals(Couleur.BLANC) ?
                           min(transitionBoard, joueur, depthSearch-1) : max(transitionBoard, joueur, depthSearch-1);
            if(!transitionBoard.equals(board)) { // Their was a valid mouvement
                if(currentValue > max && board.currentPlayer().equals(Couleur.BLANC)) {
                    max = currentValue;
                    System.out.println("\t\tNew Best move found");
                    bestMove = mouvement;
                } else if(currentValue < min && board.currentPlayer().equals(Couleur.NOIR)) {
                    min = currentValue;
                    System.out.println("\t\tNew Best move found");
                    bestMove = mouvement;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("Search ended for the player " + joueur + " after " + executionTime + "ms");
        System.out.println("\n************************************************************************\n");

        return bestMove;

    }

    public int min(Board board, Couleur joueur, int depth) {
        if(depth == 0 || board.gameOver()) {
            return this.boardEvaluator.evaluate(board, joueur);
        }

        int min = Integer.MAX_VALUE;
        for(Mouvement move : board.getAllPossibleMoves()) {
            Board boardTransition = board.update(move, board.currentPlayer());
            int currentValue = max(boardTransition, joueur, depth-1);
            if(currentValue < min) min = currentValue;
        }

        return min;
    }

    public int max(Board board, Couleur joueur, int depth) {
        if(depth == 0 || board.gameOver()) {
            return this.boardEvaluator.evaluate(board, joueur);
        }

        int max = Integer.MIN_VALUE;
        for(Mouvement move : board.getAllPossibleMoves()) {
            Board boardTransition = board.update(move, board.currentPlayer());
            int currentValue = min(boardTransition, joueur, depth-1);
            if(currentValue > max) max = currentValue;
        }

        return max;
    }
}
