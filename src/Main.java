
import model.plateau.Board;
import controller.*;
import javax.swing.*;
import view.*;
public class Main {
    public static void main(String[] args) {

        Board mod = new Board(3);
        mod.initBoard();
        BoardView v = new BoardView(mod);
        mod.addObserver(v);
        BoardController c = new BoardController(mod);
        c.testEnterPanel();

        JFrame frame = new JFrame();
        frame.setTitle("Plateau Kuba"); 
        frame.add(v);
        frame.pack();
        frame.show();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}