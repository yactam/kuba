package view;
import model.*;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class CellView extends JPanel{
    
    private Bille b;
    private BufferedImage image;
    public static final int TAILLE=64;
    private int x;
    private int y;

    public CellView(Bille b, int x, int y){
        super();
        this.setLayout(null);
        this.x=x;
        this.y=y;
        this.initImage(b);
        Dimension d = new Dimension(image.getWidth(), image.getHeight());
        this.setPreferredSize(d);
        this.b = b;    
    }

    public void setBille(Bille b){
        this.b = b;
        initImage(b);
        this.repaint();
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y=y;
    }

    public void initImage(Bille b) {
        if(b==null){    
         
        return ;
        }
        try{
            if(b.toString().equals("B")) image = ImageIO.read(new File("src/resources/white.png"));
            else if(b.toString().equals("N")) image = ImageIO.read(new File("src/resources/black.png"));
            else if(b.toString().equals("R")) image = ImageIO.read(new File("src/resources/red.png"));
        }
        catch(Exception e){ 
            System.out.println("Erreur Image CellView");
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(image, x, y, null);
    }
    
}
