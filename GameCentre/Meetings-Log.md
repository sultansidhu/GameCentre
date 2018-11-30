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

==================================================================================================================
Meetings Log - Group 0647 - CSC207 - Phase 2 

Meeting #1 
* Briefly got together to discuss the feedback given to us via the Feedback.md file 
* Two members agreed that refactoring was largely ignored in Phase 1, and thus we must refactor more for Phase 2
* One member suggested the idea of using a factory design pattern for one part of the code, to refactor the code significantly 
* The other members briefly agreed that design patterns must be implemented in order for more efficient code 
* All members agreed a time and place for the next meeting 

Meeting #2 
* Met in Bahen room 2200 around 11am 
* All members started discussing ideas about potential refactoring efforts 
* Two members bounced ideas off of each other about potential implementation of the Strategy pattern 
* One member proposed the use of inheritance, claiming that "several new board managers" would have to be created for the new games
* All members agreed on the two new games to be implemented: Hasami Shogi and Connect 4  
* All members agreed a time and place for the next meeting 

Meeting #3 
* Met in Bahen room 2200 around 11am 
* Each member came with a UML diagram representing their design for implementation 
* After much debate, all members unanimously voted in favor of a particular design. Thus we decided to implement this design. 
* One member suggested that inheritance can actually be used for various other "families of classes" to reduce duplicate code 
* Divided ourselves into 2 subteams: one subteam focused on discussing design patterns, the other team started implementing by writing the classes 
* All members agreed a time and place for the next meeting 

Meeting #4  
* Met in Bahen room 2200 around 12pm  
* The team divided into 2 subteams: one subteam focused on developing algorithms for the solvability of boards, and the other team continued implementation of the other classes, including 2 new BoardManagers 
* Started implementation of different GestureDetectGridView classes, for each game 
* All members agreed a time and place for the next meeting 

Meeting #5  
* Met in Bahen room 2200 around 12pm  
* Finished all Gesture classes and BoardManagers 
* One subteam started work on the different Scoring classes 
* The other subteam started working on the implementation of various other classes 
* One member implemented two interfaces: one for Scoring classes and one for BoardManagers 
* Two members collaborated to implement a Factory design pattern 
* One member worked on the Graphical User Interface design and XML files 
* All members agreed a time and place for the next meeting 

Meeting #6 
* Met in Bahen room 2200 around 12pm  
* Finished implementation of various classes 
* Three members continued removing logic from Activity, so as to satisfy the MVC design pattern 
* Two members worked on Scoreboard, and used various debugging techniques to solve problems 
* One member started implementation of the HashMaps data structures 
* One member worked on the Graphical User Interface design and XML files 
* All members agreed a time and place for the next meeting

Meeting #7 
* Met in Bahen room 2200 around 12pm  
* Two members implemented Strategy design pattern for Score classes 
* Three members worked on implementing the factory design pattern for Score classes and for BoadManagers 
* All members started thinking about cases to test for Unittesting 
* One member worked on the Graphical User Interface design and XML files 
* All members agreed a time and place for the next meeting

Meeting #8 
* Met in Bahen room 2200 around 12pm  
* Subteam 1 finished implementation of all the features 
* Subteam 2 continued writing Unittests 
* Subteam 1 soon joined Subteam 2 in writing Unittests 
* Improved code coverage significantly: 
    * One class has 100% code coverage (ShogiBoardManager)
    * Several other classes have near-100% code coverage (ranging from 80%-93%)
* All members agreed a time and place for the next meeting

Meeting #9 
* Met in Bahen room 2200 around 12pm  
* All members made sure all the Unittests are passing 
* Various members worked on testing: all reported bugs were fixed 
* Members worked on finalizing touches, fixing whitespace issues 
* Members worked on finalizing the code
* One member started preparing the presentation powerpoint 
* All members started practicing for presentation 


