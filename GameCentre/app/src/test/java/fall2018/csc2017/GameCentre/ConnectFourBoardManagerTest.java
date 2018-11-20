package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConnectFourBoardManagerTest {

    @Test
    public void getBoard() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertNotNull(bm.getBoard());
    }

    @Test
    public void switchPlayer() {
    }

    @Test
    public void puzzleSolved() {
    }

    @Test
    public void puzzleSolved1() {
    }

    @Test
    public void checkDiagonals() {
    }

    @Test
    public void checkRightDiagDown() {
    }

    @Test
    public void checkRightDiagUp() {
    }

    @Test
    public void checkLeftDiagDown() {
    }

    @Test
    public void checkLeftDiagUp() {
    }

    @Test
    public void checkSides() {
    }

    @Test
    public void numLeft() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        assertEquals(1, bm.numLeft(2, 19));//1 black to the left
        assertEquals(0, bm.numLeft(2, 18));//at the absolute left
        assertEquals(2, bm.numLeft(1, 34));//different colors to the left
    }

    @Test
    public void numRight() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        assertEquals(2, bm.numRight(1, 30));//multiple colors right
        assertEquals(0, bm.numRight(2, 35));//absolute right
        assertEquals(0, bm.numRight(1, 21));//Blanks at right

    }

    @Test
    public void checkUnder() {
        ConnectFourBoardManager bm = makeRedWinVerticalBoard();
        assertEquals(true, bm.checkUnder(13));
        assertEquals(false, bm.checkUnder(14));
    }

    @Test
    public void isValidTap() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertEquals(false, bm.isValidTap(30));//Already occupied position
        assertEquals(false, bm.isValidTap(0));//Too high
        assertEquals(true, bm.isValidTap(12));//Just right
    }

    @Test
    public void checkEmptyTile() {
    }

    @Test
    public void checkUnderneath() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        assertEquals(false, bm.checkUnderneath(0));//Not underneath
        assertEquals(true,  bm.checkUnderneath(12));//Is underneath
    }

    @Test
    public void gameDrawn() {
    }


    public ConnectFourBoardManager makeEarlyBoard(){
        //Integer[] moveList = {30, 24, 31, 18, 25, 32, 34, 26, 20, 33, 27, 21, 19, 35};
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        bm.touchMove(30);
        bm.touchMove(24);
        bm.touchMove(31);
        bm.touchMove(18);
        return bm;
    }
    public ConnectFourBoardManager makeRedOneMoveAwayBoard(){
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        bm.touchMove(30);
        bm.touchMove(24);
        bm.touchMove(31);
        bm.touchMove(18);
        bm.touchMove(25);
        bm.touchMove(32);
        bm.touchMove(34);
        bm.touchMove(26);
        bm.touchMove(20);
        bm.touchMove(33);
        bm.touchMove(27);
        bm.touchMove(21);
        bm.touchMove(19);
        return bm;
    }
    public ConnectFourBoardManager makeRedWinDiagonalBoard(){
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        bm.touchMove(30);
        bm.touchMove(24);
        bm.touchMove(31);
        bm.touchMove(18);
        bm.touchMove(25);
        bm.touchMove(32);
        bm.touchMove(34);
        bm.touchMove(26);
        bm.touchMove(20);
        bm.touchMove(33);
        bm.touchMove(27);
        bm.touchMove(21);
        bm.touchMove(19);
        bm.touchMove(35);
        bm.touchMove(15);
        return bm;
    }
    public ConnectFourBoardManager makeRedWinVerticalBoard(){
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        bm.touchMove(30);
        bm.touchMove(24);
        bm.touchMove(31);
        bm.touchMove(18);
        bm.touchMove(25);
        bm.touchMove(32);
        bm.touchMove(34);
        bm.touchMove(26);
        bm.touchMove(20);
        bm.touchMove(33);
        bm.touchMove(27);
        bm.touchMove(21);
        bm.touchMove(19);
        bm.touchMove(35);
        bm.touchMove(13);
        return bm;
    }
}