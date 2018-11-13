/*
=============================================================================
This class represents a single user who has registered for the Game Center
File Name: User.java
Date: November 2, 2018
Author: CSC207 Group 0506
===========================================================================*/

package fall2018.csc2017.GameCentre;

import java.io.Serializable;
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
    private Stack<Board> savedStates;
    /**
    This is answer to this user's security question
    */
    private String answer;
    /**
    This is the current highest score of this user
    */
    private int highestScore = 0;
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
    private int availableUndos;

    private boolean isLoggedIn = false;

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
        this.savedStates = new Stack<>();
        this.playTime = (long)0.0;
        this.availableUndos = 3; // the default number of undo's for every user.
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
    Stack<Board> getStack()
    {
        return savedStates;
    }

    void setAvailableUndos(int newUndos){
        this.availableUndos = newUndos;
    }
    int getAvailableUndos(){
        return this.availableUndos;
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
    int getNumMoves() {
        return getStack().size();
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
    void addState(Board board) {
        savedStates.push(board);
    }

    /**
     * Sets a stack of boards as the savedStates of a user.
     * @param savedStates the stack of boards to be set for the savedStates of the user
     */
    void setSavedStates(Stack<Board> savedStates)
    {
        this.savedStates = savedStates;
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
    int getHighestScore()
    {
        return highestScore;
    }

    /**
     * Sets the highest score of the user to the given highestScore.
     *
     * @param highestScore the new highestScore of the user
     */
    void setHighestScore(int highestScore)
    {
        this.highestScore = highestScore;
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
//    }

    public void setLoggedIn() {
        isLoggedIn = true;
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
        double highestScore = this.highestScore;
        return "User: " + username + " || Highest score: " + highestScore + " ";
    }




}
