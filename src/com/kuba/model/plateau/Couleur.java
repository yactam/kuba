package model.plateau;

public enum Couleur {
    BLANC {
        @Override
        public Couleur opposite() {
            return NOIR;
        }
    },
    NOIR {
        @Override
        public Couleur opposite() {
            return BLANC;
        }
    },
    ROUGE {
        @Override
        public Couleur opposite() {
            return null;
        }
    };

    public abstract Couleur opposite();
}
