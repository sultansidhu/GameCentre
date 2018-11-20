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
        ConnectFourBoardManager bm = makeEarlyBoard();
        int currPlayer = bm.getCurrentPlayer(18);
        bm.touchMove(12);
        int newPlayer = bm.getCurrentPlayer(12);
        assertNotEquals(currPlayer, newPlayer);
        bm.touchMove(6);
        int newnewPlayer = bm.getCurrentPlayer(6);
        assertEquals(currPlayer, newnewPlayer);
    }

    @Test
    public void puzzleSolved() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertEquals(false, bm.puzzleSolved(30));//Not 4 in a row
        assertEquals(false, bm.puzzleSolved(0));//Empty tiles all around
        bm = makeRedOneMoveAwayBoard();
        assertEquals(false, bm.puzzleSolved(34));//3 in a diagonal row for red
        assertEquals(false, bm.puzzleSolved(31));//3 in a vertical row
        bm = makeRedWinVerticalBoard();
        assertEquals(true, bm.puzzleSolved(31));//red vertical win TODO
        assertEquals(true, bm.puzzleSolved(20));//Red diagonal win.
    }
    @Test
    public void checkDiagonals() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        bm.touchMove(12);
        assertEquals(false, bm.checkDiagonals(12));
        bm = makeRedWinDiagonalBoard();
        assertEquals(true, bm.checkDiagonals(15));
        bm = makeRedWinVerticalBoard();
        assertEquals(true, bm.checkDiagonals(13));
    }

    @Test
    public void checkSides() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertEquals(false, bm.checkSides(18));//Empty on right side
        assertEquals(false, bm.checkSides(26));//Various colors on the right
        bm = makeRedWinVerticalBoard();
        assertEquals(false, bm.checkSides(13));//Blanks on either side
        bm = makeRedWinHorizontal();
        assertEquals(true, bm.checkSides(33));
    }

    @Test
    public void numLeft() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        //TODO: Fix numLeft since it only returns 0
        assertEquals(1, bm.numLeft(2, 19));//1 black to the left
        assertEquals(0, bm.numLeft(2, 18));//at the absolute left
        assertEquals(2, bm.numLeft(1, 34));//different colors to the left
    }

    @Test
    public void numRight() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        //TODO: Fix NumRight since it only seems to return 0
        assertEquals(1, bm.numRight(1, 30));//multiple colors right
        assertEquals(0, bm.numRight(2, 35));//absolute right
        assertEquals(0, bm.numRight(1, 21));//Blanks at right
        assertEquals(1, bm.numRight(2, 20) );//Only black on right

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
        ConnectFourBoardManager bm = makeRedWinVerticalBoard();
        assertEquals(true, bm.checkEmptyTile(12));//Empty tile directly above another
        assertEquals(true, bm.checkEmptyTile(5));//Empty tile in middle of nowhere
        assertEquals(false, bm.checkEmptyTile(20));//Red tile
        assertEquals(false, bm.checkEmptyTile(32));//Black tile
    }

    @Test
    public void checkUnderneath() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        assertEquals(false, bm.checkUnderneath(0));//Not underneath
        assertEquals(true,  bm.checkUnderneath(12));//Is underneath
    }

    @Test
    public void gameDrawn() {
        ConnectFourBoardManager bm = makeRedWinDiagonalBoard();
        assertEquals(false, bm.gameDrawn());
        bm = makeEarlyBoard();
        assertEquals(false, bm.gameDrawn());
        //TODO: Write true tests for gameDrawn
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
    public ConnectFourBoardManager makeRedWinHorizontal(){
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        bm.touchMove(30);
        bm.touchMove(24);
        bm.touchMove(31);
        bm.touchMove(25);
        bm.touchMove(32);
        bm.touchMove(26);
        bm.touchMove(33);
        return bm;
    }
}