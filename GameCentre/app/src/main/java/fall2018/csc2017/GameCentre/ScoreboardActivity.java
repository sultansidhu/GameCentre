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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static fall2018.csc2017.GameCentre.MovementController.username;

public class ScoreboardActivity extends AppCompatActivity
{
    /**
    List of all the users' highscores.
     */
    private StringBuilder scoresList;

    //TextView scoresDisplay;
    TextView slidingScore;
    TextView hasamiScore;
    TextView connect4Score;
    TextView sessionScore;

    String winner;
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
        String winnerUsername = getIntent().getStringExtra("username");//new LoginManager().getPersonLoggedIn();
        System.out.println("THE WINNERS USERNAME IS --------------%%%%%%%%%%%% -------------" + winnerUsername);
        assert winnerUsername != null;
        winner = winnerUsername;
        int[] results = getIntent().getIntArrayExtra("results");
        if (results == null) {
            results = new int[3];
            results[0] = getSlidingScoreLocal(winnerUsername);
            results[1] = getHasamiScoreLocal(winnerUsername);
            results[2] = getConnect4ScoreLocal(winnerUsername);
        }
        System.out.println("Results is: ");
        System.out.println(results);
        System.out.println("THE RESULTS NOW ARE: ----------------------------" + results);
        setContentView(R.layout.scoreboard);
        scoresList = new StringBuilder();
        slidingScore = findViewById(R.id.slidingTilesScoreViewer);
        hasamiScore = findViewById(R.id.hasamiShogiScoreViewer);
        connect4Score = findViewById(R.id.connect4ScoreViewer);
        sessionScore = findViewById(R.id.currentScoreViewer);
        HashMap<String, User> users = fm.readObject();
        assert users != null;

        fm.saveObject(users);
        slidingScore.setText("Sliding Tiles: "+ String.valueOf(results[0]));
        hasamiScore.setText("Hasami Shogi: "+ String.valueOf(results[1]));
        connect4Score.setText("Connect 4: "+ String.valueOf(results[2]));
        try {
            sessionScore.setText("Your Score: " + String.valueOf(results[3]));
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("You came from the StartingActivity screen, so no recent score.");
            sessionScore.setText("Your Score: " + "<N/A>");
        }

        addResetScoresButtonListener();
        addGoToGameListListener();
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
                FileManager fm = new FileManager();
                HashMap <String, User> users = fm.readObject();
                User user = users.get(winner);

                user.resetScoreHashmapForAllGames();
                users.put(winner, user);

                fm.saveObject(users);
                updateResetScores();
                Toast.makeText(GlobalApplication.getAppContext(), "Scores for "+user.getUsername()+" are reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateResetScores(){
        slidingScore.setText("Sliding Tiles: 0");
        hasamiScore.setText("Hasami Shogi: 0");
        connect4Score.setText("Connect 4: 0");
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


    public int getSlidingScoreLocal(String username){
        //TextView slidingScores = findViewById(R.id.slidingTilesScoreViewer);
        Integer maxScore;
        User user = fm.readObject().get(username);
        assert user != null;
        List scoreList = user.getHighestScore(0);
        if (scoreList.size() == 0){
            maxScore = 0;
        } else {
            maxScore = (int) Collections.max(scoreList);
        }
        if (maxScore > 0 && scoreList.size() != 0){
            return maxScore;
        } else {
            return 0;
        }


    }

    public int getHasamiScoreLocal(String username){
        Integer maxScore;
        User user = fm.readObject().get(username);
        assert user != null;
        List scoreList = user.getHighestScore(1);
        if (scoreList.size() == 0) {
            maxScore = 0;
        } else {
            maxScore = (int) Collections.max(scoreList);
        }
        if (maxScore > 0 && scoreList.size() != 0){
            return maxScore;
        } else {
            return 0;
        }

    }

    public int getConnect4ScoreLocal(String username){
        Integer maxScore;
        User user = fm.readObject().get(username);
        assert user != null;
        List scoreList = user.getHighestScore(2);
        if (scoreList.size() == 0){
            maxScore = 0;
        } else {
            maxScore = (int) Collections.max(scoreList);
        }
        if (maxScore > 0 && scoreList.size() != 0){
            return maxScore;
        } else {
            return 0;
        }

    }


    public int[] updateUserHighScore(String username, int gameIndex)
    {
        int[] result = new int[4];
        int newScore = -1;
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        //setScores(username); // todo: instead of calling this make three calls

        newScore = scoreFactory.getScore(gameIndex).calculateUserScore(user);
        user.addSessionScore(newScore, gameIndex);

        assert newScore >= 0;

        fm.saveObject(users);

        System.out.println("THE ARRAY OF THE SESSION SCORES IS DISPLAYED HERE: ");
        user.printAllSessionScores();

        result[0] = getSlidingScoreLocal(username);
        result[1] = getHasamiScoreLocal(username);
        result[2] = getConnect4ScoreLocal(username);
        result[3] = newScore;
        return result;
        }


}
