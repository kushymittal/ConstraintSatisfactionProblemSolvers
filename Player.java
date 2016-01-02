package mp2Game;

import java.util.ArrayList;

public class Player {

	private ArrayList<GameCell> cells;
	private int totalValue;
	private int order;
	// true - minimax, false - alphabeta
	private boolean strategy;
	
	public Player(int playerNumber) {
		totalValue = 0;
		order = playerNumber;
		cells = new ArrayList<GameCell>();
	}

	public void captureCell(GameCell cell) {
		cells.add(cell);
		totalValue+= cell.getValue();
		System.out.println("Score after Capture: "+ totalValue);
	}
	
	public void cellStolen(GameCell cell) {
		cells.remove(cell);
		totalValue-= cell.getValue();
		System.out.println("Score after Stolen: "+ totalValue);
	}
	
	public int playerColor() {
		return order;
	}
	
	public int playerScore() {
		return totalValue;
	}
	
	public ArrayList<GameCell> cellsCaptured() {
		return cells;
	}
}
