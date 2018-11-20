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

public class FileManager implements Serializable {

    private HashMap<String, User> theHashMap = new HashMap<>();

    public FileManager()
    {
        HashMap<String, User> HMfromfile = readObject();
        if(HMfromfile != null)
        {
            this.theHashMap = HMfromfile;
        }
        else
        {
            saveObject(new HashMap<String, User>());
        }

    }
    //public HashMap<String, User> readObject() {
        //return theHashMap;
    //}

//    public String returnLoggedInUsername()
//    {
//        // ITERATE THROUGH THE HASHMAP AND RETURN THE USERNAME
//        // OF THE USER THAT IS LOGGED IN
//        return "You still have to do this return loggedInUsername function, silly!";
//    }

    /**
     This method establishes a connection
     with the serialized file and reads the HashMap of user info from the file.
     The read HashMap is then assigned to the "logins" attribute of this class.
     return null
     */

    public HashMap<String, User> readObject()
    {
        FileInputStream fis;
        ObjectInputStream objectIn;
        try
        {
            fis = GlobalApplication.getAppContext().openFileInput("testFile.ser");
            objectIn = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashMap<String, User> hashMapFromFile = (HashMap<String, User>)objectIn.readObject();

            objectIn.close();
            return hashMapFromFile;
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

    public void saveObject(HashMap<String, User> hashMap)
    {
        FileOutputStream fos;
        ObjectOutputStream objectOut;

        try
        {
            fos = GlobalApplication.getAppContext().openFileOutput("testFile.ser", Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(hashMap);
            objectOut.close();

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
