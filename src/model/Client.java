package model;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Client {
   public static void main(String[] args) {
    try {
        Socket socket = new Socket("localhost", 2023);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un nombre");
        int nb = sc.nextInt();
        os.write(nb);
        System.out.println("En attente de réponse");
        int res = is.read();
        System.out.println("Réponse reçue : " + res);

    } catch (UnknownHostException e) { // gerer les erreurs de serveur
        
        e.printStackTrace();
    } catch (IOException e) { // gerer les erreurs de port
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
   }
}
