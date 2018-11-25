package fall2018.csc2017.GameCentre;

class ShogiScoreCalc {
    private FileManager fm;
    private LoginManager lm;
    public ShogiScoreCalc() {
        fm = new FileManager();
        lm = new LoginManager();
    }

    public int calculateUserScore(User user) {
        int numMoves = user.getNumMoves(1);
        System.out.println("Number of moves until game ended: "+numMoves);
        Board board = user.getGameStack(1).peek();
        int pieceDiff = board.numBlacks() - board.numReds();
        if (pieceDiff < 0){
            pieceDiff *= -1;
        }
//        if (!(lm.getPersonLoggedIn() == user.getUsername())){
//            pieceDiff *= -1; //If the user is not the one logged in, he is player 2
//        }
        System.out.println("TOOK MOVES: "+numMoves);
        if(numMoves < 14){
            return 200 + (20 * pieceDiff);
        }
        int score = Math.round(-1*numMoves + 213 + (20 * pieceDiff));
        if(score < 0)
        {
            score = 0;
        }
        return score;
    }
}
