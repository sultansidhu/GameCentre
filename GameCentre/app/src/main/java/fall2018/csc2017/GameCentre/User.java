/*
=============================================================================
This class represents a single user who has registered for the Game Center
File Name: User.java
Date: November 2, 2018
Author: CSC207 Group 0506
===========================================================================*/

package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;

public class User implements Serializable
{
    /**
   This is the username of this user
   */
    private String username;
    /**
    This is the password of this user
    */
    private String password;
    /**
    This is a stack holding all the game states of the saved game for this user
    */
    private HashMap<Integer, Stack<Board>> savedStates;
    /**
    This is answer to this user's security question
    */
    private String answer;

    String opponent = "Guest";
    /**
    This is the current highest score of this user per game
    */
    private HashMap<Integer, Integer> highestScore;
    /**
    This is the security question of this user
    */
    private String securityQuestion;
    /**
    This is a counter for calculating the total playing time of the user.
     */
    long playTime;
    /**
    This is a couter keeping track of the available undos foe a user
     */
    private HashMap<Integer, Integer> availableUndos;

    private boolean isLoggedIn = false;

    private HashMap<Integer, String> opponents;
    /**
     * The user of the application. Object is used to sign in and access various functions.
     * @param username the username of the user
     * @param password the password of the user
     * @param securityQuestion the security question answered by the user
     * @param answer the answer recorded by the user during sign up
     */
    User(String username, String password, String securityQuestion, String answer)
    {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.opponents = new HashMap<Integer, String>();
        this.highestScore = new HashMap<Integer, Integer>();
        this.savedStates = new HashMap<Integer, Stack<Board>>();
        this.availableUndos = new HashMap<Integer, Integer>();
        for(int i = 0; i <= 2; i++) {//Initializes the stack
            this.savedStates.put(i, new Stack<Board>());
            this.highestScore.put(i, 0);
            this.availableUndos.put(i, 3); // the default number of undos for every user.
        }
        this.playTime = (long)0.0;

    }
//    String getOpponent(){
//        return this.opponent;
//    }
//    void setOpponent(String newOpponent){
//        this.opponent = newOpponent;
//    }
    String getUsername(){
        return this.username;
    }
    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    String getPassword()
    {
        return password;
    }

    /**
     * The stack of boards used to keep track of the moves made by the user.
     *
     * @return the stack of the boards
     */
    Stack<Board> getGameStack(int gameNum)
    {
        return savedStates.get(gameNum);
    }

    void setAvailableUndos(int gameIndex, int newUndos){
        assert gameIndex <=2;
        assert gameIndex >= 0;
        this.availableUndos.put(gameIndex, newUndos);
    }
    int getAvailableUndos(int gameIndex){
        assert gameIndex <=2;
        assert gameIndex >= 0;
        return this.availableUndos.get(gameIndex);
    }

    /**
     * Starts the timer for the playing time of the player
     */
    void startTimer(){
        playTime = System.currentTimeMillis();
    }

    /**
     * Stops the timer and sets the timePlayed attribute when the
     * player has finished the game, or paused the game.
     */
    void stopTimer(){
        playTime = System.currentTimeMillis() - playTime;
    }

    /**
     * Gets the total time the player has played the game.
     *
     * @return the total playtime of the player
     */
    double getTotalTime() {
        return playTime;
    }

    /**
     * Gets the number of moves made by the player.
     *
     * @return the number of moves the player has made so far in the game.
     */
    int getNumMoves(int game) {
        return getGameStack(game).size();
    }

    /**
     * Resumes the timer after the game has been resumed from pause
     * or premature exit.
     */
    public void resumeTimer(){
        playTime = playTime + System.currentTimeMillis();
    }

    /**
     * Adds the current state of the board into the stack of boards.
     *
     * @param board the board to be added to the stack
     */
    public void addState(Board board, int gameNum) {
        try {
            savedStates.get(gameNum).push(board);
        }
        catch(NullPointerException e) {
            this.savedStates.put(gameNum, new Stack<Board>());
            savedStates.get(gameNum).push(board);
        }

    }

    /**
     * Sets a HashMap of stack of boards as the savedStates of a user.
     * @param savedStates the HashMap of boards to be set for the savedStates of the user
     */
    public void setSavedStates(HashMap<Integer, Stack<Board>> savedStates)
    {
        this.savedStates = savedStates;
    }

    public void resetGameStack(int gameIndex) {
        this.savedStates.put(gameIndex, new Stack<Board>());
    }

    /**
     * Returns the answer to the security question that the user answered during
     * account creation.
     *
     * @return the answer of the user's security question
     */
    String getAnswer()
    {
        return answer;
    }

    /**
     * Sets the answer to the security question answered by the user.
     *
     * @param answer the answer to the security question
     */
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    /**
     * Returns the highest score of the user.
     *
     * @return the highest score of the user
     */
    int getHighestScore(int gameIndex)
    {
        return highestScore.get(gameIndex);
    }

    /**
     * Sets the highest score of the user to the given highestScore.
     *
     * @param highestScore the new highestScore of the user
     */
    void setHighestScore(int gameIndex, int highestScore)
    {
        this.highestScore.put(gameIndex, highestScore);
    }

    /**
     * Returm the HashMap mapping a game index integer to the String representing the opponent
     * of the game
     * @return HashMap<Integer, String>
     */
    public HashMap<Integer, String> getOpponents()
    {
        return this.opponents;
    }

    /**
     * Set the opponents attribute of this class to newOpponents
     * @param newOpponents, a HashMap representing the new opponents
     */
    public void setOpponents(HashMap<Integer, String> newOpponents)
    {
        this.opponents = newOpponents;
    }
    public void setOpponent(int gameIndex, String username){
        this.opponents.put(gameIndex, username);
    }

//
    String getSecurityQuestion()
    {
        return securityQuestion;
    }
//
//    public void setSecurityQuestion(String securityQuestion)
//    {
//        this.securityQuestion = securityQuestion;
//        this.securityQuestion = securityQuestion;
//    }

    public void setLoggedIn(boolean login) {
        isLoggedIn = login;
    }

    public boolean getLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Returns a string representation of the user object.
     *
     * @return the string representation of the user
     */
    @Override
    public String toString() {
        String username = this.username;
        return "User: " + username + " || Highest score for SlidingTiles: " + highestScore.get(0) + " Highest score for Shogi: " +highestScore.get(1) + " Highest score for Connect 4: " + highestScore.get(2);
    }


    public String getOpponent(int i) {
        return this.opponents.get(i);
    }
}
