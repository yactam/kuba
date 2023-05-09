package com.kuba.vue;

import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Background extends JPanel {
    private final Image image;
    private final int width, height;
    public final static String absPath = System.getProperty("user.dir") 
                        + "/2022-vp2-gb-kuba-board-game/";
    
    public Background(String filepath, Dimension dimension) {
        try {
            image = ImageIO.read(new File(absPath+filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLayout(null);
        setSize(dimension);
        setPreferredSize(dimension);
        this.width = dimension.width;
        this.height = dimension.height;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, this);
    }
}