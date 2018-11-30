Meetings Log - Group 0506 - CSC207 - Phase 1


Meeting #1 October 23 
* Met in Bahen Room 2270 at around 12pm 
* Brainstormed ideas to implement the Login/Signup feature 
* 3 people were strongly in favor of using serialized file that stores a HashMap of login information, 2 people were in favor of using databases 
* After a long discussion, we agreed that we shall use serialized files for now (and perhaps databases for phase 2) 




Meeting #2 October 25 
* Met in Bahen Room 2270 at around 12pm 
* Discussed the different features we wish to implement, and what each one would entail 
* Drew UML diagrams for 3 major classes: User, GameState, and MainActivity 
* Discussed possible bonus features (Statistics page, and ability to add background music) 


Meeting #3 October 26         
* Met in Bahen Room 2270 around 12pm 
* Continued discussion on the idea of having a Statistics page 
* Discussed features offered by other games 
* Drew UML diagrams for the remaining classes 




Meeting #4 October 28 
* Met in Bahen Room 2270 around 12pm 
* Divided ourselves into two subteams 
   * Team 1 worked on creating the serialized file read() and write() functions 
   * Team 2 worked on designing the GUI for Login/Signup Screen 




Meeting #5 October 29 
* Met in Bahen Room 2270 around 12pm 
* Agreed on a design choice: 3 activity classes will have access to the serialized file 
* Team 1 finished and pushed the Signup and Login functionality 
* Team 2 started working on the GameListActivity class 




Meeting #6 October 31 
* Met in our lab room in Bahen at around 12pm 
* Team 1 members discussed a design choice with GameList, and then agreed that we should use a list of games (more games to be implemented in Phase 2) 
* Team 2 started modifying the GUI Design to include the ability to modify the size/complexity of the board 






Meeting #7 November 2 
* Met in our Bahen lab room at around 12pm 
* Team 2 finished the complexity/size of the board feature (and pushed it) 
* Team 1 started the undo stack functionality 
   * Used a stack of Boards 
* After a long discussion involving almost all team members, members agreed that the size/complexity and undo options should be on the StartingActivity screen (in order to avoid duplicate code and other code smells) 




Meeting #8 November 3 
* Met in our Bahen lab room at around 12pm 
* Team 1 finished the undo functionality. Team 2 tested this functionality and reported 2 bugs: undoing more times than the number of moves that have been made crashes the app, and clicking undo has no limit. 
* Team 1 quickly fixed these bugs, and pushed all changes 
* Team 2 started implementing Scoreboard 




Meeting #9 November 5 
* Met in our Bahen Lab room at around 12pm 
* After discussions, Team 2 made a few modifications in the UML diagrams for some Scoreboard functionality 
* Team 2 finished Scoreboard and pushed all changes. Team 1 tested the changes and reported no bugs 
* Each individual member made various JavaDocs for all the classes (and pushed all changes)