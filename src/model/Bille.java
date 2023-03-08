package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bille implements Cloneable, Serializable{
    private Couleur color;
    public static final int width = 50;
    private transient BufferedImage image;

    public Bille(Couleur c){
        color = c;
    }

    public Couleur getColor() { return color; }

    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "N";
    }

    @Override
    public Object clone(){
        return new Bille(color);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(color); 
        ImageIO.write(image, "png", out); 
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = (Couleur) in.readObject();
        image = ImageIO.read(in);
    }

}
