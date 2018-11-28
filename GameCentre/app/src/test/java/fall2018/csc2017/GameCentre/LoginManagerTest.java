package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnit.*;
import org.mockito.runners.MockitoJUnitRunner;
//import org.mockito.junit.MockitoJUnitRunner;

//import static com.google.common.truth.Truth.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.security.AccessController.getContext;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginManagerTest {

    @Test
    public void create() {
//        LoginManager lm = new LoginManager();
//        //File fis = GlobalApplication.getAppContext().openFileInput("testFile.ser");
//
//        assertEquals(false, lm.create("", "kapanen", "kapanen", "What is your city of birth?", "finland"));//first fail branch
//        assertEquals(false, lm.create("kasperi", "kapanen", "marleau", "What is your city of birth?", "finland"));//pwd not match
//        assertTrue(lm.create("gardiner", "kadri", "kadri", "What is your city of birth?", "finland"));
//        assertFalse(lm.create("gardiner", "kapanen", "kapanen", "What is your city of birth?", "finland"));//Same username
//        //TODO: Why does not mocked error appear
    }

    @Test
    public void authenticate() {

    }

    @Test
    public void authenticateP2() {
    }

    @Test
    public void setUsersLoggedOut() {
    }
}