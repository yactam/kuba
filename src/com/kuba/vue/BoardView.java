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
    public static final int billeWidth = 80;
    private BufferedImage red;
    private BufferedImage black;
    private BufferedImage white;

    public BoardView(Board board) {
        this.board = board;
        board.addObserver(this);
        initBille();
        setPreferredSize(new Dimension(billeWidth * board.size(), billeWidth * board.size()));
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
        BufferedImage background = new BufferedImage(billeWidth * board.size(), billeWidth * board.size(), BufferedImage.TYPE_INT_RGB);
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
                    int width = 598/board.size();
                    Image image = switch (board.board(j, i).getBille().getColor()) {
                        case BLANC -> white;
                        case ROUGE -> red;
                        case NOIR -> black;
                    };
                    Image img = image.getScaledInstance(width, width, Image.SCALE_SMOOTH);
                    graphics2D.drawImage(img, width * i, width * j, width, width, null);
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, 598, 598);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(i != board.size()-1 && j != board.size()-1) {
                    graphics2D.setColor(Color.BLACK);
                    int width = 598/board.size();
                    graphics2D.drawRect(j * width + (width / 2), i * width + (width / 2), width, width);
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
