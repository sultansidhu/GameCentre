package fall2018.csc2017.GameCentre;

import java.util.HashMap;


class SlidingScoreCalc
{
    /**
     * The FileManager object used to access the HashMap and serialized file on disk
     */
    private FileManager fm;

    /**
     * The constructor for this class, which intiailizes the fm object
     */
    public SlidingScoreCalc()
    {
        fm = new FileManager();
    }

    /**
     * Calculate and return the score of User user
     * @param user, a User object representign the current player
     * @return score, an integer representing the score of this user
     */

    public int calculateUserScore(User user)
    {
        double time = user.getTotalTime();
        int numMoves = user.getNumMoves(0);
        if (time == 0)
            return 0;
        else {
            double score = 2000 * ((1 / numMoves));
            return (int)score;
        }
    }
}
