//package fall2018.csc2017.GameCentre;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//import android.content.Context;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoJUnit.*;
//import org.mockito.runners.MockitoJUnitRunner;
////import org.mockito.junit.MockitoJUnitRunner;
//
////import static com.google.common.truth.Truth.assertThat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Stack;
//
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ShogiScoreCalcTest {
//    @Test
//    public void generateScore() {
//        ShogiScoreCalc s = new ShogiScoreCalc();
//        int score = s.generateScore(13, 5);
//        assertEquals(960, score);
//        score = s.generateScore(16, 4);
//        assertEquals(838, score);
//    }
//
//    @Test
//    public void getPieceDiff() {
//        User user = Mockito.mock(User.class);
//        Board board = makeRedIsWinner().getBoard();
//        Stack tempStack = new Stack();
//        FileManager fm = Mockito.mock(FileManager.class);
//        tempStack.add(board);
//        Mockito.when(user.getNumMoves(1)).thenReturn(16);
//        Mockito.when(user.getGameStack(1)).thenReturn(tempStack);
//        ShogiScoreCalc sc = new ShogiScoreCalc();
//        assertEquals(6, sc.getPieceDiff(board));
//
//    }
//    public ShogiBoardManager makeRedIsWinner(){
//        ShogiBoardManager bm = new ShogiBoardManager(7);
//        bm.touchMove(1, 22);
//        bm.touchMove(46, 32);
//        bm.touchMove(2, 23);
//        bm.touchMove(42, 21);
//        bm.touchMove(3, 24);
//        bm.touchMove(48, 41);
//        bm.touchMove(4, 25);
//        bm.touchMove(43, 36);
//        bm.touchMove(5, 26);
//        bm.touchMove(41, 27);
//        bm.touchMove(6, 1);
//        bm.touchMove(44, 43);
//        bm.touchMove(1, 29);
//        bm.touchMove(21, 22); //<-- Winning move for red
//        return bm;
//    }
//}