package view;
import model.*;
import javax.swing.JPanel;
import java.awt.*;
import observerpattern.*;

import model.plateau.Board;

public class BoardView extends JPanel implements Observer{
    private  Board board;
    private CellView[][] view;
    public BoardView(Board b){
        super();
        this.setLayout(null);
        board = b;
        // on a une taille d'element de 64px pour le moment 
        Dimension d = new Dimension(board.getRow()*CellView.TAILLE,board.getCol()*CellView.TAILLE);
        this.setPreferredSize(d);
        this.initCellView(board);
    }

    public void initCellView(Board b){
        view = new CellView[b.getRow()][b.getCol()];
        for(int i = 0; i < b.getRow(); ++i) {
            for(int j = 0; j < b.getCol(); ++j) {
                int x = (CellView.TAILLE) * j; 
                int y = (CellView.TAILLE) * i;
                
                if(!b.getCell(i, j).estVide()){
                    Bille toPlace = b.getCell(i, j).getBille();
                    view[i][j] = new CellView(toPlace,x,y);
                    view[i][j].setLocation(x, y);
                    add(view[i][j]);
                }
                else {
                    view[i][j] = new CellView(null,x,y);
                    view[i][j].setLocation(x, y);
                    add(view[i][j]);
                }
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < board.getRow(); i++) {
            for(int j = 0; j < board.getCol(); j++) {
                if(view[i][j] != null){
                view[i][j].paintComponent(g);
                }
            }
        }
    }

    @Override
    public void update(SubjectObserver subject) {
       this.initCellView((Board)subject);
       this.repaint();
    }

}
