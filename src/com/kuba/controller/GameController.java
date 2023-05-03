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
import java.io.ObjectOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;

public class GameController {
    public ObjectOutputStream gameOutput;
    public final Board board;
    private final Joueur blanc, noir;
    private Joueur courant;
    private Position from;
    private Direction direction;
    private final Game game;
    private final Son son;
    private final GameView gameView;
    private final BoardView boardView;
    private boolean online=false;
    private Joueur jOnlineValidation;
    private boolean turn=false;
    private int lenTabBytes;

    public GameController(Game g, Board board, GameView gameView, Joueur blanc, Joueur noir) {
        game = g;
        this.gameView = gameView;
        this.boardView = gameView.boardview();
        this.board = board;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        son=new Son();
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

    
    public GameController(Game g, Board board,BoardView boardView,GameView gv,Joueur blanc, Joueur noir,boolean online, ObjectOutputStream out, Joueur j) {
        game = g;
        this.board=board;
        System.out.println("Online");
        son=new Son();
        this.gameView = gv;
        this.boardView = boardView;
        this.blanc = blanc;
        this.noir = noir;
        this.courant = blanc;
        initListeners();
        this.online = online;
        jOnlineValidation = j;
        System.out.println(online);
        this.gameOutput = out;
        initListeners();
    }
    


    private void deplacement(Direction d){
        gameView.cleanError();
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
                System.out.println(" Verification Joueur deplacement "+ courant.equals(jOnlineValidation));
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
                            Mouvement send =  new Mouvement(from, d);
							gameOutput.writeObject(send);
							gameOutput.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        System.out.println("DATA SENT");
                        ((JPanel)board.getObserver()).setFocusable(false);
                        ((JPanel)board.getObserver()).setEnabled(false);
                        courant = (courant.equals(blanc))? noir : blanc;
                    }
                }
                if(board.gameOver(blanc, noir)) {
                    gameView.showError(board.getWinner(blanc, noir).getNom() + " a gagné la partie.");
                    enableController(false);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
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

    public void serverGestion(Mouvement mv){
        try{
            if(mv != null){
                Joueur j = (jOnlineValidation.equals(blanc)) ? noir : blanc;
                MoveStatus moveStatus = j.move(board, mv);
                if(moveStatus.getStatus() == MoveStatus.Status.BASIC_MOVE){
                    //lancerAnimationBille();
                    board.notifyObservers();
                    son.playSoundEffect(1);
                    ((JPanel)board.getObserver()).setFocusable(true);
                    ((JPanel)board.getObserver()).setEnabled(true);
                    changePlayer();
                }
                else if(moveStatus.getStatus() == MoveStatus.Status.MOVE_OUT){
                    //lancerAnimationBille();
                    board.notifyObservers();
                    son.playSoundEffect(1);
                    ((JPanel)board.getObserver()).setFocusable(true);
                    ((JPanel)board.getObserver()).setEnabled(true);
                    changePlayer();
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

    private void completebyte(int x){
        this.lenTabBytes = x;
    }
    public int getlenTabBytes(){
        return this.lenTabBytes;
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

    public Joueur getCourant(){
        return this.courant;
    }

    public boolean verifTo(){
        return this.turn;
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
                            deplacement(direction);
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


