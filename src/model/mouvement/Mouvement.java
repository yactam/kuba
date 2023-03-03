package model.mouvement;

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
}
