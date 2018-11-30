package fall2018.csc2017.GameCentre;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;

public class FileManager implements Serializable {

    public FileManager()
    {
        HashMap<String, User> HMfromfile = readObject();
        if(HMfromfile == null) {
            saveObject(new HashMap<String, User>());
        }
    }

    public HashMap<String, User> readObject()
    {
        FileInputStream fis;
        ObjectInputStream objectIn;
        try
        {
            //fis = context.openFileInput("testFile.ser");
            fis = GlobalApplication.getAppContext().openFileInput("testFile2.ser");
            objectIn = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashMap<String, User> hashMapFromFile = (HashMap<String, User>)objectIn.readObject();

            objectIn.close();
            return hashMapFromFile;
        }
        catch(NullPointerException e){
            return new HashMap<String, User>();
        }
        catch(ClassCastException ca)
        {
            System.out.println("unable to Cast");
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("CLASS NOT FOUND WHILE READING FROM SERIALIZED FILE");
        }
        catch(FileNotFoundException f)
        {
            System.out.println("FILE NOT FOUND WHILE READING FROM SERIALIZED FILE");
        }
        catch(IOException e)
        {
            System.out.println("IO EXCEPTION WHILE READING FROM SERIALIZED FILE");
        }
        return null;

    }

    /**
     This method saves a user to the HashMap and writes the HashMap to the file
     @param user, the User object to add
     @param username, a String representing the username of the user to add
     @return null
     @throws null
     */

    public void saveUser(User user, String username)
    {
        HashMap<String, User> HMfromfile = readObject();
        HMfromfile.put(username, user);
        saveObject(HMfromfile);
    }

    public User getUser(String username) {
        HashMap<String, User> users = readObject();
        assert users != null;
        return users.get(username);
    }


    public Stack<Board> getStack(String username, int gameIndex) {
        return getUser(username).getGameStack(gameIndex);
    }


    public void saveObject(HashMap<String, User> hashMap)
    {
        FileOutputStream fos;
        ObjectOutputStream objectOut;

        try
        {
            //fos = context.openFileOutput("testFile.ser", Context.MODE_PRIVATE);
            if(GlobalApplication.getAppContext() == null) {
                System.out.println("CONTEXT NULL");
            } else {
                System.out.println("CONTEXT NOT NULL");
            }
            fos = GlobalApplication.getAppContext().openFileOutput("testFile2.ser", Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(hashMap);
            objectOut.close();

        } catch (NullPointerException e2) {
            e2.printStackTrace();
            System.out.println("CONTEXT IS NULL WHILE SAVING");
        }
        catch(FileNotFoundException e1)
        {
            System.out.println("FILE NOT FOUND WHILE SAVING TO SERIALIZED FILE");
        }
        catch(IOException e)
        {
            System.out.println("IO EXCEPTION WHILE SAVING TO SERIALIZED FILE");
        }

    }
}
