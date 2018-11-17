package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager = null;

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
    void processTapMovement(Context context, int position) {

        if (boardManager.isValidTap(position))
        {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else { Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show(); }
    }

    /**
     * Processes a tap for the user.
     * @param context of the application.
     * @param position that the user tapped.
     */
    void processTapMovement(Context context, ConnectFourBoardManager boardManager, int position) {

        if (boardManager.isValidTap(position))
        {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved(position)) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else { Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show(); }
    }

    /**
     * Overloaded processTapmovement parameters for shogi
     * @param context context
     * @param boardManager boardmanager
     * @param fromTile subject tile
     * @param toTile target tile
     */

    void processTapMovement(Context context, ShogiBoardManager boardManager, int fromTile, int toTile) {
        if (boardManager.isValidTap(fromTile, toTile)) {
            boardManager.touchMove(fromTile, toTile);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
