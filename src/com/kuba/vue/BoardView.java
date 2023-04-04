package com.kuba.vue;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.observerpattern.Data;
import com.kuba.observerpattern.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class BoardView extends JPanel implements Observer<Data> {

    private Data board;
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int HEIGHT = screenSize.height - 100;
    private Timer timer;
    private static final int sleep_time = 5;
    private Date dt;
    private boolean is_animating = false;

    public BoardView(Board board) {
        this.board = board;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                Bille b = board.obtenirBille(i, j);
                if (b != null) {
                    b.setBV(new BilleAnimateView(b, j, i));
                }
            }
        }
        board.addObserver(this);
        setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        setSize(new Dimension(HEIGHT, HEIGHT));
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, HEIGHT, HEIGHT);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (i != board.size() - 1 && j != board.size() - 1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * BilleAnimateView.width + (BilleAnimateView.width / 2),
                            i * BilleAnimateView.width + (BilleAnimateView.width / 2),
                            BilleAnimateView.width, BilleAnimateView.width);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        draw(graphics2D);
        if (!is_animating && timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    public void statrAnimation(Position from, Direction d) {
        Bille b = board.obtenirBille(from.getI(), from.getJ());
        if (b!=null && b.getBV()!=null)
            b.getBV().createAnimation(d);
        is_animating = true;
        dt = new Date(System.currentTimeMillis() + sleep_time);
        timer = new Timer();
        timer.schedule(update(), dt);
    }

    private TimerTask update() {
        return new TimerTask() {
            @Override
            public void run() {
                MouseInfo.getPointerInfo();
                repaint();
                dt = new Date(dt.getTime() + sleep_time);
                timer.schedule(update(), dt);
            }
        };
    }

    private boolean estDansLimite(Position position) {
        int i = position.getI(), j = position.getJ();
        return i >= 0 && i < board.size() && j >= 0 && j < board.size();
    }

    public void draw(Graphics2D graphics2D) {
        is_animating = false;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                BilleAnimateView b = null;
                if (board.obtenirBille(i, j) != null) b = board.obtenirBille(i, j).getBV();
                if (b != null) {
                    graphics2D.drawImage(b.image(), b.getX(),
                            b.getY(), BilleAnimateView.width,
                            BilleAnimateView.width, null);

                    if (b.is_animate()) {
                        is_animating = true;
                        Position neibPos = new Position(i, j).next(b.getAnimation().getDirection());
                        Bille bille = board.obtenirBille(neibPos.getI(), neibPos.getJ());
                        b.update(
                                (estDansLimite(neibPos) && bille != null) ? bille.getBV() : null);

                    }
                }
            }
        }
    }

    public BilleAnimateView getAnimatedBille(int i, int j) {
        if (board.obtenirBille(i, j) != null){
            return board.obtenirBille(i, j).getBV();
        }
        return null;
    }

    @Override
    public void update(Data e) {
        board = e;
        this.repaint();
    }

}