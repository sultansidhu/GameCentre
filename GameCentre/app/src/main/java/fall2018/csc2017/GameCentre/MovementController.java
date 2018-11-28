package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;

class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    private String username = new LoginManager().getPersonLoggedIn();

    private FileManager fm = new FileManager();

    private int gameIndex = 0;


    /**
     * Username of current player.
     */
    //static String username = null;
    MovementController() {
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

//    /**
//     * Processes a tap for the user.
//     * @param context of the application.
//     * @param position that the user tapped.
//     */
//    void processTapMovement(Context context, ConnectFourBoardManager boardManager, int position) {
//        if (!boardManager.gameOver){
//            if (boardManager.isValidTap(position)) {
//                boardManager.touchMove(position);
//                if (boardManager.puzzleSolved(position)) {
//                   // Toast.makeText(context, winner + " Wins!", Toast.LENGTH_LONG).show();
//                    boardManager.setGameOver();
//                }
//
//
//            } else { Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show(); }
//        } else {
//            boardManager.makeToast("The game is over! Please start a new game!");
//        }
//    }

//    /**
//     * Overloaded processTapmovement parameters for shogi
//     * @param context context
//     * @param boardManager boardmanager
//     * @param fromTile subject tile
//     * @param toTile target tile
//     */

//    void processTapMovement(Context context, ShogiBoardManager boardManager, int fromTile, int toTile) {
//        if (boardManager.isValidTap(fromTile, toTile)) {
//            boardManager.touchMove(fromTile, toTile);
//            if (boardManager.puzzleSolved()) {
//                if(boardManager.getBoard().numBlacks() <= 1)
//                    Toast.makeText(context, "BLACK WINS!", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context, "RED WINS!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void checkSolved(Context context, int gameIndex) {
        if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            ScoreboardActivity sc = new ScoreboardActivity();
            sc.updateUserHighScore(username, gameIndex);
            switchToScoreboardScreen(context);
        }
    }


    /*
    Switches to the scoreboard screen if a game is won
     */
    public void switchToScoreboardScreen(Context context)
    {
        Intent tmp = new Intent(context, ScoreboardActivity.class);
        context.startActivity(tmp);
    }
}
