package fall2018.csc2017.GameCentre;

public class ConnectFourScore implements Score {
    /**
     * A board object
     */
    private Board board;

    /**
     * Calculates the score for Connect4
     */
    ConnectFourScore() {

    }

    /**
     * Sets the board for the game to the given board
     *
     * @param board the new board to set to
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Calculates the user score depending on the size of
     * the board and the number of moves executed by the user
     *
     * @param numMoves number of moves executed by the user
     * @param size     size of the board
     * @return the score of the user
     */
    public int calculateUserScore(int numMoves, int size) {
        return Math.round(-4 * (numMoves - 7) + 1000) + 50 * size;
    }

    /**
     * Updates the user score, given the user object and the
     * new highscore set by the user
     *
     * @param user         the user setting the highscore and playing
     *                     the current game
     * @param newHighScore the highscore set by the user
     */
    public void updateUserScore(User user, int newHighScore) {

    }
}
