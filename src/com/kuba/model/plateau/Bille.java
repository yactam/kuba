package com.kuba.model.plateau;

import javax.imageio.ImageIO;
import com.kuba.model.mouvement.Direction;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Bille implements Cloneable, Serializable{
    private Couleur color;
    public static int width;
    private transient BufferedImage image;
    private AnimationBille animate = null;
    private int x,y;

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private void updatePos(){
        this.x = animate.x;
        this.y = animate.y;
    }

    //to check for collision
    public void update(Bille b){
        if (is_animate()){
            animate.move();
            updatePos();
            if (b != null && !b.is_animate()){
                switch (animate.d) {
                    case NORD -> {
                        if (y == b.y + Bille.width / 2)
                            b.createAnimation(animate.d);
                    }
                    case SUD -> {
                        if (y + Bille.width / 2 == b.y)
                            b.createAnimation(animate.d);
                    }
                    case OUEST -> {
                        if (x == b.x + Bille.width / 2)
                            b.createAnimation(animate.d);
                    }
                    case EST -> {
                        if (x + Bille.width / 2 == b.x)
                            b.createAnimation(animate.d);
                    }
                }
            }
        }
    }


    public void createAnimation(Direction d){
        switch (d) {
            case NORD -> animate = new AnimationBille(x, y, x, y - Bille.width, 0, -1, d);
            case SUD -> animate = new AnimationBille(x, y, x, y + Bille.width, 0, 1, d);
            case OUEST -> animate = new AnimationBille(x, y, x - Bille.width, y, -1, 0, d);
            case EST -> animate = new AnimationBille(x, y, x + Bille.width, y, 1, 0, d);
        }
    }

    public AnimationBille getAnimation(){
        return animate;
    }

    public boolean is_animate(){
        return animate != null && animate.is_moving();
    }

    public static class AnimationBille{
        private int x;
        private int y;
        private int d_x;
        private int d_y;
        private int dx;
        private int dy;
        private Direction d;

        public AnimationBille(int x, int y, int d_x, int d_y, int dx, int dy, Direction d){
            this.x = x;this.y=y;this.d_x = d_x;this.d_y=d_y;this.dx=dx;this.dy=dy;
            this.d = d;
        }

        public AnimationBille(AnimationBille an){
            this(an.x, an.y, an.d_x, an.d_y, an.dx, an.dy, an.d);
        }

        public void move(){
            x+=dx;
            y+=dy;
        }

        public boolean is_moving(){
            return x!=d_x || y!=d_y;
        }

        public Direction getDirection(){
            return d;
        }
    }

    public Bille(Couleur c) {
        this(c, 0, 0);
    }

    public Bille(Couleur c, int x_, int y_){
        this.x = x_*Bille.width;
        this.y = y_*Bille.width;
        color = c;
        String imageDesc = switch (color) {
            case NOIR -> "black";
            case BLANC -> "white";
            case ROUGE -> "red";
        };
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/" + imageDesc + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Override
    public Object clone(){
        Bille b = new Bille(color, x/Bille.width, y/Bille.width);
        if (animate != null) b.animate = new AnimationBille(animate);

        return b;
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
