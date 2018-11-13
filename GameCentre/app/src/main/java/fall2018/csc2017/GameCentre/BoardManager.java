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

    BoardManager(Board board) {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
    }

    /**
     * Manage a new shuffled board.
     *
     * @param size: the size of the board being constructed
     *              within the board manager.
     */
    BoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles-1; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Tile testTile = new Tile(24);
        testTile.setId(size*size);
        tiles.add(testTile);

        Collections.shuffle(tiles);
        this.board = new Board(tiles);

    }
    /**
     * Return the current board.
     *
     * @return a board
     */
    Board getBoard() {
        return board;
    }

    public abstract boolean puzzleSolved();

    public abstract boolean isValidTap(int position);

    public abstract void touchMove(int position);


}
