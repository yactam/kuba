package model.mouvement;

public enum Direction {
	// Placement dans un tableau de tableau
	NORD(-1, 0), SUD(1, 0), OUEST(0, -1), EST(0, 1);

	private final int i;
	private final int j;

	Direction(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int gDirI() {
		return this.i;
	}

	public int gDirJ() {
		return this.j;
	}
}
