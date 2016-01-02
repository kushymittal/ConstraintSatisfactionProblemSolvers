package mp2Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard {

	private ArrayList<Player> players;
	private ArrayList<GameCell> cells;
	private Scanner sc;
	private int width;
	private int moves;
	File game;
	
	public GameBoard(String filename, int numberPlayers) {
		players = new ArrayList<Player>();
		for(int i = 0; i< numberPlayers; i++){
			players.add(new Player(i));
		}
		width = 0;
		moves = 0;
		cells = new ArrayList<GameCell>();
		readBoard(filename);
	}
	
	public boolean isFull() {
		for(GameCell cell: cells) {
			if(!cell.isOccupied())
				return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("resource")
	private void readBoard(String filename) {
		game = new File(filename);
		try {
			sc = new Scanner(game);
			int counter = 0;
			
			while(sc.hasNextLine()) {
				String val = sc.nextLine();
				System.out.println("Val: " + val);
				String[] vals = val.split("\t");
				width = vals.length;
				for(int i =0; i < vals.length; i++) {
					cells.add(new GameCell(counter%vals.length, counter/vals.length,Integer.parseInt(vals[i]) , Integer.MIN_VALUE, false));
					counter++;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
		
	}
	
	public void cellCapture(Player player, GameCell cell) {
		if(cell.isOccupied()){
			return;
		}
		player.captureCell(cell);
		cell.setOccupied(player.playerColor());
		
		int currX = cell.getX();
		int currY = cell.getY();
		
		int random = (int)(Math.random() * 100);
		boolean toBlitz = true;
		
		System.out.println("Did we blitz? " + toBlitz);
		
		if(toBlitz){
			if((this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).getColor() == player.playerColor())
					|| (this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).getColor() == player.playerColor()) || 
					(this.getCell(currX, currY-1) != null  && this.getCell(currX, currY-1).getColor() == player.playerColor()) ||
					(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).getColor() == player.playerColor())) {
					
				if(this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).isOccupied()) {
					if(this.getCell(currX-1, currY).getColor() != player.playerColor()) {
						players.get(getCell(currX-1, currY).getColor()).cellStolen(getCell(currX-1, currY));
						player.captureCell(getCell(currX-1, currY));
						getCell(currX-1, currY).setOccupied(player.playerColor());
					}
				}
				
				if(this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).isOccupied()) {
					if(this.getCell(currX+1, currY).getColor() != player.playerColor()) {
						players.get(getCell(currX+1, currY).getColor()).cellStolen(getCell(currX+1, currY));
						player.captureCell(getCell(currX+1, currY));
						getCell(currX+1, currY).setOccupied(player.playerColor());
					}
				}
				
				if(this.getCell(currX, currY-1) != null && this.getCell(currX, currY-1).isOccupied()) {
					if(this.getCell(currX, currY-1).getColor() != player.playerColor()) {
						players.get(getCell(currX, currY-1).getColor()).cellStolen(getCell(currX, currY-1));
						player.captureCell(getCell(currX, currY-1));
						getCell(currX, currY-1).setOccupied(player.playerColor());
					}
				}
				
				if(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).isOccupied()) {
					if(this.getCell(currX, currY+1).getColor() != player.playerColor()) {
						players.get(getCell(currX, currY+1).getColor()).cellStolen(getCell(currX, currY+1));
						player.captureCell(getCell(currX, currY+1));
						getCell(currX, currY+1).setOccupied(player.playerColor());
					}
				}
			}
		}
		moves = (++moves)%players.size();
	}
	
	public ArrayList<GameCell> cells() {
		return cells;
	}
	
	public GameCell getCell(int currX, int currY) {
		for(GameCell currCell: cells) {
			if(currCell.getX() == currX && currCell.getY() == currY)
				return currCell;
		}
		
		return null;
	}
	

	public int getMoves() {
		return moves;
	}
	
	public int getWidth() {
		return width;
	}
	
	public ArrayList<Player> players() {
		return players;
	}
	
	public ArrayList<GameCell> unoccupiedCells() {
		ArrayList<GameCell> unoccupiedCells = new ArrayList<GameCell>();
		for(GameCell uncell: cells) {
			if(!uncell.isOccupied())
				unoccupiedCells.add(uncell);
		}
		
		return unoccupiedCells;
	}
	
	public int hypotheticalMove(GameCell cell) {
		int currScore = players.get(0).playerScore();
		int max = 0;
		int currX = cell.getX();
		int currY = cell.getY();
		
		//max player
		if(moves == 0) {
			
			currScore += cell.getValue();
			
			if((this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).getColor() == players.get(moves).playerColor())  || 
				(this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).getColor() == players.get(moves).playerColor()) || 
				(this.getCell(currX, currY-1) != null  && this.getCell(currX, currY-1).getColor() == players.get(moves).playerColor()) ||
				(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).getColor() == players.get(moves).playerColor())) {
				
				if(this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).isOccupied()) {
					if(this.getCell(currX-1, currY).getColor() != players.get(moves).playerColor()) {
						currScore+= this.getCell(currX-1, currY).getValue();
					}
				}
				
				if(this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).isOccupied()) {
					if(this.getCell(currX+1, currY).getColor() != players.get(moves).playerColor()) {
						currScore+= this.getCell(currX+1, currY).getValue();
					}
				}
				
				if(this.getCell(currX, currY-1) != null && this.getCell(currX, currY-1).isOccupied()) {
					if(this.getCell(currX, currY-1).getColor() != players.get(moves).playerColor()) {
						currScore+= this.getCell(currX, currY-1).getValue();
					}
				}
				
				if(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).isOccupied()) {
					if(this.getCell(currX, currY+1).getColor() != players.get(moves).playerColor()) {
						currScore+= this.getCell(currX, currY+1).getValue();
					}
				}
			}
			
			return currScore - players.get(0).playerScore();
		}
		
		// min player
		else {
			// if it borders an existing min cell
			if((this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).getColor() == players.get(moves).playerColor())
				|| (this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).getColor() == players.get(moves).playerColor()) || 
				(this.getCell(currX, currY-1) != null  && this.getCell(currX, currY-1).getColor() == players.get(moves).playerColor()) ||
				(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).getColor() == players.get(moves).playerColor())) {
					
					if(this.getCell(currX-1, currY) != null && this.getCell(currX-1, currY).isOccupied()) {
						if(this.getCell(currX-1, currY).getColor() == players.get(0).playerColor()) {
							currScore-= this.getCell(currX-1, currY).getValue();
						}
					}
					
					if(this.getCell(currX+1, currY) != null && this.getCell(currX+1, currY).isOccupied()) {
						if(this.getCell(currX+1, currY).getColor() == players.get(0).playerColor()) {
							currScore-= this.getCell(currX+1, currY).getValue();
						}
					}
					
					if(this.getCell(currX, currY-1) != null && this.getCell(currX, currY-1).isOccupied()) {
						if(this.getCell(currX, currY-1).getColor() == players.get(0).playerColor()) {
							currScore-= this.getCell(currX, currY-1).getValue();
						}
					}
					
					if(this.getCell(currX, currY+1) != null && this.getCell(currX, currY+1).isOccupied()) {
						if(this.getCell(currX, currY+1).getColor() == players.get(0).playerColor()) {
							currScore-= this.getCell(currX, currY+1).getValue();
						}
					}
				}
			
			// if it doesn't border an existing min cell
			else {
					max = cell.getValue();
			}
			
			/*
			 * Want to return a negative value
			 * currScore - players.get(0).playerScore() -> Max stolen value
			 * max value we can obtain ourselves
			 */
			//System.out.println("Utility: " + Math.min(currScore - players.get(0).playerScore(), 0-max));
			return Math.min(currScore - players.get(0).playerScore(), 0-max);

		}
	}
	
	public int hypotheticalMoveNoBlitz(GameCell cell) {
		int currScore = players.get(0).playerScore();
		int max = 0;
		int currX = cell.getX();
		int currY = cell.getY();
		
		//max player
		if(moves == 0) {
			
			currScore += cell.getValue();
			
			return currScore - players.get(0).playerScore();
		}
		
		// min player
		else {
			max = cell.getValue();
			
			return 0-max;

		}
	}
	
	public void printBoard(int width) {
		try{
			PrintWriter writer = new PrintWriter("status.txt", "UTF-8");
			for(int i = 0; i < cells.size(); i++) {
				if(i%width ==0)
					writer.println();
				if(cells.get(i).isOccupied()){
					if(cells.get(i).getColor() == 0)
						writer.print("B: " + cells.get(i).getValue());
					else
						writer.print("G: " + cells.get(i).getValue());
				}
				else
					writer.print("X");
				writer.print("\t");
			}
			writer.close();
			
		}catch(UnsupportedEncodingException | FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	
	
}
