# Sudoku

<img src="README Images/Sudoku Normal.jpg" align="right"
     alt="Sudoku" width="90" height="120">
     
     
     
This Repository has code in both Java and Python for creating Sudoku puzzles. The Java files just have code for creating the games while the Python File also has a GUI that can be used to play the game. The following examples and descriptions mostly pertain to the Python file.



## Logic Behind Making a Sudoku Puzzle

For making a sudoku puzzle from scratch, there are a few key steps. First is knowing the rules. A sudoku board is a square board that typically has 9 cells in each dimension. This is further broken down into 9 "subsquares" that each consist of a 3x3 square of cells. The rules for playing are as follows:

1. Each row must have one and only one 1-n where n is the dimension (9 usually)
2. Each column must have one and only one 1-n
3. Each subsquare must have one and only one 1-n

So in the case of the following board setup, the pink square must be a 4 since this would satisfy the previous requirements

<p align="center">
  <img width="240" align="center" alt="Pink Square is a 4" src="README Images/Sudoku Intro.jpg">
</p>

Now, the above rules apply to both making and playing a game of sudoku, but there is also one additional rule that only applies to the creation of games: each game must have one and only one solution. So for example, the following game would not be a valid game:

<p align="center">
  <img width="240" align="center" alt="Ambiguous Puzzle" src="README Images/Sudoku Ambig1.jpg">
</p>

This is because both of the following solutions would work:

<p align="center">
     <img width="240" align="center" alt="Solution 1" src="README Images/Sudoku Ambig2.jpg">
     <img width="240" align="center" alt="Solution 2" src="README Images/Sudoku Ambig2b.jpg">
</p>

Since these two solutions follow all the rules for playing the game, but violate the additional rule for no ambiguity, this puzzle is not valid. So part of the code ensures a scenario like this would never happen

## Classes in the Code and what they do

### Game
This class creates a filled out game board of variable size. It works by randomly choosing legal numbers for each square starting at the top left and moving from left to right, top to bottom.

### AI
This class has an AI that can play a game of Sudoku. This class is used when making the partially filled game board to make sure that there is only one solution.

### GameBoard
This class creates a partially filled game board that can be played by a human. It takes a completed game board from Game and also utilizes AI to make sure the game has one and only one solution

### Miscellaneous

The last two cells were not made into a class (they probably should have been though) and are used for the GUI. This GUI uses a package called pygame. The size of the board can be altered (3 would be standard) and the user can play one of 3 game modes.

<p align="center">
  <img width="240" align="center" alt="Ambiguous Puzzle" src="README Images/Sudoku Modes.jpg">
</p>

The game can be played with pressing the buttons on the screen or those on the keyboard. After a game is completed, the user has the option to start a new game:

<p align="center">
  <img width="240" align="center" alt="Ambiguous Puzzle" src="README Images/Sudoku Result.jpg">
</p>

Note that there is a pygame (package used for the GUI) related bug where if you exit the game from the pop up window (by pressing the red 'X'), then if you rerun the cell to play again, you cannot resize the window or the screen turns black. The workaround is to terminate the program in Jupyter notebook (by pressing the square 'interupt the kernel' button for example). Doing this will allow for resizing in subsequent runs.


## Next Steps

* Making the last two cells (the code for the GUI) into a class
* Adding more comments
* Adding functionality to put small numbers in squares as "prospective" numbers
* Increasing the depth of the AI to make it better at solving more complex problems

