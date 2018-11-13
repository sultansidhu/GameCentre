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
        return true;
    };

    public void touchMove(int position) {

    };
}
