package mp2Game;

public class GameCell {

	/*
	 * xLocation/yLocation - position in grid
	 * 					   - x is horizontal across the top
	 * 					   - y is vertical top to bottom
	 * value - value of the GameCell
	 * color - So if the color is 0 -> player 0
	 * 		 - Color is 1 -> player 1
	 * occupied - whether or not the cell is occupied in the first place
	 */
	private int xLocation,yLocation, value, color;
	private boolean occupied;
	
	public GameCell() {
		new GameCell(0,0,0,0,false);
	}
	
	public GameCell(int x, int y, int val, int cellColor, boolean occ) {
		xLocation = x;
		yLocation = y;
		value = val;
		color = cellColor;
		
		occupied = occ;
	}

	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(int playerColor) {
		occupied = true;
		setColor(playerColor);
	}
	
	public int getValue() {
		return value;
	}
	
	private void setColor(int playerColor) {
		color = playerColor;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getX() {
		return xLocation;
	}
	
	public int getY() {
		return yLocation;
	}
	
}
