package com.kuba.online;

import com.kuba.Game;
import com.kuba.controller.GameController;
import com.kuba.controller.Son;
import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.vue.BoardView;
import com.kuba.vue.GameView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Online implements Runnable{
    private final String ip="localhost";
    private int port= 22222;
    private Socket socket;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;
    private ServerSocket serverSocket;
    private boolean accepted = false;
    private int errors = 0;
    private final Joueur j1 = new Joueur("Joueur 1", Couleur.BLANC), j2 =  new Joueur("Joueur 2", Couleur.NOIR);
    public Joueur current;
    private boolean colorC = true;
    private Game game;
    private OnlineController onlineController;

    public Online() {
        System.out.println("Online");
        if(!connect()) initializeServer();
        else current = j2;

        if( dos != null )  {
            System.out.println("Online P ");
            int n = 2;
            game = new Game();
            onlineController = new OnlineController(game, j1, j2, dos, current);
            game.setContentPane(onlineController.view);
        }

        System.out.println(" 1 - Verification Joueur server : "+ j1.equals(current));
        System.out.println(" 2 - Verification Joueur connect : "+ j2.equals(current));

        Thread thread = new Thread(this, "Online");
        thread.start();

    }

    public void run(){
        while(true){
            if(game !=null) onlineController.view.repaint();
            if(dos != null) play();
            
            if (!colorC && !accepted) {
                System.out.println(" run wait ");
                listenForServerRequest();
            }
            else{
                System.out.println(" !! ");
            }
        }
    }
    private void play() {
        if (errors >= 10) {
            System.exit(1);
        }
        try {
            if(!onlineController.verifTo() && onlineController.getCourant() != current){
                System.out.println("Not your turn");
                Mouvement objectReceived = (Mouvement) dis.readObject();
                onlineController.serverGestion(objectReceived);
            }
            else{
                System.out.println(" your turn! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors++;
        }
    }

    private void listenForServerRequest() {
        socket = null;
        try {
            System.out.println(" ............... ");
            socket = serverSocket.accept();
            dos = new ObjectOutputStream(socket.getOutputStream());
            dis = new ObjectInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            game = new Game();
            onlineController = new OnlineController(game, j1, j2, dos, current);
            game.setContentPane(onlineController.view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {
        try {
            System.out.println("Online 2");
            socket = new Socket(ip, port);
            System.out.println("Online 3");
            dis = new ObjectInputStream(socket.getInputStream());
            dos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Online 4");
           
            accepted = true;
            System.out.println("Online 5");
            
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
            //accepted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        colorC = false;
        current = j1;
    }
}
