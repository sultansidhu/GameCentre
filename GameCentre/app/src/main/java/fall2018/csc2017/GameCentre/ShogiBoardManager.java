package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShogiBoardManager extends BoardManager {

    /**
     * The board being managed.
     */
    private Board board;


    public ShogiBoardManager(Board board) {
        super(board);
        this.board = board;
    }

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

    public Board getBoard() { return this.board; }

    public boolean puzzleSolved() {
        return false;
    }

    public boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        return false;
    };

    public void touchMove(int position) {
        if (isValidTap(position)) {
            int row1 = position / Board.NUM_COLS;
            int col = position % Board.NUM_COLS;
        }
    };
}
