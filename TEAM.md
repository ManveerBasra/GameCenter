# GameCentre

## Contributors

+ Manveer Basra
+ Kirby Chin
+ Salman Mohamad
+ Ryan Denniston
+ Maxwell Garrett

## Communication Tools

Facebook Messenger

## Team Contract

+ I will get my allotted work done on time.
+ I will attend every team meeting and actively participate.
+ Should an emergency arise that prevents me from attending a team meeting, I will notify my team immediately.
+ The work will be divided roughly equally among all team members.
+ I will help my team to understand every concept in the project.
+ If I do not understand a concept or code, I will immediately ask my team for help.

## Task Division (what each person ended up working on )

+ Max 
  + Created TicTacToe game and all States, Activities and tests associated with it
  + Created superclass for StateManager and State
  + Created superclass for GameActivityController
  + Implemented GameActivityController into TicTacToeGameActivity
+ Ryan
  + Migrated Score class into User class
  + Created BoardFactory for SlidingTiles
  + Switched saving from ArrayList to HashMap
  + Created Pipes game and all States, Activities and most tests associated with it
  + Implemented GameActivityController into PipeTileGameActivity
+ Salman 
  + Completed a moderate portion of tests for Phase 1 code
  + Refactor Sign In/Sign Up activity to make it testable
  + Created Mock GameCentre to make some testing possible
  + Created some tests for SlidingTiles and TicTacToe StateManagers
+ Kirby
  + Handled score saving in TicTacToe Activities
  + Refactored to make SlidingTilesGame always solvable in SlidingTilesBoardFactory and created
  respective unit-tests
  + Refactored code to get rid of switch statement code smell in UserFieldValidator, 
  SlidingTilesSettings
  + Refactored variable declarations to make use of more abstract classes (ArrayList -> List)
  + Cleaned up storage of Scores into one single Score class
  + Removed bloater code-smell in numerous classes
+ Manveer
  + Created part of the test for Phase 1 code
  + Created and implemented Scoreboard and ScoreboardManager classes and respective tests
  + Created and implemented PreferenceHandler and respective tests
  + Created and implemented SaveManager and respective tests
  + Created and implemented SlidingTilesGameActivityController and respective tests

## Team Meetings

### Meeting 1

We made a list of things to do and created a preliminary task devision

#### TODO

+ TicTacToe Game
+ Pipe Game
+ Make Sliding Tiles Solvable
+ Refactor Code
  + User Class
+ Unit Tests

#### Task Division

+ Max, Ryan - 2 New Games and unit tests for their games
+ Manveer, Salman - Unit-Testing Phase 1 code, and refactoring/implementing design patterns
+ Kirby - Refactor code/implementing design patterns, make Sliding Tiles solvable

### Meeting 2

We created a list of things todo/design requirements to improve upon.

#### Higher-Level Design Improvements

Scoreboard.convertScoresToMap is only accessed in ScoreboardManager and thus can be moved to ScoreboardManager. (Make sure to update tests too)

**DONE** Use a spinner to select Board complexity in SlidingTilesStartActivity.

Implement Single-Responsibility Principle for SlidingTilesBoardFactory
SlidingTilesBoardFactory shouldn’t have to check image type from preferences, instead SlidingTilesStartActivity could read preferences and call the respective method (makeDrawableBoard, makeImageBoard) with the respective parameters.
Thus SlidingTilesBoardFactory wouldn’t need to keep a reference to PreferenceHandler and would clean up our UML map

**DONE** Rename Board to SlidingTilesState

GameActivities have a lot of interactions with Board/State, however should be interacting with only StateManager to ensure proper abstraction.
(Suggestion: Don’t know if it’s possible/efficient) Could move undoLimit and undosPossible into StateManager
Remove GameActivity’s calls to StateManager.getState() and instead only call methods of StateManager.

GameCenterActivity’s playButtonOnClick() method can make use of a HashMap of <String gameName, Activity GameActivity> to reduce if/else statements.

Implement the observer pattern into Controllers for most activities.

##### Suggestions

(Suggestion: Up for discussion) Store user as a static member in PreferenceHandler, to avoid having to pass user in across activity intents.

(Suggestion: Up for discussion) Use resources instead of static final members to avoid “over-using” the “static” keyword.

(Suggestion: Up for discussion) Clicking certain buttons in SlidingTilesSettingsActivitiy updates the UI so other buttons are rendered “unclickable”, but this takes up a lot of code. We could consider removing this “pretty” feature to reduce the amount of code in SettingsActivity.

(Suggestion: Don’t know if it’s possible/efficient) Using a SettingsHandler to take some functions away from the Activity, such as saveImageTypeToSavedPreferences, so the Activity has the Single-Responsibility of updating/handling the view.

#### Code Smells

+ Long method
  + **DONE** SlidingTilesGameActivity, TicTacToeGameActivity - onCreate(), update() 
  + **DONE** ByGameFragment, ByUserFragment - onCreateView() 
  + SlidingTilesBoardFactory - makeImageBoard()
  + SlidingTilesSettingsActivity - saveImageTypeToSavedPreferences()
  + SlidingTilesStartActivity, TicTacToeStartActivity - onCreate()
  
### Meeting 3

We decided what was left to finish, and discussed the fixing of remaining minor bugs. We created
a WALKTHROUGH.md and decided the general structure of our presentation.

We also planned out a date and time to practice our presentation.
  
