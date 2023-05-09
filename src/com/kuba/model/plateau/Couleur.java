package com.kuba.model.plateau;

public enum Couleur {
    BLANC {
        @Override
        public Couleur opposite() {
            return NOIR;
        }
        @Override
        public String toString() {
            return "white";
        }
    },
    NOIR {
        @Override
        public Couleur opposite() {
            return BLANC;
        }
        @Override
        public String toString() {
            return "black";
        }
    },
    ROUGE {
        @Override
        public Couleur opposite() {
            return null;
        }
        @Override
        public String toString() {
            return "red";
        }
    };

    public abstract Couleur opposite();
}
