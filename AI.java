
import java.util.*;

/**
 * This class is an AI that both verifies the unambiguity of a given incomplete game board as well as solves games
*/
public class AI extends Game{
	
	private int game_board[][];
	private int square_length = 3;
	private int side_length = 9;
	private Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_moves;		//hashmap for moves left for each square. Format = (int row : int col: ArrayList legal moves left)
	private Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_copy;		//deep copy of legal_moves used when trying different values for a given square
	
	
	/**
     * This constructor initializes the square_length and side_length variables given a passed along square length as well as intiitalizing the legal_moves hashmap
     * @param game_board: int[][] representing game board at its current unfilled state
     * @param square_length: int that represents the length of a sub_square (3 is standard)
    */
	public AI(int[][] game_board, int square_length)
	{
		this.game_board = game_board;
		this.square_length = square_length;
		this.side_length = square_length*square_length;
		
		legal_moves = new HashMap<>();
		
		for(int i=0; i<side_length; i++)
		{
			legal_moves.put(i, new HashMap<Integer, ArrayList<Integer>>());	//initializing hashmap
			
			for(int j=0; j<side_length; j++)
			{
				if(game_board[i][j]==0)
				{
					legal_moves.get(i).put(j, new ArrayList<Integer>());
					
					for(int k=1; k<=side_length; k++)
					{
						legal_moves.get(i).get(j).add(k);
					}
				}
			}
		}
	}
	
	/**
     * This method systematically removes possible numbers for each square in the game board
     * @param board: int[][] representing game board at its current unfilled state
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any changes made to the moves hashmap, false otherwise
    */
	public boolean removeValues(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		boolean move_made = false;
		
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++) 
			{
				if(board[row][col]!=0)
				{
					Object temp = board[row][col];
					int current_sub_square = (row/square_length)*square_length + col/square_length;
					
					for(int num=0; num<side_length; num++)
					{
						if(board[row][num]==0 && moves.get(row).get(num).contains(temp)) 	//if number is in a given row, remove it from possibilities of other squares in row
							moves.get(row).get(num).remove(temp);
						
						if(board[num][col]==0 && moves.get(num).get(col).contains(temp)) 	//if number is in a given column, remove it from possibilities of other squares in column
							moves.get(num).get(col).remove(temp);
						
						for(int s=0; s<side_length; s++)	//if number found in a sub_square, remove it from possibilities of other squares in sub square
						{
							if(board[num][s]==0 && ((num/square_length)*square_length + s/square_length)==current_sub_square && moves.get(num).get(s).contains(temp))
								moves.get(num).get(s).remove(temp);
						}
					}
				}
			}
		}
	
		return move_made;
	}
	
	/**
     * This method checks if only one possible move is left for a given square and plays the move
     * @param board: int[][] representing game board at its current unfilled state
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean onlyLeft(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves, boolean verifying)
	{
		boolean success = false;
		
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{				
				if(board[i][j]==0 && moves.get(i).get(j).size()==1)    //checking if the size of remaining moves possible for a given square is 1
				{
					board[i][j] = moves.get(i).get(j).get(0);
					moves.get(i).get(j).clear();
					
					if(verifying)
						return true;
					else
						success = true;
				}
			}
		}

		return success;
	}
	
	/**
     * This method looks at a given row and plays a move if a certain number can only go in one square in that row
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean errorChecker(int board[][], int num)
	{
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++)
			{
				return false;
			}
		}
			
		return true;
	
	}
	
	/**
     * This method looks at a given row and plays a move if a certain number can only go in one square in that row
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean checkRow(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves, boolean verifying)
	{
		Map<Integer, Integer> num_left = new HashMap<>();	//hashmap with each number in a row as key and number of places it can go in the row as the value
		boolean success = false;
		
		for(int row=0; row<side_length; row++)
		{
			for(int i=1; i<=side_length; i++)
			{
				num_left.put(i, 0);		//initializing hashmap
			}
			
			for(int col=0; col<side_length; col++)
			{
				for(int num=1; num<=side_length; num++)
				{
					if(board[row][col]==0 && moves.get(row).get(col).contains((Object) num))  //adding one to each element of the hashmap if that element can go in given square
					{
						int val = num_left.get(num);
						num_left.put(num, ++val);
					}
				}
			}
			
			for(int j=1; j<=side_length; j++)
			{
				if(num_left.get(j)==1)     //if a move can only go in one entry in a given row, play it
				{
					for(int c=0; c<side_length; c++)
					{
						if(board[row][c]==0 && moves.get(row).get(c).contains(j))
						{
							board[row][c] = j;
							moves.get(row).get(c).clear();
							
							if(verifying)
								return true;
							else
								success = true;
						}
					}
				}
			}
		}
		
		return success;
	}
	
	/**
     * This method looks at a given column and plays a move if a certain number can only go in one square in that column
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean checkColumn(int[][] board, Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves, boolean verifying)
	{
		Map<Integer, Integer> num_left = new HashMap<>();    //hashmap with each number in a column as key and number of places it can go in the column as the value
		boolean success = false;
		
		for(int col=0; col<side_length; col++)
		{
			for(int i=1; i<=side_length; i++)
			{
				num_left.put(i,  0);
			}
			
			for(int row=0; row<side_length; row++)
			{
				for(int num=1; num<=side_length; num++)
				{
					if(board[row][col]==0 && moves.get(row).get(col).contains((Object) num))   //adding one to each element of the hashmap if that element can go in given square
					{
						int val = num_left.get(num);
						num_left.put(num, ++val);
					}
						
				}
			}
			
			for(int j=1; j<=side_length; j++)
			{
				if(num_left.get(j)==1)	//if a move can only go in one entry in a given column, play it
				{
					for(int r=0; r<side_length; r++)
					{
						if(board[r][col]==0 && moves.get(r).get(col).contains(j))
						{
							board[r][col] = j;
							moves.get(r).get(col).clear();
							
							if(verifying)
								return true;
							else
								success = true;
						}
							
					}
				}
			}
		}
		
		return success;
	}
	
	/**
     * This method looks at a given subsquare and plays a move if a certain number can only go in one square in that subsquare
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean checkSubSquare(int[][] board, Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves, boolean verifying)
	{
		Map<Integer, HashMap<Integer, Integer>> num_left = new HashMap<>();	  //hashmap with each num in a subsquare as key and num of places it can go in the subsquare as value
		boolean success = false;
		
		for(int i=0; i<side_length; i++)
		{
			num_left.put(i, new HashMap<Integer, Integer>());
			
			for(int j=1; j<=side_length; j++)
			{
				num_left.get(i).put(j, 0);
			}
		}
		
		for(int col=0; col<side_length; col++)
		{
			for(int row=0; row<side_length; row++)
			{
				int current_sub_square = (row/square_length)*square_length + col/square_length;
				
				for(int num=1; num<=side_length; num++)
				{
					if(board[row][col]==0 && moves.get(row).get(col).contains((Object) num))   //adding one to each element of the hashmap if that element can go in given square
					{
						int val = num_left.get(current_sub_square).get(num);
						num_left.get(current_sub_square).put(num, ++val);
					}
				}
			}
		}
		
		for(int ss=0; ss<side_length; ss++)
		{
			for(int j=1; j<=side_length; j++)
			{
				if(num_left.get(ss).get(j)==1)	  //if a move can only go in one entry in a given subsquare, play it
				{
					for(int r=0; r<side_length; r++)
					{
						for(int c=0; c<side_length; c++)
						{
							int current_sub_square = (r/square_length)*square_length + c/square_length;
							
							if(board[r][c]==0 && current_sub_square==ss && moves.get(r).get(c).contains(j))
							{
								board[r][c] = j;
								moves.get(r).get(c).clear();
								
								if(verifying)
									return true;
								else
									success = true;
							}
						}
					}
				}
			}
		}
		
		return success;
	}
	
	/**
     * This method iterates through other methods that update logic of game and make moves 
     * @param board: int[][] representing game board at its current unfilled state
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean makeMoves(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves, boolean verifying)
	{
		boolean success = false;
		removeValues(board, moves);
		
		if(checkRow(board, moves, verifying))
		{
			removeValues(board, moves);
			success = true;
		}
		
		if(checkColumn(board, moves, verifying))
		{
			removeValues(board, moves);
			success = true;
		}
				
		if(checkSubSquare(board, moves, verifying))
		{
			removeValues(board, moves);
			success = true;
		}

		if(!onlyLeft(board, moves, verifying) && !success)
			return false;
		
		return true;
	}
	
	/**
     * This method tries moves if no moves can be made in makeMoves method. Will look for game logic contradictions to remove possible game moves  
     * @param board: int[][] representing game board at its current unfilled state
     * @param verifying: boolean that is true if trying to verify if a certain game board is legal, false if trying to just solve game.
     * @return boolean: true if any moves are made, false otherwise
    */
	public boolean tryMove(int board[][], boolean verifying)
	{
		boolean success = false;
		boolean completed_board = false;
		
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++) 
			{
				if(board[row][col]==0) 
				{
					ArrayList<Integer> remove_nums = new ArrayList<>();
					
					for(Object num: legal_moves.get(row).get(col))		//"playing" a possible move for a given square
					{
						legal_copy = copyMap(legal_moves);		//creating a deep copy of legal moves map
						
						int[][] temp_board = new int[side_length][side_length];   //creating a deep copy of the game board
						
						for(int i=0; i<side_length; i++)
						{
							for(int j=0; j<side_length; j++)
							{
				    			temp_board[i][j] = board[i][j];
							}
						}

						temp_board[row][col] = (int) num;		//trying a given move
						System.out.println("Tryingggggggggg");
						System.out.println("row " + row);
						System.out.println("col " + col);
						System.out.println("num " + num);
						for(int i=0; i<side_length; i++)
						{
							for(int j=0; j<side_length; j++)
							{
				    			System.out.print(temp_board[i][j] + " ");
							}
							System.out.println("");
						}
						System.out.println("");
						
						while(makeMoves(temp_board, legal_copy, verifying))	//simulating rest of game based on updated game board
						{
							if(attemptFailed(temp_board, legal_copy) && verifying)	//if attempt fails, can remove initial guess from hashmap
							{
								for(int i=0; i<side_length; i++)
								{
									for(int j=0; j<side_length; j++)
									{
						    			System.out.print(temp_board[i][j] + " ");
									}
									System.out.println("");
								}
								System.out.println("");
								System.out.println(legal_copy);
								remove_nums.add((int) num);
								System.out.println("SUUUCCCESSS");
								System.out.println("row " + row);
								System.out.println("col " + col);
								System.out.println("num " + num);
								
								success = true;
							}
							

							if(verifying && isGameOver(temp_board))  //if game is won with initital guess, simply return the update the real game board to be the this attempt
								if(!completed_board)  // if verifying game  and first successful attempt
									completed_board = true;
								else    // if verifying game and already had successful attempt, return false (ambiguous)
									return false;
								
							
							if(!verifying && isGameOver(temp_board))  //if game is won with initital guess, simply return the update the real game board to be the this attempt
								board = temp_board.clone();
						}
					}
					
					for(Object num: remove_nums)		//removing elements from hashmap if they cause a contradiction
						legal_moves.get(row).get(col).remove(num);
				}
			}
		}

		return success;
	}
	
	/**
     * This method creates a deep copy of a hashmap
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return copy: deep copy of passed in hashmap
    */
	public Map<Integer, HashMap<Integer, ArrayList<Integer>>> copyMap(Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		Map<Integer, HashMap<Integer, ArrayList<Integer>>> copy = new HashMap<>();
		
		for(int i=0; i<side_length; i++)
		{
			copy.put(i, new HashMap<Integer, ArrayList<Integer>>());
			
			for(int j=0; j<side_length; j++)
			{
				if(legal_moves.get(i).get(j)!=null)
					copy.get(i).put(j, new ArrayList<Integer>(legal_moves.get(i).get(j)));
			}
		}
		
		return copy;
	}
	
	/**
     * This method looks for empty spaces ina game board with no possible moves which results in a contradiction
     * @param board: int[][] representing game board at its current unfilled state
     * @param moves: hashmap representing legal moves left for each given square. Format = (int row : int col: ArrayList legal moves left)
     * @return boolean: true if attempt failed, false otherwise
    */
	public boolean attemptFailed(int[][] board, Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				if(board[i][j]==0 && moves.get(i).get(j).size()==0)
				{
					System.out.println("bij " + board[i][j]);
					System.out.println("i " + i);
					System.out.println("j " + j);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
     * Iterates through board updating logic behind game and making moves
     * @param board: int[][] representing game board at its current unfilled state
     * @return boolean: true if game is completed, false otherwise
    */
	public boolean iterateBoard(int[][] board, boolean verifying) 
	{
		while(!isGameOver(board) && makeMoves(board, legal_moves, verifying)){} //keep iterating through game_board to make moves until game is complete or no moves made in complete iteration
		
		if(!isGameOver(game_board))
			return false;
		
		return true;
	}
	
	
	/**
     * This method plays a game
     * @param board: int[][] representing game board
     * @param verifying: boolean that is true if trying to verify if a certain game board is legal, false if trying to just solve game.
     * @return boolean: true if game completed, false otherwise
    */
	public boolean playGame(int[][] board, boolean verifying)
	{		
		while(!isGameOver(board))	//iterate through game board while game is not over
		{			
			if(!iterateBoard(board, verifying))
			{	
				System.out.println("Trying move");
				for(int i=0; i<side_length; i++)
				{
					for(int j=0; j<side_length; j++)
					{
		    			System.out.print(board[i][j] + " ");
					}
					System.out.println("");
				}
				System.out.println("");
				if(!tryMove(board, verifying))
					return false;
			}
		}
		
		if(isGameOver(board))
			return true;
		
		return false;
	}
	
	/**
     * This method checks if a game has been completed
     * @param board: int[][] representing game board
     * @return boolean: true if game completed, false otherwise
    */
	public boolean isGameOver(int[][] board)
	{
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++)
			{
				if(board[row][col]==0)
					return false;
			}
		}
		
		return true;
	}
	
	/**
     * This method returns a given game board
     * @param none
     * @return board: int[][] representing game board
    */
	public int[][] returnBoard()
	{
		return game_board;
	}
	
	/**
     * This method print the game board
     * @param none
     * @return void
    */
	public void printBoard()
	{
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
    			System.out.print(game_board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

}
