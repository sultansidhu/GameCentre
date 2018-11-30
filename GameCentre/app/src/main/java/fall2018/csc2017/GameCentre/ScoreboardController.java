package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class ScoreboardController {
    private FileManager fm;
    //private LoginManager lm;
    private ScoreFactory scoreFactory = new ScoreFactory();

    public ScoreboardController(){
        this.fm = new FileManager();
        //this.lm = new LoginManager();

    }
    /**
     * Calculates and updates the user's score, and returns a //TODO: This method does too much stuff
     */
    public int updateUserHighScore(String username, int gameIndex)
    {
        int[] result = new int[4];
        //////////////////////////
        int newScore = generateUserScore(username, gameIndex);
        ////////////////////////
        for(int i = 0; i < 3; i++){
            //result[i] = user.getMaxScore(i);
        }
        result[3] = newScore;
        return newScore;
    }

    private int generateUserScore(String username, int gameIndex) {
        int newScore;
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        Score s1 = scoreFactory.getScore(gameIndex);
        Board userBoard = user.getGameStack(gameIndex).peek();
        s1.setBoard(userBoard);
        newScore = s1.calculateUserScore(user.getNumMoves(gameIndex), userBoard.NUM_COLS);
        user.addSessionScore(newScore, gameIndex);
        assert newScore >= 0;
        fm.saveObject(users);
        System.out.println("THE ARRAY OF THE SESSION SCORES IS DISPLAYED HERE: ");
        user.printAllSessionScores();
        return newScore;
    }

    public StringBuilder formatScores(ArrayList c4Scores, int numToDisplay) {
        StringBuilder scoreFormat = new StringBuilder();
        for(int i = 0; i < numToDisplay && i < c4Scores.size(); i++){
            Object[] scoreTup = (Object[])c4Scores.get(i);
            scoreFormat.append(scoreTup[0] + ": " + scoreTup[1] + "\n");//Appends the username and score to the tup.
        }
        if(scoreFormat.length() == 0){
            scoreFormat.append("No scores to show! Be the first one!");
        }
        return scoreFormat;
    }

    public ArrayList getGlobalScores(int gameIndex) {

        HashMap<String, User> users = fm.readObject();
        ArrayList highScoresTup = new ArrayList();
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            User u = (User) pair.getValue();
            ArrayList<Integer> highScoresList = u.getHighestScoresList(gameIndex);
            for (int i = 0; i < highScoresList.size(); i++){
                Object[] tempTup = new Object[2];
                tempTup[0] = u.getUsername();
                tempTup[1] = highScoresList.get(i);
                highScoresTup.add(tempTup);
            }

        }
        return sortListofUserandScores(highScoresTup);
    }
    private ArrayList sortListofUserandScores(ArrayList highScoresTup) {
        Collections.sort(highScoresTup, new Comparator() {
            @Override
            public int compare(Object o, Object t) {
                Object[] tempO = (Object[]) o;
                Object[] tempT = (Object[]) t;
                int o1 = (int) tempO[1];
                int t1 = (int) tempT[1];
                return t1 - o1;
            }
        });
        return highScoresTup;
    }


}
