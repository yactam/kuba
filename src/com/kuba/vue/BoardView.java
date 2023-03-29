package com.kuba.vue;


import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class BoardView extends JPanel implements Observer<Data> {

    private Data board;
    public static final int billeWidth = 80;
    private BufferedImage red;
    private BufferedImage black;
    private BufferedImage white;
    private Timer timer;
    private static int sleep_time = 5;
    private Date dt;


    public BoardView(Board board) {
        timer = new Timer();
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
    /* 
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
*/
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
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        animate(graphics2D);
    }

    public void StatAnimation(){
        dt = new Date (System.currentTimeMillis () + sleep_time);
        timer.schedule(update(), dt);

    }

    private TimerTask update(){
        return new TimerTask() {
            @Override
            public void run() {
                MouseInfo.getPointerInfo ();
                repaint();
                dt = new Date (dt.getTime () + sleep_time);
		        timer.schedule(update (), dt);
            }
        };   
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.size() && j >= 0 && j < board.size();
    }

    public void animate(Graphics2D graphics2D){
        for (int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                Bille b = board.board(i, j).getBille();
                if (b != null){
                    graphics2D.drawImage(b.image(), b.getX()+(Bille.scale/2), 
                                                    b.getY()+(Bille.scale/2),Bille.width-Bille.scale, 
                                                    Bille.width-Bille.scale, null);
                    if (b.is_animate()){
                        Position neibPos = new Position(i, j).next(b.getAnimation().getDirection());
                        b.update(
                            estDansLimite(neibPos) ? board.board(neibPos.getI(), neibPos.getJ()).getBille():null
                        );

                    }
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
