//package fall2018.csc2017.GameCentre;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class MovementControllerTest {
//
//    @Test
//    public void processTapMovement() {
//        SlidingBoardManager bm = new SlidingBoardManager(3);
//        MovementController m = new MovementController();
//        m.setBoardManager(bm);
//        int position = 0;
//        while (!bm.isValidTap(position)){
//            position++;
//        }
//        m.processTapMovement(GlobalApplication.getAppContext(), position);
//        assertEquals(bm.getBoard().getPos(), position);
//    }
//
//    @Test
//    public void processTapMovement1() {
//        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
//        MovementController m = new MovementController();
//        m.processTapMovement(GlobalApplication.getAppContext(), bm, 30);
//        m.processTapMovement(GlobalApplication.getAppContext(), bm, 31);
//        assertNotEquals(bm.getTileColor(30), bm.getTileColor(31) );
//        assertNotEquals(bm.getTileColor(30), bm.getTileColor(32));
//        assertNotEquals(bm.getTileColor(31), bm.getTileColor(32));
//        assertEquals(bm.getTileColor(32), bm.getTileColor(33));
//    }
//
//    @Test
//    public void processTapMovement2() {
//        MovementController m = new MovementController();
//        ShogiBoardManager bm = new ShogiBoardManager(7);
//        m.processTapMovement(GlobalApplication.getAppContext(), bm, 0, 7);
//        m.processTapMovement(GlobalApplication.getAppContext(), bm, 42, 35);
//        assertTrue(bm.isBlack(1, 0));
//        assertTrue(bm.isRed(5, 0));
//        assertFalse(bm.isRed(2, 0));
//    }
//}