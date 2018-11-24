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

public class StartingActivity extends AppCompatActivity
{
    private int size;
    private String username;
    public FileManager fm = new FileManager();
    //private BoardManager boardManager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void onClickHelper(int gameParameter)
    {
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

    /**
     * Switch to the ConnectFourActivity view to play the game.
     */
    public void switchToGame(int gameToSwitchTo)
    {
        Intent intent = null;
        switch(gameToSwitchTo)
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

    public BoardManager setBoardManager(Board board, int gameToSwitchTo)
    {
        switch(gameToSwitchTo)
        {
            case 0:
                return new SlidingBoardManager(board);
            case 1:
                return new ShogiBoardManager(board);
            case 2:
                return new ConnectFourBoardManager(board);
        }
        return null;
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

    public BoardManager selectBoardManager(int gameToSwitchTo, int size)
    {
        switch(gameToSwitchTo)
        {
            case 0:
                return new SlidingBoardManager(size);
            case 1:
                return new ShogiBoardManager(size);
            case 2:
                return new ConnectFourBoardManager(size);
        }
        return null;

    }

    public void setUpTwoPlayerProps(Board board, String p2usernameString, String p2passwordString, int gameParameter)
    {
        if(p2usernameString.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
            board.setOpponentString("Guest");
            switchToGame(gameParameter);
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
                HashMap<String, User> users = fm.readObject();
                User p1 = users.get(lm.getPersonLoggedIn());
                p1.setOpponent(p2usernameString);
                System.out.println("PLAYER 1 ("+lm.getPersonLoggedIn()+")'s OPPONENT NOW IS: " + p1.getOpponent());
                users.get(lm.getPersonLoggedIn()).getOpponents().put(gameParameter, p2usernameString);
                int size = users.get(lm.getPersonLoggedIn()).getOpponents().size();
                fm.saveObject(users);
                System.out.println("PLAYER 2 LOGGED IN AS " + p2usernameString + " with a game parametetr of "+gameParameter);
                System.out.println("SIZE OF OPPONENT HASHMAP FOR THIS GAME IS: "+size);
                switchToGame(gameParameter);
            }
            else
                Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();

        }
    }
//    public void setUpTwoPlayerProps(Board board, String p2usernameString, String p2passwordString, int gameParameter, LoginManager lm)
//    {
//        if(p2usernameString.equals(""))
//        {
//            Toast.makeText(getApplicationContext(), "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
//            board.setOpponentString("Guest");
//            switchToGame(gameParameter);
//        }
//        else if(p2passwordString.equals(""))
//        {
//            Toast.makeText(getApplicationContext(), "Password Field is Empty!", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//
//            //LoginManager lm = new LoginManager();
//            if(lm.authenticateP2(p2usernameString, p2passwordString))
//            {
//                Toast.makeText(getApplicationContext(), "Starting Game...", Toast.LENGTH_SHORT).show();
//                HashMap<String, User> users = fm.readObject();
//                users.get(lm.getPersonLoggedIn()).getOpponents().put(gameParameter, p2usernameString);
//                int size = users.get(lm.getPersonLoggedIn()).getOpponents().size();
//                lm.setP2LoggedIn(p2usernameString);
//                fm.saveObject(users);
//                System.out.println("PLAYER 2 LOGGED IN AS " + p2usernameString + " with a game parametetr of "+gameParameter);
//                System.out.println("SIZE OF OPPONENT HASHMAP FOR THIS GAME IS: "+size);
//                switchToGame(gameParameter);
//            }
//            else
//                Toast.makeText(getApplicationContext(), "Invalid Credentials...", Toast.LENGTH_SHORT).show();
//
//        }
//    }

}