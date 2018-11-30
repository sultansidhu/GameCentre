package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    private ScoreboardManager scon = new ScoreboardManager();
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        winner = getIntent().getStringExtra("winner");
        setContentView(R.layout.scoreboard_global);
        List c4Scores = scon.getGlobalScores(2);
        List shScores = scon.getGlobalScores(1);
        List stScores = scon.getGlobalScores(0);
        StringBuilder c4Format = scon.formatScores(c4Scores);
        StringBuilder shFormat = scon.formatScores(shScores);
        StringBuilder stFormat = scon.formatScores(stScores);
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
                if (winner == null || !winner.equals("Guest")) {
                    goToLocalScoreboard();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign up to save and see your scores!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToLocalScoreboard() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        String username = new LoginManager().getPersonLoggedIn();
        intent.putExtra("username", username);
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
