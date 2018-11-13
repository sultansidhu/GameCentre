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
import java.util.HashMap;

import static fall2018.csc2017.GameCentre.MovementController.username;

public class GestureDetectGridView extends GridView {
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
    private BoardManager boardManager;

    private int gameIndex;

    /*
    Overloaded Constructor that takes a Context
    */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    /*
    An initializer method
    */

    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                if (boardManager.isValidTap(position)) {
                    mController.processTapMovement(context, position);
                    HashMap<String, User> users = GameActivity.readObject();
                    assert users != null;
                    users.get(username).addState(boardManager.getBoard());
                    GameActivity.saveObject(users);
                    users = GameActivity.readObject();
                    assert users != null;
                    if ((BoardManager)peekBoardManager(users.get(username).getStack().peek()).puzzleSolved()) {
                        users.get(username).stopTimer();
                        GameActivity.saveObject(users);
                        System.out.println("Total Time: " + users.get(username).getTotalTime());
                        ScoreboardActivity.updateUserHighScore(username);
                        switchToScoreboardScreen();
                    }

                    return true;
                } else {
                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }


    public Object peekBoardManager(Board board) {
        Object peekedBoardManager = null;
        switch(gameIndex) {
            case 0:
                peekedBoardManager = new SlidingBoardManager(board);
            case 1:
                peekedBoardManager = new ShogiBoardManager(board);
            case 2:
                peekedBoardManager = new ConnectFourBoardManager(board);
        };
        return peekedBoardManager;
    }


    @Override
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

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    /*
    Switches to the scoreboard screen if a game is won
     */
    private void switchToScoreboardScreen()
    {
        Intent tmp = new Intent(GlobalApplication.getAppContext(), ScoreboardActivity.class);
        GlobalApplication.getAppContext().startActivity(tmp);
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
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
