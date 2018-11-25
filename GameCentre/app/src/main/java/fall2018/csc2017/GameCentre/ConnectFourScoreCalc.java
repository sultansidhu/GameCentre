package fall2018.csc2017.GameCentre;

import java.util.HashMap;

public class ConnectFourScoreCalc {
    private FileManager fm = new FileManager();

    public ConnectFourScoreCalc(){

    }
    public int calculateUserScore(User user){
        LoginManager lm = new LoginManager();
        int numMoves;
        if(!(lm.getPersonLoggedIn().equals(user.getUsername()))){
            HashMap<String, User> hm = fm.readObject();
            assert hm != null;
            User user1 = hm.get(lm.getPersonLoggedIn());//Gets the person logged in
            System.out.println("The username seen by c4scorecalc is: "+user1.getUsername());
            numMoves = user1.getNumMoves(2) - 2;//p2 requires 1 more move to win
        }
        else {
            numMoves = user.getNumMoves(2) - 1;
        }
        System.out.println("Number of moves before game over: "+numMoves);
        return Math.round(-4*(numMoves-7) + 1000);
    }

}
