package mp2Game;

import java.util.ArrayList;

public class WarGame {

	private GameBoard g;
	public static int movesMaxMade;
	public static int movesMinMade;
	public static int nodesMaxMade;
	public static int nodesMinMade;
	
	
	public WarGame(String filename, int players) {
		g = new GameBoard(filename, players);
	}
	
	public void play() {
		ArrayList<Integer> move = new ArrayList<Integer>();
		
		movesMaxMade = 0;
		movesMinMade = 0;
		nodesMaxMade = 0;
		nodesMinMade = 0;
		
		int depth = 2;
		
		// while there are spaces remaining
		while(!g.isFull()) {
			
			System.out.println("Player turn: " + g.getMoves());
			// if max player
			if(g.getMoves() == 0) {
				//if it's not the first move
				System.out.println("Moves Max Made: " + movesMaxMade);
				if (movesMaxMade != 0){
					int index = g.players().get(g.getMoves()).cellsCaptured().size();
					GameCell lastmove = g.players().get(g.getMoves()).cellsCaptured().get(index-1);
					//move = Strategies.minimax(g, g.getMoves(), lastmove.getX(), lastmove.getY(), depth);
					move = Strategies.alphaBeta(g, g.getMoves(), lastmove.getX(), lastmove.getY(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
				}
				//if it is the first move
				else{
					//move = Strategies.minimax(g, g.getMoves(), 0, 0, depth);
					move = Strategies.alphaBeta(g, g.getMoves(), 0, 0, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
				}
				movesMaxMade++;
				//nodesMaxMade+=Strategies.nodesMinMaxExp;
				//Strategies.nodesMinMaxExp = 0;
				nodesMaxMade+=Strategies.nodesAlphaBetaExp;
				Strategies.nodesAlphaBetaExp = 0;
				System.out.println("Nodes Max expanded: " + nodesMaxMade);
			}
			
			//if min player
			else{
				System.out.println("Moves Min Made: " + movesMinMade);
				
				// if it's not the first move
				if (movesMinMade != 0){
					int index = g.players().get(g.getMoves()).cellsCaptured().size();
					
					if(index == 0){
						move = Strategies.minimax(g, g.getMoves(), 0,0, depth);
						//move = Strategies.alphaBeta(g, g.getMoves(), 0,0, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
					}
					else {
						GameCell lastmove = g.players().get(g.getMoves()).cellsCaptured().get(index-1);
						move = Strategies.minimax(g, g.getMoves(), lastmove.getX(), lastmove.getY(), depth);
						//move = Strategies.alphaBeta(g, g.getMoves(), lastmove.getX(), lastmove.getY(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
					}
					
				}
				// if it is the first move
				else{
					move = Strategies.minimax(g, g.getMoves(), 0, 0, depth);
					//move = Strategies.alphaBeta(g, g.getMoves(), 0,0, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
				}
				
				movesMinMade++;
				nodesMinMade+=Strategies.nodesMinMaxExp;
				Strategies.nodesMinMaxExp = 0;
				//nodesMinMade+=Strategies.nodesAlphaBetaExp;
				//Strategies.nodesAlphaBetaExp = 0;
				System.out.println("Nodes Min expanded: " + nodesMinMade);
			}
			System.out.println("X: " + move.get(1) + " Y: " + move.get(2));
			g.cellCapture(g.players().get(g.getMoves()), g.getCell(move.get(1), move.get(2)));
		}
	}
	
	public GameBoard board() {
		return g;
	}
	
	public static void main(String[] args) {
		WarGame w = new WarGame("Westerplatte.txt", 2);
		GameBoard g = w.board();
		System.out.println(g.cells().size());
		long start = System.currentTimeMillis();
		w.play();
		long end = System.currentTimeMillis();
		System.out.println("Time for Game: " + (end-start));
		System.out.println("Time per Move: " + (end-start)/(movesMaxMade + movesMinMade));
		System.out.println("Nodes per Move: " + (nodesMinMade+nodesMaxMade)/(movesMaxMade + movesMinMade));
		System.out.println("Nodes Player 1: " + nodesMaxMade);
		System.out.println("Nodes Player 2: " + nodesMinMade);
		g.printBoard(6);
		System.out.println("Player 1: " + g.players().get(0).playerScore());
		System.out.println("Player 2: " + g.players().get(1).playerScore());
	}
}
