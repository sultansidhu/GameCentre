package fall2018.csc2017.GameCentre;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

//import static fall2018.csc2017.GameCentre.MovementController.username;

public class SlidingGestureDetectGridView extends GestureDetectGridView {
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
    private SlidingBoardManager boardManager;

    private String username = new LoginManager().getPersonLoggedIn();

    private int gameIndex = 0;

    private UserManager userManager = new UserManager();

    /*
    Overloaded Constructor that takes a Context
    */
    public SlidingGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public SlidingGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */
    public SlidingGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialization function for the gridview, contains instructions for every tap
     * of the user
     *
     * @param context the given context
     */
    private void init(final Context context) {
        mController = new MovementController(context);
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = SlidingGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                if(mController.processTapMovement(position, boardManager)) {
                    if (boardManager.getChanged()) {
                        userManager.saveState(username, boardManager, gameIndex);
                        checkSolved(context, gameIndex);
                    }
                }
                else {
                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * Checks if the board is solved, and thus whether the game is over
     * @param context   the current context
     * @param gameIndex the identity index of the game
     */
    private void checkSolved(Context context, int gameIndex) {
        if (boardManager.puzzleSolved()) {
            String winner = mController.getWinnerUsername(gameIndex);
            Toast.makeText(context, winner + " wins!", Toast.LENGTH_SHORT).show();
            if (winner.equals("Guest")) {
                switchToLeaderBoardScreen(context, winner);
            } else {
                ScoreboardController scon = new ScoreboardController();
                int result = scon.generateUserScore(winner, gameIndex);
                switchToScoreboardScreen(context, result, winner);
            }
        }
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

    /*
    This function sets the BoardManager attribute of this class
    @param boardManager
    @return null
    */
    public void setBoardManager(SlidingBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
