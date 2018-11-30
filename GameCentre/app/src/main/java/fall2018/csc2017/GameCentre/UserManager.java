package fall2018.csc2017.GameCentre;

import android.content.Context;

import java.util.Stack;

class UserManager {

    private FileManager fm;

    UserManager() {
        fm = new FileManager();
    }

    boolean processUndo(String username, int gameIndex) {
        User user = fm.getUser(username);
        Stack<Board> userStack = fm.getStack(username, gameIndex);
        if (user.getAvailableUndos(gameIndex) == 0 || userStack.size() == 1) {
            return false;
        }
        else {
            user.getGameStack(gameIndex).pop();
            user.setAvailableUndos(gameIndex, user.getAvailableUndos(gameIndex) - 1);
            fm.saveUser(user, username);
            return true;
        }
    }

    void addOpponent(int gameIndex, String opponent) {
        LoginManager lm = new LoginManager();
        String username = lm.getPersonLoggedIn();
        User user = fm.getUser(username);
        user.getOpponents().put(gameIndex, opponent);
        fm.saveUser(user, username);
    }

    void setUserUndos(String username, int undoLimit, int gameIndex, BoardManager boardManager) {
        User user = fm.getUser(username);
        user.setAvailableUndos(gameIndex, undoLimit);
        user.resetGameStack(gameIndex);
        user.addState(boardManager.getBoard(), gameIndex);
        fm.saveUser(user, username);
    }

}
