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
        Stack<Board> userStack = fm.getStack(username, gameIndex);
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
        User user = fm.getUser(username);
        user.setAvailableUndos(gameIndex, undoLimit);
        user.resetGameStack(gameIndex);
        user.addState(boardManager.getBoard(), gameIndex);
        fm.saveUser(user, username);
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
            LoginManager lm = new LoginManager();
            String username = lm.getPersonLoggedIn();
            User user = fm.getUser(username);
            user.getOpponents().put(gameIndex, "Guest");
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
            LoginManager lm = new LoginManager();
            if(lm.authenticateP2(p2usernameString, p2passwordString))
            {
                Toast.makeText(getApplicationContext(), "Starting Game...", Toast.LENGTH_SHORT).show();
                User p1 = fm.getUser(lm.getPersonLoggedIn());
                //p1.setOpponent(p2usernameString);
                p1.getOpponents().put(gameIndex, p2usernameString);
                fm.saveUser(p1, lm.getPersonLoggedIn());
                switchToGame(gameIndex);
            }
            else{}
                //Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();

        }
    }
    public void loadButton(String p2usernameString, String p2passwordString, int gameIndex){
        if(p2usernameString.equals("")||p2passwordString.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter in the username and password of the player you were playing with!", Toast.LENGTH_SHORT).show();
        }
        else if (true){

        }
    }

}