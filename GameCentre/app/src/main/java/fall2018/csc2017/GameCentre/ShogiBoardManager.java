package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class  ShogiBoardManager implements BoardManager
{
    /**
     * The board being managed.
     */
    private Board board;

    //private int Board.NUM_COLS;

    /**
     * This constructor takes a board object and sets this class' board attribute object
     * equal to it
     * @param board, a Board object representing the board
     */

    private String opponent;

    private int tileSelected = -1;

    private int tileOwner = -1;

    private boolean changed = false;


    public ShogiBoardManager(Board board)
    {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
        //Board.NUM_COLS = board.getTiles().length;
    }

    /**
     * This constructor takes an int size and creates the board of size size
     * @param size, an integer representing the size of the board
     */

    public ShogiBoardManager(int size) {
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
     */

    public Board getBoard() { return this.board; }

    /**
     * This method returns whether or not the board has been solved
     * @return boolean, true if the puzzle has been solved, false otherwise
     */

    public boolean puzzleSolved() {
        return getBoard().numBlacks() <= 1 || getBoard().numReds() <= 1;
    }

    /**
     * This method returns whether or not the tap made by the user is a valid tap
     * @return boolean, true if the puzzle has been solved, false otherwise
     */

    public boolean isValidTap(int position) {
        Tile currTile = getBoard().getTile(position/Board.NUM_ROWS, position%Board.NUM_ROWS);
        tileOwner = getTileOwner(currTile);
        if (isTurn()) {
            return setTileToMove(position, currTile);
        }
        else if (tileOwner == 0 && tileSelected != -1) {
            int fromTile = tileSelected;
            int toTile = position;
            if ((inSameRow(fromTile, toTile) && !tileBlockingRow(fromTile, toTile) && isWhite(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS))
                    || inSameCol(fromTile, toTile) && !tileBlockingCol(fromTile, toTile) && isWhite(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
                return true;
            }
            else {
                resetTileSelected();
                return false;
            }
        }
        return false;
    }


    public void resetTileSelected() {
        tileSelected = -1;
    }

    public void switchPlayer() {
        getBoard().setCurrPlayer(3 - getBoard().getCurrPlayer());
        //TODO: move this to view
//        Toast.makeText(GlobalApplication.getAppContext(), "Player " + getBoard().getCurrPlayer() + "'s turn", Toast.LENGTH_SHORT).show();
    }

    public int getTileOwner(Tile currTile) {
        if (currTile.getBackground() == R.drawable.black) { return 1; }
        else if (currTile.getBackground() == R.drawable.red) {return 2; }
        else { return 0; }
    }

    public boolean isTurn() {
        return tileOwner == getBoard().getCurrPlayer();
    }

    public boolean setTileToMove(int position, Tile currTile) {
        if (tileSelected == -1
                || getBoard().getTile(
                tileSelected/Board.NUM_ROWS, tileSelected%Board.NUM_ROWS).getBackground()
                == currTile.getBackground()) {
            tileSelected = position;
            return true;
        }
        else {
            return false;
        }
    }

//    /**
//     * Overloaded isValidTap with 2 parameters
//     */
//    public boolean isValidTap(int fromTile, int toTile) {
//        if (inSameRow(fromTile, toTile) && !tileBlockingRow(fromTile, toTile) && isWhite(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
//            return true;
//        }
//        else return inSameCol(fromTile, toTile) && !tileBlockingCol(fromTile, toTile) && isWhite(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS);
//    }

    /**
     * This method is a stub
     * @param position: an nt representing the position touched
     */

    public void touchMove(int position) {
        int fromTile = tileSelected;
        int toTile = position;
        if (isValidTap(toTile) && fromTile != -1 && tileOwner == 0) {
            board.swapTiles(fromTile/Board.NUM_COLS, fromTile%Board.NUM_COLS, toTile/Board.NUM_COLS, toTile%Board.NUM_COLS);
            int left = checkCapturedLeft(toTile);
            for (int i = 1; i<= left; i++) {
                board.setTileBackground(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS - i, R.drawable.tile_25);
            }
            int right = checkCapturedRight(toTile);
            for (int i = 1; i <= right; i++) {
                board.setTileBackground(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS + i, R.drawable.tile_25);
            }
            int up = checkCapturedUp(toTile);
            for (int i = 1; i<= up; i++) {
                board.setTileBackground(toTile/Board.NUM_COLS - i, toTile%Board.NUM_COLS, R.drawable.tile_25);
            }
            int down = checkCapturedDown(toTile);
            for (int i = 1; i<= down; i++) {
                board.setTileBackground(toTile/Board.NUM_COLS + i, toTile%Board.NUM_COLS, R.drawable.tile_25);
            }
            setChanged(true);
            resetTileSelected();
            switchPlayer();
        }
        else { setChanged(false); }
    }

//    /**
//     * Overloaded touchMove with 2 parameters.
//     */
//    void touchMove(int fromTile, int toTile) {
//        if (isValidTap(fromTile, toTile)) {
//            board.swapTiles(fromTile/Board.NUM_COLS, fromTile%Board.NUM_COLS, toTile/Board.NUM_COLS, toTile%Board.NUM_COLS);
//            int left = checkCapturedLeft(toTile);
//            for (int i = 1; i<= left; i++) {
//                board.setTileBackground(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS - i, R.drawable.tile_25);
//            }
//            int right = checkCapturedRight(toTile);
//            for (int i = 1; i <= right; i++) {
//                board.setTileBackground(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS + i, R.drawable.tile_25);
//            }
//            int up = checkCapturedUp(toTile);
//            for (int i = 1; i<= up; i++) {
//                board.setTileBackground(toTile/Board.NUM_COLS - i, toTile%Board.NUM_COLS, R.drawable.tile_25);
//            }
//            int down = checkCapturedDown(toTile);
//            for (int i = 1; i<= down; i++) {
//                board.setTileBackground(toTile/Board.NUM_COLS + i, toTile%Board.NUM_COLS, R.drawable.tile_25);
//            }
//
//            //Toast.makeText(GlobalApplication.getAppContext(), "Player "+ (3 - board.getCurrPlayer()) + "'s turn", Toast.LENGTH_SHORT).show();
//            //TODO: I JUST PUT 3- TO MAKE IT SWAP PROPERLY....PROBABLY SHOULD FIND A  BETTER SOLUTION TO THIS!
//
//        }
//    }

    boolean isBlack(int row, int col) {
        if (row >= Board.NUM_COLS || row < 0 || col >= Board.NUM_COLS || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.black;
    }
    boolean isRed(int row, int col) {
        if (row >= Board.NUM_COLS || row < 0 || col >= Board.NUM_COLS || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.red;
    }

    private boolean isWhite(int row, int col) {
        return !isBlack(row, col) && !isRed(row, col);
    }

    private int checkCapturedDown(int toTile) {
        int numCap = 0;
        int nextRow = toTile/Board.NUM_COLS + 1;
        if (isBlack(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isRed(nextRow, toTile%Board.NUM_COLS)) {
                numCap++;
                nextRow++;
            }
            if (isBlack(nextRow, toTile%Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        else if (isRed(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isBlack(nextRow, toTile%Board.NUM_COLS)) {
                numCap++;
                nextRow++;
            }
            if (isRed(nextRow, toTile%Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    public int checkCapturedUp(int toTile) {
        int numCap = 0;
        int nextRow = toTile/Board.NUM_COLS - 1;
        if (isBlack(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isRed(nextRow, toTile%Board.NUM_COLS)) {
                numCap++;
                nextRow--;
            }
            if (isBlack(nextRow, toTile%Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        else if (isRed(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isBlack(nextRow, toTile%Board.NUM_COLS)) {
                numCap++;
                nextRow--;
            }
            if (isRed(nextRow, toTile%Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    private int checkCapturedRight(int toTile) {
        int numCap = 0;
        int nextCol = toTile%Board.NUM_COLS + 1;
        if (isBlack(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isRed(toTile/Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isBlack(toTile/Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        else if (isRed(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isBlack(toTile/Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isRed(toTile/Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    private int checkCapturedLeft(int toTile) {
        int numCap = 0;
        int nextCol = toTile%Board.NUM_COLS - 1;
        if (isBlack(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isRed(toTile/Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isBlack(toTile/Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        else if (isRed(toTile/Board.NUM_COLS, toTile%Board.NUM_COLS)) {
            while (isBlack(toTile/Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isRed(toTile/Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    boolean inSameRow(int fromTile, int toTile) {
        return fromTile/Board.NUM_COLS == toTile/Board.NUM_COLS;
    }

    boolean inSameCol(int fromTile, int toTile) {
        return fromTile%Board.NUM_COLS == toTile%Board.NUM_COLS;
    }

    boolean tileBlockingRow(int fromTile, int toTile)
    {
        int row = fromTile/Board.NUM_COLS;
        int start = fromTile%Board.NUM_COLS < toTile%Board.NUM_COLS ? fromTile%Board.NUM_COLS : toTile%Board.NUM_COLS;
        int end = fromTile%Board.NUM_COLS > toTile%Board.NUM_COLS ? fromTile%Board.NUM_COLS : toTile%Board.NUM_COLS;
        int col = start + 1;
        while (col < end) {
            if (getBoard().getTile(row, col).getBackground() != R.drawable.tile_25) {
                return true;
            }
            col++;
        }
        return false;
    }

    boolean tileBlockingCol(int fromTile, int toTile) {
        int col = fromTile%Board.NUM_COLS;
        int start = fromTile/Board.NUM_COLS < toTile/Board.NUM_COLS ? fromTile/Board.NUM_COLS : toTile/Board.NUM_COLS;
        int end = fromTile/Board.NUM_COLS > toTile/Board.NUM_COLS ? fromTile/Board.NUM_COLS : toTile/Board.NUM_COLS;
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

    public void setTileSelected(int tile) { this.tileSelected = tile; }

    public void setChanged(boolean changed) { this.changed = changed; }

    public boolean getChanged() { return changed; }


}
