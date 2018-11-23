package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface BoardManager {
    /**
     * Return the current board.
     *
     * @return a board
     */
    public abstract Board getBoard();

    public abstract boolean puzzleSolved();

    public abstract boolean isValidTap(int position);

    public abstract void touchMove(int position);

    public abstract void setOpponent(String opponent);


}
