package model.mouvement;

public class Position {
	private final int i;
	private final int j;

	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int gPosI() {
		return this.i;
	}

	public int gPosJ() {
		return this.j;
	}

	public Position next(Direction d) {
		return new Position(this.i + d.gDirI(), this.j + d.gDirJ());
	}

	public Direction nextDir(Position b) {
		if (this.i < a.i)
			return Direction.NORD;
		else if (this.i > a.i)
			return Direction.SUD;
		else if (this.j < a.j)
			return Direction.OUEST;
		else
			return Direction.EST;
	}
}
