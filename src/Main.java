import model.plateau.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(17);

        board.initBoard();
        System.out.println(board);
    }
}