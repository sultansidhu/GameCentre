package fall2018.csc2017.GameCentre;

import android.widget.Toast;

public class ConnectFourBoardManager extends BoardManager {


    /**
     * The board being managed.
     */
    private Board board;
    private int currentPlayer = 1; // currently the game is set to having player 1 as red and player 2 as black


    public ConnectFourBoardManager(Board board) {
        super(board);
        this.board = board;
    }

    public ConnectFourBoardManager(int size) {
        super(size);
    }

    public Board getBoard() { return this.board; }

    public void makeToast(String textToDisplay) {
        System.out.println(textToDisplay);
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
        return true;
        //return (checkDiagonals() || checkSides() || checkUnder());
    }

    /**
     * Returns if a puzzle has been solved after a series of 4 of
     * same colored chips have been connected.
     * @param position the position of the most recent addition.
     * @return whether a puzzle has been solved.
     */
    //@Override
    public boolean puzzleSolved(int position) {
        // TODO: IN EACH OF THESE FUNCTIONS, YOU ARE GONNA HAVE TO GET THE COLOR OF THE DUDE THAT JUST PLAYED.
        return (checkDiagonals(position) ||
                checkSides(position) ||
                checkUnder(position));
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
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile nextTile = null;
        while (position >= 0){
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
        int currentPlayerID = getCurrentPlayer(position);
        int leftDiagUp = checkLeftDiagUp(currentPlayerID, position);
        int leftDiagDown = checkLeftDiagDown(currentPlayerID, position);
        int rightDiagUp = checkRightDiagUp(currentPlayerID, position);
        int rightDiagDown = checkRightDiagDown(currentPlayerID, position);
        return ((leftDiagDown + leftDiagUp - 1) > 3) || ((rightDiagDown + rightDiagUp - 1) > 3);
    }

    /**
     * Returns the number of same colored chips to the right diagonal, upwards
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the upper right diagonal
     */
    public int checkRightDiagDown(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % board.NUM_COLS;
        int rowNewlyAdded = position / board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded <= board.NUM_ROWS){
            // here check for same color and increment colorCounter
            // TODO: CHECK FOR R.DRAWABLE.BLACK OR R.DRAWABLE.RED DEPENDING ON THE CURRENT PLAYER'S ID.
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position + (board.NUM_COLS - 1);
            colNewlyAdded = position % board.NUM_COLS;
            rowNewlyAdded = position / board.NUM_COLS;
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
        int colNewlyAdded = position % board.NUM_COLS;
        int rowNewlyAdded = position / board.NUM_COLS;
        while (colNewlyAdded <= board.NUM_COLS && rowNewlyAdded >= 0){
            // here check for same color and increment colorCounter
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position - (board.NUM_COLS - 1);
            colNewlyAdded = position % board.NUM_COLS;
            rowNewlyAdded = position / board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of same colored chips to the left diagonal, downwards
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the lower left diagonal
     */
    public int checkLeftDiagDown(int currentPlayerID, int position){
        int colorCounter = 0;
        int colNewlyAdded = position % board.NUM_COLS;
        int rowNewlyAdded = position / board.NUM_COLS;
        while (colNewlyAdded <= board.NUM_COLS && rowNewlyAdded <= board.NUM_ROWS){
            // here check for same color and increment colorCounter
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position + (board.NUM_COLS + 1);
            colNewlyAdded = position % board.NUM_COLS;
            rowNewlyAdded = position / board.NUM_COLS;
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
        int colNewlyAdded = position % board.NUM_COLS;
        int rowNewlyAdded = position / board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded >= 0){ // TODO: CHECK THESE INEQUALITIES FOR ALL SIMILAR FUNCTIONS
            // here check for same color and increment colorCounter
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position - (board.NUM_COLS + 1);
            colNewlyAdded = position % board.NUM_COLS;
            rowNewlyAdded = position / board.NUM_COLS;
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
        int colNewlyAdded = position % board.NUM_COLS;
        int colorCounter = 0;
        while (colNewlyAdded >= 0){
            // check if there is a same colored tile to the left, and increment colorCounter
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position - 1;
            colNewlyAdded = position % board.NUM_COLS;
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
        int colNewlyAdded = position % board.NUM_COLS;
        int colorCounter = 0;
        while (colNewlyAdded <= board.NUM_COLS){
            // check if there is a same colored tile to the right, and increment colorCounter
            if (getTileColor(position) == currentPlayerID){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            position = position + 1;
            colNewlyAdded = position % board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Checks underneath for sequences of 4 or more same colored chips.
     * @return whether there are sequence of 4 or more same-colored-chips
     * under the newly added chip.
     */
    public boolean checkUnder(int position){
        int currentPlayerColor = getCurrentPlayer(position);
        int colorCounter = 0; // to be incremented if same color found.
        int rowNewTile = position / board.NUM_ROWS;
        while (rowNewTile < board.NUM_ROWS){
            if (getTileColor(position) == currentPlayerColor){
                colorCounter++;
            } else {
                break; // TODO: GET THESE FUNCTIONS CHECKED BY TEAM MEMBERS
            }
            // here, check for it being the same colored tile
            // if color different stop iteration
            position += board.NUM_ROWS;
            rowNewTile = position / board.NUM_ROWS;
        }
        return (colorCounter > 3);
    }

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
        } else { // TODO: DETERMINING THE COLOR OF THE GUY THAT JUST PLAYED IS IMPERATIVE YOU DUMBASS
            return false;
        }
    };

    /**
     * Checks if the position has an empty tile.
     * @param position the position tapped by the user.
     * @return whether there is an empty tile present on the tapped tile.
     */
    public boolean checkEmptyTile(int position){
        int blankID = 25;

        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile next = null;
        while (position < 0){
            next = iter.next();
            position = position - 1;
        }
        return next.getBackground() == blankID;


    }

    /**
     * Returns if there is a tile present underneath the given tile.
     * @param position
     * @return
     */
    public boolean checkUnderneath(int position){
        int blankID = 25;
        int positionBelow = position + board.NUM_ROWS;
        int numRows = positionBelow / board.NUM_ROWS;
        if(numRows <= board.NUM_ROWS){
            Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
            Tile next = null;
            while (positionBelow < 0){
                next = iter.next();
                positionBelow --;
            }
            return next.getBackground() == blankID;
        } else {
            return true;
        }


    }

    /**
     * Method to process the movement of the touch, if the touch is a valid move
     * @param position the position at which the move is to be made
     */
    public void touchMove(int position) {
//        int row = position / Board.NUM_ROWS;
//        int col = position % Board.NUM_COLS;
        int positionCopy = position;
        if(isValidTap(position))
        {
            //Tile newTile = new Tile(getBackgroundForPlayer());
            Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
            Tile tile = null;
            while (position >= 0){
                tile = iter.next();
                position--;
            }
            tile.setBackground(getBackgroundForPlayer());
            switchPlayer(); // this should be the last line of touchMove()
        } else {
            makeToast("Invalid Move! Try again");
        }

    };
}
