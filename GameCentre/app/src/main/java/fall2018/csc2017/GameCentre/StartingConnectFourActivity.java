package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Stack;

public class StartingConnectFourActivity extends StartingActivity
{

    private ConnectFourBoardManager boardManager;
    private LoginManager lm = new LoginManager();
    private String username = lm.getPersonLoggedIn();
    private FileManager fm = new FileManager();
    private int size;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_connect4);

        addLoadButtonListener();
        addNewGameButtonListener();
        addbtnScoreboardListener();
        setSizeDropdown();//            }

        assert username != null; //There should be someone logged in once we get to this screen.

    }

    private void setSizeDropdown()
    {
        Spinner dropdown = findViewById(R.id.dropdownC4);
        String[] itemsForDropdown = new String[]{"6x6", "7x7", "8x8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    private void addbtnScoreboardListener()
    {//TODO: To parent class!
        Button btnScoreboard = findViewById(R.id.btnScoreboardC4);

        btnScoreboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                switchToScoreboardScreen();
            }
        });
    }

    private void addNewGameButtonListener()
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
                boardManager = (ConnectFourBoardManager)selectBoardManager(2, size);
                ///////////////////////////////////////////////////////////////TODO: To parent class
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                User user = users.get(username);
                //users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
                users.get(username).resetStates(2);
                //User user = users.get(username); already screwed up here
                users.get(username).addState(boardManager.getBoard(), 2);
                fm.saveObject(users);


                TextView p2username = findViewById(R.id.txtP2UsernameC4);
                String p2usernameString = p2username.getText().toString().trim();
                TextView p2password = findViewById(R.id.txtP2PasswordC4);
                String p2passwordString = p2password.getText().toString().trim();

                setUpTwoPlayerProps(boardManager.getBoard(), p2usernameString, p2passwordString, 2);
                // in the above call the login manager's p2 gets set.
                ////////////////////////////////////////////////////////////////////////////////////

            }
        });
    }

    public void addLoadButtonListener()
    {
        Button loadButton = findViewById(R.id.btnLoadGameConnect4);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onClickHelper(2);
            }
        });
    }

    public void onClickHelper(int gameParameter)
    {//TODO: To parent class
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

