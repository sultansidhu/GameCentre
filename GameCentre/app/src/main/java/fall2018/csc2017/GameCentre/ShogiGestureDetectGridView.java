package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Context;
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


    private FileManager fm = new FileManager();

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
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = ShogiGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                return checkTap(context, position);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

    public boolean checkTap(Context context, int position) {
        Tile currTile = boardManager.getBoard().getTile(position/Board.NUM_ROWS, position%Board.NUM_ROWS);
        int tileOwner = getTileOwner(currTile);
        if (isTurn(tileOwner)) {
            setTileToMove(context, position, currTile);
        }
        else if (tileOwner == 0 && tileSelected != -1 && boardManager.isValidTap(tileSelected, position)) {
            mController.processTapMovement(context, boardManager, tileSelected, position);
            resetTileSelected();
            switchPlayer();
            if (boardManager.puzzleSolved()) {
                updateScoreboard(context);
                switchToScoreboardScreen();
            }
        }
        else {
            makeInvalidToast(context, tileOwner);
        }
        return true;
    }

    public int getTileOwner(Tile currTile) {
        if (currTile.getBackground() == R.drawable.black) { return 1; }
        else if (currTile.getBackground() == R.drawable.red) {return 2; }
        else { return 0; }
    }

    public boolean isTurn(int tileOwner) {
        return tileOwner == boardManager.getBoard().getCurrPlayer();
    }

    public void setTileToMove(Context context, int position, Tile currTile) {
        if (tileSelected == -1
                || boardManager.getBoard().getTile(
                tileSelected/Board.NUM_ROWS, tileSelected%Board.NUM_ROWS).getBackground()
                == currTile.getBackground()) {
            tileSelected = position;
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateScoreboard(Context context) {
        ScoreboardActivity sc = new ScoreboardActivity();
        if (boardManager.getBoard().getCurrPlayer() == 2) {//Player already swapped therefore p2
            sc.updateUserHighScore(username, 1);
        }
        else {
            String opponent = fm.getUser(username).getOpponent();
            if (!opponent.equals("Guest")){
                sc.updateUserHighScore(opponent, 1);
            } else {
                Toast.makeText(context, "Guest won!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void makeInvalidToast(Context context, int tileOwner) {
        if (tileOwner != boardManager.getBoard().getCurrPlayer() && tileOwner != 0){
            Toast.makeText(context, "It is Player " + boardManager.getBoard().getCurrPlayer() + "'s turn!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetTileSelected() {
        tileSelected = -1;
    }

    public void switchPlayer() {
        boardManager.getBoard().setCurrPlayer(3 - boardManager.getBoard().getCurrPlayer());
        Toast.makeText(getContext(), "Player " + boardManager.getBoard().getCurrPlayer() + "'s turn", Toast.LENGTH_SHORT).show();
        User user = fm.getUser(username);
        user.addState(boardManager.getBoard(), 1);
        fm.saveUser(user, username);
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
    @Override
    public boolean peekBoardManagerSolved(Board board) {
        return new ShogiBoardManager(board).puzzleSolved();
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
