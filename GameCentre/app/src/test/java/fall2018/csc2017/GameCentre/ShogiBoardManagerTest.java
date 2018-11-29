package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ShogiBoardManagerTest {

    private ArrayList<Integer> tilesSelected;
    private ArrayList<Integer> targetTiles;

    @Test
    public void getBoard() {
    }


    @Test
    public void puzzleSolved() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.puzzleSolved());
        bm = makeMidGameBoard();
        assertEquals(false, bm.puzzleSolved());
        bm = makeOneMoveAwayforRed();
        assertEquals(false, bm.puzzleSolved());
        bm = makeRedIsWinner();
        assertEquals(true, bm.puzzleSolved());
        bm = makeOneMoveAwayforBlack();
        assertFalse(bm.puzzleSolved());
        bm = makeBlackisWinner();
        assertTrue(bm.puzzleSolved());
        //TODO: Find out why the not mocked error appears and how to fix it.
    }

    @Test
    public void isValidTap() {
        ShogiBoardManager bm = makeOneMoveBoard();
        bm.setTileSelected(48);
        assertFalse(bm.isValidTap(6));//move red onto black
        bm.setTileSelected(42);
        assertTrue(bm.isValidTap(43));//move red onto another red
        bm.setTileSelected(43);
        assertFalse(bm.isValidTap(37));//red diagonal
        bm.setTileSelected(46);
        assertTrue(bm.isValidTap(39));//red moving up.
        bm = makeMidGameBoard();
        bm.setTileSelected(43);
        assertTrue(bm.isValidTap(36));
    }

    @Test
    public void isValidTap1() {
    }

    @Test
    public void touchMove1() {
    }

    @Test
    public void isBlack(){
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.isBlack(6, 0));//Red tile
        assertEquals(false, bm.isBlack(5, 3)); //Blank tile
        assertEquals(true, bm.isBlack(3, 1));//Moved black tile
        assertEquals(true, bm.isBlack(0,0));//Black tile that did not move
    }

    @Test
    public void isRed(){
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.isRed(3, 1));//Black tile
        assertEquals(false, bm.isRed(2, 0));//Blank tile
        assertEquals(true, bm.isRed(6, 6)); //Red tile

    }

    @Test
    public void inSameRow() {
        ShogiBoardManager bm = makeMidGameBoard();
        assertEquals(false, bm.inSameRow(0, 7));
        assertEquals(false, bm.inSameRow(0, 48));
        assertEquals(true, bm.inSameRow(14, 16));
    }

    @Test
    public void inSameCol() {
        ShogiBoardManager bm = makeOneMoveAwayforRed();
        assertEquals(false, bm.inSameCol(0, 8));
        assertEquals(true, bm.inSameCol(15, 22));
        assertEquals(false, bm.inSameCol(1,2));
    }

    @Test
    public void tileBlockingRow() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.tileBlockingRow(22, 27));//row only has 1 tile
        assertEquals(true, bm.tileBlockingRow(42, 47));//row has multiple tiles
        bm.setTileSelected(44);
        bm.touchMove(23);
        assertEquals(true, bm.tileBlockingRow(22, 24));//Try to go on top of a tile
    }

    @Test
    public void tileBlockingCol() {
        ShogiBoardManager bm = new ShogiBoardManager(makeOneMoveBoard().getBoard());
        assertEquals(false, bm.tileBlockingCol(42, 7));//way is clear
        assertEquals(true, bm.tileBlockingCol(43, 15));//Try to jump over another tile

    }

    @Test
    public void getChanged() {
        ShogiBoardManager bm = makeMidGameBoard();
        bm.setTileSelected(-1);
        bm.touchMove(30);
        assertFalse(bm.getChanged());
        bm.setTileSelected(43);
        bm.touchMove(36);
        assertTrue(bm.getChanged());
    }

    @Test
    public void setTileToMove() {
        ShogiBoardManager bm = makeOneMoveAwayforBlack();
        bm.setTileSelected(33);
        assertFalse(bm.setTileToMove(35, bm.getBoard().getTile(5,5)));
    }

//    @Test
//    public void checkCapturedUp() {
//        ShogiBoardManager bm = new ShogiBoardManager(7);
//        bm.setTileSelected(5);
//        bm.touchMove(12);
//        assertEquals(0, bm.checkCapturedUp(12));
//    }


    public ShogiBoardManager makeOneMoveBoard(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.setTileSelected(1);
        bm.touchMove(22);
        return bm;
    }
    public ShogiBoardManager makeMidGameBoard(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        tilesSelected = new ArrayList<>(Arrays.asList(1,46,2,42,3,48,4));
        targetTiles = new ArrayList<>(Arrays.asList(22,32,23,21,24,41,25));
        return makeMoves(bm, tilesSelected, targetTiles);
    }
    public ShogiBoardManager makeOneMoveAwayforRed(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        tilesSelected = new ArrayList<>(Arrays.asList(1,46,2,42,3,48,4,43,5,41,6,44,1));
        targetTiles = new ArrayList<>(Arrays.asList(22,32,23,21,24,41,25,36,26,27,1,43,29));
        return makeMoves(bm, tilesSelected, targetTiles);
        //bm.touchMove(21, 22); <-- Winning move for red
    }
    public ShogiBoardManager makeRedIsWinner(){
        ShogiBoardManager bm = makeOneMoveAwayforRed();
        bm.setTileSelected(21);
        bm.touchMove(22);
        return bm;
    }

    public ShogiBoardManager makeOneMoveAwayforBlack() {
        ShogiBoardManager bm = new ShogiBoardManager(6);
        tilesSelected = new ArrayList<>(Arrays.asList(4,33,0,31,3,32,6,34,9,30,1,24));
        targetTiles = new ArrayList<>(Arrays.asList(16,15,6,13,9,14,12,22,33,24,19,28));
        return makeMoves(bm, tilesSelected, targetTiles);
    }

    public ShogiBoardManager makeBlackisWinner() {
        ShogiBoardManager bm = makeOneMoveAwayforBlack();
        bm.setTileSelected(33);
        bm.touchMove(34);
        return bm;
    }

    public ShogiBoardManager makeMoves(ShogiBoardManager bm, ArrayList<Integer> tilesSelected, ArrayList<Integer> targetTiles) {
        for (int i=0; i < tilesSelected.size(); i++) {
            bm.setTileSelected(tilesSelected.get(i));
            bm.touchMove(targetTiles.get(i));
        }
        return bm;
    }
}