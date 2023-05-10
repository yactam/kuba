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
    private final String ip;
    private int port;
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
        System.out.println("Please input the IP: ");
        Scanner scanner = new Scanner(System.in);
        ip = scanner.nextLine();
        System.out.println("Please input the port: ");
        port = scanner.nextInt();
        if(!connect()) initializeServer();
        else current = j2;
        while (port < 1 || port > 65535) {
            System.out.println("The port you entered was invalid, please input another port: ");
            port = scanner.nextInt();
        }

        if(dos!=null)  {
            int n = 2;
            game = new Game();
            game.moveToBoard(n, j1, j2);
            new GameView(game, 2, j1, j2);
            onlineController = new OnlineController(game, j1, j2, dos, current);
        }

        System.out.println(" 1 - Verification Joueur server : "+ j1.equals(current));
        System.out.println(" 2 - Verification Joueur connect : "+ j2.equals(current));

        Thread thread = new Thread(this, "Online");
        thread.start();
    }

    public void run(){
        while(true){
            if(game !=null)
            if(dos != null) play();
            if (!colorC && !accepted) {
                listenForServerRequest();
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
            socket = serverSocket.accept();
            dos = new ObjectOutputStream(socket.getOutputStream());
            dis = new ObjectInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            game = new Game();
            game.setTitle("Server Board");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new ObjectOutputStream(socket.getOutputStream());
            dis = new ObjectInputStream(socket.getInputStream());
            accepted = true;
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
            accepted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        colorC = false;
        current = j1;
    }
}
