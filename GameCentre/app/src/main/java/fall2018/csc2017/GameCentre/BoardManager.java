package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BoardManager {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */

    public BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Manage a new shuffled board.
     *
     * @param size: the size of the board being constructed
     *              within the board manager.
     */
    public BoardManager(int size) {

    }
    /**
     * Return the current board.
     *
     * @return a board
     */
    public abstract Board getBoard();

    public abstract boolean puzzleSolved();

    public abstract boolean isValidTap(int position);

    public abstract void touchMove(int position);


}
