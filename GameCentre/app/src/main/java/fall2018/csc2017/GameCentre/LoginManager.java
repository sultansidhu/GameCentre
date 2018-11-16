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
            MovementController.username = username;
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

