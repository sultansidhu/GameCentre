package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConnectFourBoardManagerTest {

    @Test
    public void getBoard() {
        ConnectFourBoardManager bm = makeEarlyBoad();
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
    }

    @Test
    public void numRight() {
    }

    @Test
    public void checkUnder() {
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void checkEmptyTile() {
    }

    @Test
    public void checkUnderneath() {
    }

    @Test
    public void gameDrawn() {
    }

    @Test
    public void touchMove() {
    }

    public ConnectFourBoardManager makeEarlyBoad(){
        Integer[] moveList = {30, 24, 31, 18, 25, 32, 34, 26, 20, 33, 27, 21, 19};
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
        bm.touchMove(35);
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