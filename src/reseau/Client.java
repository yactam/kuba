package reseau;
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

        while(true){ 
        System.out.println("->Donnez le numero de ligne : ");
        int nb = sc.nextInt();
        os.write(nb);
        System.out.println("En attente de reponse");
        int res = is.read();
        System.out.println("Reponse recue : " + res);

        System.out.println("-> Donnez le numero de colonne : ");
        int nb2 = sc.nextInt();
        os.write(nb2);
        System.out.println("En attente de reponse");
        int res2 = is.read();
        System.out.println("Reponse recue : " + res);

 
        System.out.println(" Donner une direction selon la demande suivante:\n 0=NORD\n 1=SUD\n 2=EST\n 3=OUEST\n");
        int nb3 = sc.nextInt();
        os.write(nb3);
        System.out.println(" Le server a agit selon votre demande regarder la partie ");
                
    }
            
    } catch (UnknownHostException e) { // gerer les erreurs de serveur
        
        e.printStackTrace();
    } catch (IOException e) { // gerer les erreurs de port
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
   }
}
