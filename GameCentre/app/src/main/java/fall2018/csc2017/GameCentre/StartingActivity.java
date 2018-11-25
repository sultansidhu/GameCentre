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
        users.get(username).resetGameStack(gameIndex);
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

    public void startButtonHelper(String p2usernameString, String p2passwordString, int gameIndex)
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
        else if(p2usernameString.equals(new LoginManager().getPersonLoggedIn())){
            Toast.makeText(getApplicationContext(), "The opponent cannot be the same as Player 1! Use a different player!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Todo for Sultan: Shogi crashes; and game crashes when guest is the other player and guest wins
            LoginManager lm = new LoginManager();
            if(lm.authenticateP2(p2usernameString, p2passwordString))
            {
                Toast.makeText(getApplicationContext(), "Starting Game...", Toast.LENGTH_SHORT).show();
                HashMap<String, User> users = fm.readObject();
                User p1 = users.get(lm.getPersonLoggedIn());
                p1.setOpponent(p2usernameString);
                System.out.println("PLAYER 1 ("+lm.getPersonLoggedIn()+")'s OPPONENT NOW IS: " + p1.getOpponent());
                users.get(lm.getPersonLoggedIn()).getOpponents().put(gameIndex, p2usernameString);
                int size = users.get(lm.getPersonLoggedIn()).getOpponents().size();
                fm.saveObject(users);
                System.out.println("PLAYER 2 LOGGED IN AS " + p2usernameString + " with a game parametetr of "+gameIndex);
                System.out.println("SIZE OF OPPONENT HASHMAP FOR THIS GAME IS: "+size);
                switchToGame(gameIndex);
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();

        }
    }

}