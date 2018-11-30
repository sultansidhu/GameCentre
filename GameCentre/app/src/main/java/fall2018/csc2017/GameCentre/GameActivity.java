package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Makes toast representing the number of undo's remaining.
     * @param number number of undo's remaining for the user
     */
    public void makeToastUndoText(int number) {
        Toast.makeText(this, "Undo used: "+number+" undo(s) remain.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast representing the notion that the user has used
     * all of his/her undo's.
     */
    public void makeToastNoUndo() {
        Toast.makeText(this, "You have used all your undos!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make toast notifying the user of a successful undo
     * when the number of maximum possible undo's is set to
     * unlimited.
     */
    public void makeToastUnlimitedUndoText() {
        Toast.makeText(this, "Undo used", Toast.LENGTH_SHORT).show();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    public void makeToastUndo(User user, int gameIndex) {
        if (user.getAvailableUndos(gameIndex) < 0) {
                makeToastUnlimitedUndoText();
            }
            else {
                makeToastUndoText(user.getAvailableUndos(gameIndex));
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