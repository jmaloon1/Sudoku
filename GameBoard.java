import java.util.*;

public class GameBoard {
	
	private int filled_board[][];
	private int game_board[][];
	private int square_length = 3;
	private int side_length = square_length*square_length;
	private char difficulty;
	
	public GameBoard(int filled_board[][], int square_length, char difficulty)
	{
		this.filled_board = filled_board;
		this.square_length = square_length;
		this.side_length = square_length*square_length;
		this.difficulty = difficulty;
		
		game_board = new int[side_length][side_length];
		
		 for (int i=0; i<side_length; i++)
		    {
			    for (int j=0; j<side_length; j++)
			    {
			    	game_board[i][j] = 0;
			    }
		    }
		 
	}
	
	public void initiateBoard()
	{
		game_board = new int[side_length][side_length];
		
		for (int i=0; i<side_length; i++)
	    {
		    for (int j=0; j<side_length; j++)
		    {
		    	game_board[i][j] = 0;
		    }
	    }
	}
	
	
	public int[][] makeGame()
	{
		initiateBoard();
		
		int total;
		int fill = 30;
		
		if(difficulty=='h')		//if difficulty level is set to 'h' for hard, fill in squares with low probability (with odds fill/total or 30/100)
			total = 100;
		else					//if difficulty level is not  set to 'h' for hard, fill in squares with high probability (with odds fill/total or 30/70)
			total = 70;
		
		for(int row=0; row<side_length; row++)  //filling in each individual square with probability fill/total
		{
			for(int col=0; col<side_length; col++)
			{
				Random rand = new Random();
				int x = rand.nextInt(total);		//creating random number between 0 and total
				
				if(x<fill)    //if random number is less than fill, fill in square
					game_board[row][col] = filled_board[row][col];
			}
		}
		
		ambiguityFinder();  //for ambiguous squares, making it unambiguous
		
		System.out.println("new board");
		printBoard();
		
		int [][] test_game_board = new int[side_length][];
		for(int i=0; i<side_length; i++)
		    test_game_board[i] = game_board[i].clone();
		
		
		AI ai = new AI(test_game_board, square_length);
		
		if(!ai.playGame(test_game_board))
		{	
			makeGame();
		}
		
		return game_board;
	}
	
	public void ambiguityFinder() 
	{
		Random rand = new Random();
		int current_sub_square;
		int[] arr = new int[square_length-1];
		
		for(int m=1; m<square_length; m++)   //adding values to arr
		{
			arr[m-1] = m;
			
		}
		
		
		for(int row=0; row<side_length-1; row++)  //filling in each individual square with probability fill/total
		{
			for(int col=0; col<side_length-1; col++)
			{
				current_sub_square = (row/square_length)*square_length + col/square_length;
				
				if(game_board[row][col]==0) 
				{
					for(int num: arr)
					{
						if(row+num<side_length && game_board[row+num][col]==0 && current_sub_square==((row+num)/square_length)*square_length + col/square_length) 
						{
							for(int j=col+1; j<side_length; j++) 
							{
								
								if(game_board[row][j]==0 && game_board[row+num][j]==0)
								{
									if(filled_board[row][col]==filled_board[row+num][j] && filled_board[row+num][col]==filled_board[row][j])
									{
										int x = rand.nextInt(4);
										
										if(x==0)
											game_board[row][col] = filled_board[row][col];
										else if(x==1)
											game_board[row+num][col] = filled_board[row+num][col];
										else if(x==2)
											game_board[row][j] = filled_board[row][j];
										else
											game_board[row+num][j] = filled_board[row+num][j];
									}
								}
							}
						}
						
						if(col+num<side_length && game_board[row][col+num]==0 && current_sub_square==(row/square_length)*square_length + (col+num)/square_length) 
						{
							for(int j=row+1; j<side_length; j++) 
							{
								if(game_board[j][col]==0 && game_board[j][col+num]==0)
								{
									if(filled_board[row][col]==filled_board[j][col+num] && filled_board[row][col+num]==filled_board[j][col])
									{
										int x = rand.nextInt(4);
										
										if(x==0)
											game_board[row][col] = filled_board[row][col];
										else if(x==1)
											game_board[row][col+num] = filled_board[row][col+num];
										else if(x==2)
											game_board[j][col] = filled_board[j][col];
										else
											game_board[j][col+num] = filled_board[j][col+num];
									}
								}
							}
						}
					}
					
				}
			}
		}
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
