package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The username of the person logged in
     */
    private String username = new LoginManager().getPersonLoggedIn();
    /**
     * The file manager, to assist in reading and
     * saving the .ser files
     */
    private FileManager fm = new FileManager();

    /**
     * Default constructor for the Movement Controller
     */
    MovementController(Context context) {
        // TODO: FIX THE CONTEXT BOII WITHOUT BREAKING THIS SHIT
    }

    /**
     * Sets the boardManager of MovementController.
     *
     * @param boardManager is type BoardManager.
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes a tap for the user.
     *
     * @param context  of the application.
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
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the board is solved, and thus whether the game is over
     *
     * @param context   the current context
     * @param gameIndex the identity index of the game
     */
    private void checkSolved(Context context, int gameIndex) {
        if (boardManager.puzzleSolved()) {
            String winner = getWinnerUsername(gameIndex);
            User user = fm.getUser(winner);
            if (winner.equals("Guest")) {
                Toast.makeText(context, "Guest wins!", Toast.LENGTH_SHORT).show();
                switchToLeaderBoardScreen(context);
            } else {
            }
            else {
                Toast.makeText(context, username + " wins!", Toast.LENGTH_SHORT).show();
                ScoreboardActivity sc = new ScoreboardActivity();
                ScoreboardController scon = new ScoreboardController();
                int result = scon.generateUserScore(winner, gameIndex);
                switchToScoreboardScreen(context, result, winner);
            }
        }
    }

    /**
     * Returns the username of the winning player of the specified game
     *
     * @param gameIndex the identity index of the game
     * @return the winner player's username
     */
    private String getWinnerUsername(int gameIndex) {
        // get the winning player (current player in the bm)
        int playerNumber = 3 - boardManager.getBoard().getCurrPlayer();
        if (gameIndex != 0 && playerNumber == 2) {
            return fm.getUser(username).getOpponents().get(gameIndex);
        }
        return username;
    }

    /**
     * Switches to the scoreboard screen if a game is won
     */
    private void switchToScoreboardScreen(Context context, int result, String username) {
        Intent intent = new Intent(context, ScoreboardActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    /**
     * Switches to the leader-board screen after the game is won by
     * the guest
     *
     * @param context the current context
     */
    private void switchToLeaderBoardScreen(Context context) {
        context.startActivity(new Intent(context, LeaderBoardActivity.class));
    }
}
