package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartingShogiActivity extends StartingActivity {

    /**
     * The board manager.
     */
    private ShogiBoardManager boardManager;
    private int undoLimit =3;
    private int gameIndex = 1;
    private int size = 7;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();
    private BoardManagerFactory bmFactory = new BoardManagerFactory();

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
        setUndoDropdown();
    }

    /**
     * This method initializes the dropdown menu that displays the various size options
     * return null
     */

    private void setSizeDropdown()
    {
        Spinner dropdown = findViewById(R.id.dropdownHS);
        String[] itemsForDropdown = new String[]{"6x6", "7x7", "8x8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    public void setUndoDropdown() {
        Spinner dropdown = findViewById(R.id.dropdown_undo_hs);
        System.out.println("IS DROPDOWN NHLL?" + dropdown==null);
        String[] itemsForDropdown = new String[]{"3", "5", "10", "20", "30", "Unlimited"};
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
                Spinner undoDropdown = findViewById(R.id.dropdown_undo_hs);
                String selectedUndo = undoDropdown.getSelectedItem().toString();
                Spinner sizeSelected = findViewById(R.id.dropdownHS);
                String boardSize = sizeSelected.getSelectedItem().toString();
                char Size = boardSize.charAt(0);
                size = Character.getNumericValue(Size);
                try {
                    undoLimit = Integer.parseInt(selectedUndo);
                } catch (NumberFormatException e) {
                    undoLimit = -1;
                } finally {
                    boardManager = (ShogiBoardManager) bmFactory.getBoardManager(gameIndex, size);
                    setUserUndos(username, undoLimit, gameIndex, boardManager);
                    TextView p2username = findViewById(R.id.txtP2UsernameHS);
                    String p2usernameString = p2username.getText().toString().trim();
                    TextView p2password = findViewById(R.id.txtP2PasswordHS);
                    String p2passwordString = p2password.getText().toString().trim();
                    startButtonHelper(p2usernameString, p2passwordString, gameIndex);
                }
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
                onClickHelper(gameIndex, username);
            }
        });
    }
}
