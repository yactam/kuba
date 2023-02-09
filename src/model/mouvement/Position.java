package model.mouvement;
public class Position {
	private final int i;
	private final int j;

	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return this.i;
	}

	public int getJ() {
		return this.j;
	}
	@Override
	public Object clone(){
		Position p = null;
		try{	
			p = (Position) super.clone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
	}
	public Position next(Direction dir) {
		return new Position(i + dir.getI(), j + dir.getJ());
	}

	public Direction nextDir(Position p) {
		if(j == p.j && p.i < i)
			return Direction.NORD;
		else if(j == p.j && p.i > i)
			return Direction.SUD;
		else if(i == p.i && p.j < j)
			return Direction.OUEST;
		else if(i == p.i && p.j > j)
			return Direction.EST;
		return null;
	}
}