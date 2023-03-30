package com.kuba.vue;


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
    private final Timer timer;
    private static final int sleep_time = 5;
    private Date dt;
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int HEIGHT = screenSize.height - 40;
    public final int billeWidth;


    public BoardView(Board board) {
        timer = new Timer();
        this.board = board;
        board.addObserver(this);
        billeWidth = HEIGHT / board.size();
        setPreferredSize(new Dimension(billeWidth * board.size(), billeWidth * board.size()));
        setSize(new Dimension(billeWidth * board.size(), billeWidth * board.size()));
        StatAnimation();
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, HEIGHT, HEIGHT);
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.size(); j++) {
                if(i != board.size()-1 && j != board.size()-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * billeWidth + (billeWidth / 2), i * billeWidth + (billeWidth / 2),
                            billeWidth, billeWidth);
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
                Bille b = board.board(j, i).getBille();
                if (b != null){
                    graphics2D.drawImage(b.image(), (int) (b.getX() * billeWidth), (int) (b.getY() * billeWidth), billeWidth, billeWidth, null);

                    if (b.is_animate()){

                        Position neibPos = new Position(j, i).next(b.getAnimation().getDirection());
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
