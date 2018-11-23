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

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;
//import static fall2018.csc2017.GameCentre.MovementController.username;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingSlidingActivity extends StartingActivity {

    /**
     * The board manager.
     */
    private SlidingBoardManager boardManager;
    public int undoLimit;
    private int gameIndex;
    private int size;
    private String username;
    private FileManager fm = new FileManager();


    /**
     * Creats start screen for sliding tiles with game options.
     * @param savedInstanceState of starting activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);
        /////////////////////////////////////////////////////////////////////////////TODO: To parent
        addStartButtonListener();
        addLoadButtonListener();
        setSizeDropdown();
        setUndoDropdown();
        addScoreboardButtonListener();
        gameIndex = getIntent().getExtras().getInt("gameIndex");
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
        assert username != null; //There should be someone logged in once we get to this screen.
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    /**
     * This method initializes the dropdown menu that displays the various size options
     * return null
     */

    public void setSizeDropdown() {
        Spinner dropdown = findViewById(R.id.dropdown_size);
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


    /**
    Activate the LaunchScoreboard button
    */
    private void addScoreboardButtonListener() {
        //TODO: To parent class...
        Button scoreboard = findViewById(R.id.btnScoreboard);

        scoreboard.setOnClickListener(new View.OnClickListener()
        {
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
                Spinner dropdown = findViewById(R.id.dropdown_size);
                String selectedSize = dropdown.getSelectedItem().toString();
                size = Integer.parseInt(selectedSize.substring(0, 1));

                Spinner undoDropdown = findViewById(R.id.dropdown_undo);
                String selectedUndo = undoDropdown.getSelectedItem().toString();

                try {
                    undoLimit = Integer.parseInt(selectedUndo);

                } catch (NumberFormatException e) {
                    undoLimit = -1;
                } finally {
                    boardManager = (SlidingBoardManager)selectBoardManager(0, size);
                    HashMap<String, User> users = fm.readObject();
                    assert users != null;
                    users.get(username).setAvailableUndos(gameIndex, undoLimit);
                    //users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
                    users.get(username).addState(boardManager.getBoard(), 0);
                    fm.saveObject(users);
                    switchToGame(0);
                }

            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener()
    {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onClickHelper(0);

            }
        });
    }

    public void onClickHelper(int gameParameter)
    {
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        Stack<Board> userStack = users.get(username).getGameStack(gameParameter);
        if (userStack.size() < 1)
        {
            Toast.makeText(getApplicationContext(), "No game to load! Start a new game!", Toast.LENGTH_LONG).show();
        } else {
            makeToastLoadedText();
            switchToGame(gameParameter);
        }
    }


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        Stack<Board> userStack = users.get(username).getGameStack(0);
        try
        {
            setBoardManager(userStack.peek(),0);
        } catch (EmptyStackException e)
        {
            System.out.println("Empty stack, nothing to resume!");
        }
    }


}