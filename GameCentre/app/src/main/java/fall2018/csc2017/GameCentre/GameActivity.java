package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

//import static fall2018.csc2017.GameCentre.MovementController.username;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;
    private int gameIndex;
    private String username;
    private FileManager fm = new FileManager();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
        gameIndex = getIntent().getExtras().getInt("gameIndex");
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        Stack<Board> userStack = user.getGameStack(0);
        if (userStack.peek() == null) {
            System.out.println("STACK IS NULL!!!");
        }
        setBoardManager(userStack.peek());
        if (user.getTotalTime() == 0) {
            user.startTimer();
        }
        System.out.println("the starting time for the playTime is: " + user.playTime);
        users.put(username, user);
        fm.saveObject(users);


        createTileButtons(this);
        setContentView(R.layout.activity_main);
        // Add View to activity
        addUndoButtonListener();
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;
                        display();
                    }
                });
    }

    /**
     * Adds the listener for the undo button on the UI
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HashMap<String, User> users = fm.readObject();
                assert users != null;
                User user = users.get(username);
                Stack<Board> userStack = user.getGameStack(0);

                if(user.getAvailableUndos() == 0) {
                    makeToastNoUndo();
                } else if (user.getAvailableUndos() < 0) {

                    if (userStack.size() > 1){
                        userStack.pop();
                        setBoardManager(userStack.peek());

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No more undos possible!", Toast.LENGTH_LONG).show();
                    }

                    boardManager.getBoard().addObserver(GameActivity.this);
                    gridView.setBoardManager(boardManager);
                    display();
                    users.put(username, user);
                    user.setAvailableUndos(user.getAvailableUndos() - 1);
                    if (user.getGameStack(0).size() > 1){
                        makeToastUnlimitedUndoText();
                    }
                    fm.saveObject(users);
                }

                else if (userStack.size() > 1 ) {
                    userStack.pop();
                    setBoardManager(userStack.peek());
                    boardManager.getBoard().addObserver(GameActivity.this);
                    gridView.setBoardManager(boardManager);
                    display();
                    users.put(username, user);
                    user.setAvailableUndos(user.getAvailableUndos() - 1);
                    makeToastUndoText(user.getAvailableUndos());
                    fm.saveObject(users);

                }

                else makeToastEmptyStack();
            }
        });
    }

    /**
     * Makes toast representing the number of undo's remaining.
     * @param number number of undo's remaining for the user
     */
    private void makeToastUndoText(int number) {
        Toast.makeText(this, "Undo used: "+number+" undo(s) remain.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast representing the notion that the user has used
     * all of his/her undo's.
     */
    private void makeToastNoUndo() {
        Toast.makeText(this, "You have used all your undos!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast notifying the user of an empty Board stack.
     */
    private void makeToastEmptyStack(){
        Toast.makeText(this, "There are no previous boards.", Toast.LENGTH_SHORT).show();
    }


    public void setBoardManager(Board board)
    {
        switch(gameIndex) {
            case 0:
                boardManager = new SlidingBoardManager(board);
                break;
            case 1:
                boardManager = new ShogiBoardManager(board);
                break;
            case 2:
                boardManager = new ConnectFourBoardManager(board);
        };
    }

    /**
     * Make toast notifying the user of a successful undo
     * when the number of maximum possible undo's is set to
     * unlimited.
     */
    private void makeToastUnlimitedUndoText() {
        Toast.makeText(this, "Undo used", Toast.LENGTH_SHORT).show();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        // TODO: Add pause functionality for the timer
        super.onPause();


    }


    /**
     * Updates the display
     * @param o the observable
     * @param arg the object
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}