package com.kuba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.kuba.online.*;

public class GameOnline extends JFrame {
    private JButton bouton;

    public GameOnline() {
        super("Online");
        setContentPane(new ColorPanel());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - bouton.getSize().width) / 2;
        int y = (dim.height - bouton.getSize().height) / 2;
        bouton.setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect = ge.getMaximumWindowBounds();

        int x2 = (screenRect.width - this.getWidth()) / 2;
        int y2 = (screenRect.height - this.getHeight()) / 2;

        this.setLocation(x2, y2);
        
        setVisible(true);
        new Controller();
    }

    private class Controller extends MouseAdapter {
        public Controller() {
            bouton.addActionListener(e -> {
               new Online();
               dispose();
            });
        }
    }

    private class ColorPanel extends JPanel {
        public ColorPanel() {
            setBackground(new Color(119, 81, 44));
    
            ImageIcon icon = new ImageIcon("src/resources/images/onllineButton.png");
            bouton = new JButton(icon);
            bouton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            bouton.setOpaque(false);
            bouton.setContentAreaFilled(false);
            bouton.setBorderPainted(false);
            add(bouton);
    
            setLayout(new GridBagLayout());
            add(bouton, new GridBagConstraints());
            bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bouton.setAlignmentY(Component.CENTER_ALIGNMENT);
        }
    
    }

    public static void main(String[] args) {
        new GameOnline();
    }

}