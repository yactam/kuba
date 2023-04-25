package reseau;
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

public class Server{
    private static Son son = new Son();
    private static Joueur j1 = new Joueur("Joueur 1", Couleur.BLANC);
    private static Joueur j2 =  new IA(Couleur.NOIR, j1);
    private static Game g = new Game(2, j1, j2,son);

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2023); // le serveur écoute sur le port 2023
            System.out.println("Serveur en attente de connexion");
            Socket socket = server.accept(); // bloque le thread tant qu'une connexion n'a pas été établie
            System.out.println("Connexion etablie");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            while(connected()){
            System.out.println("En attente de message");
            int nb = is.read(); // bloque le thread tant qu'un message n'a pas été reçu
            System.out.println("Message 1 recu");
            int res = nb;
            System.out.println(" Envoi du message i ");
            os.write(res);

            int nb2 = is.read();
            System.out.println("Message 2 recu");
            int res2 = nb2;
            System.out.println(" Envoi du message j ");
            os.write(res2);
            
            int nb3 = is.read();
            gestion(nb,nb2,nb3);
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   private static void gestion(int i, int j , int n){
    if(n==0){
        System.out.println(" Direction NORD ");
        g.gameView.control.serverGestion(i,j,Direction.NORD);
    }
    else if(n==1) {
        System.out.println(" Direction SUD ");
        g.gameView.control.serverGestion(i,j,Direction.SUD);
    }
    else if(n==2) {
        System.out.println(" Direction EST ");
        g.gameView.control.serverGestion(i,j,Direction.EST);
    }
    else if(n==3) {
        System.out.println(" Direction OUEST ");
        g.gameView.control.serverGestion(i,j,Direction.OUEST);
    }
    else{
        System.out.println(" Il y a une erreur dans la direction ");
    }
   } 

   private static boolean connected(){
    return true;
   }
}
