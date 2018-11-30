package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

class ScoreboardController {
    private FileManager fm;
    private ScoreFactory scoreFactory = new ScoreFactory();

    ScoreboardController(){
        this.fm = new FileManager();

    }
    /**
     * Calculates and updates the user's score, and returns a score
     */
    int updateUserHighScore(String username, int gameIndex)
    {
        int[] result = new int[4];
        int newScore = generateUserScore(username, gameIndex);
        result[3] = newScore;
        return newScore;
    }

    /**
     * Generates the user score for a user, given the username and the
     * game index of the game
     * @param username the username of the user
     * @param gameIndex the game index identity of the game
     * @return the score of the user
     */
    int generateUserScore(String username, int gameIndex) {
        int newScore;
        HashMap<String, User> users = fm.readObject();
        assert users != null;
        User user = users.get(username);
        //User user = fm.getUser(username);
        Score s1 = scoreFactory.getScore(gameIndex);
        assert user != null;
        Board userBoard = user.getGameStack(gameIndex).peek();
        s1.setBoard(userBoard);
        newScore = s1.calculateUserScore(user.getNumMoves(gameIndex), Board.NUM_COLS);
        user.addSessionScore(newScore, gameIndex);
        fm.saveObject(users);
        user.printAllSessionScores();
        return newScore;
    }

    /**
     * Formats the scores to display them onto the screen in a better
     * fashion
     * @param c4Scores the scores of a user from a game of Connect 4
     * @param numToDisplay the number to display
     * @return the StringBuilder representing the
     */
    StringBuilder formatScores(ArrayList c4Scores, int numToDisplay) {
        StringBuilder scoreFormat = new StringBuilder();
        for(int i = 0; i < numToDisplay && i < c4Scores.size(); i++){
            Object[] scoreTup = (Object[])c4Scores.get(i);
            scoreFormat.append(scoreTup[0]).append(": ").append(scoreTup[1]).append("\n");
            //Appends the username and score to the tup.
        }
        if(scoreFormat.length() == 0){
            scoreFormat.append("No scores to show! Be the first one!");
        }
        return scoreFormat;
    }

    /**
     * Gets the global scores for all users, with the game gameIndex
     * @param gameIndex the identity of the game
     * @return the ArrayList of global scores
     */
    ArrayList getGlobalScores(int gameIndex) {

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

    /**
     * Sorts the arrays of users and their scores for various games.
     * @param highScoresTup the arraylist of highscores of a user
     * @return the sorted arraylist of highscores of a user
     */
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
