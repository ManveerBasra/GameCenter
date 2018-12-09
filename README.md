# GameCentre

A Game Centre where a user can sign in to save and play a collection of mini games.

## Demo Images

Welcome Screen                        |  Game Start Center                  | Sliding Tiles Game
:------------------------------------:|:-----------------------------------:|:-----------------------------------:
![](/demo_images/welcome_screen.jpg)  |  ![](/demo_images/start_centre.jpg) | ![](/demo_images/sliding_tiles.jpg)

## Setup Instructions

1. In the command line, clone the repository using `git clone https://github.com/ManveerBasra/GameCenter.git`
2. Open Android Studio
3. Select "Open an existing Android Studio project"
4. Navigate to the folder where the repository was cloned and inside that folder select Phase1\Gamecentre
    - When the correct folder has been selected the path should be: location_where_project_was_cloned\group_0571\Phase1\GameCentre
5. Click OK and wait until Gradle sync is complete

## Usage Instructions

1. Create an account and sign in.
2. Choose a game to play (We will use Sliding Tiles as an example)
3. You are now at Sliding Tiles Centre and you have a couple options
   + Settings are at the top right of the activity
       + Default undo limit is 3, you can set it to values between 0 - 999, or set it to unlimited
       + Default Tile Background is numbers, you can pick from the set of preset images, or load one of 
       your own
         + From Gallery
            + Open a browser (Chrome) on the phone
            + Google Image Search the image you want
            + Click on the image you want
            + Long-Click on the image, and select 'Download Image'
            + Return to Settings and select 'From Gallery', the 'Select a Photo' dialog will open
            + Open the 'Download' folder and select your image
         + URL
            + Open a browser (Chrome) on the phone
            + Google Image Search the image you want
            + Click on the image you want
            + Long-Click on the image, and select 'Open Image in New Tab'
            + Copy the URL in the browser search bar
            + Return to Settings and select 'URL', paste the copied URL in the 'Enter Image URL' textbox
            + Click 'GET IMAGE' and if the URL worked, you will get a toast saying 'Image Loaded'
       + Make sure to click Save when you're done changing settings, otherwise it won't save.
   + Create a new 3x3, 4x4, or 5x5 game with the Tile Background chosen in settings
   + Load Saved Game
       + Loads the newest unfinished game the user has played, or latest autosave
   + Save Game
       + Save the current game you've been playing
4. Once you start a game you can play the game according to the game instructions below. You can also 
hit the button at the top right to undo.
   + You can undo as many times as the undo limit you've set
   + If you return to the game, you can undo from that state assuming you haven't already reached the
   undo limit in that state
   + You can exit the game, change the undo limit, and the game will update to accommodate this change
5. Once you win the game, you will automatically be taken to the Scoreboard screen, where you can view scores by:
   + By User
       + Score is displayed by its user and sorted highest to lowest
   + By Game
       + Score is displayed by game and user and sorted highest to lowest
   
## Game Instructions

### Sliding Tiles

+ When you start the game, you will see a screen of tiles and one blank tile
+ You can tap the tiles surrounding the blank tile to swap the blank tile and the tile
you selected
+ You will win by having the tiles in proper order:
  + Increasing order for numbered tiles
  + Matched to the original image for tiles with background images
 
### Tic Tac Toe

This game works as a usual Tic Tac Toe game, you will need 2 people to properly play the game.

+ When you start the game, you will see a screen of empty tiles
+ You can tap any tile and it will update with a user's "symbol", turns are switched after every move.
  + Player 1 has symbol "X"
  + Player 2 has symbol "O"
+ A player wins when they have a full line of their symbols, so
  + A vertical line of their symbols
  + A horizontal line of their symbols
  + A diagonal line of their symbols

### Pipes

+ When you start the game, you will see a bunch of tiles, the top and botton row has red, and the middle has numerous moveable tiles
+ You can tap on any green tile to rotate it
+ The objective of the game is to connect some red pipe at the top with a red pipe at the bottom using the green pipes
