package fall2018.csc2017.GameCentre;

import java.util.HashMap;

class ShogiScoreCalc {
    private FileManager fm;
    private LoginManager lm;
    public ShogiScoreCalc() {
        fm = new FileManager();
        lm = new LoginManager();
    }

    public int calculateUserScore(User user) {
        int numMoves;
        Board board;
        if(!(lm.getPersonLoggedIn().equals(user.getUsername()))){
            HashMap<String, User> hm = fm.readObject();
            assert hm != null;
            User user1 = hm.get(lm.getPersonLoggedIn());//Gets the person logged in
            System.out.println("The username seen by shogiscorecalc is: "+user1.getUsername());
            numMoves = user1.getNumMoves(1) - 1;//p2 requires 1 more move to win
            board = user1.getGameStack(1).peek();
        }
        else {
            numMoves = user.getNumMoves(1);
            board = user.getGameStack(1).peek();
        }
        System.out.println("Number of moves until game ended: "+numMoves);
        int pieceDiff = board.numBlacks() - board.numReds();
        if (pieceDiff < 0){
            pieceDiff *= -1;
        }
        System.out.println("TOOK MOVES: "+numMoves);
        if(numMoves < 14){
            return 860 + (20 * pieceDiff);
        }
        int score = Math.round(-1*numMoves + 774 + (20 * pieceDiff));
        if(score < 0){
            score = 0;
        }
        return score;
    }
}
