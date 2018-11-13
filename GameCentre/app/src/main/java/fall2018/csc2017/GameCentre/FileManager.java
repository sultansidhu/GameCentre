package fall2018.csc2017.GameCentre;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class FileManager implements Serializable {

    private File USERS_MAP;

    public FileManager(File USERS_MAP) {
        this.USERS_MAP = USERS_MAP;
    }

    public void saveUser(User user, String username) {
        HashMap<String, User> users = loadUsersMap();
        users.put(username, user);
        users.writeObject();
    }

    public HashMap<String, User> loadUsersMap() {
        return USERS_MAP.readObject();
    }
}
