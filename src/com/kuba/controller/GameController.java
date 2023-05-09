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

public class GameController {
    public final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private final Game game;
    private final Son son;
    private final GameView gameView;

    public GameController(Game g, Board board, GameView gameView, Joueur blanc, Joueur noir) {
        game = g;
        this.gameView = gameView;
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        son=new Son();
        initListeners();
        gameView.enableController(true);
    }

    private void initListeners() {
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        enableController(true);
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
            enableController(true);
        });

        gameView.abandonner(e -> {
            enableController(false);
            gameView.showError((courant == blanc ? noir.getNom() : blanc.getNom()) + " a gagné la partie.");
        });
    }

    private void deplacement(Direction d){
        gameView.cleanError();
        if(d !=null && from != null){
            MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
            if(moveStatus.getStatus() == MoveStatus.Status.INVALID_MOVE){
                son.playSoundEffect(2);
                gameView.showError(moveStatus.getMessage());
            }
            else {
                son.playSoundEffect(1);
                lancerAnimationBille(from, d);
                if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    checkEndGame();
                } else {
                    changePlayer();
                }
            }
        }

    }

    private void lancerAnimationBille(Position position, Direction direction){
        gameView.startAnimation(position, direction);
    }

    private void checkEndGame() {
        if(board.gameOver(blanc, noir)) {
            gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
            enableController(false);
        }
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
            aiPlayer();
        } else {
            enableController(true);
        }
    }
    public void enableController(boolean enable) {
        gameView.enableController(enable);
    }

    private void aiPlayer() {
        IA aiPlayer = (IA) courant;
        Mouvement mouvement = aiPlayer.getMouvement(board);
        MoveStatus moveStatus = aiPlayer.move(board, mouvement);
        gameView.updateScore();
        if(board.gameOver(blanc, noir)) {
            gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
            return;
        }
        if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE) changePlayer();
        else aiPlayer();
    }

    private class KeyController extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> deplacement(Direction.NORD);
                case KeyEvent.VK_LEFT -> deplacement(Direction.OUEST);
                case KeyEvent.VK_RIGHT -> deplacement(Direction.EST);
                case KeyEvent.VK_DOWN -> deplacement(Direction.SUD);
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
                    deplacement(direction);
                }
            }
        }
    };
}


