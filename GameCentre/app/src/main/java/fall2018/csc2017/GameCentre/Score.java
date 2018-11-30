package fall2018.csc2017.GameCentre;

public interface Score
{

    int calculateUserScore(int numMoves, int size);

    void updateUserScore(User user, int newHighScore);

    void setBoard(Board board);

}
