package com.kuba.vue;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.kuba.model.mouvement.Direction;
import com.kuba.model.plateau.Bille;

public class BilleAnimateView {
    private int x;
    private int y;
    private AnimationBille animate = null;
    public static int width;
    private final transient BufferedImage image;

    public BilleAnimateView(Bille b, int x_, int y_) {
        this.x = x_ * BilleAnimateView.width;
        this.y = y_ * BilleAnimateView.width;
        String imageDesc = switch (b.getColor()) {
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

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean contains(int x_, int y_){
        return x_ >= getX() && x_ <= getX() + BilleAnimateView.width
                && y_ >= getY() && y_ <= getY() + BilleAnimateView.width;
    }

    public BufferedImage image() {
        return image;
    }

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

    public void update(BilleAnimateView b){
        if (is_animate()){
            animate.move();
            updatePos();
            if (b != null && !b.is_animate()){
                switch (animate.d) {
                    case NORD -> {
                        if (y == b.y + BilleAnimateView.width / 2)
                            b.createAnimation(animate.d);
                    }
                    case SUD -> {
                        if (y + BilleAnimateView.width / 2 == b.y)
                            b.createAnimation(animate.d);
                    }
                    case OUEST -> {
                        if (x == b.x + BilleAnimateView.width / 2)
                            b.createAnimation(animate.d);
                    }
                    case EST -> {
                        if (x + BilleAnimateView.width / 2 == b.x)
                            b.createAnimation(animate.d);
                    }
                }
            }
        }
    }

    public void createAnimation(Direction d){
        switch (d) {
            case NORD -> animate = new AnimationBille(x, y, x, y - BilleAnimateView.width, 0, -1, d);
            case SUD -> animate = new AnimationBille(x, y, x, y + BilleAnimateView.width, 0, 1, d);
            case OUEST -> animate = new AnimationBille(x, y, x - BilleAnimateView.width, y, -1, 0, d);
            case EST -> animate = new AnimationBille(x, y, x + BilleAnimateView.width, y, 1, 0, d);
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
}