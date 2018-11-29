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

    private String username;

    private FileManager fm;

    private int gameIndex = 0;

    private Score score = new SlidingScore();

    /*
    Overloaded Constructor that takes a Context
    */
    public SlidingGestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager(context);

        username = lm.getPersonLoggedIn();
    }

    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public SlidingGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LoginManager lm = new LoginManager(context);

        username = lm.getPersonLoggedIn();
    }
    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */

    public SlidingGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        LoginManager lm = new LoginManager(context);

        username = lm.getPersonLoggedIn();
    }
    /*
    An initializer method
    */

    private void init(final Context context) {
        fm = new FileManager(context);
        mController = new MovementController(context);
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = SlidingGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                mController.processTapMovement(context, position, gameIndex);
                return true;
            }
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
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
//    public boolean manageTap(int position) {
//        if (boardManager.isValidTap(position)) {
//            mController.processTapMovement(GlobalApplication.getAppContext(), position);
//        }
//        else {
//            Toast.makeText(GlobalApplication.getAppContext(), "Invalid Tap", Toast.LENGTH_SHORT).show();
//        } return true;
//    }

}
