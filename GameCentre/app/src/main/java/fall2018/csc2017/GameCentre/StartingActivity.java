package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Stack;

public class StartingActivity extends AppCompatActivity
{
    public FileManager fm = new FileManager();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void onClickHelper(int gameIndex, String username)
    {
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        Stack<Board> userStack = users.get(username).getGameStack(gameIndex);
        if (userStack.size() < 1)
        {
            Toast.makeText(getApplicationContext(), "No game to load! Start a new game!", Toast.LENGTH_LONG).show();
        } else {
            makeToastLoadedText();
            switchToGame(gameIndex);
        }
    }

    /**
     * Switch to the Activity view of the game specified by the gameIndex.
     */
    public void switchToGame(int gameIndex)
    {
        Intent intent = null;
        switch(gameIndex)
        {
            case 0:
                intent = new Intent(getApplicationContext(), SlidingActivity.class);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), ShogiActivity.class);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), ConnectFourActivity.class);
                break;
        }

        startActivity(intent);
    }

    public void setUserUndos(String username, int undoLimit, int gameIndex, BoardManager boardManager) {
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        users.get(username).setAvailableUndos(gameIndex, undoLimit);
        //users.get(username).setSavedStates(new HashMap<Integer, Stack<Board>>());
        users.get(username).addState(boardManager.getBoard(), gameIndex);
        fm.saveObject(users);
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    public void switchToScoreboardScreen()
    {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public void makeToastLoadedText()
    {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    public void startButtonHelper(BoardManager boardManager, String p2usernameString, String p2passwordString, int gameIndex)
    {
        if(p2usernameString.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
            switchToGame(gameIndex);
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
                switchToGame(gameIndex);
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();

        }
    }

}