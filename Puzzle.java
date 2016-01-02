package src.mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle 
{
	/**
	 * Stores the categories possible for this maze
	 */
	private ArrayList<Category> categoryList;

	/**
	 * Stores the spots for this maze
	 */
	private ArrayList<Spot> spotList;
	
	/**
	 * Stores the number of letters in this maze
	 */
	private int length;
	
	/**
	 * Stores the list of Solutions possible for this Maze
	 */
	private ArrayList<String> solution;
	
	/**
	 * Constructor
	 */
	public Puzzle(String filename)
	{
		//Initialize the arraylists
		categoryList = new ArrayList<Category>();
		solution = new ArrayList<String>();
		
		//Make the Actual Maze
		makePuzzle(filename);
	}

	// ___________________________________ Constructs Puzzle __________________________________
	
	/**
	 * Makes the Actual Representation of the Puzzle
	 * @param filename The name of the file containing the maze
	 */
	public void makePuzzle(String filename)
	{
		//Create a new file
		File puzzleFile = new File(filename);
		
		try
		{
			//The actual input reader
			Scanner inputReader = new Scanner(puzzleFile);
			
			//Assign the value of the length
			length = inputReader.nextInt();
			
			inputReader.nextLine();
			
			while (inputReader.hasNextLine())
			{
				//Get the Current Line
				String curr_line = inputReader.nextLine();
				
				//Make a new category to add
				Category newCategory = new Category();
				
				String [] temp = curr_line.split(":");
								
				//Set the name of this category
				newCategory.setCategoryName(temp[0]);
				
				//Remove the spaces
				temp[1] = temp[1].replaceAll("\\s", "");
				
				String [] temp2 = temp[1].split(",");
				
				//Add Each Position
				for(int i = 0; i < temp2.length; i++)
				{
					newCategory.addPosition(Integer.parseInt(temp2[i]));
				}
				
				//Read in Words from the File
				newCategory.makeWordList();
				
				//Add this category to this maze's list
				categoryList.add(newCategory);
			}
			
			inputReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}	
		
	}
	
	/**
	 * Tests the Basic Puzzle Parameters
	 */
	public void testPuzzleParameters()
	{
		System.out.println(length);
		
		for (Category temp_category : categoryList)
		{
			System.out.print(temp_category.getCategoryName() + ": ");
			
			ArrayList<Integer> positions_list = temp_category.getPositions();
			
			for (int x: positions_list)
			{
				System.out.print(x + ",");
			}
			
			if(temp_category.getCategoryName().equals("emotion"))
			{
				ArrayList<String> wordList = temp_category.getWordList();
				
				for(String word: wordList)
				{
					System.out.println(word+ " ,");
				}
			}
			
			System.out.println();
		}
		
	}
	

	// ___________________________________ Functions for Word Based Assignment __________________________________

	/** 
	* Method to call recursive algorithm for 
	* word based assignment and sets up necessary variables and print
	* the solution
	*/
	public void runWordBasedAssignment()
	{

		// Create temporary solution
		char[] tempSolution = new char[this.length];

		// Set each spot to blank
		for(int i = 0; i < this.length ; i++)
		{
			tempSolution[i] = ' ';
		}

		// Sort category list
		ArrayList<Category> sortedCategoryList = sortCategoryList();

		// Start the recursive baktracing algorithm
		wordBasedAssignment(tempSolution, 0 , sortedCategoryList , "");

		//System.out.println("Solution Size: " + solution.size());
		
		//Print the Solution
		//for(int i = 0 ; i < solution.size() ; i++)
		//{
		//	System.out.println(solution.get(i));
		//}

	}

	
	/** 
	* Runs recursive back-tracing algorithm for word based assignment
	*/
	public void wordBasedAssignment(char[] tempSolution, int index, ArrayList<Category> sortedCategoryList, String buffer)
	{	
		// If solution doesn't contain any blank spaces, it is printed and returned up a level
		if(!(new String(tempSolution).contains(" ")))
		{
			solution.add(new String(tempSolution));
			System.out.printf("(found result: %s)" , new String(tempSolution));
			return;
		}

		// Gets array of positions for current category
		ArrayList<Integer> positions = sortedCategoryList.get(index).getPositions();

		// Iterate through words in current category
		for(String temp_word : sortedCategoryList.get(index).getWordList())
		{
		 

			// Check if word meets constraints
			if(checkConstraintsWordBasedAssignment(tempSolution, positions, temp_word))
			{
				char[] newTempSolution = new char[tempSolution.length];

				// Copy in temporary solution to be passed in to the next recursive part
				for(int i = 0; i < tempSolution.length ; i++)
				{
					newTempSolution[i] = tempSolution[i];
				}

				// Finds the places for the current category and enters the current temp_word into that spot
				for(int i = 0; i < 3 ; i ++ )
				{
					newTempSolution[positions.get(i) - 1] = temp_word.charAt(i);
				}


					// If statements to print it out in a semi-template				
				     if(index == 0)System.out.printf("\n%s " , temp_word);
				else if(index == 1)System.out.printf("\n  %s " , temp_word);
				else if(index == 2)System.out.printf("\n    %s " , temp_word);
				else if(index == 3)System.out.printf("\n      %s " , temp_word);
				else if(index == 4)System.out.printf("\n        %s " , temp_word);
				else if(index == 5)System.out.printf("\n          %s " , temp_word);
				else if(index == 6)System.out.printf("\n            %s " , temp_word);
				else if(index == 7)System.out.printf("\n              %s " , temp_word);
				else if(index == 8)System.out.printf("\n                %s " , temp_word);
				else if(index == 9)System.out.printf("\n                  %s " , temp_word);

				// Call recursive part
				wordBasedAssignment(newTempSolution, ((index + 1) % sortedCategoryList.size()) , sortedCategoryList, buffer + "    "); // Recursive search to next category with current solution
			}

			else
			{
				continue; // Skips current word since it doesn't work
			} 

		}
	}


	/**
	 * Finds the category with the least number of words left
	 * Returns the index of this category in the categoryList
	 */
	public int getCategoryWithLowestWords()
	{
		int min = 0;
		int counter = 0;
	

		for (Category curr_category : categoryList)
		{
			if(curr_category.getNumWords() < categoryList.get(min).getNumWords())
				min = counter;
			counter++;
		}
		
		return min;
	}

	/** 
	* Sorts categories based on amount of words 
	* using the ones with the least amount(the most constraining)
	* first
	*/
	public ArrayList<Category> sortCategoryList()
	{

		ArrayList<Category> newList = new ArrayList<Category>();

		int minimum;

		int numberOfCategories = categoryList.size();

		// goes through and finds the category with lowest words and removes it and adds to new list
		for(int i = 0; i < numberOfCategories ; i++)
		{
			minimum = getCategoryWithLowestWords();
			newList.add(categoryList.get(minimum));
			categoryList.remove(minimum);
		}
		
		return newList;
	}

	/** 
	* Method checks whether the current word fits in to the solution. 
	* It will check the positions it goes in to and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraintsWordBasedAssignment(char[] tempSolution, ArrayList<Integer> positions, String word)
	{
		if((word.charAt(0) == tempSolution[positions.get(0) - 1] || tempSolution[positions.get(0) -1] == ' ')
			&& (word.charAt(1) == tempSolution[positions.get(1) - 1] || tempSolution[positions.get(1) -1] == ' ') 
				&& (word.charAt(2) == tempSolution[positions.get(2) - 1] || tempSolution[positions.get(2) -1] == ' ')) 
			return true;
		else 
			return false;
	}


	// ___________________________________ Functions for Letter Based Assignment __________________________________

	/** 
	* Method to call recursive algorithm for 
	* letter based assignment and sets up necessary variables and print
	* the solution
	*/
	public void runLetterBasedAssignment()
	{

		// assigns the spots for each space in the puzzle from categories
		getSpotsFromCategories();

		// mkae temp solution to pass in to the function
		char[] tempSolution = new char[this.length];

		for(int i = 0; i < this.length ; i++)
		{
			tempSolution[i] = ' ';
		}

		ArrayList<Spot> sortedSpotList = sortSpotList();

		// Sets up list forward checing will be ran on
		ArrayList<ArrayList<Character>> forwardChecking = new ArrayList<ArrayList<Character>>();

		for(int i = 0; i < sortedSpotList.size() ; i++){
			ArrayList<Character> inner = new ArrayList<Character>();
			inner = sortedSpotList.get(i).getLetterList();
			forwardChecking.add(inner);

		}

		// Calls recursive backtracing algorithm
		letterBasedAssignment(tempSolution, 0 , sortedSpotList, forwardChecking);

	
	}


	/** 
	* Runs recursive back-tracing algorithm for letter based search
	*/
	public void letterBasedAssignment(char[] tempSolution, int index, ArrayList<Spot> sortedSpotList , ArrayList<ArrayList<Character>> forwardChecking )
	{	

		// Checks if any remaining spots in forward checking are blank and if so returns
		for(ArrayList<Character> list : forwardChecking){
			if(list.size() == 0) 
				{
					return;
				}
		}
		// If solution doesn't contain any blank spaces, it is printed and returned up a level
		if(!(new String(tempSolution).contains(" ")))
		{
			solution.add(new String(tempSolution));
			System.out.printf("(found result: %s)" , new String(tempSolution));
			return;
		}

		

		// Gets array of categories for current position
		ArrayList<Category> categories = sortedSpotList.get(index).getCategories();

		// Iterate through letters in current position
		for(char temp_letter : forwardChecking.get(index))
		{
			
			boolean flag = true;

			char[] constraintTempSolution = new char[tempSolution.length];

			// Creates new solution to pass recursively
			for(int i = 0; i < tempSolution.length ; i++)
			{
				constraintTempSolution[i] = tempSolution[i];
			}

			// Check if letter meets constraints
			for(Category curr_category : categories){

				if(!checkConstraintsLetterBasedAssignment(constraintTempSolution, curr_category, temp_letter, sortedSpotList.get(index).getSpotNumber())){
					flag = false;
					break;
				}
			}

			if(!flag) continue;

			// Sets temp forward checking to be passed recursively
			ArrayList<ArrayList<Character>> tempForwardChecking = new ArrayList<ArrayList<Character>>();
		

			for(ArrayList<Character> list : forwardChecking)
			{
				ArrayList<Character> inner = new ArrayList<Character>();
				for(char letter : list)
				{
					inner.add(new Character(letter));
				}
				tempForwardChecking.add(inner);
			}


			// Forward checks and elimates spots that don't work with the current letter
			forwardChecking(tempForwardChecking, sortedSpotList, temp_letter, index);

			char[] newTempSolution = new char[tempSolution.length];

			// Creates new solution to pass recursively
			for(int i = 0; i < tempSolution.length ; i++)
			{
				newTempSolution[i] = tempSolution[i];
			}

			// adds current letter to solution
			newTempSolution[sortedSpotList.get(index).getSpotNumber() - 1] = temp_letter;

		         if(index == 0)System.out.printf("\n%s " , temp_letter);
		    else if(index == 1)System.out.printf("\n  -> %s " , temp_letter);
			else if(index == 2)System.out.printf("\n       -> %s " , temp_letter);
			else if(index == 3)System.out.printf("\n            -> %s " , temp_letter);
			else if(index == 4)System.out.printf("\n                 -> %s " , temp_letter);
			else if(index == 5)System.out.printf("\n                      -> %s " , temp_letter);
			else if(index == 6)System.out.printf("\n                           -> %s " , temp_letter);
			else if(index == 7)System.out.printf("\n                                -> %s " , temp_letter);
			else if(index == 8)System.out.printf("\n                                     -> %s " , temp_letter);
			else if(index == 9)System.out.printf("\n                                          -> %s " , temp_letter);

			// Recursively calls the back tracing algorithm
			letterBasedAssignment(newTempSolution, ((index + 1) % sortedSpotList.size()) , sortedSpotList , tempForwardChecking); // Recursive search to next category with current solution

		}
	}

	/**
	 * Forward checks to find errors before
	 * we try them
	 */
	public void forwardChecking(ArrayList<ArrayList<Character>> tempForwardChecking, ArrayList<Spot> sortedSpotList , char letter, int index)
	{
		int spot = sortedSpotList.get(index).getSpotNumber();
		ArrayList<Category> categories = sortedSpotList.get(index).getCategories();
		
		// Iterates through categories in spot
		for(Category category : categories){
			int placeInWord = 0;
			ArrayList<String> words = new ArrayList<String>();
			ArrayList<Integer> positions = category.getPositions();
			int j = 0;

			// Iterates through positions in current position
			for(int place : category.getPositions())
			{
				// If the position equals the current spot we are in, it saves the place of the position so we know what 
				// letter in the words for that category correspond to this spot. first letter, second, etc.
				if(place == spot) 
				{
					placeInWord = j;
					break;
				}
				j++;

			}

			// Iterates through words for va=category, if the letter matches the letter we've chosen for this 
			// spot the word is added to a list of possible words
			for(String word : category.getWordList()){
					if(letter == word.charAt(placeInWord)) words.add(word);
			}

			// Iterate through positions
			for(int position = 0 ; position < 3 ; position ++)
			{	
				// If the position matches the current spot we skip, we've already elimanted every other letter
				if(positions.get(position) == spot) continue;

				// Sort through spot list to find it's index that matches the position of category
				for(int i = 0 ; i < sortedSpotList.size() ; i ++)
				{
					if(sortedSpotList.get(i).getSpotNumber() == positions.get(position))
					{
						// uses that index we found to sort through letters at that spot
						for(int x = tempForwardChecking.get(i).size() - 1 ;  x > -1 ; x--)
						{	
							boolean flag = false;
							for(String word : words)
							{
								// If letter in spot doesn't match the possible letters for matching words it's removed
								if(word.charAt(position) == (tempForwardChecking.get(i)).get(x)) 
								{
									flag = true;
									break;
								}
							}
								
							if(!flag) tempForwardChecking.get(i).remove(x);
								
						}
						break;
					}
				}
			}
		}


	}
	/**
	 * Finds the spot with the least number of letters left
	 * Returns the index of this spot in the spotList
	 */
	public int getSpotWithLowestLetters()
	{
		int min = 0;
		int counter = 0;
		
		for (Spot curr_spot : spotList)
		{
			if(curr_spot.getNumLetters() < spotList.get(min).getNumLetters())
				min = counter;
			counter++;
		}
		
		return min;
	}

	/** 
	* Sorts spots in solution based on amount of letters
	* using the ones with the least amount(the most constraining)
	* first
	*/
	public ArrayList<Spot> sortSpotList()
	{
		ArrayList<Spot> sortedSpotList = new ArrayList<Spot>();

		int minimum;

		int numberOfSpots = spotList.size();

		for(int i = 0; i < numberOfSpots ; i++)
		{
			minimum = getSpotWithLowestLetters();
			sortedSpotList.add(spotList.get(minimum));
			spotList.remove(minimum);
		}

		return sortedSpotList;
	}

	/** 
	* Method checks whether the current letter fits in to the solution. 
	* It will check the categories to see if it fits in and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraintsLetterBasedAssignment(char[] tempSolution, Category category, char letter, int spot)
	{
		tempSolution[spot-1] = letter;
		ArrayList<Integer> positions = category.getPositions();
		ArrayList<String> words = category.getWordList();

		
		for(String word: category.getWordList()){
			if((tempSolution[positions.get(0) - 1] == word.charAt(0) || tempSolution[positions.get(0) - 1] == ' ') 
				&& (tempSolution[positions.get(1) - 1] == word.charAt(1) || tempSolution[positions.get(1) - 1] == ' ') 
					&& (tempSolution[positions.get(2) - 1] == word.charAt(2) || tempSolution[positions.get(2) - 1] == ' ')){
				return true;
			}
			else{
				continue;
			}
		}

		return false;
	}

	/** 
	* Sets the spotList from Categories
	*/
	public void getSpotsFromCategories()
	{
		ArrayList<Integer> positions;
		spotList = new ArrayList<Spot>();

		// Go through positions to initilize spots
		for(int i = 1 ; i < length + 1 ; i++){

			// Make new spot with number
			Spot newSpot = new Spot();
			newSpot.setSpotNumber(i);

			// Look through categories and check whether
			// it has a letter that matches the position
			for(Category curr_category : categoryList){

				// Iterate through positions
				positions = curr_category.getPositions();
				for(int j = 0; j < 3 ; j++){

					// If position matches
					if(positions.get(j) == i){

						// Add that letter to this position
						for(String word : curr_category.getWordList()){
							newSpot.addLetter(word.charAt(j));
						}

						// Add category to spot
						newSpot.addCategory(curr_category);
					}

					// If the position is greater than our spot, break
					else if(positions.get(j) > i) break;
				}
			}

			// Add spot to list
			this.spotList.add(newSpot); // NULL POINTER EXCEPTION
		}

	}	

// ___________________________________ Main Function __________________________________

	/**
	 * Driver Method to test the above Code
	 * @param args
	 */
	public static void main(String [] args)
	{
		Puzzle myPuzzle = new Puzzle("src/mp2/puzzle4.txt");
		//myPuzzle.testPuzzleParameters();
		myPuzzle.runLetterBasedAssignment();

	}
	
	
}