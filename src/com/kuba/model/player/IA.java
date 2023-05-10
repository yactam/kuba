package com.kuba.model.player;

import com.kuba.model.mouvement.Mouvement;
import com.kuba.model.plateau.Board;
import com.kuba.model.plateau.Couleur;
import com.kuba.model.player.Joueur;
import com.kuba.model.player.MiniMax;

import java.util.Random;

public class IA extends Joueur {
    private final MiniMax miniMax;
    public IA(Couleur couleur, Joueur opponent) {
        super("Dudu" + new Random().nextInt(1000), couleur);
        miniMax = new MiniMax(4, this, opponent);
    }

    public Mouvement getMouvement(Board board) {
        return miniMax.execute(board);
    }


}
