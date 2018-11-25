package fall2018.csc2017.GameCentre;

public class BoardManagerFactory {

    public BoardManager getBoardManager(int gameIndex, int size)
    {
        switch(gameIndex)
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

    public BoardManager getBoardManager(int gameIndex, Board board)
    {
        switch(gameIndex)
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
