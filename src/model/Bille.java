package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bille {
    private final Couleur color;
    public static final int width = 50;
    private final BufferedImage image;

    public Bille(Couleur c){
        color = c;
        switch (color) {
            case NOIR -> {
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/black.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case BLANC -> {
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/white.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case ROUGE -> {
                try {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/red.png")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> image = null;
        }
    }

    public Couleur getColor() { return color; }

    public BufferedImage image() {
        return image;
    }

    @Override
    public String toString() {
        if(color.equals(Couleur.BLANC)) return "B";
        else if (color.equals(Couleur.ROUGE)) return "R";
        else return "N";
    }

}
