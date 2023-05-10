package com.kuba.vue;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;

import static com.kuba.vue.GameView.screenSize;

public class BoardView extends JPanel implements Observer<Data> {
    private Data board;
    private final BilleAnimateView[][] billeAnimateViews;
    private BufferedImage grid;
    public static int HEIGHT = screenSize.height - 100;
    private Timer timer;
    private static final int sleep_time = 5;
    private Date dt;
    private boolean is_animating = false;

    public BoardView(Board board) {
        this.board = board;
        int n = board.size();
        BilleAnimateView.Diameter = HEIGHT / n;
        billeAnimateViews = new BilleAnimateView[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Bille b = board.obtenirBille(i, j);
                if (b != null) {
                    billeAnimateViews[i][j] = new BilleAnimateView(board.obtenirBille(i, j), j, i);
                }
            }
        }
        board.addObserver(this);
        setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        setSize(new Dimension(HEIGHT, HEIGHT));
        createBackgroundImage();
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, true));
    }

    private void createBackgroundImage() {
        grid = new BufferedImage(BilleAnimateView.Diameter * board.size(), BilleAnimateView.Diameter * board.size(), BufferedImage.TYPE_INT_RGB);
        Graphics g = grid.getGraphics();
        drawGrid((Graphics2D) g);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(grid, 0, 0, null);
        draw(graphics2D);
        if (!is_animating && timer != null){
            update(board);
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public void update(Data e) {
        board = e;
        for(int i = 0; i < billeAnimateViews.length; i++) {
            for(int j = 0; j < billeAnimateViews.length; j++) {
                Bille b = board.obtenirBille(i, j);
                if (b != null) {
                    billeAnimateViews[i][j] = new BilleAnimateView(board.obtenirBille(i, j), j, i);
                } else {
                    billeAnimateViews[i][j] = null;
                }
            }
        }
    }

    private TimerTask update() {
        return new TimerTask() {
            @Override
            public void run() {
                MouseInfo.getPointerInfo();
                repaint();
                dt = new Date(dt.getTime() + sleep_time);
                try {
                    timer.schedule(update(), dt);
                } catch (IllegalStateException ignored) {

                }

            }
        };
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, HEIGHT, HEIGHT);
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/images/layout.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                graphics2D.drawImage(image, j * BilleAnimateView.Diameter,
                        i * BilleAnimateView.Diameter,
                        BilleAnimateView.Diameter, BilleAnimateView.Diameter, null);
            }
        }
    }
    public void draw(Graphics2D graphics2D) {
        is_animating = false;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                BilleAnimateView b = billeAnimateViews[i][j];
                if (b != null) {
                    graphics2D.drawImage(b.image(), b.getX(), b.getY(), BilleAnimateView.Diameter,
                            BilleAnimateView.Diameter, null);

                    if (b.is_animate()) {
                        is_animating = true;
                        Position nibPose = new Position(i, j).next(b.getAnimation().getDirection());
                        b.update(
                                (estDansLimite(nibPose) && billeAnimateViews[nibPose.getI()][nibPose.getJ()] != null) ?
                                        billeAnimateViews[nibPose.getI()][nibPose.getJ()] : null);

                    }
                }
            }
        }
    }

    public void startAnimation(Position from, Direction d) {
        int i = from.getI(), j = from.getJ();
        BilleAnimateView bv = billeAnimateViews[i][j];
        if (bv != null){
            bv.createAnimation(d);
            is_animating = true;
            dt = new Date(System.currentTimeMillis() + sleep_time);
            timer = new Timer();
            timer.schedule(update(), dt);
        }
    }

    public boolean isAnimating() {
        return is_animating;
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.size() && j >= 0 && j < board.size();
    }
}