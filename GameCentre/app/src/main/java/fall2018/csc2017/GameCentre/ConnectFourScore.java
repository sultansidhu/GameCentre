package fall2018.csc2017.GameCentre;

import java.util.HashMap;

public class ConnectFourScore implements Score {
//    private FileManager fm = new FileManager();
    private int gameIndex = 2;

    /**
     * A board object
     */
    private Board board;


    public void setBoard(Board board){
        this.board = board;
    }

    public int calculateUserScore(int numMoves, int size){
        return Math.round(-4*(numMoves-7) + 1000) + 50*size;
    }
}
