package fall2018.csc2017.GameCentre;

import java.util.HashMap;

public class ConnectFourScoreCalc {
    private FileManager fm = new FileManager();

    public ConnectFourScoreCalc(){

    }
    public int calculateUserScore(User user){

        int numMoves = user.getNumMoves(2) - 1;
        System.out.println("Number of moves before game over: "+numMoves);
        return Math.round(-4*(numMoves-8) + 300);


    }

}
