package com.kuba.controller;

import com.kuba.Game;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.IA;
import com.kuba.vue.BilleAnimateView;
import com.kuba.vue.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Thread.sleep;

public class GameController {
    public final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private final Game game;
    private final Son son;
    private final GameView gameView;
    private Thread threadAI;
    public GameController(Game g, Board board, GameView gameView, Joueur blanc, Joueur noir) {
        game = g;
        this.gameView = gameView;
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        son=new Son();
        initListeners();
        enableController(true);
        threadAI = CreateAiThread();
    }

    private void initListeners() {
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        gameView.recommencer(e -> {
            blanc.resetScore();
            noir.resetScore();
            game.moveToBoard((board.size()+1) / 4, blanc, noir);
        });

        gameView.menu(e -> {
            son.stopMusic();
            game.moveToMenu();
        });

        gameView.mute(e -> {
            gameView.unmute(son.isPlaying());
            if(son.isPlaying()) {
                son.stopMusic();
            } else {
                son.playMusic(0);
            }
        });

        gameView.abandonner(e -> {
            enableController(false);
            gameView.showError((courant == blanc ? noir.getNom() : blanc.getNom()) + " a gagné la partie.");
        });
    }

    public void enableController(boolean enable) {
        gameView.enableController(enable);
    }

    private void deplacement(Direction d) throws InterruptedException {
        gameView.cleanError();
        if (d != null && from != null) {
            MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
            if (moveStatus.getStatus() == MoveStatus.Status.INVALID_MOVE) {
                son.playSoundEffect(2);
                gameView.showError(moveStatus.getMessage());
            } else {
                son.playSoundEffect(1);
                lancerAnimationBille(from, d);
                if (moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT) {
                    from = from.next(d);
                    gameView.updateScore();
                    checkEndGame();
                } else {
                    changePlayer();
                }
            }
        }
    }

    private Thread CreateAiThread(){
        return new Thread(this::aiPlayer);
    }

    private void lancerAnimationBille(Position position, Direction direction){
        gameView.startAnimation(position, direction);
    }

    private boolean checkEndGame() {
        if (board.gameOver(blanc, noir)) {
            gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
            enableController(false);
            return true;
        }
        return false;
    }

    private Position positionConvert(Point a){
        return new Position(a.y / BilleAnimateView.Diameter, a.x / BilleAnimateView.Diameter);
    }

    public void changePlayer(){
        gameView.cleanError();
        if(courant == blanc){
            courant = noir;
            gameView.changePlayer(2);
        }else{
            courant = blanc;
            gameView.changePlayer(1);
        }
        if(courant instanceof IA) {
            enableController(false);
            threadAI = CreateAiThread();
            threadAI.start();//start the thread
        } else {
            enableController(true);
        }
    }

    private void pause(long millis){
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void aiPlayer() {
        while (gameView.isAnimating()){
            pause(1);
        }
        pause(300);
        IA aiPlayer = (IA) courant;
        Mouvement mouvement = aiPlayer.getMouvement(board);
        MoveStatus moveStatus = aiPlayer.move(board, mouvement);
        lancerAnimationBille(mouvement.getPosition(), mouvement.getDirection());
        gameView.updateScore();
        if(checkEndGame()) return;
        if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
            changePlayer();
            threadAI.interrupt();
        }
        else aiPlayer();
    }

    private class KeyController extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            try {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> deplacement(Direction.NORD);
                    case KeyEvent.VK_LEFT -> deplacement(Direction.OUEST);
                    case KeyEvent.VK_RIGHT -> deplacement(Direction.EST);
                    case KeyEvent.VK_DOWN -> deplacement(Direction.SUD);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
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


