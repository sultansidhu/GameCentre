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
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

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

    ScoreboardController scon = new ScoreboardController();

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
        if(winnerUsername == null){//If there is no winner since we came from starting activity
            winnerUsername = new LoginManager().getPersonLoggedIn();
        }
        winner = winnerUsername;
        User user = fm.getUser(winnerUsername);
        int newScore = getIntent().getIntExtra("result", -1);
        assert newScore >= 0;
        int[] results = new int[4];
        for (int i = 0; i < 3; i++) {
            results[i] = user.getMaxScore(i);
        }
        results[3] = newScore;
        System.out.println("Results is: ");
        System.out.println(results);
        //String currentUsername = getIntent().getStringExtra("username");
        //System.out.println("USERNAME NOW IS : " + currentUsername);
        System.out.println("THE RESULTS NOW ARE: ----------------------------" + results);
        setContentView(R.layout.scoreboard);
        scoresList = new StringBuilder();
        slidingScore = findViewById(R.id.slidingTilesScoreViewer);
        hasamiScore = findViewById(R.id.hasamiShogiScoreViewer);
        connect4Score = findViewById(R.id.connect4ScoreViewer);
        sessionScore = findViewById(R.id.currentScoreViewer);
        HashMap<String, User> users = fm.readObject();
        assert users != null;
//        String username;
//
//        for (HashMap.Entry<String, User> entry : users.entrySet()) {
//            username = entry.getKey();
//            //generateScoreRow(username);
//
//        }
        //scoresDisplay.setText(scoresList);
        fm.saveObject(users);
        slidingScore.setText("Sliding Tiles: "+ String.valueOf(results[0]));
        hasamiScore.setText("Hasami Shogi: "+ String.valueOf(results[1]));
        connect4Score.setText("Connect 4: "+ String.valueOf(results[2]));
        try {
            if (results[3] > -1) {
                sessionScore.setText("Your Score: " + String.valueOf(results[3]));
            }
            else{
                sessionScore.setText("Your Score: <N/A>");
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("You came from the StartingActivity screen, so no recent score.");
            sessionScore.setText("Your Score: " + "<N/A>");
        }

        addResetScoresButtonListener();
        addGoToGameListListener();
        addGoToGlobalScoresListener();
    }
//    public void generateScoreRow(String username){
//        HashMap<String, User> users = fm.readObject();
//        assert users != null;
//        User user = users.get(username);
//        System.out.println("THE ARRAY OF THE SESSION SCORES IS DISPLAYED HERE: ");
//        user.printAllSessionScores();
//        scoresList.append(username);
//        for (int i = 0; i <= 2; i++) {
//            System.out.println(user.getHighestScoresList(i));
//            scoresList.append(": ").append(user.getHighestScoresList(i)).append(" || ");
//            System.out.println(scoresList);
//
//        }
//        scoresList.append("\n");
//    }

private void addGoToGlobalScoresListener(){
        Button globalScores = findViewById(R.id.goToGlobalScores);
        globalScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGlobalScores();
            }
        });
}

    private void goToGlobalScores() {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }

//    public void appendHighScore(int gameIndex){}

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
                User user = fm.getUser(winner);
                user.resetScoreHashmapForAllGames();
                fm.saveUser(user, winner);
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
        //gameListButton.setOnClickListener();
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
    //TODO: move this out
//    public int getSlidingScoreLocal(String username){
//        //TextView slidingScores = findViewById(R.id.slidingTilesScoreViewer);
//        Integer maxScore;
//        User user = fm.readObject().get(username);
//        assert user != null;
//        List scoreList = user.getHighestScoresList(0);
//        if (scoreList.size() == 0){
//            maxScore = 0;
//        } else {
//            maxScore = (int) Collections.max(scoreList);
//        }
//        if (maxScore > 0 && scoreList.size() != 0){
//            return maxScore;
//        } else {
//            return 0;
//        }
//
//
//    }
//    //TODO: Move this out
//    public int getHasamiScoreLocal(String username){
//        //TextView hasamiScore = findViewById(R.id.hasamiShogiScoreViewer);
//        Integer maxScore;
//        User user = fm.readObject().get(username);
//        assert user != null;
//        List scoreList = user.getHighestScoresList(1);
//        if (scoreList.size() == 0) {
//            maxScore = 0;
//        } else {
//            maxScore = (int) Collections.max(scoreList);
//        }
//        if (maxScore > 0 && scoreList.size() != 0){
//            return maxScore;
//        } else {
//            return 0;
//        }
//
//    }
//    //TODO: Move this out
//    public int getConnect4ScoreLocal(String username){
//        //TextView connect4Score = findViewById(R.id.connect4ScoreViewer);
//        Integer maxScore;
//        User user = fm.readObject().get(username);
//        assert user != null;
//        List scoreList = user.getHighestScoresList(2);
//        if (scoreList.size() == 0){
//            maxScore = 0;
//        } else {
//            maxScore = (int) Collections.max(scoreList);
//        }
//        if (maxScore > 0 && scoreList.size() != 0){
//            return maxScore;
//        } else {
//            return 0;
//        }
//
//    }

    //TODO: Move this out
//    public int[] updateUserHighScore(String username, int gameIndex)
//    {
//        int[] result = new int[4];
//        int newScore = -1;
//        HashMap<String, User> users = fm.readObject();
//        assert users != null;
//        User user = users.get(username);
//        //setScores(username); // todo: instead of calling this make three calls
//
////        newScore = scoreFactory.getScore(gameIndex).calculateUserScore(user);
//        Score s1 = scoreFactory.getScore(gameIndex);
//        Board userBoard = user.getGameStack(gameIndex).peek();
//        s1.setBoard(userBoard);
//        newScore = s1.calculateUserScore(user.getNumMoves(gameIndex), userBoard.NUM_COLS);
//        user.addSessionScore(newScore, gameIndex);
//
//        assert newScore >= 0;
//
////            if (newScore > user.getHighestScoresList(gameIndex)) {
////                System.out.println("new score is "+newScore + " AND THE HIGHEST SCORE IS "+user.getHighestScoresList(1));
////                user.setHighestScore(gameIndex, newScore);
////                Context context = GlobalApplication.getAppContext();
////                Toast.makeText(context, user.getUsername() + " won and got a new high score: "+user.getHighestScoresList(gameIndex) +"!", Toast.LENGTH_SHORT).show();
////                //System.out.println(newScore);//Works perfectly up to here!
////            }
////            else
////                {
////                    System.out.println("new score is "+newScore + " AND THE HIGHEST SCORE IS "+user.getHighestScoresList(1));
////                    Context context = GlobalApplication.getAppContext();
////                    Toast.makeText(context, username+" won with a score of " + newScore, Toast.LENGTH_SHORT).show();
////                }
//        fm.saveObject(users);
////        //System.out.println("Session score: "+newScore);
////        HashMap<String, User> users2 = fm.readObject();
////        assert users != null;
////        // todo: user setScores() over here
////        User user2 = users2.get(username);
////        //System.out.println("Checking high score got saved to file....");
////        //System.out.println(user2.getHighestScoresList(gameIndex));
////        user2.addSessionScore(newScore, gameIndex);
//        System.out.println("THE ARRAY OF THE SESSION SCORES IS DISPLAYED HERE: ");
//        user.printAllSessionScores();
//
//        result[0] = getSlidingScoreLocal(username);
//        result[1] = getHasamiScoreLocal(username);
//        result[2] = getConnect4ScoreLocal(username);
//        result[3] = newScore;
//        //System.out.println("USERNAME IS "+username);
//        return result;
//        }
//
//        // TODO: DISPLAY STUFF ON THE SCOREBOARD CUZ OTHERWISE WE FCKED UP BOY

}
