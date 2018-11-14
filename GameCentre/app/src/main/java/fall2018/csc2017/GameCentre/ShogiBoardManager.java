package fall2018.csc2017.GameCentre;

public class ShogiBoardManager extends BoardManager {

    /**
     * The board being managed.
     */
    private Board board;


    public ShogiBoardManager(Board board) {
        super(board);
    }

    public ShogiBoardManager(int size) {
        super(size);
    }

    public boolean puzzleSolved() {
        return true;
    }

    public boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        return board.getTile(row, col).getId() == board.numTiles();
    };

    public void touchMove(int position) {
        if (isValidTap(position)) {
            int row1 = position / Board.NUM_COLS;
            int col = position % Board.NUM_COLS;
        }
    };
}
