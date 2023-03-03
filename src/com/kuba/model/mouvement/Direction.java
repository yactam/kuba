package com.kuba.model.mouvement;

public enum Direction {
	NORD(-1, 0), SUD(1, 0), OUEST(0, -1), EST(0, 1);
	static final Direction[] reverse = {SUD, NORD, EST, OUEST};
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
		return reverse[this.ordinal()];
	}

	@Override
	public String toString() {
		return switch (this) {
			case OUEST -> "OUEST";
			case SUD -> "SUD";
			case NORD -> "NORD";
			case EST -> "EST";
		};
	}
}
