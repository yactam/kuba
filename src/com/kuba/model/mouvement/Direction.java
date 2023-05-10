package com.kuba.model.mouvement;
import java.io.Serializable;
public enum Direction implements Serializable{
	NORD(-1, 0), SUD(1, 0), OUEST(0, -1), EST(0, 1);
	static final Direction[] reverse = {SUD, NORD, EST, OUEST};
	private final int di;
	private final int dj;

	Direction(int i, int j) {
		this.di = i;
		this.dj = j;
	}
	public int getDi() {
		return this.di;
	}
	public int getDj() {
		return this.dj;
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
