package fall2018.csc2017.GameCentre;

import android.widget.Toast;

import java.util.HashMap;
public class LoginManager {
    private String personLoggedIn = null;
    private FileManager f1;

    public LoginManager(){
        this.f1 = new FileManager();
        for(User user: f1.getTheHashMap().values()){
            if(user.getLoggedIn()){
                assert personLoggedIn == null; //we should only be setting this once
                personLoggedIn = user.getUsername();
            }
        }
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
            makeToast("Data cannot be empty!");
        }
        else if (!password.equals(password2))
        {
            makeToast("Passwords do not match!");
        }
        else if (f1.getTheHashMap().containsKey(username))
        {
            makeToast("User Already Exists!");
        }
        else {
            User newUser = new User(username, password, selQ, ans);
            setUsersLoggedOut(f1.getTheHashMap());
            newUser.setLoggedIn(true);
            f1.getTheHashMap().put(username, newUser);

            f1.saveObject();
            makeToast("Success!");
            return true;
        }
        return false;
    }

    public boolean authenticate(String username, String password){
        if (!f1.getTheHashMap().containsKey(username)){
            makeToast("User Does Not Exist!");
        }
        else if(!f1.getTheHashMap().get(username).getPassword().equals(password)){
            makeToast("Password Rejected!");
        }

        else{
            setUsersLoggedOut(f1.getTheHashMap());
            f1.getTheHashMap().get(username).setLoggedIn(true);
            personLoggedIn = username;
            makeToast("Logging in...");
            f1.saveObject();
            //MovementController.username = username;
            return true;
        }
        return false;
    }
    public void setUsersLoggedOut(HashMap<String, User> theHashMap){
        if(personLoggedIn != null){
            User u1 = theHashMap.get(personLoggedIn);
            u1.setLoggedIn(false);
            personLoggedIn = null;
            //f1.saveObject();
        }
    }
    public void makeToast(String textToDisplay){
        System.out.println(textToDisplay);
        Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();//TODO: Make GlobalApplication not static
    }

}

