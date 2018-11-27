package fall2018.csc2017.GameCentre;

import android.widget.Toast;

import java.util.HashMap;
public class LoginManager {
    private String personLoggedIn = null;
    private FileManager f1;
    private String p2LoggedIn = null;

    public LoginManager()
    {
        this.f1 = new FileManager();
        for(User user: f1.readObject().values())
        {
            if(user.getLoggedIn())
            {
                assert personLoggedIn == null; //we should only be setting this once
                personLoggedIn = user.getUsername();
            }
        }
        System.out.println(personLoggedIn);
    }

    /**
     * Returns the username of the person currently logged in
     * @return a String representing the username of the person currently logged in
     */
    public String getPersonLoggedIn()
    {
        return this.personLoggedIn;
    }

    /**
     This method instantiates a new object of type User and adds it to the HashMap.
     The HashMap is then saved in the serialized file.
     @param username: the username of this new account
     password: the password of this new account
     password2: the confirm password of this new account
     selQ: the security question of this new account
     ans: the answer to the user's security question
     @return null
     @throws null
     */

    public boolean create(String username, String password, String password2, String selQ, String ans)
    {
        if (username.equals("") || password.equals("") || ans.equals(""))
            makeToast("You must enter username, password, and security answer!");
        else if (!password.equals(password2))
            makeToast("Passwords do not match!");
        else if (f1.readObject().containsKey(username))
            makeToast("User Already Exists!");
        else {
            User newUser = new User(username, password, selQ, ans);
            HashMap<String, User> hm = f1.readObject();
            hm.put(username, newUser);

            f1.saveObject(hm);

            f1.saveObject(setUsersLoggedOut(f1.readObject())); // logs all users out and saves
            setLoggedInTrueAndSave(newUser); // changes loggedIn of current user to true and saves

            makeToast("Success!");
            return true;
        }
        return false;
    }

    /**
     * Returns true if the User whose username is username is present in the HashMap
     * and the password entered is correct, false otherwise
     * @param username, A String representing the username of the User.
     * @param password, A String representing the password entered by the user
     * @return true if password is correct and user exists, false otherwise
     * @throws null
     */

    public boolean authenticate(String username, String password)
    {
        if (!f1.readObject().containsKey(username))
            makeToast("User Does Not Exist!");
        else if(!f1.readObject().get(username).getPassword().equals(password))
            makeToast("Password Rejected!");
        else{
            f1.saveObject(setUsersLoggedOut(f1.readObject())); // logs all users out and saves
            setLoggedInTrueAndSave(f1.readObject().get(username)); // sets the loggedIn of the current user to true and saves

            personLoggedIn = username;
            makeToast("Logging in...");
            f1.saveObject(f1.readObject());
            return true;
        }
        return false;
    }

    /**
     * Returns true if the User whose username is username is present in the HashMap
     * and the password entered is correct, false otherwise
     * @param username, A String representing the username of the User
     * @param password, A String representing the password of the User
     * @return true if User exists and password is correct, false otherwise
     * @throws null
     */

    public boolean authenticateP2(String username, String password)
    {
        if (!f1.readObject().containsKey(username))
        {
            makeToast("User Does Not Exist!");
            return false;
        }
        else if(!f1.readObject().get(username).getPassword().equals(password))
        {
            makeToast("Password Rejected!");
            return false;
        }
        else {
            System.out.println("PRINTING AFTER PLAYER TWO HAS LOGGED IN");
            printPlayerLoggedInStatus(f1.readObject());
            return true;
        }
    }

    /**
     * This function iterates through the HashMap and logs all Users out
     * by changing the isLoggedIn attribute of all Users to false
     * @param theHashMap, the HashMap mapping username to User object
     * @return theHashMap, a HashMap where all the Users have been logged out
     */
    public HashMap<String, User> setUsersLoggedOut(HashMap<String, User> theHashMap)
    {
        if(personLoggedIn != null)
        {
            for (User user: theHashMap.values())
            {
                user.setLoggedIn(false);
            }
        }
        return theHashMap;
    }

    /**
     * This method iterates through the HashMap and prints the loggedInStatus of all users
     * by printing the .getLoggedIn() method result
     * @param hm, a HashMap mapping username (String) to the User object
     */

    public void printPlayerLoggedInStatus(HashMap<String, User> hm)
    { // ISSA TESTER FUNCTION TING
        for (User user: hm.values())
            System.out.println("WILL TELL IF THE USER ( " + user.getUsername() + " ) IS LOGGED IN: " + user.getLoggedIn());
    }

    /**
     * This method sets the User represented by user to logged in, by changing this
     * user's isLoggedIn attribute to true
     * @param user, a User object representing the current user
     */

    public void setLoggedInTrueAndSave(User user)
    {
        HashMap<String, User> hm = f1.readObject();
        hm.get(user.getUsername()).setLoggedIn(true);
        f1.saveObject(hm);
    }

    /**
     * This method takes a String and makes a Toast to display the String on the screen
     * @param textToDisplay, a String representing the text that must be toasted to the screen
     */

    public void makeToast(String textToDisplay)
    {
        Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();//TODO: Make GlobalApplication not static
    }

}

