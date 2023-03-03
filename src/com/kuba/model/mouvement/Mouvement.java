package com.kuba.model.mouvement;

public class Mouvement {

    private final Direction direction;
    private final Position position;

    public Mouvement(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Mouvement " + position + " " + direction;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(! (o instanceof Mouvement move)) return false;
        return move.direction.equals(this.direction) && move.position.equals(this.position);
    }
}
