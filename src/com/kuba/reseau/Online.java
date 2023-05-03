package com.kuba.reseau;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.awt.BasicStroke;
import  com.kuba.model.mouvement.*;
import  com.kuba.model.plateau.*;
import  com.kuba.model.player.*;
import  com.kuba.model.player.ai.*;
import com.kuba.controller.*;
import com.kuba.vue.*;
import com.kuba.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class Online implements Runnable{
    private String ip = "localhost";
	private int port = 22222;
	//private Scanner scanner = new Scanner(System.in);
    private Thread thread;

    private Socket socket;
	private ObjectOutputStream dos;
	private ObjectInputStream dis;

	private ServerSocket serverSocket;

    private boolean yourTurn = false;
	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent = false;
	private boolean won = false;
	private boolean enemyWon = false;
	private boolean tie = false;

    private int errors = 0;
    
    private Joueur j1 = new Joueur("Joueur 1", Couleur.BLANC);
    private  Joueur j2 =  new Joueur("Joueur 2", Couleur.NOIR);
	
	public Joueur current;
	private boolean colorC = true; 
    private Game g ;

    public Online(){

		if(!connect()) initializeServer();
		else current = j2;
		
		if(dos!=null){
			g = new Game(2, j1, j2,true,dos,current);
		}
		
		System.out.println(" 1 - Verification Joueur server : "+ j1.equals(current));
        System.out.println(" 2 - Verification Joueur connect : "+ j2.equals(current));
         
		
        thread = new Thread(this,"Online");
        thread.start();
    }

    public void run(){
        while(true){
    		if(g!=null) g.getGameView().boardview().repaint();
			if(dos != null) play();
			
			if (!colorC && !accepted) {
				listenForServerRequest();
			}

        }
    }

    private void play() {
		if (errors >= 10) unableToCommunicateWithOpponent = true;
		try {
			if(!g.getGameView().control.verifTo() && g.getGameView().control.getCourant() != current){
				System.out.println("Not your turn");
				Mouvement objectReceived = (Mouvement) dis.readObject();
				testR(objectReceived);
				g.getGameView().control.serverGestion(objectReceived);
			}
			else{
				//System.out.println(" your turn! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errors++;
		}
	}
    
	public void testR(Mouvement v){
		System.out.println("----------------------------------");
		if(v != null) System.out.println(" Mouvement reçu");
		else System.out.println(" Mouvement null  ");
		System.out.println("----------------------------------");
	}

    private void listenForServerRequest() {
		socket = null;
		try {
			socket = serverSocket.accept();
			dos = new ObjectOutputStream(socket.getOutputStream());
			dis = new ObjectInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
			g = new Game(2, j1, j2,true,dos,current);
			g.setTitle("Server Board");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		yourTurn = true;
		colorC = false;
		current = j1;
	}

    public static void main(String[] args) {
		Online on = new Online();
	}

}