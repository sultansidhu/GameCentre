package fall2018.csc2017.GameCentre;

public interface Score
{

    int calculateUserScore(User user);

    void updateUserScore(User user, int newHighScore);

}
