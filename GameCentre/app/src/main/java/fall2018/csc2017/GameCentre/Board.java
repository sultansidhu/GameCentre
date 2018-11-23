package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class Board extends Observable implements Serializable, Iterable<Tile> {
    /**
     * The number of rows.
     */

    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];
    private String opponent;

    private int currPlayer = 1;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
    *This method sets the opponent attribute of this class to the string opponent
     @param opponent, a String representing the opponent
     @return null
     @throws null
    */

    public void setOpponentString(String opponent)
    {
        this.opponent = opponent;
    }


    /**
     * A getter method to return the array of tiles contained within
     * the board.
     *
     * @return an array of tiles used in the construction of the board.
     */
    public Tile[][] getTiles(){
        return this.tiles;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return Board.NUM_ROWS * Board.NUM_COLS;
    }

    public int numBlacks() {
        Iterator<Tile> iter = iterator();
        int numBlacks = 0;
        while (iter.hasNext()) {
            if (iter.next().getBackground()==R.drawable.black) {
                numBlacks++;
            }
        }
        return numBlacks;
    }

    public int numReds() {
        Iterator<Tile> iter = iterator();
        int numReds = 0;
        while (iter.hasNext()) {
            if (iter.next().getBackground()==R.drawable.red) {
                numReds++;
            }
        }
        return numReds;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tile1 = getTile(row1, col1);
        Tile tile2 = getTile(row2, col2);
        Tile[][] newTiles = tiles.clone();
        newTiles[row1][col1] = tile2;
        newTiles[row2][col2] = tile1;
        tiles = newTiles;
        setChanged();
        notifyObservers();
    }

    public void setTileBackground(int row, int col, int background) {
        getTile(row, col).setBackground(background);
        setChanged();
        notifyObservers();
    }

    /**
     * Return the string representation of the board.
     *
     * @return the string representation of the board.
     */
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Override iterator to return an instance of BoardIterator.
     *
     * @return a new instance of BoardIterator.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * A class for iterating over each tile in the board.
     */
    protected class BoardIterator implements Iterator<Tile> {
        /**
         * The row number of next tile.
         */
        int row = 0;
        /**
         * The column number of next tile.
         */
        int col = 0;

        /**
         * Returns whether you have reached the last tile in the board.
         *
         * @return true if there is a next tile.
         */
        @Override
        public boolean hasNext() {
            return row != Board.NUM_ROWS && col != Board.NUM_COLS;
        }


        /**
         * Return the next tile if there is one, updates row and col.
         *
         * @return nextTile, which is a Tile object.
         */
        @Override
        public Tile next() {
            //System.out.println("THE CURRENNT ROW IS: " + row);
            //System.out.println("THE CURRENT COLUMN IS: " + col);
            Tile nextTile = getTile(row, col);
            if (col == Board.NUM_COLS - 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
            return nextTile;
        }

    }

    /**
     * Return the number of inversions within the board for a odd side length.
     * An inversion is when a larger tile precedes a smaller valued tile.
     * Therefore, a solved board has no inversions.
     *
     * @return the number of inversions present within the board.
     */
    int checkInversions(){
        int inversions = 0;
        ArrayList<Integer> arrayList = new ArrayList<>();
        BoardIterator iterator = new BoardIterator();
        while (iterator.hasNext()){
            Tile nextTile = iterator.next();
            if (nextTile.getId()!=numTiles()){
                arrayList.add(new Integer(nextTile.getId()));
            }


        }
        for (int i = 0; i < arrayList.size(); i++){
            Integer item = arrayList.get(i);
            for (int j = i; j < arrayList.size(); j++){
                Integer parsedItem = arrayList.get(j);
                if (parsedItem < item){
                    inversions++;
                }
            }
        }

        return inversions;

    }

    /**
     * Returns whether the board is solveable.
     *
     * @return whether the board can be solved.
     */

    boolean isSolveable(){
        int numTiles = this.numTiles();
        if (numTiles % 2 == 1){
            int numInversions = this.checkInversions();
            if (numInversions % 2 == 0){
                return true;
            } else {
                return false;
            }
        } else {
            int numInversions = this.checkInversions();
            System.out.println("THE NUMBER OF INVERSIONS IS " + numInversions);
            int[] position = this.getEmptyTilePosition();
            System.out.println("THE POSITION OF THE EMPTY TILE IS: " + position);
            int row = position[0];
            if (row % 2 == 1){ // covers row being even from below case
                if (numInversions%2 == 1){
                    return false;
                } else {
                    return true;
                }

            } else { // covers row being odd from below case
                if (numInversions%2 == 0){
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * Returns the position of the empty tile within the board in the format [row, column]
     * @return the position of the empty tile within the board in the format [row, column]
     */
    int[] getEmptyTilePosition(){
        int position = getPos();
        System.out.println(position); // todo: remove this printy boi
        int row = position / NUM_ROWS;
        int column = position % NUM_COLS;
        int[] list = new int[2];
        list[0] = row;
        list[1] = column;
        return list;

    }

    /**
     * Returns the index of the empty tile within the board
     * @return the index of the empty tile within the board
     */
    int getPos(){
        int counter = 0;
        BoardIterator iterator = new BoardIterator();
        while (iterator.hasNext()){
            Tile tile = iterator.next();
            if (tile.getId() == numTiles()){
                return counter;
            } else {
                counter ++;
            }

        }
        return 0;
    }

    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    public int getCurrPlayer() { return currPlayer; }
}
