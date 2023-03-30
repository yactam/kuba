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
            System.out.println(animate.dx + " " + animate.dy);
            System.out.println(animate.d);
            animate.move();
            System.out.println(x + ' ' + y);
            updatePos();
            System.out.println(x + ' ' + y);
            if (b != null && !b.is_animate()){
                switch (animate.d) {
                    case NORD -> {
                        if (y == b.y)
                            b.createAnimation(animate.d);
                    }
                    case SUD -> {
                        if (y == b.y)
                            b.createAnimation(animate.d);
                    }
                    case OUEST -> {
                        if (x == b.x)
                            b.createAnimation(animate.d);
                    }
                    case EST -> {
                        if (x == b.x)
                            b.createAnimation(animate.d);
                    }
                }
            }
        }
    }


    public void createAnimation(Direction d){
        switch (d) {
            case NORD -> animate = new AnimationBille(x, y, x, y-1, 0, -1, d);
            case SUD -> animate = new AnimationBille(x, y, x, y+1, 0, 1, d);
            case OUEST -> animate = new AnimationBille(x, y, x-1, y, -1, 0, d);
            case EST -> animate = new AnimationBille(x, y, x+1, y, 1, 0, d);
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
        private final int d_x;
        private final int d_y;
        private final int dx;
        private final int dy;
        private final Direction d;

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

    public Bille(Couleur c, int x, int y){
        this.x = x;
        this.y = y;
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
        Bille b = new Bille(color, x, y);
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
