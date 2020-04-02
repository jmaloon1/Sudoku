
import java.util.*;


public class Game 
{
	private int square_length = 3;
    private int side_length = square_length*square_length;
    private int current_sub_square = 0;
    private int board[][];
    private Map<Integer, ArrayList<Integer>> sub_squares = new HashMap<>();
    private Map<Integer, ArrayList<Integer>> rows = new HashMap<>();
    private Map<Integer, ArrayList<Integer>> columns = new HashMap<>();
    private Map<Integer, HashMap<Integer, ArrayList<Integer>>> temp_sub_squares = new HashMap<>();
    private Map<Integer, ArrayList<Integer>> temp_columns = new HashMap<>();
    private boolean num_found = false;
    private boolean restart = false;
    private int iterations = 0;
    private int entries = 0;
    
    
    public Game()
    {

    }
    
    public Game(int square_length)
    {
    	this.square_length = square_length;
    	this.side_length = square_length*square_length;
    }
    
    
    public void gameSetup() 
    {
    	gameSetupRestart();

	    while(entries<side_length*side_length)
	    {

	    	for(int row_num=0; row_num<side_length; row_num++)
	    	{
	    		for(int col_num=0; col_num<side_length; col_num++)
		    	{
	    			current_sub_square = (row_num/square_length)*square_length + col_num/square_length;
	    			num_found = false;
	    			
	    			while(num_found==false)
	    			{
	    				ArrayList<Integer> temp = new ArrayList<>(sub_squares.get(current_sub_square));
			    		temp.retainAll(rows.get(row_num));
			    		temp.retainAll(columns.get(col_num));
			    		
			    		if(temp.isEmpty())
			    		{
			    			sub_squares = subSquareReset(sub_squares, current_sub_square, row_num);
			    			rows = rowReset(rows, row_num);
			    			columns = colReset(columns, col_num);
			    			boardReset(board, row_num);
			    			col_num = 0;
			    			current_sub_square = (row_num/square_length)*square_length + col_num/square_length;
			    			iterations++;
			    			if(iterations>side_length*side_length)
			    			{
			    				restart = true;
			    				break;
			    			}
			    		}
			    		else
			    		{
				    		Random rand = new Random();
				    		int seed = rand.nextInt(temp.size());
				    		int value = temp.get(seed);
			    			board[row_num][col_num] = value;
			    			Object o = value;
			    			temp_columns.get(col_num).add(value);
			    			temp_sub_squares.get(current_sub_square).get(row_num%square_length).add(value);
			    			sub_squares.get(current_sub_square).remove(o);
			    			rows.get(row_num).remove(o);
			    			columns.get(col_num).remove(o);
			    			entries++;
			    			num_found = true;
			    		}
	    			} 
	    			
	    			if(restart) {
	    				break;
	    			}
		    	}
	    		
	    		for(int m=0; m<side_length; m++)
	    		{
	    			temp_columns.get(m).clear();
	    		}
	    		
	    		if(restart)
	    		{
	    			gameSetupRestart();
	    			break;
	    		}
	    	}
	    }
	    
		if(!boardChecker(board, side_length, square_length))
		{
		    gameSetup();
		}
		else		//adding 1 in each entry of the board so they go from 1-side_length instead of 0-(side_length-1)
		{
			for(int i=0; i<side_length; i++)
			{
				for(int j=0; j<side_length; j++)
				{
					board[i][j] = board[i][j] + 1;
				}
			}
		}

    }
    
    public void gameSetupRestart()
	{
		entries = 0;
		iterations = 0;
		restart = false;
		board = new int[side_length][side_length];

	    for (int i=0; i<side_length; i++)
	    {
	    	sub_squares.put(i, new ArrayList<>());
	    	rows.put(i, new ArrayList<>());
	    	columns.put(i, new ArrayList<>());
	    	temp_sub_squares.put(i, new HashMap<>());
	    	temp_columns.put(i, new ArrayList<>());
	    }
	    
	    for (int j=0; j<side_length; j++)
	    {
	    	for(int k=0; k<side_length; k++)
	    	{
	    		sub_squares.get(j).add(k);
	    		rows.get(j).add(k);
	    		columns.get(j).add(k);
	    		board[j][k] = -1;
	    	}
	    	for(int m=0; m<side_length; m++)
	    	{	
	    		for(int n=0; n<square_length; n++)
		    	{
	    			temp_sub_squares.get(m).put(n, new ArrayList<>());
		    	}
	    	}
	    }
	}
	
	private Map<Integer, ArrayList<Integer>> subSquareReset(Map<Integer, ArrayList<Integer>> m, int sub_square, int row_num)
    {
		int num_to_clear = sub_square%square_length;
		int sub_row = row_num%square_length;

		for(int i=0; i<=num_to_clear; i++)
    	{
			ArrayList<Integer> temp = new ArrayList<>(temp_sub_squares.get(sub_square-i).get(sub_row));
    		m.get(sub_square-i).addAll(temp);
    		temp_sub_squares.get(sub_square-i).get(sub_row).clear();
    	}

		return m;
    }
	
	private Map<Integer, ArrayList<Integer>> rowReset(Map<Integer, ArrayList<Integer>> m, int row_num)
    {
		m.get(row_num).clear();

		for(int i=0; i<side_length; i++)
    	{
			m.get(row_num).add(i);
    	}
		
		return m;
    }
	
	private Map<Integer, ArrayList<Integer>> colReset(Map<Integer, ArrayList<Integer>> m, int col_num)
    {
		for(int i=0; i<col_num; i++)
    	{
			for(int num:temp_columns.get(i))
			{
				m.get(i).add(num);
			}
			temp_columns.get(i).clear();
    	}
		return m;
    }
	
	public void boardReset(int wrong_board[][], int row_num)
    {
		for(int k=0; k<side_length; k++)
    	{
    		wrong_board[row_num][k] = 0;
    	}
    }
	
	public boolean boardChecker(int board[][], int side_length, int square_length)
	{
		
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
				for(int k=j+1; k<side_length; k++)
				{
					if(board[i][j]==board[i][k] || board[j][i]==board[k][i])
					{
						return false;
					}
				}
			}
		}
		
		int entries = 0;
		int row = 0;
		int col = 0;
		int modifier = 0;
		Map<Integer, ArrayList<Integer>> values = new HashMap<>();
		
		for(int p = 0; p<side_length; p++)
		{
			values.put(p, new ArrayList<>());
		}
		
		while(entries<side_length*side_length)
		{
			for(int i=row; i<(row+square_length); i++)
			{
				for(int j=col; j<(col+square_length); j++)
				{
					entries+=1;
					
					if(values.get(board[i][j]).size()==0)
						values.get(board[i][j]).add(1);
					else
					{
						return false;
					}
				}
			}
			
			for(int m=0; m<side_length; m++)
			{
				values.get(m).clear();
			}
			
			modifier++;
			
			if(modifier%square_length==0)
			{
				col = 0;
				row+=square_length;
			}
			else
			{
				col+=square_length;
			}
		}
	
		return true;
	}
	
	public int[][] returnBoard()
	{
		return board;
	}
	
	public void printBoard()
	{
		for(int i=0; i<side_length; i++)
		{
			for(int j=0; j<side_length; j++)
			{
    			System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
