package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {
    //private FileManager fm = new FileManager();
    private ScoreboardController scon = new ScoreboardController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_global);
        ArrayList c4Scores = scon.getGlobalScores(2);
        ArrayList shScores = scon.getGlobalScores(1);
        ArrayList stScores = scon.getGlobalScores(0);
        StringBuilder c4Format = scon.formatScores(c4Scores, 4);
        StringBuilder shFormat = scon.formatScores(shScores, 4);
        StringBuilder stFormat = scon.formatScores(stScores, 4);
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
}
