package fall2018.csc2017.GameCentre;

import java.util.HashMap;

public class ConnectFourScoreCalc {
    private FileManager fm = new FileManager();

    public ConnectFourScoreCalc(){

    }
    public int calculateUserScore(User user){

        int numMoves = user.getNumMoves(2);
        System.out.println("Number of moves before game over: "+numMoves);
        return (int) Math.round(-1*(Math.pow(0.45*(numMoves-8), 2)) + 200);
    }

}
