This project is a java implementation of a tweaked version of the Solo Red card game. 

Model : manages the game state
View: provides a text based interface to display the game state
Controller: input handling and coordination

How the game works:
1. Players start with a deck and palettes
2. On each players turn, they can play a card from their deck to a palette or the canvas
3. The current game winning rule is determined by the color of the canvas
4. the game continues until legal moves run out
