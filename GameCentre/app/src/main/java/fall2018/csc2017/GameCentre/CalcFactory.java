/** ======================================================================
 * This class is a Factory that instantiates different BoardManagers
 * based on the input
 * File Name: BoardManagerFactory.java
 * Authors: Group 0647
 * Date: November 25, 2018
 ======================================================================== */
package fall2018.csc2017.GameCentre;

public class CalcFactory
{

    /***
     * Returns a new BoardManager for different games, based on size and gameIndex
     * @param gameIndex, an integer representing which game to initialize a BoardManager for
     *                   (0 --> SlidingTiles    1 --> HasamiShogi   2 --> Connect4)
     * @return an object of a class implementing Calculate, depending on gameIndex
     * @throws null
     */
    public Calculate getScoreCalc(int gameIndex)
    {
        switch(gameIndex)
        {
            case 0:
                return new SlidingScoreCalc();
            case 1:
                return new ShogiScoreCalc();
            case 2:
                return new ConnectFourScoreCalc();
        }
        return null;

    }

}
