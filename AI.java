import java.util.*;

public class AI extends Game{
	
	private int filled_board[][];
	private int game_board[][];
	private int square_length = 3;
	private int side_length = 9;
	
	public AI(int filled_board[][], int square_length)
	{
		this.filled_board = filled_board;
		this.square_length = square_length;
		this.side_length = square_length*square_length;
	}
	
	private int[][] game_board_initializer(int side_length) 
	{
		game_board = new int[side_length][side_length];
		
		 for (int i=0; i<side_length; i++)
		    {
			    for (int j=0; j<side_length; j++)
			    {
			    	game_board[i][j] = 0;
			    }
		    }
		 return game_board;
	}
	
	public void fill_square(int game_board[][], int filled_board[][])
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
	
	public void make_move(int game_board[][])
	{
		Map<Integer, HashMap<Integer, ArrayList<Integer>>> legal_moves = new HashMap<>();
		
		for(int i=0; i<side_length; i++)
		{
			legal_moves.put(i,  new HashMap<Integer, ArrayList<Integer>>());
			
			for(int j=0; j<side_length; j++)
			{
				legal_moves.get(i).put(j,  new ArrayList<Integer>());
				
				for(int k=0; k<side_length; k++)
				{
					legal_moves.get(i).get(j).add(k);
				}
				
			}
		}
		
		for(int row=0; row<side_length; row++)
		{
			for(int col=0; col<side_length; col++)
			{
				if(game_board[row][col] != 0)
				{
					Object temp = game_board[row][col];
					
					for(int num=0; num<side_length; num++)
					{
						if(legal_moves.get(row).get(num).contains(temp))
							legal_moves.get(row).get(num).remove(temp);
						if(legal_moves.get(num).get(col).contains(temp))
							legal_moves.get(num).get(col).remove(temp);
					}
				}
			}
		}
		
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				if(legal_moves.get(i).get(j).size() == 1)
				{
					game_board[i][j] = legal_moves.get(i).get(j).get(0);
				}
			}
		}
		
	}

}
