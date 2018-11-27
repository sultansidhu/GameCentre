package fall2018.csc2017.GameCentre;

import java.util.HashMap;

class ShogiScoreCalc implements Calculate
{
    /**
    A FileManager object used for accessing the HashMap from the serialized file
     */
    private FileManager fm;
    /**
    A LoginManager object used for authentication and getting the user logged in
     */
    private LoginManager lm;

    /**
     * The constructor of this class, which initializes the FileManager and LoginManager attributes
     */
    public ShogiScoreCalc()
    {
        fm = new FileManager();
        lm = new LoginManager();
    }

    /**
     * Calculates and returns and integer representing the score of User user
     * @param user, a User object whose score must be calculated
     * @return score, an integer representing the score of this user
     * @throws null
     */

    public int calculateUserScore(User user)
    {
        Object[] arrayMoveCountandBoard = getMovesCountAndBoard(user);
        int numMoves = (int)arrayMoveCountandBoard[0];
        Board board = (Board)arrayMoveCountandBoard[1];

        int pieceDiff = board.numBlacks() - board.numReds();
        if (pieceDiff < 0)
            pieceDiff *= -1;

        if(numMoves < 14)
            return 860 + (20 * pieceDiff);

        int score = Math.round(-1*numMoves + 774 + (20 * pieceDiff));

        if(score < 0)
            score = 0;

        return score;
    }

    /**
     * A helper method that obtains and returns the number of moves taken by User user and the
     * last board
     * @param user, a User object representing the user who just won the game
     * @return An array of type Object, holding an integer (numOfMoves) and a Board
     */

    public Object[] getMovesCountAndBoard(User user)
    {
        Object[] array = new Object[2];

        if(!(lm.getPersonLoggedIn().equals(user.getUsername())))
        {
            HashMap<String, User> hm = fm.readObject();
            assert hm != null;
            User user1 = hm.get(lm.getPersonLoggedIn());//Gets the person logged in
            array[0] = user1.getNumMoves(1) - 1;//p2 requires 1 more move to win
            array[1] = user1.getGameStack(1).peek();
        }
        else {
            array[0] = user.getNumMoves(1);
            array[1] = user.getGameStack(1).peek();
        }
        return array;
    }
}
