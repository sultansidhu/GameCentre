package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class StartingShogiActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    private ShogiBoardManager boardManager;
    public int undoLimit = 3;
    private int gameIndex;
    private int size = 7;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();


    /**
     * Creats start screen for sliding tiles with game options.
     * @param savedInstanceState of starting activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_hs);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreboardButtonListener();
        setSizeDropdown();
        gameIndex = getIntent().getExtras().getInt("gameIndex");
    }

    /**
     * This method initializes the dropdown menu that displays the various size options
     * return null
     */

    public void setSizeDropdown()
    {
        Spinner dropdown = findViewById(R.id.dropdownHS);
        String[] itemsForDropdown = new String[]{"6x6", "7x7", "8x8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }



    /*
    Activate the LaunchScoreboard button
    */
    private void addScoreboardButtonListener() {
        Button scoreboard = findViewById(R.id.btnScoreboardHS);

        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboardScreen();
            }
        });
    }

    /**
     * Activate the start button.
     */

    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.btnStartGameHS);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner undoDropdown = findViewById(R.id.dropdownHS);
                String selectedUndo = undoDropdown.getSelectedItem().toString();
                selectBoardManager();
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                users.get(username).setAvailableUndos(undoLimit);
                users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
                users.get(username).addState(boardManager.getBoard(), 1);
                fm.saveObject(users);
                switchToGame();


            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnLoadGameHS);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                Stack<Board> userStack = users.get(username).getGameStack(1);
                if (userStack == null || userStack.size() < 1) {
                    Toast.makeText(getApplicationContext(), "No game to load! Start a new game!", Toast.LENGTH_LONG).show();
                } else {
                    setBoardManager(userStack.peek());
                    makeToastLoadedText();
                    switchToGame();
                }

            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */

    /*protected void onResume() {
        super.onResume();
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        Stack<Board> userStack = users.get(username).getGameStack(1);
        try {
            setBoardManager(userStack.peek());
        } catch (EmptyStackException e) {
            System.out.println("Empty stack, nothing to resume!");
        }
    }
*/
    /**
     * Switch to the ShogiActivity view to play the game.
     */
    private void switchToGame() {
        Intent intent = new Intent(getApplicationContext(), ShogiActivity.class);
        startActivity(intent);
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    private void switchToScoreboardScreen() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }


    /**
     * Selects the correct boardmanager basefd on which game is being played.
     */

    public void selectBoardManager() {
        boardManager = new ShogiBoardManager(size);
    }

    public void setBoardManager(Board board) {
        boardManager = new ShogiBoardManager(board);
    }


}
