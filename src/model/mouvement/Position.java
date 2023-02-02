package model.mouvement;

public class Position {
	private final int x;
	private final int y;
	
	public Position(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int gPosX() {
		return this.x;
	}
	public int gPosY() {
		return this.y;
	}
}	
