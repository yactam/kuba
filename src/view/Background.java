package view;

import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Background extends JComponent {
    private Image image;
    
    public Background(String filepath) throws IOException {
        image = ImageIO.read(new File(filepath));
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}