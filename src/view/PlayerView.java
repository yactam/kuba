package view;

import java.awt.*;
import javax.swing.*;
import model.Joueur;

public class PlayerView extends JPanel {
    private static int count;
    private JLabel nom;
    private JLabel billesCapturesR;
    private JLabel billesRestantes;
    private Joueur joueur;

    public PlayerView(Joueur j){
        count++;
        Color bg = new Color(70*(count%2+1)+50,70*(count%2+1)+50,70*(count%2+1)+50);
        this.setBackground(bg);
        this.setLayout(new GridLayout(3,1));
        this.setPreferredSize(new Dimension(250, 340));
        joueur = j;
        nom = new JLabel("   "+j.getNom());
        nom.setFont(new Font("Serif", Font.BOLD, 18));
        JLabel lab1 = new JLabel("  Billes restantes");
        JLabel lab2 = new JLabel("  Billes rouges capturées");
        billesRestantes = new JLabel(j.getNbAdversaireCapture()+"  ");
        billesCapturesR = new JLabel(j.getNbBilleRougeCapturer()+"  ");
        this.add(nom);
        JPanel rest = new JPanel(new BorderLayout());
        rest.add(lab1, BorderLayout.WEST);
        rest.add(billesRestantes, BorderLayout.EAST);
        rest.setBackground(bg);
        this.add(rest);
        JPanel rouge = new JPanel(new BorderLayout());
        rouge.add(lab2, BorderLayout.WEST);
        rouge.add(billesCapturesR, BorderLayout.EAST);
        rouge.setBackground(bg);
        this.add(rouge);
    }

    public void update(){
        billesRestantes = new JLabel(joueur.getNbAdversaireCapture()+"  ");
        billesCapturesR = new JLabel(joueur.getNbBilleRougeCapturer()+"  ");
        this.repaint();
    }
}
