package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

import java.util.HashMap;

public class ShogiGestureDetectGridView extends GestureDetectGridView {

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

    private ShogiBoardManager boardManager;

    /**
     *  Tile position selected by the user. -1 means no tile has been selected to move.
     */
    private int tileSelected = -1;

    /**
     * Username of user currently playing.
     */
    private String username;


    private FileManager fm;

    private int gameIndex = 1;

    private UserManager userManager = new UserManager();

    /*
    Overloaded Constructor that takes a Context
    */
    public ShogiGestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }
    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public ShogiGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }
    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */

    public ShogiGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

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
                if(!mController.processTapMovement(position, boardManager)) {
                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (boardManager.getChanged()) {
                        userManager.saveState(username, boardManager, gameIndex);
                        checkSolved(context, gameIndex);
                    }
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
                switchToLeaderBoardScreen(context);
            } else {
                ScoreboardActivity sc = new ScoreboardActivity();
                ScoreboardController scon = new ScoreboardController();
                int result = scon.generateUserScore(winner, gameIndex);
                switchToScoreboardScreen(context, result, winner);
            }
        }
    }

    /**
     * Switches to the leader-board screen after the game is won by
     * the guest
     * @param context the current context
     */
    private void switchToLeaderBoardScreen(Context context) {
        context.startActivity(new Intent(context, LeaderBoardActivity.class));
    }

    /**
     * Switches to the scoreboard screen if a game is won
     */
    private void switchToScoreboardScreen(Context context, int result, String username) {
        Intent intent = new Intent(context, ScoreboardActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("username", username);
        context.startActivity(intent);
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
    public void setBoardManager(ShogiBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
