package com.kuba.online;

import com.kuba.Game;
import com.kuba.controller.Son;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.vue.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.kuba.vue.GameView.HEIGHT;
import static com.kuba.vue.GameView.WIDTH;

public class OnlineController {
    public ObjectOutputStream gameOutput;
    private final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private final Son son;
    private final Joueur jOnlineValidation;
    public Background view;
    private boolean turn=false;
    public BoardView boardView;
    private PlayersPanel playersPanel;

    public OnlineController(Game g, Joueur blanc, Joueur noir, ObjectOutputStream out, Joueur j) {
        this.board = new Board(2);
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        initView();
        son=new Son();
        initListeners();
        this.gameOutput = out;
        jOnlineValidation = j;
        enableController(true);
    }

    private void initView() {
        this.view = new Background("src/resources/images/table.jpg", new Dimension(WIDTH, HEIGHT));
        view.setLayout(null);
        view.setSize(new Dimension(WIDTH, HEIGHT));
        view.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        boardView = new BoardView(board);
        playersPanel = new PlayersPanel(blanc, noir, WIDTH, HEIGHT);

        boardView.setLocation((int) (0.1 * WIDTH), (int) (0.05 * HEIGHT));
        playersPanel.setLocation((int) (0.65 * WIDTH), (int) (0.05 * HEIGHT));

        view.add(boardView);
        view.add(playersPanel);
    }

    private void initListeners() {
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        ((JPanel)board.getObserver()).setFocusable(true);
        ((JPanel)board.getObserver()).requestFocusInWindow();
    }

    public void enableController(boolean enable) {
        view.setFocusable(enable);
        if(enable) view.requestFocusInWindow();
    }

    private Position positionConvert(Point a){
        return new Position(a.y / BilleAnimateView.Diameter, a.x / BilleAnimateView.Diameter);
    }

    private boolean checkEndGame() {
        if (board.gameOver(blanc, noir)) {
            //gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
            enableController(false);
            return true;
        }
        return false;
    }

    private void deplacement(Direction d) {

        if(courant.equals(jOnlineValidation)){
            turn = true;
            if(d !=null && from != null){
                MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    lancerAnimationBille(from, d);
                    son.playSoundEffect(1);
                    turn=false;
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    lancerAnimationBille(from, d);
                    son.playSoundEffect(1);
                    //turn = false;
                } else {
                    son.playSoundEffect(3);
                    System.out.println(moveStatus.getMessage());
                    son.playSoundEffect(2);
                }

                try {
                    // Gestion partage des données de la partie
                    Mouvement send =  new Mouvement(from, d);
                    gameOutput.writeObject(send);
                    gameOutput.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("DATA SENT");
                ((JPanel)board.getObserver()).setFocusable(false);
                ((JPanel)board.getObserver()).setEnabled(false);
                if(!turn)courant = (courant.equals(blanc)) ? noir : blanc;
            }
        }
        else{
            playersPanel.showError(" Not your turn ");
        }

    }

    public boolean verifTo(){
        return this.turn;
    }


    public void changePlayer(){
        playersPanel.cleanError();
        if(courant == blanc){
            courant = noir;
            playersPanel.setCurrent(2);
        }else{
            courant = blanc;
            playersPanel.setCurrent(1);
        }
    }

    public void serverGestion(Mouvement mv){
        try{
            if(mv != null){
                System.out.println("ServerGestion");

                Joueur j = (jOnlineValidation.equals(blanc)) ? noir : blanc;
                MoveStatus moveStatus = j.move(board, mv);
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    board.notifyObservers();
                    son.playSoundEffect(1);
                    ((JPanel)board.getObserver()).setFocusable(true);
                    ((JPanel)board.getObserver()).setEnabled(true);
                    changePlayer();
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    board.notifyObservers();
                    son.playSoundEffect(1);
                } else {
                    son.playSoundEffect(3);
                    System.out.println(moveStatus.getMessage());
                    son.playSoundEffect(2);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("--------------------------");
        }
    }


    private void lancerAnimationBille(Position position, Direction direction){
        boardView.startAnimation(position, direction);
    }

    public Joueur getCourant() {
        return courant;
    }

    private class KeyController extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            try{
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> deplacement(Direction.NORD);
                    case KeyEvent.VK_LEFT -> deplacement(Direction.OUEST);
                    case KeyEvent.VK_RIGHT -> deplacement(Direction.EST);
                    case KeyEvent.VK_DOWN -> deplacement(Direction.SUD);
                }
            }
            catch(Exception ex){
                son.playSoundEffect(3);
                System.out.println("^");
            }
        }
    }



    private class MouseController extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                from = positionConvert(e.getPoint());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                Position to = positionConvert(e.getPoint());
                Direction direction = from.nextDir(to);
                if(from != null && direction != null){
                    try {
                        deplacement(direction);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    };
}
