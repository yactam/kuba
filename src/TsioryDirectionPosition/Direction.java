package TsioryDirectionPosition;

public enum Direction {
	// Placement dans un tableau de tableau 
	NORD(-1,0),SUD(1,0),OUEST(0,-1),EST(0,1);
	
	private int x;
	private int y;
	
	Direction(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getDirectionX() {
		return this.x;
	}
	public int getDirectionY() {
		return this.y;
	}
}
