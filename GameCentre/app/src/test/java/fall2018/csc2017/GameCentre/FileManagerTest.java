package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileManagerTest {

    @Test
    public void readObject() {
        FileManager fm = new FileManager();
        assertNotNull(fm.readObject());
    }
}