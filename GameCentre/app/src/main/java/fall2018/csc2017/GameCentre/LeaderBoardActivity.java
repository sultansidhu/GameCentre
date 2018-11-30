package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {
    private FileManager fm = new FileManager(GlobalApplication.getAppContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_global);
        ArrayList c4Scores = getGlobalScores(2);
        ArrayList shScores = getGlobalScores(1);
        ArrayList stScores = getGlobalScores(0);
        StringBuilder c4Format = formatScores(c4Scores, 4);
        StringBuilder shFormat = formatScores(shScores, 4);
        StringBuilder stFormat = formatScores(stScores, 4);
        TextView connect4box = findViewById(R.id.c4HighScores);
        TextView shBox = findViewById(R.id.hsHighScores);
        TextView stBox = findViewById(R.id.stHighScores);
        connect4box.setText(c4Format);
        shBox.setText(shFormat);
        stBox.setText(stFormat);

        addGameListButtonListener();
        addUserStatsButtonListener();
    }

    private void addUserStatsButtonListener() {
        Button LocalScoreboard = findViewById(R.id.goToLocalScoreboard);
        LocalScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLocalScoreboard();
            }
        });
    }

    private void goToLocalScoreboard() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    private void addGameListButtonListener() {
        Button gameListBtn = findViewById(R.id.goToGameListbtn);
        gameListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGamesList();
            }
        });
    }

    private void goToGamesList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }


    /////////////////////////LOGIC STUFF BELOW, MOVE IT OUT/////////////////////////////////////////

    private StringBuilder formatScores(ArrayList c4Scores, int numToDisplay) {
        StringBuilder scoreFormat = new StringBuilder();
        for(int i = 0; i < numToDisplay && i < c4Scores.size(); i++){
            Object[] scoreTup = (Object[])c4Scores.get(i);
            scoreFormat.append(scoreTup[0] + ": " + scoreTup[1] + "\n");//Appends the username and score to the tup.
        }
        if(scoreFormat.length() == 0){
            scoreFormat.append("No one to show! Be the first one!");
        }
        return scoreFormat;
    }

    public ArrayList getGlobalScores(int gameIndex) {

        HashMap<String, User> users = fm.readObject();
        ArrayList highScoresTup = new ArrayList();
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            User u = (User) pair.getValue();
            ArrayList<Integer> highScoresList = u.getHighestScore(gameIndex);
            for (int i = 0; i < highScoresList.size(); i++){
                Object[] tempTup = new Object[2];
                tempTup[0] = u.getUsername();
                tempTup[1] = highScoresList.get(i);
                highScoresTup.add(tempTup);
            }

        }
        return sortListofUserandScores(highScoresTup);
    }

    private ArrayList sortListofUserandScores(ArrayList highScoresTup) {
        Collections.sort(highScoresTup, new Comparator() {
            @Override
            public int compare(Object o, Object t) {
                Object[] tempO = (Object[]) o;
                Object[] tempT = (Object[]) t;
                int o1 = (int) tempO[1];
                int t1 = (int) tempT[1];
                return t1 - o1;
            }
        });
        return highScoresTup;
    }
}
