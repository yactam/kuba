package com.kuba.controller;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import static java.lang.Thread.sleep;

public class GameController {
    public ObjectOutputStream gameOutput;
    public final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private Direction direction;
    private Son son;
    private final BoardView boardView;
    private boolean online=false;
    private Joueur jOnlineValidation;
    private boolean turn=false;
    private int lenTabBytes;

    public GameController(Board board,BoardView boardView, Joueur blanc, Joueur noir, Son son) {
        this.boardView = boardView;
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        this.son = son;
        if(!son.mute){
            son.playMusic(0);
        }
        ((JPanel)board.getObserver()).addMouseListener(new MouseController());
        ((JPanel)board.getObserver()).addKeyListener(new KeyController());
        ((JPanel)board.getObserver()).setFocusable(true);
        ((JPanel)board.getObserver()).requestFocusInWindow();
    }

    public GameController(Board board,BoardView boardView, Joueur blanc, Joueur noir, Son son,boolean online, ObjectOutputStream out, Joueur j) {
        this(board,boardView,blanc,noir,son);
        System.out.println("Online");
        this.online = online;
        jOnlineValidation = j;
        System.out.println(online);
        this.gameOutput = out;
    }


    private void deplacement(Direction d){
        try{
            if(!online){
                if(d !=null && from != null){
                    MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                    if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                        lancerAnimationBille();
                        son.playSoundEffect(1);
                        changePlayer();
                    }
                    else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                        lancerAnimationBille();
                        son.playSoundEffect(1);
                        changePlayer();
                    } else {
                    son.playSoundEffect(3);
                        System.out.println(moveStatus.getMessage());
                        son.playSoundEffect(2);
                    }
                }
            }
            else{
                System.out.println("Verification Joueur "+ courant.equals(jOnlineValidation));
                if(courant.equals(jOnlineValidation)){
                    turn = true;
                    if(d !=null && from != null){
                        MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                        if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                            lancerAnimationBille();
                            son.playSoundEffect(1);
                            turn=false;
                        }
                        else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                            lancerAnimationBille();
                            son.playSoundEffect(1);
                            turn = false;
                        } else {
                        son.playSoundEffect(3);
                            System.out.println(moveStatus.getMessage());
                            son.playSoundEffect(2);
                        }

                        try {
                            // Gestion partage des données de la partie
							gameOutput.writeObject(board.getCells());
							gameOutput.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        System.out.println("DATA SENT");
                        // Verifier si la partie termine 
                    }
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

    public void serverGestion(int i, int j, Direction d){
        try{
            BilleAnimateView bv = boardView.getAnimatedBille(i, j);
            from = new Position(bv.getY() / BilleAnimateView.width,bv.getX() / BilleAnimateView.width);
            direction = d;
            if(d !=null && from != null){
                MoveStatus moveStatus = courant.move(board, new Mouvement(from, d));
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    lancerAnimationBille();
                    son.playSoundEffect(1);
                    changePlayer();
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    lancerAnimationBille();
                    son.playSoundEffect(1);
                    changePlayer();
                } else {
                   son.playSoundEffect(3);
                    System.out.println(moveStatus.getMessage());
                    son.playSoundEffect(2);
                }
            }
        }
        catch(Exception e){
            //e.printStackTrace();
            System.out.println("--------------------------");
        } 
    }

    private void completebyte(int x){
        this.lenTabBytes = x;
    }
    public int getlenTabBytes(){
        return this.lenTabBytes;
    }

    public void changePlayer(){
        if(courant == blanc){
            courant = noir;
        }else{
            courant = blanc;
        }
        if(courant instanceof IA) {
            boardView.setFocusable(false);
            aiPlayer();
        } else {
            boardView.setFocusable(true);
            boardView.requestFocusInWindow();
        }
    }

    public Joueur getCourant(){
        return this.courant;
    }

    public boolean verifTo(){
        return this.turn;
    }

    private void aiPlayer() {
        IA aiPlayer = (IA) courant;
        Mouvement mouvement = aiPlayer.getMouvement(board);
        MoveStatus moveStatus = aiPlayer.move(board, mouvement);
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
                    System.out.println(direction);
                    try{
                        if(direction != null && from != null){
                            MoveStatus moveStatus = courant.move(board, to, direction);
                            if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE) {
                                changePlayer();
                                son.playSoundEffect(1);
                                lancerAnimationBille();
                            } else if(!moveStatus.isLegal()) {
                               son.playSoundEffect(1);
                            } else if(moveStatus.isLegal()) {
                               son.playSoundEffect(3);
                                System.out.println(moveStatus.getMessage());
                            }
                        }
                    }
                    catch(Exception exception){
                        son.playSoundEffect(3);
                        System.out.print("_");
                    }
                }
            }
        }
    };
}


