package model;
/* Serveur en modele TCP(par flux) */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Serveur {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2023); // le serveur écoute sur le port 2023
            System.out.println("Serveur en attente de connexion");
            Socket socket = server.accept(); // bloque le thread tant qu'une connexion n'a pas été établie
            System.out.println("Connexion établie");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            System.out.println("En attente de message");
            int nb = is.read(); // bloque le thread tant qu'un message n'a pas été reçu
            System.out.println("Message reçu");
            int res = nb*2;
            System.out.println("Envoi du message");
            os.write(res);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
