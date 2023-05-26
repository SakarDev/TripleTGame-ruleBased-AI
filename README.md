# Triple T Game (rule-based Artificial intelligence)

This is a 2-player game built in Java that simulates a competitive gameplay similar to Tic Tac Toe. It allows for both player vs player and player vs computer modes. The player can select the size of the game board, from 3x3 to 9x9.

**Note: This project is a Java console-based game which is my first university assignment in (2020) and is meant for learning purposes only. The main focus is to provide hands-on experience in Java programming and to become familiar with basic programming concepts like loops, conditionals, arrays, methods, and user input.**

## Step-by-Step Functionality:
- Select a Mode: The game begins by allowing the user to choose whether to play against another player or against the computer.
- Player Details and Board Size: Next, the game collects the names of the players and the desired size of the game board. If the names are left blank, they default to "Player 1" and "Player 2". The size of the game board can be chosen within a range of 3x3 to 9x9.
- Dice Roll to Determine Turn: A dice roll is conducted to determine which player will go first. If the dice roll results in a tie, the process repeats until there's a clear winner.
- Gameplay: The gameplay begins, with players taking turns to enter a number corresponding to a position on the game board where they wish to place their marker ("X" for player 1, "O" for player 2). The game ensures that positions once taken cannot be chosen again.
In case of player vs. computer mode, the computer uses a simple AI that prioritizes blocking the player's potential scoring points and, if possible, taking the highest scoring points for itself.
- Game End: The game ends when there are no positions left to choose. At this point, the game determines the winner based on points scored.
- Scoring: Players can score points by aligning their markers in a row, column, or diagonal direction. The player with the highest score wins.


This game provides an enjoyable way to learn and practice Java programming. It helps understand the practical application of concepts such as loops, arrays, and methods, and the handling of complex logic. It can serve as a stepping stone for students to create more complex and interactive games in the future.

# How points are calculated?

- Row points: If Player 1 has placed his symbols on three consecutive positions in the same row (and these positions do not include the ending points of a row), then he earns a point.
- Column points: If Player 1 has placed his symbols on three consecutive positions in the same column, then he earns a point.
- Right cross points: If Player 1 has placed his symbols on three consecutive positions along a diagonal from top left to bottom right (and these positions do not include the starting points of a diagonal in this direction), then he earns a point.
- Left cross points: If Player 1 has placed his symbols on three consecutive positions along a diagonal from bottom left to top right, then he earns a point.

## Game Example (6x6 board) (Beginning + Point Calculation):
<ImageGroup>
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/1.png" />
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/2.png" />
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/3.png" />
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/4.png" />
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/5.png" />
<img alt="Input 0" src="https://raw.githubusercontent.com/SakarDev/TripleTGame-ruleBased-AI/master/6.png" />
</ImageGroup>
