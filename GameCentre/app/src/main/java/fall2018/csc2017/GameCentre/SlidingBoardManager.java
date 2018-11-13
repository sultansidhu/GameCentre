package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingBoardManager extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;


    public SlidingBoardManager(Board board) {
        super(board);
    }

    public SlidingBoardManager(int size) {
        super(size);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        int counter = 1;
        for (Tile tile : board) {
            if (tile.getId() != counter) return false;
            counter++;
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        System.out.println("TouchMove is working!!!!!!");

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();

        if (isValidTap(position))
        {
            Tile above = row == 0 ? null : board.getTile(row - 1, col);
            Tile left = col == 0 ? null : board.getTile(row, col - 1);
            Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

            if (above != null && above.getId() == blankId) board.swapTiles(row, col, row - 1, col);
            else if (left != null && left.getId() == blankId)
                board.swapTiles(row, col, row, col - 1);
            else if (right != null && right.getId() == blankId)
                board.swapTiles(row, col, row, col + 1);
            else board.swapTiles(row, col, row + 1, col);
        }

    }



}
