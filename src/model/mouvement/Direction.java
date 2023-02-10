package model.mouvement;

public enum Direction {
	NORD(-1, 0), SUD(1, 0), OUEST(0, -1), EST(0, 1);

	private final int i;
	private final int j;

	Direction(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return this.i;
	}

	public int getJ() {
		return this.j;
	}

	public Direction reverse() {
		switch (this) {
			case NORD : return SUD;
			case SUD : return NORD;
			case EST : return OUEST;
			default : return EST;
		}
	}
}
