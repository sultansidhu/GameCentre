package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface BoardManager
{
    /**
     * Return the current board.
     *
     * @return a board
     */
    Board getBoard();

    boolean puzzleSolved();

    boolean isValidTap(int position);

    void touchMove(int position);

    void setOpponent(String opponent);

    boolean getChanged();


}
