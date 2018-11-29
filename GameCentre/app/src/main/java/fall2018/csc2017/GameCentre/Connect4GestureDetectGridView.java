package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class Connect4GestureDetectGridView extends GestureDetectGridView
{

    /*
     An int representing the minimum distance to swipe
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /*
    The GestureDetector object that will be used here
    */
    private GestureDetector gDetector;
    /*
    The MovementController object that will be used here
    */
    private MovementController mController;
    /*
    A boolean value representing if mFlingConfirmed
    */
    private boolean mFlingConfirmed = false;
    /*
    The X coordinate of the mTouch
    */
    private float mTouchX;
    /*
    The Y coordinate of the mTouch
    */
    private float mTouchY;

    /*
    An instance of BoardManager that will be used in this class
    */

    private ConnectFourBoardManager boardManager;

    /**
     * Username of user currently playing.
     */
    private String username;
   // private String usernameP2;

    /**
    The file manager instance
     */
    private FileManager fm;

    private int gameIndex = 2;

    /*
    Overloaded Constructor that takes a Context
    */
    public Connect4GestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager(context);
        username = lm.getPersonLoggedIn();
    }
    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public Connect4GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LoginManager lm = new LoginManager(context);
        username = lm.getPersonLoggedIn();
    }
    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */

    public Connect4GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        LoginManager lm = new LoginManager(context);
        username = lm.getPersonLoggedIn();
    }

    private void init(final Context context) {
        fm = new FileManager(context);
        mController = new MovementController(context);
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = Connect4GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                mController.processTapMovement(context, position, gameIndex);
                return true;
//                if (boardManager.isValidTap(position)) {
//                    mController.processTapMovement(context, boardManager, position);
//                    User user = fm.getUser(username);
//                    user.addState(boardManager.getBoard(), gameIndex);
//                    fm.saveUser(user, username);
//                    if (boardManager.checkFull() || boardManager.gameDrawn()) {
//                        switchToScoreboardScreen();
//                    }
//                    else if (boardManager.puzzleSolved(position)) {
//                        updateScoreboard(context, position);
//                        switchToScoreboardScreen();
//                    }
//                    return true;
//                }
//                else
//                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//                return false;
            }
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    public void updateScoreboard(Context context, int position) {
        ScoreboardActivity sc = new ScoreboardActivity();
        if (boardManager.getCurrentPlayer(position) == R.drawable.red) { // in this case the red won, so p1 won // this was changed from being 1
            sc.updateUserHighScore(username, gameIndex);
        }
        else{//black wins

            // what this needs is the username of the other player.

            String opponent = fm.getUser(username).getOpponents().get(gameIndex);
            if (!opponent.equals("Guest")){
                sc.updateUserHighScore(opponent, gameIndex);
            } else {
                Toast.makeText(context, "Guest won!", Toast.LENGTH_SHORT).show();
                // TODO: BOYS WE NEED A WAY TO DISPLAY THE SCORE THE USER MADE DURING THE GAME <- SULTAN SAID THAT
            }
        }
    }

    /*
    This function sets the BoardManager attribute of this class
    @param boardManager
    @return null
    */
    public void setBoardManager(ConnectFourBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {
            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }
}
