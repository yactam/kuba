package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import observerpattern.*;
import model.plateau.*;
import model.Bille;

public class BoardView extends JPanel implements Observer<BoardChangedEvent>{
    private Cell[][] board;
    public static final int billeWidth = 50;
    private BufferedImage red, black, white;

    public BoardView(Board board) {
        this.board = board.getCellBoard();
        initBille();
        setPreferredSize(new Dimension(billeWidth * board.size(), billeWidth * board.size()));
        this.board = new Cell[board.getN()*4- 1][board.getN()*4- 1];
    }

    private void initBille() {
        loadImages();
    }

    private void loadImages() {
        try {
            red = ImageIO.read(new File("src/resources/red.png"));
            black = ImageIO.read(new File("src/resources/black.png"));
            white = ImageIO.read(new File("src/resources/white.png"));
        } catch (IOException e) {
            System.out.println("Erreur load Images");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(g);
        drawGrid(graphics2D);
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(!board[j][i].estVide()) {
                    Bille bille = board[j][i].getBille();
                    BufferedImage image = switch (bille.getColor()) {
                        case NOIR -> black;
                        case ROUGE -> red;
                        case BLANC -> white;
                    };
                    graphics2D.drawImage(image, billeWidth * i, billeWidth * j, billeWidth, billeWidth, null);
                }
            }
        }
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(i != board.length-1 && j != board.length-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * billeWidth + (billeWidth / 2), i * billeWidth + (billeWidth / 2), billeWidth, billeWidth);
                }
            }
        }
    }

    @Override
    public void update(BoardChangedEvent e){
        board = e.getNewcells();
        this.repaint();
    }
}
