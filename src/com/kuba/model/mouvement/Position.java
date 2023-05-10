package com.kuba.model.mouvement;
import java.io.Serializable;
public class Position implements Cloneable , Serializable {
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
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Position)) return false;
		return ((Position) o).i == i && ((Position) o).j == j;
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
		return new Position(i + dir.getDi(), j + dir.getDj());
	}

	public Position prev(Direction dir) {
		return next(dir.reverse());
	}

	public Direction nextDir(Position p) {
		if(p == null) return null;
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

	@Override
	public String toString() {
		return "(i = " + i + ", j = " + j + ")";
	}
}