package fall2018.csc2017.GameCentre;
/*
=========================================================================
File Name: ScoreboardActivity.java
Purpose: This Activity controls the Scoreboard screen, which
shows the highest scores among all users
Date: October 31, 2018
Group #: 0506
==========================================================================
*/

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

//import static fall2018.csc2017.GameCentre.MovementController.username;

public class ScoreboardActivity extends AppCompatActivity
{
    /**
    List of all the users' highscores.
     */
    private StringBuilder scoresList;

    TextView scoresDisplay;
    /**
    Called when the scoreboard button is pressed, or when the user completes a game.
    Displays high scores in a list format.
     */

    FileManager fm = new FileManager();

    /**
     * The scoreFactory object used to instantiate different ScoreCalc objects
     */
    private ScoreFactory scoreFactory = new ScoreFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        scoresDisplay = findViewById(R.id.scoresDisplay);
        scoresDisplay.setMovementMethod(new ScrollingMovementMethod());
        scoresList = new StringBuilder();
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        String username;

        for (HashMap.Entry<String, User> entry : users.entrySet()) {
            username = entry.getKey();
            generateScoreRow(username);

        }
        scoresDisplay.setText(scoresList);
        fm.saveObject(users);

        addResetScoresButtonListener();
        addGoToGameListListener();
    }
    public void generateScoreRow(String username){
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        System.out.println("THE ARRAY OF THE SESSION SCORES IS DISPLAYED HERE: ");
        user.printAllSessionScores();
//        scoresList.append(username);
//        for (int i = 0; i <= 2; i++) {
//            System.out.println(user.getHighestScore(i));
//            scoresList.append(": ").append(user.getHighestScore(i)).append(" || ");
//            System.out.println(scoresList);
//
//        }
//        scoresList.append("\n");
    }
    /*
    Adds a reset scores button listener which calls a method to set all user high scores to 0.
    @return null
     */

    private void addResetScoresButtonListener() {
        Button resetScores = findViewById(R.id.resetScores);
        resetScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager lm = new LoginManager();
                String username = lm.getPersonLoggedIn();
                FileManager fm = new FileManager();
                User user = fm.readObject().get(username);
                user.resetScoreHashmapForAllGames();
                //resetAllScores();
                Toast.makeText(GlobalApplication.getAppContext(), "Scores for "+user.getUsername()+" are reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addGoToGameListListener(){
        Button gameListButton = findViewById(R.id.goToGameList);
        gameListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToGameList();
            }
        });

    }

    public void goToGameList(){
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }


    public void updateUserHighScore(String username, int gameIndex)
    {
        int newScore = -1;
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        System.out.println("USERNAME IS "+username);

        newScore = scoreFactory.getScore(gameIndex).calculateUserScore(user);

            assert newScore >= 0;

//            if (newScore > user.getHighestScore(gameIndex)) {
//                System.out.println("new score is "+newScore + " AND THE HIGHEST SCORE IS "+user.getHighestScore(1));
//                user.setHighestScore(gameIndex, newScore);
//                Context context = GlobalApplication.getAppContext();
//                Toast.makeText(context, user.getUsername() + " won and got a new high score: "+user.getHighestScore(gameIndex) +"!", Toast.LENGTH_SHORT).show();
//                //System.out.println(newScore);//Works perfectly up to here!
//            }
//            else
//                {
//                    System.out.println("new score is "+newScore + " AND THE HIGHEST SCORE IS "+user.getHighestScore(1));
//                    Context context = GlobalApplication.getAppContext();
//                    Toast.makeText(context, username+" won with a score of " + newScore, Toast.LENGTH_SHORT).show();
//                }
            fm.saveObject(users);
        System.out.println("Session score: "+newScore);
        HashMap<String, User> users2 = fm.readObject();
        assert users != null;
        User user2 = users2.get(username);
        System.out.println("Checking high score got saved to file....");
        System.out.println(user2.getHighestScore(gameIndex));
        }

        // TODO: DISPLAY STUFF ON THE SCOREBOARD CUZ OTHERWISE WE FCKED UP BOY

}
