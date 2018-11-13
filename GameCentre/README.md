# README: GameCenter

Cloning the URL: Use the following link to clone the repository: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0506.

##### Setting up the repository
Follow these steps to set up the repository:
* Open Android Studio. Close any open projects by clicking on File -> Close Project, if applicable.
* On the welcome page of Android Studio, click "Checkout from Version Control."
* Select "Git" then enter in the URL found above. Click "clone."
* Select "Yes" when prompted to create an Android Studio project.
* On the following screen, select "Create Project From Existing Sources." Click "Next".
* Add \Phase1\slidingtiles to the project directory.
* Continue to click "Next" then click "Finish".
* The project should now clone.
* Should you encounter a dialog which alludes to a VCS root not being set, expand the dialog and click "Add Root."
* Note, the local.properties sdk and build.gradle (applicationId "com.example.nizar17.group0506.phase1.slidingtiles") should be modified to local paths.
* Should you encounter enourmous blocks of red text, go to File -> Invalidate Caches and Restart -> Invalidate Caches and restart.
* You will now be able to run the app by clicking the "run" button (green arrow) near the top right corner of your screen.
* Should you encounter issues when running the application, specifically when setting up configurations, close the project (File -> Close Project) and open the subdirectory labelled "SlidingTiles" by clicking on it. The app will now run.

#### Using the Application

* When the application is started, a login/sign-up screen will appear. Use the bottom three fields to specify a new Username and Password. 
* From the dropdown menu below, select a security question and provide the answer in the text field below. This will help you to recover your password should you ever forget it.
* Click the sign-up button below. You will be taken to the game list where you can select a game to play.
##### Sliding Tiles
Sliding tiles is a game where one tries to arrange numbered tiles in order in the east amount of time. Players click tiles to shuffle them around in order to produce the correct order.
There are several features on the Sliding tiles start screen.
* The dropdown menu on the left will allow you to select the number of times that you can "undo" a move. Your score will be adjusted accordingly. The defualt number of allowed undos per game is 3.
* The dropdown menu on the right will allow you to select a board size. The default size is 3x3.
* The Scoreboard button will allow you to see the global high scores of several users.
* The Load Game button will allow you to load the previous in-progress game, if applicable.
* The New Game button will allow you to begin a new game with your chosen board size and undo limit.

Happy Playing!

