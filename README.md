# Sudoku

<img src="README Images/Sudoku Normal.jpg" align="right"
     alt="Sudoku" width="90" height="120">
     
     
     
This Repository has code in both Java and Python for creating Soduko puzzles. The Java files just have code for creating the games while the Python File also has a GUI that can be used to play the game.



### Logic Behing Making a Sudoku Puzzle

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

## Classes in the Coe and what they do

### Sports Attendance
The file Hackathon.ipynb (same one where data was collected) is where the statistical tests were done for sports team performance. These statistical tests checked whether teams were fairing worse at their home venues during COVID in part due to the lack of fans. The data was saved in our repo to a file called csvData.csv.

### COVID Vaccination Predictions
A python script called vaccination_prediction.ipynb used ARIMA to generate predictions on COVID vaccinations for several states. We focused on states with NBA teams as the NBA was the focus of our project. An interactive dashboard built in Google Data Studio can be found here: https://datastudio.google.com/s/rWABKN05Wng

## Reports
A Tableau file called Hackathon_Viz.twb contains several visualizations for different sports and their performances pre and during COVID. This program uses sports_data.csv as its data source.

### Usage
To get the most out of this repo, there are two routes: Follow the path we did in collecting the data and evaluating it or using the complete dataset (sports_data.csv) to evaluate to look for new interesting findings.

1. Following the path we took - The path we took started with evaluating a problem stemming COVID-19. We decided that seeing how sports teams have fared at their home venues during COVID would be a really interesting problem as it is a potentially once-in-a-lifetime natural experiment. Our next task was to decide what exact problems we hoped to answer with data analytics. We settled on:

* How has the lack of attendance brought on by COVID affected home team performance?
* When is the right time for teams to fully reopen their venues?

Focusing on these two problems ties in our attempt as a country to get back to normal but not to do it in a unnecessarily risky fashion. We then looked for places to get data. For the vaccination data, there are plenty of sources and we used a Github Repo (linked above) that pulled data from the CDC. The sports data was a little trickier to get but using a combination of API's (in Hackathon.ipynb) and data directly from sports-referene.com, we obtained a usable dataset. With this dataset for sports, the next step was to use Tableau (Hackathon_Viz.twb) to learn how COVID has affected sports team performance. We saw that baseball was the only sport where teams actually did better at home with limited fans. Soccer had the biggest drop-off in performance and the other sports we analyzed (baseball, basketball, hockey, american football) all also saw drop-offs as was to be expected. We then settled on basketball as the NBA season is winding down and teams are trying to reopen their venues. To validate our findings with statistical analysis, we used the same Python script (Hackathon.ipynb) to find the statistical difference in sports league performance before and during COVID. 
For finding when it would be safe for teams to reopen their stadiums, we used the NBA again as our focus and used vaccination data (csvData.csv) to predict when different states would reach herd immunity. We settled on a 60% vaccination rate as along with people being naturally immune, it would fall in line CDC estimates for herd immunity. Using the ARIMA technique in the Python script (vaccination_prediction.ipynb), we made predictions for the states with NBA teams. We then used Google Data Studio to make some visualizations with these findings.

2. Look for new and interesting findings - For those that want to do some exploration on this topic, the dataset we collected (sports_data.csv) is fairly comprehensive with years of data from major sports. Using visualization tools like Tableau is a great way to make new findings.
