package com.kuba.model.mouvement;

public class MoveStatus {

    public enum Status {
        INVALID_MOVE,
        MOVE_OUT,
        BASIC_MOVE;
    }


    private final String message;
    private final Status status;
    public MoveStatus(Status status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLegal() {
        return status != Status.INVALID_MOVE;
    }

    public Status getStatus() {
        return status;
    }


}
