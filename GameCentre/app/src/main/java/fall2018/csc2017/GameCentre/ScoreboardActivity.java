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
    /*
    List of all the users' highscores.
     */
    private StringBuilder scoresList;

    TextView scoresDisplay;
    /*
    Called when the scoreboard button is pressed, or when the user completes a game.
    Displays high scores in a list format.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        scoresDisplay = findViewById(R.id.scoresDisplay);
        scoresDisplay.setMovementMethod(new ScrollingMovementMethod());
        scoresList = new StringBuilder();
        HashMap<String, User> users = GameActivity.readObject();
        assert users != null;
        String username; //created 13:35 friday nov 16

        for (HashMap.Entry<String, User> entry : users.entrySet()) {
            username = entry.getKey();
            users = GameActivity.readObject();
            assert users != null;
            User user = users.get(username);
            scoresList.append(username).append(": ").append(user.getHighestScore()).append("\n");

        }
        scoresDisplay.setText(scoresList);

        addResetScoresButtonListener();
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
                resetAllScores();
                Toast.makeText(GlobalApplication.getAppContext(), "Scores are reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*
    Resets all the users' high scores.
     */
    public void resetAllScores() {
        scoresList = new StringBuilder();
        HashMap<String, User> users = GameActivity.readObject();
        assert users != null;
        for (HashMap.Entry<String, User> entry : users.entrySet()) {
            String username = entry.getKey();

            users.get(username).setHighestScore(0);
            scoresList.append(username).append(": 0").append("\n");
        }
        GameActivity.saveObject(users);
        scoresDisplay.setText(scoresList);
    }
    /*
    Calculates the user's high score.
    @return int
     */

    public static int calculateUserScore(String username)
    {
        HashMap<String, User> users = GameActivity.readObject();
        assert users != null;
            User user = users.get(username);

            double time = user.getTotalTime();
            int numMoves = user.getNumMoves();
            if (time == 0) {
                return 0;
            } else {
                return (int) Math.round(100 * ((1 / numMoves) + 100000 / (time)));
            }
    }
    /*
    Updates user high score if the user beats their previous highest score in the game.
    @return null
     */

    public static void updateUserHighScore(String username)
    {
            int newScore = calculateUserScore(username);
            HashMap<String, User> users = GameActivity.readObject();
            assert users != null;
            if (newScore > users.get(username).getHighestScore()) {
                users.get(username).setHighestScore(newScore);
                Context context = GlobalApplication.getAppContext();
                Toast.makeText(context, "New High score!", Toast.LENGTH_SHORT).show();
            }
            GameActivity.saveObject(users);
        }
}
