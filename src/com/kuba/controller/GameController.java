package com.kuba.controller;

import com.kuba.Game;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.ai.IA;
import com.kuba.vue.BilleAnimateView;
import com.kuba.vue.BoardView;
import com.kuba.vue.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import static java.lang.Thread.sleep;

public class GameController {

    private final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private Direction direction;
    private final Game game;
    private final Son son;
    private final GameView gameView;
    private final BoardView boardView;

    public GameController(Game g, Board board, GameView gameView, Joueur blanc, Joueur noir) {
        game = g;
        this.gameView = gameView;
        this.boardView = gameView.boardview();
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        son = new Son();
        son.playMusic(0);
        initListeners();

    }

    private void initListeners() {
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        enableController(true);
        gameView.recommencer(e -> {
            blanc.setNbAdversaireCapturee(0);
            blanc.setNbRougesCapturee(0);
            noir.setNbAdversaireCapturee(0);
            noir.setNbRougesCapturee(0);
            game.moveToBoard((board.size()+1) / 4, blanc, noir);
        });

        gameView.mute(e -> {
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
        try{
            if(d !=null && from != null){
                MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    lancerAnimationBille();
                    son.playSoundEffect(1);
                    from = from.next(d);
                    changePlayer();
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    lancerAnimationBille();
                    son.playSoundEffect(1);
                    from = from.next(d);
                    gameView.updateScore();
                } else {
                    gameView.showError(moveStatus.getMessage());
                    son.playSoundEffect(2);
                }
                if(board.gameOver(blanc, noir)) {
                    gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
                    enableController(false);
                }
            }
        }
        catch(Exception e){
            System.out.println("--------------------------");
        }
    }

    private void lancerAnimationBille(){
        boardView.startAnimation(from, direction);
    }

    private Position positionConvert(Point a){
        for (int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                BilleAnimateView bv = boardView.getAnimatedBille(i, j);
                if (bv != null && bv.contains(a.x, a.y)) {
                    return new Position(bv.getY() / BilleAnimateView.width,
                                        bv.getX() / BilleAnimateView.width);
                }
            }
        }

        return null;
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
        boardView.setFocusable(enable);
        if(enable) boardView.requestFocusInWindow();
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
            try{
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> {
                        direction = Direction.NORD;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_LEFT -> {
                        direction = Direction.OUEST;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_RIGHT -> {
                        direction = Direction.EST;
                        deplacement(direction);
                    }
                    case KeyEvent.VK_DOWN -> {
                        direction = Direction.SUD;
                        deplacement(direction);
                    }
                }
            }
            catch(Exception ex){
                System.out.println("^");
            }
        }
    }

    private class MouseController extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                from = positionConvert(e.getPoint());
                //System.out.println(from);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                Position to = positionConvert(e.getPoint());
                if(from != null){
                    assert to != null;
                    direction = from.nextDir(to);
                    try{
                        if(direction != null && from != null){
                            deplacement(direction);
                        }
                    }
                    catch(Exception exception){
                        System.out.print("_");
                    }
                }
            }
        }
    };
}


