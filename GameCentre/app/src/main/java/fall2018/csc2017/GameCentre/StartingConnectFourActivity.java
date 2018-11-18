package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Stack;

public class StartingConnectFourActivity extends AppCompatActivity
{
    private ConnectFourBoardManager boardManager;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();
    private int size;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_connect4);

        addLoadButtonListener();
        addNewGameButtonListener();
        addbtnScoreboardListener();
        setSizeDropdown();

        assert username != null; //There should be someone logged in once we get to this screen.

    }

    public void addLoadButtonListener()
    {
        Button loadButton = findViewById(R.id.btnLoadGameConnect4);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                Stack<Board> userStack = users.get(username).getGameStack(2);
                if (userStack.size() < 1)
                {
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
     * This method initializes the dropdown menu that displays the various size options
     * return null
     */

    public void setSizeDropdown()
    {
        Spinner dropdown = findViewById(R.id.dropdownC4);
        String[] itemsForDropdown = new String[]{"6x6", "7x7", "8x8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    public void addNewGameButtonListener()
    {
        Button startGameButton = findViewById(R.id.btnStartGameC4);
        startGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Spinner dropdown = findViewById(R.id.dropdownC4);
                String selectedSize = dropdown.getSelectedItem().toString();
                size = Integer.parseInt(selectedSize.substring(0, 1));
                selectBoardManager();
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                //users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
                users.get(username).addState(boardManager.getBoard(), 2);
                fm.saveObject(users);


                TextView p2username = findViewById(R.id.txtP2UsernameC4);
                String p2usernameString = p2username.getText().toString().trim();
                TextView p2password = findViewById(R.id.txtP2PasswordC4);
                String p2passwordString = p2password.getText().toString().trim();

                if(p2usernameString.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
                    boardManager.getBoard().setOpponentString("Guest");
                    switchToGame();
                }
                else if(p2passwordString.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Password Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LoginManager lm = new LoginManager();
                    if(lm.authenticateP2(p2usernameString, p2passwordString))
                    {
                        Toast.makeText(getApplicationContext(), "Starting Game...", Toast.LENGTH_SHORT).show();
                        switchToGame();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void addbtnScoreboardListener()
    {
        Button btnScoreboard = findViewById(R.id.btnScoreboardC4);

        btnScoreboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                switchToScoreboardScreen();
            }
        });
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    private void switchToScoreboardScreen()
    {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public void selectBoardManager()
    {
        boardManager = new ConnectFourBoardManager(size);
    }


    private void makeToastLoadedText()
    {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the ConnectFourActivity view to play the game.
     */
    private void switchToGame() {
        Intent intent = new Intent(getApplicationContext(), ConnectFourActivity.class);
        startActivity(intent);
    }

    public void setBoardManager(Board board)
    {
        this.boardManager = new ConnectFourBoardManager(board);
    }


}

