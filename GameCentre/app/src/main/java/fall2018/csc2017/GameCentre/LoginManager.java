package fall2018.csc2017.GameCentre;

import android.widget.Toast;

import java.util.HashMap;
public class LoginManager {
    private String personLoggedIn = null;
    private FileManager f1;
    private String p2LoggedIn = null;

    public LoginManager(){
        this.f1 = new FileManager();
        for(User user: f1.readObject().values()){
            if(user.getLoggedIn()){
                assert personLoggedIn == null; //we should only be setting this once
                personLoggedIn = user.getUsername();
            }
        }
        System.out.println(personLoggedIn);
    }
    public LoginManager(FileManager f1){

    }
    public void setP2LoggedIn(String username){
        p2LoggedIn = username;
    }
    public String getP2LoggedIn(){
        return p2LoggedIn;
    }
    public String getPersonLoggedIn(){
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
     return null
     */

    public boolean create(String username, String password, String password2, String selQ, String ans)
    {
        if (username.equals("") || password.equals("") || ans.equals(""))
        {
            makeToast("You must enter username, password, and security answer!");
        }
        else if (!password.equals(password2))
        {
            makeToast("Passwords do not match!");
        }
        else if (f1.readObject().containsKey(username))
        {
            makeToast("User Already Exists!");
        }
        else {
            User newUser = new User(username, password, selQ, ans);
            HashMap<String, User> hm = f1.readObject();
            hm.put(username, newUser);

            f1.saveObject(hm);

            //System.out.println("NOW SHOWING BEFORE THE LOGGING OUT BEGINS");
            //printPlayerLoggedInStatus(f1.readObject());
            // ----------------------------------------------
//            setUsersLoggedOut(f1.readObject());
//            newUser.setLoggedIn(true);
            f1.saveObject(setUsersLoggedOut(f1.readObject())); // logs all users out and saves
            setLoggedInTrueAndSave(newUser); // changes loggedIn of current user to true and saves
            // ----------------------------------------------
            //System.out.println("NOW SHOWING AFTER THE LOGGING IN HAS BEEN DONE");
            //printPlayerLoggedInStatus(f1.readObject());
            // ----------------------------------------------

            makeToast("Success!");
            return true;
        }
        //makeToast("Unknown Error, try again!");
        return false;
    }

    public boolean authenticate(String username, String password){
        if (!f1.readObject().containsKey(username)){
            makeToast("User Does Not Exist!");
        }
        else if(!f1.readObject().get(username).getPassword().equals(password)){
            makeToast("Password Rejected!");
        }

        else{
            //System.out.println("NOW SHOWING BEFORE THE LOGGING OUT BEGINS");
            //printPlayerLoggedInStatus(f1.readObject());
            // --------------------------------------------------
            //setUsersLoggedOut(f1.readObject());
            f1.saveObject(setUsersLoggedOut(f1.readObject())); // logs all users out and saves
            setLoggedInTrueAndSave(f1.readObject().get(username)); // sets the loggedIn of the current user to true and saves
            //f1.readObject().get(username).setLoggedIn(true);
            // --------------------------------------------------
            //System.out.println("NOW SHOWING AFTER THE LOGGING IN HAS BEEN DONE");
            //printPlayerLoggedInStatus(f1.readObject());
            // -------------------------------------------------
            personLoggedIn = username;
            makeToast("Logging in...");
            f1.saveObject(f1.readObject());
            //MovementController.username = username;
            return true;
        }
        return false;
    }

    public boolean authenticateP2(String username, String password){
        if (!f1.readObject().containsKey(username)){
            makeToast("User Does Not Exist!");
            return false;
        }
        else if(!f1.readObject().get(username).getPassword().equals(password)){
            makeToast("Password Rejected!");
            return false;
        }
        else {
            //f1.readObject().get(username).setLoggedIn(true); // this was added to set the logged in status of the
            // second player to be true
            setLoggedInTrueAndSave(f1.readObject().get(username));
            System.out.println("PRINTING AFTER PLAYER TWO HAS LOGGED IN");
            printPlayerLoggedInStatus(f1.readObject());
            return true;
        }
    }


    // I MADE THIS FUNCTION TO GO THROUGH THE HASHMAP AND SIGN OUT EVERY USER OTHER
    // THAN THE USER THAT IS PLAYING RIGHT NOW
    public HashMap<String, User> setUsersLoggedOut(HashMap<String, User> theHashMap){
        if(personLoggedIn != null){
            for (User user: theHashMap.values()){
                user.setLoggedIn(false);
            }
           // User u1 = theHashMap.get(personLoggedIn);
            //u1.setLoggedIn(false);
            //personLoggedIn = null;
            //f1.saveObject();
        }
        return theHashMap;
    }

    public void printPlayerLoggedInStatus(HashMap<String, User> hm){ // ISSA TESTER FUNCTION TING
        for (User user: hm.values()) {
            System.out.println("WILL TELL IF THE USER ( " + user.getUsername() + " ) IS LOGGED IN: " + user.getLoggedIn());
        }
    }

    public void setLoggedInTrueAndSave(User user){
        HashMap<String, User> hm = f1.readObject();
        hm.get(user.getUsername()).setLoggedIn(true);
        f1.saveObject(hm);
    }


    public void makeToast(String textToDisplay)
    {
        System.out.println(textToDisplay);
        Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();//TODO: Make GlobalApplication not static
    }

}

