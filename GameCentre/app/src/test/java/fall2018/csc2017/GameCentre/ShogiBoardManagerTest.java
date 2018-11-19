package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShogiBoardManagerTest {

    @Test
    public void getBoard() {
    }

    @Before


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
        //TODO: Find out why the not mocked error appears and how to fix it.
    }

    @Test
    public void isValidTap() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.isValidTap(48, 6));//move red onto black
        assertEquals(false, bm.isValidTap(42, 43));//move red onto another red
        assertEquals(false, bm.isValidTap(43, 37));//red diagonal
        assertEquals(true, bm.isValidTap(46, 39));//red moving up.
    }

    @Test
    public void isValidTap1() {
    }

    @Test
    public void touchMove() {
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
        bm.touchMove(44, 23);
        assertEquals(true, bm.tileBlockingRow(22, 24));//Try to go on top of a tile
    }

    @Test
    public void tileBlockingCol() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertEquals(false, bm.tileBlockingCol(42, 7));//way is clear
        assertEquals(true, bm.tileBlockingCol(43, 15));//Try to jump over another tile

    }


    public ShogiBoardManager makeOneMoveBoard(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.touchMove(1, 22);
        return bm;
    }
    public ShogiBoardManager makeMidGameBoard(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.touchMove(1, 22);
        bm.touchMove(46, 32);
        bm.touchMove(2, 23);
        bm.touchMove(42, 21);
        bm.touchMove(3, 24);
        bm.touchMove(48, 41);
        bm.touchMove(4, 25);
        return bm;
    }
    public ShogiBoardManager makeOneMoveAwayforRed(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.touchMove(1, 22);
        bm.touchMove(46, 32);
        bm.touchMove(2, 23);
        bm.touchMove(42, 21);
        bm.touchMove(3, 24);
        bm.touchMove(48, 41);
        bm.touchMove(4, 25);
        bm.touchMove(43, 36);
        bm.touchMove(5, 26);
        bm.touchMove(41, 27);
        bm.touchMove(6, 1);
        bm.touchMove(44, 43);
        bm.touchMove(1, 29);
        //bm.touchMove(21, 22); <-- Winning move for red
        return bm;
    }
    public ShogiBoardManager makeRedIsWinner(){
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.touchMove(1, 22);
        bm.touchMove(46, 32);
        bm.touchMove(2, 23);
        bm.touchMove(42, 21);
        bm.touchMove(3, 24);
        bm.touchMove(48, 41);
        bm.touchMove(4, 25);
        bm.touchMove(43, 36);
        bm.touchMove(5, 26);
        bm.touchMove(41, 27);
        bm.touchMove(6, 1);
        bm.touchMove(44, 43);
        bm.touchMove(1, 29);
        bm.touchMove(21, 22); //<-- Winning move for red
        return bm;
    }
}