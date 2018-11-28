package fall2018.csc2017.GameCentre;

import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectFourBoardManager implements BoardManager {


    /**
     * The board being managed.
     */
    private Board board;
    private int currentPlayer = 1; // currently the game is set to having player 1 as red and player 2 as black
    public boolean gameOver = false;
    public String opponent;
    private int currentPos = -1;


    public ConnectFourBoardManager(Board board) {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
    }

    public ConnectFourBoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;

        final int numTiles = Board.NUM_COLS*Board.NUM_ROWS; // TODO: make this changeable
        for (int tileNum = 0; tileNum != numTiles; tileNum++)
        {
            Tile tile = new Tile(tileNum);

            tile.setBackground(R.drawable.tile_25);

            tiles.add(tile);
        }

        this.board = new Board(tiles);
    }

    public Board getBoard() { return this.board; }

    public void makeToast(String textToDisplay) {
        //System.out.println(textToDisplay);
        Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();
    }

    /**
     * Switches the player after every turn
     */
    public void switchPlayer(){
        if (this.currentPlayer == 1){
            this.currentPlayer = 2;
        } else {
            this.currentPlayer = 1;
        }
    }

    /**
     * Gets the suitable background depending on the current player
     * @return the id of the background for the current player
     */
    public int getBackgroundForPlayer(){
        if (this.currentPlayer == 1){
            return R.drawable.red;
        } else{
            return R.drawable.black;
        }
    }

    /**
     * Returns if a puzzle has been solved after a series of
     * 4 chips of same color have been connected.
     * @return whether a puzzle has been solved.
     */
    public boolean puzzleSolved() {
        if ((checkDiagonals(currentPos) ||
                checkSides(currentPos) ||
                checkUnder(currentPos))) {
            setGameOver();
            return true;
        }
        return false;
    }

    /**
     * Returns the Background ID of the tile which is positioned at the place of
     * the most recent move
     * @param position the position of the most recent move
     * @return BackgroundID of the most recently added tile
     */
    public int getCurrentPlayer(int position){
        return getTileColor(position);
    }

    /**
     * Returns the color of the tile at given position
     * @param position the position of the tile we need to obtain the tileColor of
     * @return an int representing the colorID of the found tile
     */
    public int getTileColor(int position){
        if (position == 36){
            System.out.println("POSITION BECAME 36 MAYDAY MAYDAY");
        }
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile nextTile = null;
        while (position >= 0){
            //System.out.println("THE POSITION IS ------========------- :" + position);
            nextTile = iter.next();
            position--;
        }
        return nextTile.getBackground();
    }

    /**
     * Checks diagonally for sequences of 4 or more same colored
     * chips.
     * @return whether there is a sequence of same-color-chips of
     * length 4 or more on either diagonal of the newly added chip
     */
    public boolean checkDiagonals(int position){
//        System.out.println("THE POSITION IN checkdiagonals IS %%%%%%%%%%%%%%%%%%%: " + position);

        int currentPlayerID = getCurrentPlayer(position);
        int leftDiagUp = checkLeftDiagUp(currentPlayerID, position);
        int leftDiagDown = checkLeftDiagDown(currentPlayerID, position);
        int rightDiagUp = checkRightDiagUp(currentPlayerID, position);
        int rightDiagDown = checkRightDiagDown(currentPlayerID, position);
        System.out.println("right diagonal down: ---------------- " + rightDiagDown );
        System.out.println("right diagonal up: ---------------- " + rightDiagUp);
        System.out.println("left diagonal down: ---------------- " + leftDiagDown);
        System.out.println("left diagonal up: ---------------- " + leftDiagUp);
        return ((leftDiagDown + leftDiagUp - 1) > 3) || ((rightDiagDown + rightDiagUp - 1) > 3); // changed to 2 from 1
    }

    /**
     * Returns the number of same colored chips to the right diagonal, upwards
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the upper right diagonal
     */
    public int checkRightDiagDown(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded < board.NUM_ROWS && rowNewlyAdded >= 0){
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }

            if(position == Board.NUM_COLS*(rowNewlyAdded))
            {
                System.out.println("INSIDE BREAK");
                break;
            }
            position = position + (Board.NUM_COLS - 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of same colored chips to the right diagonal, upwards
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the upper right diagonal
     */
    public int checkRightDiagUp(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        System.out.println("ROW NEWLY  ADDED ISSSSSS");
        System.out.println(rowNewlyAdded);
        while (colNewlyAdded < Board.NUM_COLS && rowNewlyAdded >= 0 && colNewlyAdded >= 0){
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }
            if(position == Board.NUM_COLS*(rowNewlyAdded))
            {
                System.out.println("INSIDE BREAK");
                break;
            }

            position = position - (Board.NUM_COLS - 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }
//
//    /**
//     * Returns whether the game is over or not within the current board manager
//     * @return whether the game is over
//     */
//    public boolean gameOver() {
//        for (int i = 0; i < board.numTiles(); i++) {
//            if (puzzleSolved(i)) {
//                System.out.println("The i value is " + i);
//                System.out.println("THIS BOY PRINTS IF THE PUZZLE HAS BEEN SOLVED ON THE MOST RECENT TAP: " + puzzleSolved(i));
//                return false;
//            }
//        }
//        return true;
//    }

    /**
     * Sets the status of the current game as over
     */
    public void setGameOver(){
        this.gameOver = true;
    }

    /**
     * Returns the number of same colored chips to the left diagonal, downwards
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the lower left diagonal
     */
    public int checkLeftDiagDown(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded < Board.NUM_COLS && rowNewlyAdded < Board.NUM_ROWS && colNewlyAdded >= 0 && rowNewlyAdded >= 0){
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }
            if(position == Board.NUM_COLS*(rowNewlyAdded + 1) - 1){
                break;
            }
            position = position + (Board.NUM_COLS + 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of same colored chips to the left diagonal,
     * in the upward direction
     * @param position the position of the newly added tile
     * @return number of same colored chips in the upper left diagonal
     */
    public int checkLeftDiagUp(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded >= 0){
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }

            if(position == Board.NUM_COLS*(rowNewlyAdded))
            {
                System.out.println("INSIDE BREAK");
                break;
            }

            position = position - (Board.NUM_COLS + 1);

            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Checks on either side for sequences of 4 or more same colored chips
     * @return whether there are sequences of same-colored-chips or
     * length 4 or more on either sides of the newly added chip
     */
    public boolean checkSides(int position){
        int currentPlayerColor = getCurrentPlayer(position);
        return (numLeft(currentPlayerColor, position) + numRight(currentPlayerColor, position)-1)>3;
    }

    /**
     * Returns the number of tiles with the same color to the left
     * of the newly added tile.
     * @param position the position of the newly added tile
     * @return the number of the same colored tiles to the left
     */
    public int numLeft(int currentPlayerID, int position){
        int colNewlyAdded = position % Board.NUM_COLS;
        int colorCounter = 0;
        while (colNewlyAdded >= 0){
            // check if there is a same colored tile to the left, and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }
            position = position - 1;
            colNewlyAdded = position % Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of tiles with the same color to the right
     * of the newly added tile.
     * @param position the position of the newly added tile
     * @return the number of the same colored tiles to the right
     */
    public int numRight(int currentPlayerID, int position){

        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_ROWS;
        int colorCounter = 0;
        while (colNewlyAdded < board.NUM_COLS && rowNewlyAdded < board.NUM_ROWS){
            // check if there is a same colored tile to the right, and increment colorCounter
            if ((getTileColor(position) == currentPlayerID)&&(getTileColor(position)!=R.drawable.tile_25)){
                colorCounter++;
            } else {
                break;
            }
            position = position + 1;
            colNewlyAdded = position % board.NUM_COLS;
            rowNewlyAdded = position / board.NUM_ROWS;
        }
        return colorCounter;
    }

    /**
     * Checks underneath for sequences of 4 or more same colored chips.
     * @return whether there are sequence of 4 or more same-colored-chips
     * under the newly added chip.
     */
    public boolean checkUnder(int position){
//        System.out.println("THE POSITION IN checkunder IS %%%%%%%%%%%%%%%%%%%: " + position);

        int currentPlayerColor = getCurrentPlayer(position);
        int colorCounter = 0; // to be incremented if same color found.
        int rowNewTile = position / Board.NUM_ROWS;
        while (rowNewTile < Board.NUM_ROWS){
            if (getTileColor(position) == currentPlayerColor){
                colorCounter++;
            } else {
                break;
            }
            // here, check for it being the same colored tile
            // if color different stop iteration
            position += Board.NUM_ROWS;
            rowNewTile = position / Board.NUM_ROWS;
        }
        return (colorCounter > 3);
    }

    /**
     * Returns whether the board is full of chips so there is no space
     * to place more chips.
     * @return whether the board is full of chips, thus ending the game
     */
    public boolean checkFull(){
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile nextTile = null;
        while (iter.hasNext()){
            nextTile = iter.next();
            if (nextTile.getBackground() == R.drawable.tile_25){
                return false;
            }
        }
        System.out.println("CHECK FULL RETURNED TRUE MAYDAY MAYDAY");
        //Toast.makeText(GlobalApplication.getAppContext(), "The game is drawn", Toast.LENGTH_LONG).show();
        //makeToast("The game is drawn!");
        return true;
    }
    // TODO: ADD GAME OVER WHEN THERE ARE NO PLACES LEFT TO PLACE CHIPS

    /**
     * Returns if the position tapped by the use is a valid tap.
     * A tap is valid if the position tapped has on chips and there
     * is a chip present right underneath it.
     * @param position the position of tapped by the user.
     * @return if the tap is a valid tap or not.
     */
    public boolean isValidTap(int position) {
        //check if the position tapped is empty.
        //use getBackground method on the position; if
        //it returns tile_25, then the place is empty
        boolean result = checkEmptyTile(position);

        // then we check if there is a tile underneath.
        // if there is a tile present then the tap is valid.
        if (result){ // if empty tile
            return checkUnderneath(position);
        } else {
            return false;
        }
    };

    /**
     * Checks if the position has an empty tile.
     * @param position the position tapped by the user.
     * @return whether there is an empty tile present on the tapped tile.
     */
    public boolean checkEmptyTile(int position){
        //int blankID = R.drawable.tile_25;

        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile next = null;
        while (position >= 0){
            next = iter.next();
            position = position - 1;
        }
        return next.getBackground() == R.drawable.tile_25;


    }

    /**
     * Returns if there is a tile present underneath the given tile.
     * @param position
     * @return
     */
    public boolean checkUnderneath(int position){
        //int blankID = 25;
        int positionBelow = position + Board.NUM_ROWS;
        int numRows = positionBelow / Board.NUM_ROWS;
        if(numRows < Board.NUM_ROWS){
            Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
            Tile next = null;
            while (positionBelow >= 0){
                next = iter.next();
                positionBelow--;
                //numRows = positionBelow / board.NUM_ROWS;
            }
            return !(next.getBackground() == R.drawable.tile_25);
        } else {
            return true;
        }


    }

    /**
     * Returns whether the current game is drawn (ended in stalemate)
     * @return whether the curernt game has eneded in stalemate
     */
    public boolean gameDrawn(){
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile tile = null;
        while (iter.hasNext()){
            tile = iter.next();
            if (tile.getBackground() == R.drawable.tile_25){
                return false;
            }
        }
        makeToast("The game is drawn!");
        setGameOver();
        return true;
    }

    /**
     * Method to process the movement of the touch, if the touch is a valid move
     * @param position the position at which the move is to be made
     */
    public void touchMove(int position) {
        if (!gameOver) {
            int row = position / Board.NUM_ROWS;
            int col = position % Board.NUM_COLS;
            currentPos = position;
            board.setTileBackground(row, col, getBackgroundForPlayer());
            //Toast.makeText(GlobalApplication.getAppContext(), "Player "+(3-this.currentPlayer)+"'s turn", Toast.LENGTH_SHORT).show();
            // TODO: the 3-this.currentplayer is probably a code smell, there should be a better solutions to this!
            if (gameDrawn()){
                makeToast("Game drawn! Start a new game");
            } else {
                switchPlayer();
            }
        }
        else {
            makeToast("Game over! Start a new game!");
        }

    }

    public void setOpponent(String opponent) {this.opponent = opponent; }

    public boolean getChanged() { return true; }

}
