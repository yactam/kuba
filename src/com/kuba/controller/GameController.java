package com.kuba.controller;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
import com.kuba.model.plateau.Bille;
import com.kuba.model.plateau.Board;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {

    private final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private Direction direction;
    private BoardView boardView;

    public GameController(Board board, BoardView boardView, Joueur blanc, Joueur noir) {
        this.boardView = boardView;
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        ((JPanel)board.getObserver()).setFocusable(true);
    }

    private void deplacement(Direction d){
        boolean move = false;
        try{
            if(d !=null && from != null){
                MoveStatus moveStatus = board.update(new Mouvement(from, d), courant);
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    changePlayer();
                    move = true;
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    from = from.next(d);
                    move = true;
                } else {
                    System.out.println(moveStatus.getMessage());
                }
                if (move) lancerAnimationBille();
            }
        }
        catch(Exception e){
            System.out.println("--------------------------");
        }
    }

    private void lancerAnimationBille(){
        boardView.statrAnimation(from);
    }

    private Position positionConvert(Point a){
        for (int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                if (board.board(i, j).contains(a.x, a.y)){
                    return new Position(i, j);
                }
            }
        }

        return null;
    }

    public void changePlayer(){
        if(courant == blanc){
            courant = noir;
        }else{
            courant = blanc;
        }
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
                System.out.println(from);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(((JPanel)board.getObserver()).contains(e.getPoint())){
                Position to = positionConvert(e.getPoint());
                if(from != null){
                    direction = from.nextDir(to);
                    try{
                        if(direction != null && from != null){
                            MoveStatus moveStatus = board.update(new Mouvement(from, direction), courant);
                            if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE) {
                                changePlayer();
                            } else if(moveStatus.isLegal()) {
                                System.out.println(moveStatus.getMessage());
                            }
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


