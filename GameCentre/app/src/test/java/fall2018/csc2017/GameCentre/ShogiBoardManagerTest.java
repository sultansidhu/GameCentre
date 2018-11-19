package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShogiBoardManagerTest {

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
        //TODO: Find out why the not mocked error appears and how to fix it.
    }

    @Test
    public void isValidTap() {
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
    public void inSameRow() {
    }

    @Test
    public void inSameCol() {
    }

    @Test
    public void tileBlockingRow() {
    }

    @Test
    public void tileBlockingCol() {
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