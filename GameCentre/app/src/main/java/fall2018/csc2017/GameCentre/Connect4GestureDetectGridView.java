package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Connect4GestureDetectGridView extends GestureDetectGridView {

    /**
     * An int representing the minimum distance to swipe
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * The GestureDetector object that will be used here
     */
    private GestureDetector gDetector;
    /**
     * The MovementController object that will be used here
     */
    private MovementController mController;
    /**
     * A boolean value representing if mFlingConfirmed
     */
    private boolean mFlingConfirmed = false;
    /**
     * The X coordinate of the mTouch
     */
    private float mTouchX;
    /**
     * The Y coordinate of the mTouch
     */
    private float mTouchY;

    /**
     * An instance of BoardManager that will be used in this class
     */

    private ConnectFourBoardManager boardManager;

    /**
     * Username of user currently playing.
     */
    private String username;

    /**
     * In index of the game being played
     */
    private int gameIndex = 2;

    /**
     * An overloaded constructor for the Connect4 Gesture detector,
     * taking a context parameter
     *
     * @param context the given context
     */
    public Connect4GestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * Overloaded constructor for the Connect4 gesture detector taking an intent
     * and an attribute set
     *
     * @param context the given context
     * @param attrs   the attribute set for gesture detection
     */
    public Connect4GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * An overloaded constructor for the Connect4 gesture detector, taking a context,
     * an attribute set, and a style resource reference
     *
     * @param context      the given context
     * @param attrs        the attribute set
     * @param defStyleAttr the style resource reference
     */
    public Connect4GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * Initialization function for the gridview, contains instructions for every tap
     * of the user
     *
     * @param context the given context
     */
    private void init(final Context context) {
        //fm = new FileManager();
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
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * This function sets the BoardManager attribute of this class
     *
     * @param boardManager the board manager for the current game of Connect 4
     */
    public void setBoardManager(ConnectFourBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }

    /**
     * Captures the user's touch
     *
     * @param ev the MotionEvent
     * @return whether the touch is intercepted
     */
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

    /***
     * Assists the onInterceptTouchEvent in registering user
     * @param ev the MotionEvent
     * @return whether the touch is captured
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }
}
