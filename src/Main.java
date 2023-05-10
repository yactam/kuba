import javax.swing.SwingUtilities;

import com.kuba.Game;
import com.kuba.GameOnline;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> {
                new Game();
            }
        );
        
    }
}