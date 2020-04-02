
import java.util.*;

public class AI extends Game{
	
	private int game_board[][];
	private int square_length = 3;
	private int side_length = 9;
	private Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_copy;
	private Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_moves;
	
	public AI(int[][] game_board, int square_length)
	{
		this.game_board = game_board;
		this.square_length = square_length;
		this.side_length = square_length*square_length;
		
		legal_moves = new HashMap<>();
		
		for(int i=0; i<side_length; i++)
		{
			legal_moves.put(i, new HashMap<Integer, ArrayList<Integer>>());
			
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
	
	public boolean onlyLeft(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		
		boolean move_made = false;
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				//if(game_board[i][j] ==0)
					//System.out.println(legal_moves.get(i).get(j).size());
				
				if(board[i][j]==0 && legal_moves.get(i).get(j).size()==0)
				{
					System.out.println("failed");
					//throw new EmptyStackException();
					return false;
				}
				
				if(board[i][j]==0 && moves.get(i).get(j).size()==1)
				{
					board[i][j] = moves.get(i).get(j).get(0);
					move_made = true;
				}
			}
		}

		return move_made;
	}
	
	
	public boolean makeMoves(int board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		boolean success = false;
		removeValues(game_board, legal_moves);
		
		if(checkRow(legal_moves))
		{
			removeValues(game_board, legal_moves);
			success = true;
		}
		System.out.println("after row");
		printBoard();
		
		if(checkColumn(legal_moves))
		{
			removeValues(game_board, legal_moves);
			success = true;
		}
		
		System.out.println("after col");
		printBoard();
		
		if(checkSubSquare(legal_moves))
		{
			removeValues(game_board, legal_moves);
			success = true;
		}
		System.out.println("after ss");
		printBoard();

		if(!onlyLeft(game_board, legal_moves) && !success)
			return false;
		
		return true;
	}
	
	public boolean tryMove(int game_board[][])
	{
		for(int row=0; row<side_length; row++)
		{
			int not_filled = 0;
			int first_row = -1;
			int first_col = -1;
			int last_row = -1;
			int last_col = -1;
			
			for(int col=0; col<side_length; col++) 
			{
				if(game_board[row][col]==0) {
					not_filled++;
					
					if(not_filled==1)
					{
						first_row = row;
						first_col = col;
					}
					else if(not_filled==2)
					{
						last_row = row;
						last_col = col;
					}
					if(not_filled>2)
						break;
				}
			}
			
			if(not_filled==2 && legal_moves.get(last_row).get(last_col).size()==2)
			{
				int trial = 0;
				int [][] first_game = new int[side_length][];
				
				for(Object num: legal_moves.get(first_row).get(first_col))
				{
					trial++;
					legal_copy = copyMap(legal_moves);
					int[][] temp_board = game_board.clone();
					
					temp_board[first_row][first_col] = (int) num;
					
					
					while(makeMoves(temp_board, legal_copy))
					{
						if(attemptFailed(temp_board, legal_copy))
						{
							if(trial==1)
								game_board[last_row][last_col] = (int) num;
							else if(trial==2)
								game_board[first_row][first_col] = (int) num;
							
							System.out.println("works");
							return true;
						}
					}
					
					if(!isGameOver(temp_board))
						break;
					else
					{
						if(trial==1)
						{
							for(int i=0; i<side_length; i++)
							    first_game[i] = temp_board[i].clone();
						}
						else if(trial==2)
						{
							return false;
						}
					}
				}
			}
		}
		
		for(int col=0; col<side_length; col++)
		{
			int not_filled = 0;
			int first_row = -1;
			int first_col = -1;
			int last_row = -1;
			int last_col = -1;
			
			for(int row=0; row<side_length; row++) 
			{
				if(game_board[row][col]==0) {
					not_filled++;
					
					if(not_filled==1)
					{
						first_row = row;
						first_col = col;
					}
					else if(not_filled==2)
					{
						last_row = row;
						last_col = col;
					}
					if(not_filled>2)
						break;
				}
			}
			
			if(not_filled==2 && legal_moves.get(last_row).get(last_col).size()==2)
			{
				int trial = 0;
				int [][] first_game = new int[side_length][];
				
				for(Object num: legal_moves.get(first_row).get(first_col))
				{
					trial++;
					legal_copy = copyMap(legal_moves);
					int[][] temp_board = game_board.clone();
					
					temp_board[first_row][first_col] = (int) num;
					
					
					while(makeMoves(temp_board, legal_copy))
					{
						if(trial==1)
							game_board[last_row][last_col] = (int) num;
						else if(trial==2)
							game_board[first_row][first_col] = (int) num;
						
						System.out.println("works");
						return true;
					}
					
					if(!isGameOver(temp_board))
						break;
					else
					{
						if(trial==1)
						{
							for(int i=0; i<side_length; i++)
							    first_game[i] = temp_board[i].clone();
						}
						else if(trial==2)
						{
							return false;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean tryMoveAdvanced(int game_board[][])
	{
		System.out.println("before");
		printBoard();
		boolean success = false;
		
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++) 
			{
				if(game_board[row][col]==0) 
				{
					ArrayList<Integer> remove_nums = new ArrayList<>();
					
					for(Object num: legal_moves.get(row).get(col))
					{
						legal_copy = copyMap(legal_moves);
						int[][] temp_board = game_board.clone();
						
						temp_board[row][col] = (int) num;
						printBoard();
						
						while(makeMoves(temp_board, legal_copy))
						{
							if(attemptFailed(temp_board, legal_copy))
							{
								System.out.println("row: " + row);
								System.out.println("col: " + col);
								System.out.println("num: " + num);
								remove_nums.add((int) num);
								success = true;
							}
						}
					}
					
					for(Object num: remove_nums)
						legal_moves.get(row).get(col).remove(num);
				}
			}
		}
		System.out.println("after");
		printBoard();
		return success;
	}
	
	public boolean sameBoard(int[][] first, int[][] second) 
	{
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++)
			{
				if(first[row][col]!=second[row][col])
					return false;
			}
		}
		
		return true;
	}
	
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
	
	public boolean attemptFailed(int[][] board, Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				if(board[i][j]==0 && moves.get(i).get(j).size()==0)
				{
					System.out.println("failed");
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean checkRow(Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		Map<Integer, Integer> num_left = new HashMap<>();
		boolean success = false;
		
		for(int row=0; row<side_length; row++)
		{
			for(int i=1; i<=side_length; i++)
			{
				num_left.put(i,  0);
			}
			
			for(int col=0; col<side_length; col++)
			{
				for(int num=1; num<=side_length; num++)
				{
					if(game_board[row][col]==0 && moves.get(row).get(col).contains((Object) num))
					{
						int val = num_left.get(num);
						num_left.put(num, ++val);
					}
						
				}
			}
			
			for(int j=1; j<=side_length; j++)
			{
				if(num_left.get(j)==1)
				{
					for(int c=0; c<side_length; c++)
					{
						if(game_board[row][c]==0 && moves.get(row).get(c).contains(j))
						{
							game_board[row][c] = j;
							success = true;
						}
							
					}
				}
			}
		}
		
		return success;
	}
	
	public boolean checkColumn(Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		Map<Integer, Integer> num_left = new HashMap<>();
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
					if(game_board[row][col]==0 && moves.get(row).get(col).contains((Object) num))
					{
						int val = num_left.get(num);
						num_left.put(num, ++val);
					}
						
				}
			}
			
			for(int j=1; j<=side_length; j++)
			{
				if(num_left.get(j)==1)
				{
					for(int r=0; r<side_length; r++)
					{
						if(game_board[r][col]==0 && moves.get(r).get(col).contains(j))
						{
							game_board[r][col] = j;
							success = true;
						}
							
					}
				}
			}
		}
		
		return success;
	}
	
	public boolean checkSubSquare(Map<Integer, HashMap<Integer, ArrayList<Integer>>> moves)
	{
		Map<Integer, HashMap<Integer, Integer>> num_left = new HashMap<>();
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
					if(game_board[row][col]==0 && moves.get(row).get(col).contains((Object) num))
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
				if(num_left.get(ss).get(j)==1)
				{
					for(int r=0; r<side_length; r++)
					{
						for(int c=0; c<side_length; c++)
						{
							int current_sub_square = (r/square_length)*square_length + c/square_length;
							
							if(game_board[r][c]==0 && current_sub_square==ss && moves.get(r).get(c).contains(j))
							{
								game_board[r][c] = j;
								success = true;
							}
						}
					}
				}
			}
		}
		
		return success;
	}
	
	public boolean iterateBoard(int[][] board) 
	{
		while(!isGameOver(board) && makeMoves(board, legal_moves)){} //keep iterating through game_board to make moves until game is complete or no moves made in complete iteration
		
		if(!isGameOver(game_board))
			return false;
		
		return true;
	}
	
	public boolean playGame(int[][] board)
	{
		int attempts = 0;
		
		while(!isGameOver(board) && attempts<20)
		{
			attempts++;
			
			if(!iterateBoard(board))
			{	
				if(!tryMoveAdvanced(board))
					return false;
					
			}
		}
		
		if(isGameOver(board))
			return true;
		
		return false;
		
	}
	
	public boolean isGameOver(int[][] game_board)
	{
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++)
			{
				if(game_board[row][col]==0)
					return false;
			}
		}
		
		return true;
	}
	
	public int[][] returnBoard()
	{
		return game_board;
	}
	
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
