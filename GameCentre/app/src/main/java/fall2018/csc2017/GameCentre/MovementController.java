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
    private String username;
    private String winnerUsername;

    /**
     * Username of current player.
     */
    //static String username = null;
    MovementController(Context context) {
        username = new LoginManager().getPersonLoggedIn();
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
        FileManager fm = new FileManager();
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
            String winner = getWinnerUsername(gameIndex); // TODO: CALL TO THE getWinnerUsername() METHOD
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            if (winner.equals("Guest")) {
                switchToLeaderBoardScreen(context);
            }
            else {
                ScoreboardActivity sc = new ScoreboardActivity();
                int[] result = sc.updateUserHighScore(winner, gameIndex);
                switchToScoreboardScreen(context, result, winner);
                System.out.println("LE RESULT ISCH ------------- : " + result);
            }
        }
    }

//    public void switchToGlobalScores(){
//        Intent intent = new Intent(GlobalApplication.getAppContext(), LeaderBoardActivity.class);
//        startActivity(intent);
//    }

    private String getWinnerUsername(int gameIndex){
        // get the winning player (current player in the bm)
        int playerNumber = 3 - boardManager.getBoard().getCurrPlayer();
        System.out.println(" THE PLAYER NUMBER IS  ----------&&&&&&&&-------- " + playerNumber);
        if (gameIndex != 0){
            if (playerNumber == 1){
                System.out.println("THE CURRENT WINNER OF THE GAME IS PLAYER 1 ---------- "+ username);
                winnerUsername = username;
                return username;
            } else {
                //return boardManager.getBoard().getOpponentString();
                FileManager fm = new FileManager();
                Map<String, User> users = fm.readObject();
                User user = users.get(username);
                Map<Integer, String> opponents = user.getOpponents();
                assert opponents != null;
                System.out.println("THE OPPONENT (who is the winner) IS : ------------ " + opponents.get(gameIndex));
                winnerUsername = opponents.get(gameIndex);
                return opponents.get(gameIndex);

            }
        }
        return username;
    }

    /*
    Switches to the scoreboard screen if a game is won
     */
    public void switchToScoreboardScreen(Context context, int[] results, String username)
    {
        Intent tmp = new Intent(context, ScoreboardActivity.class);
        tmp.putExtra("results", results);
        tmp.putExtra("username", username);
        context.startActivity(tmp);
    }

    public void switchToLeaderBoardScreen(Context context) {
        context.startActivity(new Intent(context, LeaderBoardActivity.class));
    }
}
