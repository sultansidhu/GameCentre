package fall2018.csc2017.GameCentre;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Stack;

public class StartingActivity extends AppCompatActivity
{
    public FileManager fm;
    private UserManager userManager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fm = new FileManager(this);
        userManager = new UserManager(this);
    }

    public void loadGame(int gameIndex, String username)
    {
        if (fm.getStack(username, gameIndex).size() < 1) {
            Toast.makeText(this, "No game to load! Start a new game!", Toast.LENGTH_LONG).show();
        } else {
            makeToastLoadedText();
            switchToGame(gameIndex);
        }
    }

    /**
     * Switch to the Activity view of the game specified by the gameIndex.
     */
    // TODO: GameFactory?
    public void switchToGame(int gameIndex) {
        Intent intent = null;
        switch(gameIndex) {
            case 0:
                intent = new Intent(this, SlidingActivity.class);
                break;
            case 1:
                intent = new Intent(this, ShogiActivity.class);
                break;
            case 2:
                intent = new Intent(this, ConnectFourActivity.class);
                break;
        }

        startActivity(intent);
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    public void switchToScoreboardScreen() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        LoginManager lm = new LoginManager(GlobalApplication.getAppContext());
        String username = lm.getPersonLoggedIn();
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    public void startButtonHelper(String p2usernameString, String p2passwordString, int gameIndex) {
        if(p2usernameString.equals("")) {
            Toast.makeText(this, "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
            userManager.addOpponent(gameIndex, "Guest");
            switchToGame(gameIndex);
        }
        else if(p2passwordString.equals("")) {
            Toast.makeText(this, "Password Field is Empty!", Toast.LENGTH_SHORT).show();
        }
        else if(p2usernameString.equals(new LoginManager(this).getPersonLoggedIn())){
            Toast.makeText(this, "The opponent cannot be the same as Player 1! Use a different player!", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginManager lm = new LoginManager(this);
            if(lm.authenticateP2(p2usernameString, p2passwordString)) {
                Toast.makeText(this, "Starting Game...", Toast.LENGTH_SHORT).show();
                userManager.addOpponent(gameIndex, p2usernameString);
                switchToGame(gameIndex);
            }
            else{}
                //Toast.makeText(this, "Invalid Credentials...", Toast.LENGTH_SHORT).show();
        }
    }
//    public void loadButton(String p2usernameString, String p2passwordString, int gameIndex) {
//        if(p2usernameString.equals("")||p2passwordString.equals("")) {
//            Toast.makeText(this, "Please enter in the username and password of the player you were playing with!", Toast.LENGTH_SHORT).show();
//        }
//        else if (true){
//
//        }
//    }

}