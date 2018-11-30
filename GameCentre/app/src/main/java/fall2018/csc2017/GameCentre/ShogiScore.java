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
    public ShogiScore() {
        fm = new FileManager();
        lm = new LoginManager();
    }

    /**
     * Calculates and returns and integer representing the score of User user
     * @param numMoves, an int representing the number of moves made by this user
     * @param size, an integer representing the size of the played board
     * @return score, an integer representing the score of this user
     * @throws null
     */

    public int calculateUserScore(int numMoves, int size) {
        return generateScore(numMoves, getPieceDiff(board)) + (50*size);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public int getPieceDiff(Board board){
        return Math.abs(board.numBlacks() - board.numReds());
    }

    public int generateScore(int numMoves, int pieceDiff){
        if(numMoves < 14)
            return 860 + (20 * pieceDiff);

        int score = Math.round(-1*numMoves + 774) + (20 * pieceDiff);

        if(score < 0)
            score = 0;

        return score;
    }
}
