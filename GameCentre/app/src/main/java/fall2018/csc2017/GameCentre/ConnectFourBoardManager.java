package fall2018.csc2017.GameCentre;

public class ConnectFourBoardManager extends BoardManager{


    /**
     * The board being managed.
     */
    private Board board;


    public ConnectFourBoardManager(Board board) {
        super(board);
    }

    public ConnectFourBoardManager(int size) {
        super(size);
    }

    public Board getBoard() { return this.board; }

    public boolean puzzleSolved() {
        return true;
    }

    public boolean isValidTap(int position) {
        return true;
    };

    public void touchMove(int position) {

    };
}
