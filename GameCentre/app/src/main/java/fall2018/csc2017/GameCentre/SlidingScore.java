package fall2018.csc2017.GameCentre;

import java.sql.SQLOutput;
import java.util.HashMap;


class SlidingScore implements Score
{
    /**
     * The FileManager object used to access the HashMap and serialized file on disk
     */
    private FileManager fm;

    /**
     * A board object
     */
    private Board board;

    /**
     * The constructor for this class, which intiailizes the fm object
     */
    // TODO: remove static here
    public SlidingScore()
    {
        fm = new FileManager();
    }

    /**
     * Calculate and return the score of User user
     * @param numMoves, an integer representing the number of moves made by this user
     * @param size, an integer representing the size of the board
     * @return score, an integer representing the score of this user
     */

    public int calculateUserScore(int numMoves, int size)
    {
        double score = 2000/numMoves;
        return (int)score + (50 * size);
    }

    public void setBoard(Board board){
        this.board = board;
    }


    public void updateUserScore(User user, int newHighScore) {

    }
}
