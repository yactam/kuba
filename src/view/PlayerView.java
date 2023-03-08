package view;

import java.awt.*;
import javax.swing.*;
import model.Joueur;

public class PlayerView extends JPanel {
    private JLabel nom;
    private JLabel billesCapturesR;
    private JLabel billesRestantes;
    private Joueur joueur;

    public PlayerView(Joueur j){
        this.setLayout(new GridLayout(3,1));
        joueur = j;
        nom = new JLabel(j.getNom());
        billesRestantes = new JLabel("Billes restantes : "+j.getNbAdversaireCapture());
        billesCapturesR = new JLabel("Billes rouges capturés : "+j.getNbBilleRougeCapturer());
        this.add(nom);
        this.add(billesRestantes);
        this.add(billesCapturesR);
    }

    public void update(){
        billesRestantes = new JLabel("Billes restantes : "+joueur.getNbAdversaireCapture());
        billesCapturesR = new JLabel("Billes rouges capturés : "+joueur.getNbBilleRougeCapturer());
        this.repaint();
    }
}
