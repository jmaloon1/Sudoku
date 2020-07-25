
/**
 * This class tests the game
*/
public class Test 
{
	public static void main(String[] args) 
	{
	    
	   int square_length = 3;
	   int side_length = square_length*square_length;
	   int wins = 0;
	   
	   
	   for(int i=0; i<1; i++)
	   {
		   boolean won_game = true;
		   
		   Game g = new Game(square_length);
		   g.gameSetup();
		   g.printBoard();
		   int[][] filled_board = g.returnBoard();

		   //int[][] game_board = {{1,2,3,4,5,6,7,8,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,9}};
		   GameBoard gb = new GameBoard(filled_board, square_length, 'e');
		   int[][] game_board = gb.makeGame();
		   System.out.println("Game Board");
		   gb.printBoard();
		   
		   AI ai = new AI(game_board, square_length);
		   ai.playGame(game_board, false);
		   System.out.println("Game over");
		   ai.printBoard();
		   
		   for(int row=0; row<side_length; row++)
		   {
			   for(int col=0; col<side_length; col++)
			   {
				   if(ai.returnBoard()[row][col]!=filled_board[row][col] && ai.returnBoard()[row][col]!=0) 
					   won_game = false;
			   }
		   }
		   
		   if(won_game)
			   wins++;
	   }
	   
	   System.out.println(wins);
	}
}