package mp2Game;

import java.util.ArrayList;

public class Strategies {
	
	public static int nodesMinMaxExp = 0;
	public static int nodesAlphaBetaExp = 0;
	
	public static ArrayList<Integer> alphaBeta(GameBoard g, int moves, int currX, int currY, int depth, int alpha, int beta) {
		ArrayList<Integer> tupleMax = new ArrayList<Integer>();
		tupleMax.add(Integer.MIN_VALUE);
		tupleMax.add(Integer.MIN_VALUE);
		tupleMax.add(Integer.MIN_VALUE);
		
		ArrayList<Integer> tupleMin = new ArrayList<Integer>();
		tupleMin.add(Integer.MAX_VALUE);
		tupleMin.add(Integer.MAX_VALUE);
		tupleMin.add(Integer.MAX_VALUE);
		int random = (int) (Math.random() * 100);
		
		//System.out.println("Depth: " + depth + " Turn: " + moves);
		
		if(depth == 0) {
			nodesAlphaBetaExp++;
			if(g.getCell(currX, currY) != null) {
				tupleMax.set(0,g.hypotheticalMove(g.getCell(currX, currY)));
					//tupleMax.set(0,Math.max(g.hypotheticalMove(g.getCell(currX, currY)),g.hypotheticalMoveNoBlitz(g.getCell(currX, currY))));
				tupleMax.set(1,currX);
				tupleMax.set(2,currY);
				//System.out.println("utility: " + tuple.get(0) + " x: " + tuple.get(1) + " y: " + tuple.get(2));
				return tupleMax;
			}
			else
				return null;
		}
		
		//max player
		if(moves==0) {
			
			nodesAlphaBetaExp++;
			ArrayList<Integer> potential = new ArrayList<Integer>();
			
			ArrayList<GameCell> minChoices = g.unoccupiedCells();
			for(GameCell choice: minChoices) {
				
				potential = alphaBeta(g, (++moves)%g.players().size(), choice.getX(), choice.getY(), depth-1, alpha, beta);
				if(Math.max(tupleMax.get(0), potential.get(0)) != tupleMax.get(0)){
					tupleMax = potential;
					alpha = Math.max(tupleMax.get(0), alpha);
				}
				else
					alpha = Math.max(potential.get(0), alpha);
				
				if(beta<= alpha)
					break;
				
			}
			
			return tupleMax;
		}
		
		//min player
		else {
			//System.out.println("This should never print");
			nodesAlphaBetaExp++;
			ArrayList<Integer> potential = new ArrayList<Integer>();
			
			ArrayList<GameCell> maxChoices = g.unoccupiedCells();
			for(GameCell choice: maxChoices) {
				
				potential = alphaBeta(g, (++moves)%g.players().size(), choice.getX(), choice.getY(), depth-1, alpha, beta);
				if(Math.min(tupleMin.get(0), potential.get(0)) < tupleMin.get(0) || potential.get(0) == tupleMin.get(0)){
					tupleMin = potential;
					beta = Math.min(tupleMin.get(0), beta);
				}
				else
					beta = Math.min(potential.get(0), beta);
				
				if(beta<= alpha)
					break;
				
			}
			
			return tupleMin;
		}
		
	}
	

	public static ArrayList<Integer> minimax(GameBoard g, int moves, int currX, int currY, int depth) {
		ArrayList<Integer> tupleMax = new ArrayList<Integer>();
		tupleMax.add(Integer.MIN_VALUE);
		tupleMax.add(Integer.MIN_VALUE);
		tupleMax.add(Integer.MIN_VALUE);
		
		ArrayList<Integer> tupleMin = new ArrayList<Integer>();
		tupleMin.add(Integer.MAX_VALUE);
		tupleMin.add(Integer.MAX_VALUE);
		tupleMin.add(Integer.MAX_VALUE);
		int random = (int) (Math.random() * 100);
		
		//System.out.println("Depth: " + depth + " Turn: " + moves);
		
		if(depth == 0) {
			nodesMinMaxExp++;
			if(g.getCell(currX, currY) != null) {
				tupleMax.set(0,g.hypotheticalMove(g.getCell(currX, currY)));
					//tupleMax.set(0,Math.max(g.hypotheticalMove(g.getCell(currX, currY)),g.hypotheticalMoveNoBlitz(g.getCell(currX, currY))));
				tupleMax.set(1,currX);
				tupleMax.set(2,currY);
				//System.out.println("utility: " + tuple.get(0) + " x: " + tuple.get(1) + " y: " + tuple.get(2));
				return tupleMax;
			}
			else
				return null;
		}
		
		if(moves == 0) {
			//System.out.println("This should never print");
			nodesMinMaxExp++;
			ArrayList<Integer> potential = new ArrayList<Integer>();
			
			ArrayList<GameCell> minChoices = g.unoccupiedCells();
			for(GameCell choice : minChoices) {
				potential = minimax(g, (++moves)%g.players().size(), choice.getX(), choice.getY(), depth-1);
				if(Math.max(tupleMax.get(0), potential.get(0)) != tupleMax.get(0)){
					tupleMax = potential;
				}
			}
			
			//System.out.println("utility: " + tupleMax.get(0) + " x: " + tuple.get(1) + " y: " + tuple.get(2));

			return tupleMax;
		}
		
		
		// min player
		else {
			
			nodesMinMaxExp++;
			ArrayList<Integer> potential = new ArrayList<Integer>();
			
			ArrayList<GameCell> maxChoices = g.unoccupiedCells();
			//System.out.println("No. of Max Choices: " + maxChoices.size());
			for(GameCell choice : maxChoices) {
				potential = minimax(g, (++moves)%g.players().size(), choice.getX(), choice.getY(), depth-1);
				if(Math.min(tupleMin.get(0), potential.get(0)) < tupleMin.get(0) || potential.get(0) == tupleMin.get(0)){
					tupleMin = potential;
					//System.out.println("utility: " + tupleMin.get(0));
				}
			}
			//System.out.println("utility: " + tupleMin.get(0) + " x: " + tupleMin.get(1) + " y: " + tupleMin.get(2));
			return tupleMin;
		}
		
	}
}
