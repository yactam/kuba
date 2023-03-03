package model.player.ai;


import model.mouvement.Mouvement;
import model.plateau.Board;
import model.plateau.Couleur;
import model.player.Joueur;


public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private int depthSearch;

    public MiniMax(int depthSearch) {
        this.boardEvaluator = new StandardBoardEvaluator();
        this.depthSearch = depthSearch;
    }
    @Override
    public Mouvement execute(Board board, Joueur joueur) {
        final long startTime = System.currentTimeMillis();
        Mouvement bestMove = null;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int currentValue = 0;

        System.out.println(joueur + " is THINKING with depth = " + depthSearch + " ...");
        int i = 0;
        for(Mouvement mouvement : board.getAllPossibleMoves()) {
            System.out.println("\t testing movement " + (++i));
            Board transitionBoard = board.update(mouvement, joueur);
            currentValue = board.currentPlayer().getCouleur().equals(Couleur.BLANC) ?
                           min(transitionBoard, joueur, depthSearch-1) : max(transitionBoard, joueur, depthSearch-1);

            if(currentValue > max && board.currentPlayer().getCouleur().equals(Couleur.BLANC)) {
                max = currentValue;
                bestMove = mouvement;
            } else if(currentValue < min && board.currentPlayer().getCouleur().equals(Couleur.NOIR)) {
                min = currentValue;
                bestMove = mouvement;
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("Search ended for the player " + joueur + " after " + executionTime + "ms");

        return bestMove;

    }

    public int min(Board board, Joueur joueur, int depth) {
        if(depth == 0 || board.gameOver()) {
            return this.boardEvaluator.evaluate(board);
        }

        int min = Integer.MAX_VALUE;
        // TODO get all possible moves of the actual player
        for(Mouvement move : board.getAllPossibleMoves()) {
            Board boardTransition = board.update(move, joueur);
            int currentValue = max(boardTransition, joueur, depth-1);
            if(currentValue < min) min = currentValue;
        }

        return min;
    }

    public int max(Board board, Joueur joueur, int depth) {
        if(depth == 0 || board.gameOver()) {
            return this.boardEvaluator.evaluate(board);
        }

        int max = Integer.MIN_VALUE;
        // TODO get all possible moves of the actual player
        for(Mouvement move : board.getAllPossibleMoves()) {
            Board boardTransition = board.update(move, joueur);
            int currentValue = min(boardTransition, joueur, depth-1);
            if(currentValue > max) max = currentValue;
        }

        return max;
    }
}
