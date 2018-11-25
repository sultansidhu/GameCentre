package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartingConnectFourActivity extends StartingActivity
{

    private ConnectFourBoardManager boardManager;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();
    private int size;
    private int gameIndex = 2;
    private BoardManagerFactory bmFactory = new BoardManagerFactory();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_connect4);
        addLoadButtonListener();
        addStartButtonListener();
        addbtnScoreboardListener();
        setSizeDropdown();
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
    {
        Button btnScoreboard = findViewById(R.id.btnScoreboardC4);

        btnScoreboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                switchToScoreboardScreen();
            }
        });
    }

    private void addStartButtonListener()
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
                boardManager = (ConnectFourBoardManager)bmFactory.getBoardManager(gameIndex, size);
                setUserUndos(username, 0, gameIndex, boardManager);
                TextView p2username = findViewById(R.id.txtP2UsernameC4);
                String p2usernameString = p2username.getText().toString().trim();
                TextView p2password = findViewById(R.id.txtP2PasswordC4);
                String p2passwordString = p2password.getText().toString().trim();
                startButtonHelper(p2usernameString, p2passwordString, gameIndex);
            }
        });
    }

    private void addLoadButtonListener()
    {
        Button loadButton = findViewById(R.id.btnLoadGameConnect4);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                onClickHelper(gameIndex, username);
            }
        });
    }
}

