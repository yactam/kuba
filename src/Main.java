
import com.kuba.vue.MenuView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JPanel menu =  new MenuView();
        JFrame frame = new JFrame();
        frame.add(menu);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}