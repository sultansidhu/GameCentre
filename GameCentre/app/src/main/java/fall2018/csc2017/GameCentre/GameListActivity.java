package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The gamelist activity with all the games.
 */
public class GameListActivity extends AppCompatActivity
{
    /**
     * Invokes when the user logs in or signs up. Displays list of games for user to play.
     * @param savedInstanceState of activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list);
        addSlidingTilesButtonListener();
        addHasamiShogiButtonListener();
        addConnectFourButtonListener();
    }
    /**
     * This will launch the sliding tiles GameActivity on click.
     * return null.
     */
    public void addSlidingTilesButtonListener()
    {
        Button launchSlidingTiles = findViewById(R.id.btnLaunchSlide);
        launchSlidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                launchGame(0);
            }
        });
    }

    public void addHasamiShogiButtonListener() {
        Button launchHasamiShogi = findViewById(R.id.btnLaunchShogi);
        launchHasamiShogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                launchGame(1);
            }
        });

    }

    public void addConnectFourButtonListener() {
        Button launchHasamiShogi = findViewById(R.id.btnLaunchConnectFour);
        launchHasamiShogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                launchGame(2);
            }
        });
    }


    /**
     * Launches the sliding tiles game based on index.
     * @param game_index index of the game selected.
     */
    public void launchGame(int game_index)
    {
        Intent intent = null;
        switch(game_index) {
            case 0:
                intent = new Intent(this, StartingSlidingActivity.class); break;
            case 1:
                intent = new Intent(this, StartingShogiActivity.class); break;
            case 2:
                intent = new Intent(this, StartingConnectFourActivity.class);
        }

        intent.putExtra("gameIndex", game_index);
        startActivity(intent);


    }

}
