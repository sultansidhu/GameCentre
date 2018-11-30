package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();

    /**
     * Username of current player.
     */
    //static String username = null;
    MovementController(Context context) {
    }

    /**
     * Sets the boardManager of MovementController.
     * @param boardManager is type BoardManager.
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes a tap for the user.
     * @param context of the application.
     * @param position that the user tapped.
     */
    void processTapMovement(Context context, int position, int gameIndex) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.getChanged()) {
                User user = fm.getUser(username);
                user.addState(boardManager.getBoard(), gameIndex);
                fm.saveUser(user, username);
                checkSolved(context, gameIndex);
            }
        }
        else { Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show(); }
    }

    private void checkSolved(Context context, int gameIndex) {
        if (boardManager.puzzleSolved()) {
            String winner = getWinnerUsername(gameIndex);
            Toast.makeText(context, username + " wins!", Toast.LENGTH_SHORT).show();
            if (winner.equals("Guest")) {
                switchToLeaderBoardScreen(context);
            }
            else {
                ScoreboardActivity sc = new ScoreboardActivity();
                int[] result = sc.updateUserHighScore(winner, gameIndex);
                switchToScoreboardScreen(context, result, winner);
            }
        }
    }

    private String getWinnerUsername(int gameIndex){
        // get the winning player (current player in the bm)
        int playerNumber = 3 - boardManager.getBoard().getCurrPlayer();
        if (gameIndex != 0 && playerNumber == 2){
            return fm.getUser(username).getOpponents().get(gameIndex);
        }
        return username;
    }

    /*
    Switches to the scoreboard screen if a game is won
     */
    public void switchToScoreboardScreen(Context context, int[] results, String username) {
        Intent intent = new Intent(context, ScoreboardActivity.class);
        intent.putExtra("results", results);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    public void switchToLeaderBoardScreen(Context context) {
        context.startActivity(new Intent(context, LeaderBoardActivity.class));
    }
}
