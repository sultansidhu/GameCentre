package fall2018.csc2017.GameCentre;
/*
=======================================================================
File Name: MainActivity.java
Purpose: This is the first activity that executes when the app is run
This Activity is linked with the login.xml file, where users can either
log into their account or sign up for a new account.
Date: October 28, 2018
Group #: 0506
======================================================================= */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    /*
    This is a HashMap where the key is a String representing the user's username and
    the value is an object of type User, which holds the user's password, security question,
    and last GameState
    */
    private HashMap<String, User> logins = new HashMap<>();

    /**
     Invoked as soon as the app is run. This will load the login screen, read the HashMap
     from a serialized file, and initialize all the buttons on the screen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        readObject();

        addLoginButtonListener();
        addSignUpButtonListener();
        addForgotPasswordButtonListener();
        setSecurityQuestions();
    }

    /**
     This method will capture the user-entered text from the TextView widgets, and
     invoke the authenticate method upon click.
     return null
     */

    public void addLoginButtonListener()
    {
        Button loginButton = findViewById(R.id.btn_login);


        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TextView fieldUsername = findViewById(R.id.field_username);
                final String usernameToAuthenticate = fieldUsername.getText().toString().trim();
                TextView fieldPassword = findViewById(R.id.field_password);
                final String passwordToAuthenticate = fieldPassword.getText().toString().trim();
                //authenticate(usernameToAuthenticate, passwordToAuthenticate);
                LoginManager lm = new LoginManager();
                if (lm.authenticate(usernameToAuthenticate, passwordToAuthenticate)) {
                    gotoGameList();
                }
            }
        });
    }

    /**
     This method initializes the dropdown displaying the different security questions, and
     allows the user to choose
     return null
     */

    public void setSecurityQuestions()
    {
        Spinner securityQuestions = findViewById(R.id.securityquestions);
        String[] itemsForDropdown = new String[]{"What is your city of birth?",
        "Name of your High School?", "Favorite Teacher?", "First Pet's Name?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        securityQuestions.setAdapter(adapter);
    }

    /**
     This method initializes the 'Forgot Password?' button, captures the user-entered text from
     the TextFields and invokes the forgotPassword() method upon click
     return null
     */

    public void addForgotPasswordButtonListener()
    {
        Button forgotButton = findViewById(R.id.btn_forgot_password);

        forgotButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TextView fieldUsername = findViewById(R.id.field_username);
                try {
                    final String userToGetPassword = fieldUsername.getText().toString().trim();
                    TextView answerGiven = findViewById(R.id.field_answer);
                    final String answer = answerGiven.getText().toString().trim();
                    forgotPassword(userToGetPassword, answer);
                }
                catch(NullPointerException e){//This exception catches if the username entered does not exist
                    makeToast("You must enter in a valid username!");
                }
            }
        });
    }

    /**
     This method initializes the 'Sign Up' button, and invokes the create() method upon click
     return null
     */

    public void addSignUpButtonListener()
    {

        Button signupButton = findViewById(R.id.btn_su);
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                TextView fieldUsername = findViewById(R.id.field_username_su);
                final String usernameToAdd = fieldUsername.getText().toString().trim();
                TextView fieldPassword = findViewById(R.id.field_password_su);
                final String passwordToAdd = fieldPassword.getText().toString().trim();
                TextView fieldConfirmPassword = findViewById(R.id.field_confirmpassword_su);
                final String confirmPassword = fieldConfirmPassword.getText().toString().trim();

                TextView answerField = findViewById(R.id.field_answer);
                final String securityAnswer = answerField.getText().toString().trim();

                Spinner securityQuestions = findViewById(R.id.securityquestions);
                String selectedQuestion = securityQuestions.getSelectedItem().toString();

                if (usernameToAdd.equals("") || passwordToAdd.equals("") || securityAnswer.equals("")){
                    makeToast("Please enter data into all fields!");
                } else {
                    create(usernameToAdd, passwordToAdd, confirmPassword, selectedQuestion, securityAnswer);
                }
            }
        });
    }

    /**
     This method instantiates an Intent object to switch to the GameList
     return null
     */

    public void gotoGameList()
    {
        Intent tmp = new Intent(this, GameListActivity.class);
        startActivity(tmp);

    }

    /**
     This method toasts the user's password on the screen, if the answer entered is correct.
     Otherwise, it toasts an error message on the screen
     @param username: represents the username of the person for whom the password is
     to be fetched. answer: represents the answer to the user's security question.
     return none
     */

    public void forgotPassword(String username, String answer)
    {
        Spinner sec = findViewById(R.id.securityquestions);
        String selq = sec.getSelectedItem().toString();
        if(this.logins.get(username).getSecurityQuestion().equals(selq) && this.logins.get(username).getAnswer().equals(answer))
        {
            String password = this.logins.get(username).getPassword();
            makeToast(password);
        }
        else
            makeToast("Wrong Answer!");
    }

    /**
     This method saves the current HashMap of user info to the serialized file
     return null
     */

    public void saveObject()
    {
        FileOutputStream fos;
        ObjectOutputStream objectOut;

        try
        {
            fos = openFileOutput("testFile.ser", Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fos);
            objectOut.writeObject(logins);
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

    /**
     This method establishes a connection
     with the serialized file and reads the HashMap of user info from the file.
     The read HashMap is then assigned to the "logins" attribute of this class.
     return null
     */

    public void readObject()
    {
        FileInputStream fis;
        ObjectInputStream objectIn;
        try
        {
            fis = openFileInput("testFile.ser");
            objectIn = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashMap<String, User> hashMapFromFile = (HashMap<String, User>)objectIn.readObject();

            objectIn.close();
            this.logins = hashMapFromFile;

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

    }

    /**
     This method verifies if the user has entered the correct password. If the password is correct,
     the GameListActivity Screen is opened. Otherwise, an error message is displayed.
     @param username: the username of the person.
            password: the password the user has entered
     return null
     */

    public void authenticate(String username, String password)
    {
        if(!userExists(username))
        {
            makeToast("User Does Not Exist!");
        }
        else if (!logins.get(username).getPassword().equals(password))
        {
            makeToast("Password Rejected!");
        }
        else
        {
            makeToast("Logging in...");
            MovementController.username = username;
            logEveryoneOut(this.logins);
            readObject();
            this.logins.get(username).setLoggedIn(true);
            saveObject();
            gotoGameList();
        }
    }

    /**
     This method iterates through the HashMap and changes each user's "isLoggedIn" attribute
     to false.
     @param theMap: the HashMap holding the User object
     @return null
     @throws null
     */

    public void logEveryoneOut(HashMap theMap)
    {
        Iterator it = theMap.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            User u = (User)pair.getValue();
            u.setLoggedIn(false);
            this.logins.put((String)pair.getKey(), u);
            saveObject();

            it.remove(); // avoids a ConcurrentModificationException
        }

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

    public void create(String username, String password, String password2, String selQ, String ans)
    {
        if (!password.equals(password2)){
            makeToast("Passwords do not match!");
        } else if (userExists(username)){
            makeToast("User Already Exists!");
        } else {
            User newUser = new User(username, password, selQ, ans);
            logEveryoneOut(this.logins);
            newUser.setLoggedIn(true);
            logins.put(username, newUser);

            saveObject();
            makeToast("Success!");
            MovementController.username = username;

            gotoGameList();

        }
    }

    /**
     This method makes a Toast/displays text-based information to the user
     @param textToDisplay: the text to be displayed
     return null
     */

    public void makeToast(String textToDisplay)
    {
        Toast.makeText(getApplicationContext(), textToDisplay, Toast.LENGTH_LONG).show();
    }

    /**
     This method returns true if the user whose username is username already exists
     in the user info HashMap
     @param username: A String representing the username of the user
     @return boolean; true if user represented by username is in the HashMap, false otherwise
     */

    public boolean userExists(String username){
        return logins.containsKey(username);
    }

}
