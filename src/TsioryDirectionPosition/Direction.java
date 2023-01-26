package TsioryDirectionPosition;

public enum Direction {
	
	NORD(0,1),SUD(0,-1),OUEST(-1,0),EST(1,0);
	
	private int x=0;
	private int y=0;
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
