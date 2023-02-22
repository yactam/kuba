package view;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import observerpattern.*;
import observerpattern.Observer;
import model.plateau.Board;

public class BoardView extends JPanel implements Observer{
    private  Board board;
    public static final int widthBille = 50;
   
    public BoardView(Board b){
        super();
        this.setLayout(null);
        board = b;
        int k = 4*board.getN()-1;
        // Panel
        setPreferredSize(new Dimension(widthBille * k, widthBille * k));
    }

    private void drawGrid(Graphics2D graphics2D) {
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.getCellBoard().length; i++) {
            for(int j = 0; j < board.getCellBoard()[i].length; j++) {
                if(i != board.getCellBoard().length-1 && j != board.getCellBoard()[i].length-1) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * widthBille + (widthBille / 2), i * widthBille + (widthBille / 2), widthBille, widthBille);
                }
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        drawGrid(graphics2D);
        for(int i = 0; i < board.getCellBoard().length; i++) {
            for(int j = 0; j < board.getCellBoard()[i].length; j++) {
                if(!board.board(j, i).estVide()) {
                    int width = widthBille;
                    String s = "";
                    switch(board.board(j,i).toString()){
                        case "R":s="red";break;
                        case "N":s="black";break;
                        case "B":s="white";break;
                    }
                    try {
                        BufferedImage image = ImageIO.read(new File("src/resources/"+s+".png"));
                        graphics2D.drawImage(image, width * i, width * j, width, width, null);
                    } catch (IOException e) {
                        System.out.println(" Erreur dans chargement image de board -> "+e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void update(SubjectObserver subject) {
    	board = (Board)subject;
       this.repaint();
    }

}
