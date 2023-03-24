package com.kuba.controller;

import com.kuba.model.mouvement.Direction;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.mouvement.MoveStatus;
import com.kuba.model.mouvement.Position;
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

    public GameController(Board board, Joueur blanc, Joueur noir) {
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        ((JPanel)board.getObserver()).setFocusable(true);
    }

    private void deplacement(Direction d){
        System.out.println("Deplacement de " + from + " direction " + d);
        try{
            if(d !=null && from != null){
                MoveStatus status = board.update(new Mouvement(from, d), courant);
                if(status == MoveStatus.BASIC_MOVE){
                    changePlayer();
                }
                else if(status == MoveStatus.MOVE_OUT){
                    from = from.next(d);
                } else {
                    System.out.println("Invalid move");
                }
            }
        }
        catch(Exception e){
            System.out.println("--------------------------");
        }
    }

    private Position positionConvert(Point a){
        // Calcul selon la taille des billes
        int x = (int)a.getX()/ BoardView.billeWidth;
        int y = (int)a.getY()/BoardView.billeWidth;
        return new Position(y,x);
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
            System.out.println("Key pressed");
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
            }catch(Exception ex){
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
                            MoveStatus status = board.update(new Mouvement(from, direction), courant);
                            if(status == MoveStatus.BASIC_MOVE) {
                                changePlayer();
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

