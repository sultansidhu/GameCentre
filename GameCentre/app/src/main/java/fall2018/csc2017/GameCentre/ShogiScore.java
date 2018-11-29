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
     * A Board object
     */
    private Board board;

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
     * @param numMoves, an int representing the number of moves made by this user
     * @param size, an integer representing the size of the played board
     * @return score, an integer representing the score of this user
     * @throws null
     */

    public int calculateUserScore(int numMoves, int size)
    {
        return generateScore(numMoves, getPieceDiff(board)) + (50*size);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void updateUserScore(User user, int newHighScore){

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

        int score = Math.round(-1*numMoves + 774) + (20 * pieceDiff);

        if(score < 0)
            score = 0;

        return score;
    }

//    /**
//     * A helper method that obtains and returns the number of moves taken by User user and the
//     * last board
//     * @param username, a String representing the username of the user who just won the game
//     * @return An array of type Object, holding an integer (numOfMoves) and a Board
//     */
//
//    public Object[] getMovesCountAndBoard(String username)
//    {
//        Object[] array = new Object[2];
//        User user = fm.getUser(username);
//        if(!(lm.getPersonLoggedIn().equals(user.getUsername())))
//        {
//            HashMap<String, User> hm = fm.readObject();
//            assert hm != null;
//            User user1 = hm.get(lm.getPersonLoggedIn()); //Gets the person logged in
//            array[0] = user1.getNumMoves(1) - 1; //p2 requires 1 more move to win
//            array[1] = user1.getGameStack(1).peek();
//        }
//        else {
//            array[0] = user.getNumMoves(1);
//            array[1] = getPieceDiff(user.getGameStack(1).peek());
//        }
//        return array;
//    }
}
