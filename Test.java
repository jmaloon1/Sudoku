


public class Test 
{
	
	public static void main(String[] args) 
	{
	    
	   int square_length = 3;
	   int side_length = square_length*square_length;
	   Game g = new Game(square_length);
	   g.game_setup();
	   g.current_board(side_length);
	}


}
