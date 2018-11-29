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
     * The constructor for this class, which intiailizes the fm object
     */
    // TODO: remove static here
    public SlidingScore()
    {
        fm = new FileManager(GlobalApplication.getAppContext());
    }

    /**
     * Calculate and return the score of User user
     * @param user, a User object representign the current player
     * @return score, an integer representing the score of this user
     */

    public int calculateUserScore(User user)
    {
        int numMoves = user.getNumMoves(0);
        System.out.println("TOOK MOVES::::"+numMoves);
        System.out.println(2000/numMoves);
        double score = 2000/numMoves;
        return (int)score;
    }

    public void updateUserScore(User user, int newHighScore) {

    }
}
