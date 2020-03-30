
import java.util.*;

public class AI extends Game{
	
	private int game_board[][];
	private int square_length = 3;
	private int side_length = 9;
	
	public AI(int[][] game_board, int square_length)
	{
		this.game_board = game_board;
		this.square_length = square_length;
		this.side_length = square_length*square_length;
	}
	
	
	public void fillSquare(int game_board[][], int filled_board[][])
	{
		Random rand = new Random();
		int row = rand.nextInt();
		int col = rand.nextInt();
		int value = rand.nextInt(side_length)+1;
		boolean number_filled = false;
		
		while(!number_filled)
		{
			if(game_board[row][col] == 0)
			{
				game_board[row][col] = value;
				number_filled = true;
			}
			else
			{
				row = rand.nextInt();
				col = rand.nextInt();
			}
		}
	}
	
	public void makeMove(int game_board[][], Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_moves)
	{
		
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++) 
			{
				if(game_board[row][col]!=0)
				{

					Object temp = game_board[row][col];
					int current_sub_square = (row/square_length)*square_length + col/square_length;
					
					for(int num=0; num<side_length; num++)
					{
						if(game_board[row][num]==0 && legal_moves.get(row).get(num).contains(temp)) {		//if number is in a given row, remove it from possibilities of other squares in row
							legal_moves.get(row).get(num).remove(temp);
							//System.out.println(row);
							//System.out.println(num);
							//System.out.println(temp);
						}
						
						if(game_board[num][col]==0 && legal_moves.get(num).get(col).contains(temp)) {		//if number is in a given column, remove it from possibilities of other squares in column
							legal_moves.get(num).get(col).remove(temp);
							//System.out.println(row);
							//System.out.println(col);
							//System.out.println(temp);
						}
						
						for(int s=0; s<side_length; s++)						//if number found in a sub_square, remove it from possibilities of other squares in sub square
						{
							if(game_board[num][s]==0 && ((num/square_length)*square_length + s/square_length)==current_sub_square && legal_moves.get(num).get(s).contains(temp))
								legal_moves.get(num).get(s).remove(temp);
							
							//System.out.println(row);
							//System.out.println(col);
							//System.out.println(temp);
						}
					}
				}
			}
		}
		
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				if(game_board[i][j]==0 && legal_moves.get(i).get(j).size() == 1)
				{
					game_board[i][j] = legal_moves.get(i).get(j).get(0);
					//System.out.println(i);
					//System.out.println(j);
					//System.out.println(game_board[i][j]);
				}
			}
		}
		
	}
	
	public boolean playGame() 
	{
		Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_moves = new HashMap<>();
		
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
		
		int attempts = 0;
		
		while(!isGameOver(game_board) && attempts<50)
		{
			makeMove(game_board, legal_moves);
			attempts++;

		}
		
		return true;
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
