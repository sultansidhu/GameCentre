package fall2018.csc2017.GameCentre;

import java.util.HashMap;

class ShogiScore implements Score
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
    // TODO: remove static here
    public ShogiScore() {
        fm = new FileManager(GlobalApplication.getAppContext());
        lm = new LoginManager(GlobalApplication.getAppContext());
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
        int pieceDiff = (int)arrayMoveCountandBoard[1];
        return generateScore(numMoves, pieceDiff);
    }
    public int getPieceDiff(Board board){
        int pieceDiff = board.numBlacks() - board.numReds();
        if (pieceDiff < 0) {
            pieceDiff *= -1;
        }
        return pieceDiff;
    }

    public int generateScore(int numMoves, int pieceDiff){

        if(numMoves < 14)
            return 860 + (20 * pieceDiff);

        int score = Math.round(-1*numMoves + 774 + (20 * pieceDiff));

        if(score < 0)
            score = 0;

        return score;
    }

    public void updateUserScore(User user, int newHighScore) {

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
            array[1] = getPieceDiff(user.getGameStack(1).peek());
        }
        return array;
    }
}
