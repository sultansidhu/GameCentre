package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class ShogiGestureDetectGridView extends GestureDetectGridView {

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

    private ShogiBoardManager boardManager;

    /**
     * Username of user currently playing.
     */
    private String username;

    /**
     * File manager to assist with writing and saving
     * hashmap data on .ser files
     */
    private FileManager fm;

    /**
     * The gameIndex identifies the game
     */
    private int gameIndex = 1;

    /**
     * Overloaded Constructor that takes a Context
     */
    public ShogiGestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * Overloaded Constructor that takes a Context and AttributeSet
     */
    public ShogiGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
     */

    public ShogiGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    /**
     * Initialization method to register user taps
     *
     * @param context the current context
     */
    private void init(final Context context) {
        fm = new FileManager();
        mController = new MovementController(context);
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = ShogiGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                mController.processTapMovement(context, position, gameIndex);
                return true;
//                return checkTap(context, position);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * Intercepts the touch events
     *
     * @param ev the MotionEvent
     * @return whether a touch was intercepted
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * This function sets the BoardManager attribute of this class
     *
     * @param boardManager the board manager for current shogi game
     */
    public void setBoardManager(ShogiBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
