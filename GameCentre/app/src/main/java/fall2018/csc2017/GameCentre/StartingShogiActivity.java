package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Stack;

public class StartingShogiActivity extends StartingActivity {

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
        Button startButton = findViewById(R.id.btnStartGameHS);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Spinner undoDropdown = findViewById(R.id.dropdownHS);
                String selectedUndo = undoDropdown.getSelectedItem().toString();
                Spinner sizeSelected = findViewById(R.id.dropdownHS);
                String boardSize = sizeSelected.getSelectedItem().toString();
                char Size = boardSize.charAt(0);
                int size = Character.getNumericValue(Size);
                System.out.println("SHOGI BOARD SIZE SELECTED IS ========================= " + size);
                boardManager = (ShogiBoardManager)selectBoardManager(1, size);
                // TODO: MAKE THE SIZE VARY OVER HERE
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                users.get(username).setAvailableUndos(gameIndex, undoLimit);
                //users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
                users.get(username).addState(boardManager.getBoard(), 1);
                fm.saveObject(users);

                TextView p2username = findViewById(R.id.txtP2UsernameHS);
                String p2usernameString = p2username.getText().toString().trim();
                TextView p2password = findViewById(R.id.txtP2PasswordHS);
                String p2passwordString = p2password.getText().toString().trim();

                startButtonHelper(boardManager.getBoard(), p2usernameString, p2passwordString, 1);

            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener()
    {
        Button loadButton = findViewById(R.id.btnLoadGameHS);
        loadButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                onClickHelper(1);
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



}
