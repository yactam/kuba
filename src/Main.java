
import com.kuba.vue.MenuView;
import javax.swing.*;
import com.kuba.controller.Son;

public class Main {
    public static void main(String[] args) {
        Son son=new Son();
        JPanel menu =  new MenuView(son);
        JFrame frame = new JFrame();
        frame.add(menu);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);  
    }
}