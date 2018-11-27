package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
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
    private String username = new LoginManager().getPersonLoggedIn();
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

    public void undoHelper(User user, String username, Stack<Board> userStack, int gameIndex) {
        if(user.getAvailableUndos(gameIndex) == 0) {
            makeToastNoUndo();
        } else if (userStack.size() == 1) {
            makeToastEmptyStack();
        }
        else {
            userStack.pop();
            user.setAvailableUndos(gameIndex, user.getAvailableUndos(gameIndex) - 1);
            if (user.getAvailableUndos(gameIndex) < 0) {
                makeToastUnlimitedUndoText();
            }
            else {
                makeToastUndoText(user.getAvailableUndos(gameIndex));
            }
            fm.saveUser(user, username);
        }
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