package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
public class ConnectFourScoreTest {

    @Test
    public void calculateUserScoreTest() {
        ConnectFourScore cs = new ConnectFourScore();
        assertEquals(0, cs.calculateUserScore(0));

    }
}
