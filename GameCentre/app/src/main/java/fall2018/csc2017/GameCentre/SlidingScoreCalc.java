package fall2018.csc2017.GameCentre;

import java.util.HashMap;

class SlidingScoreCalc
{
    private FileManager fm;

    public SlidingScoreCalc(){
        fm = new FileManager();
    }
    public int calculateUserScore(User user)
    {

        double time = user.getTotalTime();
        int numMoves = user.getNumMoves(0);
        if (time == 0)
        {
            return 0;
        } else {
            System.out.println("THE TIME IS LOL: "+time);
            return (int)(2000 * ((1 / numMoves) + 10000 / (time)));
        }
    }
}
