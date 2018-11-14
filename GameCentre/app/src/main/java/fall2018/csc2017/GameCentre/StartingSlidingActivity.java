package fall2018.csc2017.GameCentre;
/*
==================================================================
File Name: StartingSlidingActivity.java
Purpose: This Activity connects to the main menu of SlidingTiles
and initializes the screen
Date: October 30, 2018
Group #: 0506
================================================================== */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;
import static fall2018.csc2017.GameCentre.MovementController.username;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingSlidingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    private fall2018.csc2017.GameCentre.BoardManager boardManager;
    public static int undoLimit;
    private int gameIndex;
    private int size;
   /* private ArrayList<Class> boardManagers = new ArrayList<>();*/


    /**
     * Creats start screen for sliding tiles with game options.
     * @param savedInstanceState of starting activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        setSizeDropdown();
        setUndoDropdown();
        addScoreboardButtonListener();
        gameIndex = getIntent().getExtras().getInt("gameIndex");
        /*boardManagers.add(SlidingBoardManager.class);
        boardManagers.add(ShogiBoardManager.class);
        boardManagers.add(ConnectFourBoardManager.class);*/
    }

    /**
     * This method initializes the dropdown menu that displays the various size options
     * return null
     */

    public void setSizeDropdown() {
        Spinner dropdown = findViewById(R.id.dropdown);
        String[] itemsForDropdown = new String[]{"3x3", "4x4", "5x5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    /**
     * This method initializes the dropdown menu that displays the undo limit options
     * return null
     */

    public void setUndoDropdown() {
        Spinner dropdown = findViewById(R.id.dropdown_undo);
        String[] itemsForDropdown = new String[]{"3", "5", "10", "20", "30", "Unlimited"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }


    /*
    Activate the LaunchScoreboard button
    */
    private void addScoreboardButtonListener() {
        Button scoreboard = findViewById(R.id.btnScoreboard);

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
        Button startButton = findViewById(R.id.StartButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner dropdown = findViewById(R.id.dropdown);
                String selectedSize = dropdown.getSelectedItem().toString();
                size = Integer.parseInt(selectedSize.substring(0, 1));

                Spinner undoDropdown = findViewById(R.id.dropdown_undo);
                String selectedUndo = undoDropdown.getSelectedItem().toString();

                try {
                    undoLimit = Integer.parseInt(selectedUndo);

                } catch (NumberFormatException e) {
                    undoLimit = -1;
                } finally {
                    selectBoardManager();
                    HashMap<String, User> users = GameActivity.readObject();
                    assert users != null;
                    users.get(username).setAvailableUndos(undoLimit);
                    users.get(username).setSavedStates(new Stack<Board>());
                    users.get(username).addState(boardManager.getBoard());
                    GameActivity.saveObject(users);
                    switchToGame();
                }

            }
        });
    }

    /**
     * Selects the correct boardmanager basefd on which game is being played.
     */

    public void selectBoardManager() {
        switch(gameIndex) {
            case 0:
                boardManager = new SlidingBoardManager(size);
                break;
            case 1:
                boardManager = new ShogiBoardManager(size);
                break;
            case 2:
                boardManager = new ConnectFourBoardManager(size);
        };

    }

    public void setBoardManager(Board board) {
        switch(gameIndex) {
            case 0:
                boardManager = new SlidingBoardManager(board);
            case 1:
                boardManager = new ShogiBoardManager(board);
            case 2:
                boardManager = new ConnectFourBoardManager(board);
        };
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, User> users = GameActivity.readObject();
                assert users != null;
                Stack<Board> userStack = users.get(username).getStack();
                if (userStack.size() < 1) {
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
    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, User> users = GameActivity.readObject();
        assert users != null;
        Stack<Board> userStack = users.get(username).getStack();
        try {
            setBoardManager(userStack.peek());
        } catch (EmptyStackException e) {
            System.out.println("Empty stack, nothing to resume!");
        }
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("gameIndex", gameIndex);
        startActivity(intent);
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    private void switchToScoreboardScreen() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }
}