package model;

public class Bille implements Cloneable{
    private final Couleur color;

    public Bille(Couleur c){
        color = c;
        String imageDesc = switch (color) {
            case NOIR -> "black";
            case BLANC -> "white";
            case ROUGE -> "red";
        };
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

}
