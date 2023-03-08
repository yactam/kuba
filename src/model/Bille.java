package model;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Bille extends JLabel implements Cloneable{
    private Couleur color;
    public static final int width = 50;
    private transient ImageIcon image;

    public Bille(Couleur c){
        color = c;
        String imageDesc = switch (color) {
            case NOIR -> "black";
            case BLANC -> "white";
            case ROUGE -> "red";
        };
        try {
            image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/" + imageDesc + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setIcon(image);
    }

    public Couleur getColor() { return color; }

    public ImageIcon image() {
        return image;
    }

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
        ImageIO.write((BufferedImage)image.getImage(), "png", out); 
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        color = (Couleur) in.readObject();
        image = new ImageIcon(ImageIO.read(in));
    }

}
