package com.kuba.vue;

import java.awt.*;
import javax.swing.*;
import com.kuba.model.player.Joueur;

public class PlayerView extends JPanel {
    private JLabel billesCapturesR;
    private JLabel billesRestantes;
    private final Joueur joueur;

    public PlayerView(Joueur j){
        setBackground(new Color(0,0,0,0));
        setLayout(null);
        joueur = j;
        JLabel nom = new JLabel(j.getNom());
        nom.setFont(new Font("Serif", Font.BOLD, 22));
        JLabel lab1 = new JLabel("Billes restantes");
        lab1.setFont(new Font("Serif", Font.BOLD, 14));
        JLabel lab2 = new JLabel("Billes rouges capturées");
        lab2.setFont(new Font("Serif", Font.BOLD, 14));
        billesRestantes = new JLabel(j.getNbAdversaireCapturee()+"");
        billesRestantes.setFont(new Font("Serif", Font.BOLD, 15));
        billesCapturesR = new JLabel(j.getNbBilleRougeCapturee()+"");
        billesCapturesR.setFont(new Font("Serif", Font.BOLD, 15));

        nom.setBounds( 18, 13,200, 40);
        lab1.setBounds(18, 73,200, 30);
        lab2.setBounds(18,113,200, 30);
        billesRestantes.setBounds(228, 78, 20, 20);
        billesCapturesR.setBounds(228,118, 20, 20);

        add(nom);
        add(lab1);
        add(billesRestantes);
        add(lab2);
        add(billesCapturesR);
    }

    public void update(){
        billesRestantes = new JLabel(joueur.getNbAdversaireCapturee()+"");
        billesCapturesR = new JLabel(joueur.getNbBilleRougeCapturee()+"");
        this.repaint();
    }
}