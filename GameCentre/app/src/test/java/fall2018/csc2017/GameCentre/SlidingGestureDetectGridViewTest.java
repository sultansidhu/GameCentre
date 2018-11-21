package fall2018.csc2017.GameCentre;

import android.provider.Settings;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingGestureDetectGridViewTest {

    @Test
    public void manageTap() {
        SlidingGestureDetectGridView sg = new SlidingGestureDetectGridView(GlobalApplication.getAppContext());
        SlidingBoardManager bm = new SlidingBoardManager(3);
        sg.setBoardManager(bm);
        int position = 0;
        while(!bm.isValidTap(position)){
            position++;
        }
        assertTrue(sg.manageTap(position));
        assertFalse(sg.manageTap(position)); //Can't tap same position twice in a row


    }
}