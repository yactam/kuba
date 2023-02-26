package vue;


import model.Bille;
import model.plateau.Board;
import model.plateau.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BoardView extends JPanel {

    private Board board;
    public static final int billeWidth = 80;
    private BufferedImage red, black, white, background;

    public BoardView(Board board) {
        this.board = board;
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
        background = new BufferedImage(billeWidth * board.size(), billeWidth * board.size(), BufferedImage.TYPE_INT_RGB);
        Graphics g = background.getGraphics();
        drawGrid((Graphics2D) g);
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(g);
        graphics2D.drawImage(background, 0, 0, null);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(!board.board(j, i).estVide()) {
                    Bille bille = board.board(j, i).getBille();
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
        graphics2D.fillRect(0, 0, billeWidth * board.size(), billeWidth * board.size());
        graphics2D.setColor(Color.BLACK);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(i != board.size()-1 && j != board.size()-1) {
                    graphics2D.drawRect(j * billeWidth + (billeWidth / 2), i * billeWidth + (billeWidth / 2), billeWidth, billeWidth);
                }
            }
        }
    }
}
