package fall2018.csc2017.GameCentre;

public class BoardManagerFactory {

    public BoardManager getBoardManager(int gameToSwitchTo, int size)
    {
        switch(gameToSwitchTo)
        {
            case 0:
                return new SlidingBoardManager(size);
            case 1:
                return new ShogiBoardManager(size);
            case 2:
                return new ConnectFourBoardManager(size);
        }
        return null;

    }

    public BoardManager getBoardManager(int gameToSwitchTo, Board board)
    {
        switch(gameToSwitchTo)
        {
            case 0:
                return new SlidingBoardManager(board);
            case 1:
                return new ShogiBoardManager(board);
            case 2:
                return new ConnectFourBoardManager(board);
        }
        return null;
    }
}
