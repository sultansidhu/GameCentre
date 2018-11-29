package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import org.junit.Test;

public class SlidingScoreCalcTest
{

    @Test
    public void testCalculateUserScore()
    {
        SlidingScore s = new SlidingScore();
        assertEquals(350, s.calculateUserScore(10, 3));
        assertEquals(230, s.calculateUserScore(65, 4));
        assertEquals(260, s.calculateUserScore(200, 5));
    }


}
