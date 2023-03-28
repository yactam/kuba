package com.kuba.vue;

import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BoardView extends JPanel implements Observer<Data> {

    private Data board;
    public static int billeWidth;
    private BufferedImage red;
    private BufferedImage black;
    private BufferedImage white;
    private int width;
    private int height;
    public BoardView(Board board) {
        this.board = board;
        board.addObserver(this);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth() ;
        height = (int) screenSize.getHeight() ;
        billeWidth =(int)((height/board.size() - 0.030*height/board.size()));
        System.out.println("Bille Width : "+billeWidth);
        initBille();
        setPreferredSize(new Dimension(width,height));
    }

    private void initBille() {
        loadImages();
    }

    private void loadImages() {
        createBackgroundImage();
        try {
            red = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/red.png")));
            black = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/black.png")));
            white = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/white.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBackgroundImage() {
        BufferedImage background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = background.getGraphics();
        drawGrid((Graphics2D) g);
    }

    public void updateBoard(Board board) {
        this.board = board;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(!board.board(j, i).estVide()) {
                    Image image = switch (board.board(j, i).getBille().getColor()) {
                        case BLANC -> white;
                        case ROUGE -> red;
                        case NOIR -> black;
                    };
                    Image img = image.getScaledInstance(billeWidth, billeWidth, Image.SCALE_SMOOTH);
                    graphics2D.drawImage(img, billeWidth * i, billeWidth * j, billeWidth, billeWidth, null);
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        
        graphics2D.fillRect(0, 0, width, height);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(i != board.size()-1 && j != board.size()-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * billeWidth + (billeWidth / 2), i * billeWidth + (billeWidth / 2), billeWidth, billeWidth);
                }
            }
        }
    }

    @Override
    public void update(Data e){
        board = e;
        this.repaint();
    }

}
