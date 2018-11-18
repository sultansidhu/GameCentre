package fall2018.csc2017.GameCentre;

import android.widget.Toast;

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
        return getBoard().numBlacks() <= 1 || getBoard().numReds() <= 1;
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
    }

    /**
     * Overloaded isValidTap with 2 parameters
     */
    public boolean isValidTap(int fromTile, int toTile) {
        if (inSameRow(fromTile, toTile) && !tileBlockingRow(fromTile, toTile)) {
            return true;
        }
        else return inSameCol(fromTile, toTile) && !tileBlockingCol(fromTile, toTile);
    }

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

    /**
     * Overloaded touchMove with 2 parameters.
     */
    public void touchMove(int fromTile, int toTile) {
        if (isValidTap(fromTile, toTile)) {
            board.swapTiles(fromTile/7, fromTile%7, toTile/7, toTile%7);
            int left = checkCapturedLeft(toTile);
            for (int i = 1; i<= left; i++) {
                board.setTileBackground(toTile/7, toTile%7 - i, R.drawable.tile_25);
            }
            int right = checkCapturedRight(toTile);
            for (int i = 1; i <= right; i++) {
                board.setTileBackground(toTile/7, toTile%7 + i, R.drawable.tile_25);
            }
            int up = checkCapturedUp(toTile);
            for (int i = 1; i<= up; i++) {
                board.setTileBackground(toTile/7 - i, toTile%7, R.drawable.tile_25);
            }
            int down = checkCapturedDown(toTile);
            for (int i = 1; i<= down; i++) {
                board.setTileBackground(toTile/7 + i, toTile%7, R.drawable.tile_25);
            }

            Toast.makeText(GlobalApplication.getAppContext(), "Player "+board.getCurrPlayer() + "'s turn", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isBlack(int row, int col) {
        if (row > 6 || row < 0 || col > 6 || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.black;
    }
    private boolean isRed(int row, int col) {
        if (row > 6 || row < 0 || col > 6 || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.red;
    }

    private int checkCapturedDown(int toTile) {
        int numCap = 0;
        int nextRow = toTile/7 + 1;
        if (isBlack(toTile/7, toTile%7)) {
            while (isRed(nextRow, toTile%7)) {
                numCap++;
                nextRow++;
            }
            if (isBlack(nextRow, toTile%7) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        else if (isRed(toTile/7, toTile%7)) {
            while (isBlack(nextRow, toTile%7)) {
                numCap++;
                nextRow++;
            }
            if (isRed(nextRow, toTile%7) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        return 0;
    }

    private int checkCapturedUp(int toTile) {
        int numCap = 0;
        int nextRow = toTile/7 - 1;
        if (isBlack(toTile/7, toTile%7)) {
            while (isRed(nextRow, toTile%7)) {
                numCap++;
                nextRow--;
            }
            if (isBlack(nextRow, toTile%7) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        else if (isRed(toTile/7, toTile%7)) {
            while (isBlack(nextRow, toTile%7)) {
                numCap++;
                nextRow--;
            }
            if (isRed(nextRow, toTile%7) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        return 0;
    }

    private int checkCapturedRight(int toTile) {
        int numCap = 0;
        int nextCol = toTile%7 + 1;
        if (isBlack(toTile/7, toTile%7)) {
            while (isRed(toTile/7, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isBlack(toTile/7, nextCol) && numCap > 0) {
                System.out.println("CAPTURED: " + numCap);
                return numCap;
            } else { return 0; }
        }
        else if (isRed(toTile/7, toTile%7)) {
            while (isBlack(toTile/7, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isRed(toTile/7, nextCol) && numCap > 0) {
                System.out.println("CAPTURED: " + numCap);
                return numCap;
            } else { return 0; }
        }
        return 0;
    }

    private int checkCapturedLeft(int toTile) {
        int numCap = 0;
        int nextCol = toTile%7 - 1;
        if (isBlack(toTile/7, toTile%7)) {
            while (isRed(toTile/7, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isBlack(toTile/7, nextCol) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        else if (isRed(toTile/7, toTile%7)) {
            while (isBlack(toTile/7, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isRed(toTile/7, nextCol) && numCap > 0) {
                return numCap;
            } else { return 0; }
        }
        return 0;
    }

    public boolean inSameRow(int fromTile, int toTile) {
        return fromTile/7 == toTile/7;
    }

    public boolean inSameCol(int fromTile, int toTile) {
        return fromTile%7 == toTile%7;
    }

    public boolean tileBlockingRow(int fromTile, int toTile)
    {
        int row = fromTile/7;
        int start = fromTile%7 < toTile%7 ? fromTile%7 : toTile%7;
        int end = fromTile%7 > toTile%7 ? fromTile%7 : toTile%7;
        int col = start + 1;
        while (col < end) {
            if (getBoard().getTile(row, col).getBackground() != R.drawable.tile_25) {
                return true;
            }
            col++;
        }
        return false;
    }

    public boolean tileBlockingCol(int fromTile, int toTile) {
        int col = fromTile%7;
        int start = fromTile/7 < toTile/7 ? fromTile/7 : toTile/7;
        int end = fromTile/7 > toTile/7 ? fromTile/7 : toTile/7;
        int row = start + 1;
        while (row < end)
        {
            if (getBoard().getTile(row, col).getBackground() != R.drawable.tile_25)
            {
                return true;
            }
            row++;
        }
        return false;
    }
}
