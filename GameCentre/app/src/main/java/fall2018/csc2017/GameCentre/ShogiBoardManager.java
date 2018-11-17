package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShogiBoardManager extends BoardManager
{
    /**
     * The board being managed.
     */
    private Board board;

    /**
     * represents the current player.
     */
    private int currPlayer;

    /**
     * This constructor takes a board object and sets this class' board attribute object
     * equal to it
     * @param board, a Board object representing the board
     * @throws null
     */

    public ShogiBoardManager(Board board) {
        super(board);
        this.board = board;
    }

    /**
     * This constructor takes an int size and creates the board of size size
     * @param size, an integer representing the size of the board
     * @throws null
     */

    public ShogiBoardManager(int size) {
        super(size);
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;
        final int numTiles = size*size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            Tile tile = new Tile(tileNum);
            if (tileNum < size) {
                tile.setBackground(R.drawable.black);
            }
            else if (tileNum > numTiles-size-1) {
                tile.setBackground(R.drawable.red);
            }
            else {
                tile.setBackground(R.drawable.tile_25);
            }
            tiles.add(tile);
        }
        this.board = new Board(tiles);
        currPlayer = 1;
    }

    /**
     * This method returns the board attribute of this board manager
     * @return Board the board object attribute of this class
     * @throws null
     */

    public Board getBoard() { return this.board; }

    /**
     * This method returns whether or not the board has been solved
     * @return boolean, true if the puzzle has been solved, false otherwise
     * @throws null
     */

    public boolean puzzleSolved() {
        return false;
    }

    /**
     * This method returns whether or not the tap made by the user is a valid tap
     * @return boolean, true if the puzzle has been solved, false otherwise
     * @throws null
     */

    public boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        return false;
    };

    /**
     * This method is a stub
     * @param position: an nt representing the position touched
     * @return null
     * @throws null
     */

    public void touchMove(int position) {
        if (isValidTap(position)) {
            int row1 = position / Board.NUM_COLS;
            int col = position % Board.NUM_COLS;
        }
    }

    public int getCurrPlayer() {
       return currPlayer;
    }

    public void setCurrPlayer(int currPlayer) { this.currPlayer = currPlayer; }
}
