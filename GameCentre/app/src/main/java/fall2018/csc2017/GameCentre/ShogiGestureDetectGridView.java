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
     *  Tile position selected by the user.
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
                System.out.println(Math.round(event.getX()));
                System.out.println("Y is");
                System.out.println(Math.round(event.getY()));
                System.out.println("position");
                System.out.println(position);
                System.out.println("Row is");
                System.out.println(position/Board.NUM_ROWS);
                System.out.println("Column is");
                System.out.println(position%Board.NUM_ROWS);
                return checkTap(position);
            }
            public boolean checkTap(int position){

                Tile currTile = boardManager.getBoard().getTile(position/Board.NUM_ROWS, position%Board.NUM_ROWS);

                int tileOwner;
                if (currTile.getBackground() == R.drawable.black) { tileOwner = 1; }
                else if (currTile.getBackground() == R.drawable.red) {tileOwner = 2; }
                else {tileOwner = 0; }

                // CHECK IF THE TAP IS VALID
                if (tileOwner == boardManager.getBoard().getCurrPlayer()) {
                    if (tileSelected == -1
                            || boardManager.getBoard().getTile(
                            tileSelected/Board.NUM_ROWS, tileSelected%Board.NUM_ROWS).getBackground()
                            == currTile.getBackground()) {
                        tileSelected = position;
                        return true;
                    }
                    else {
                        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (tileOwner == 0 && tileSelected != -1 && boardManager.isValidTap(tileSelected, position)) {
                    mController.processTapMovement(context, boardManager, tileSelected, position);
                    tileSelected = -1;
                    boardManager.getBoard().setCurrPlayer(3 - boardManager.getBoard().getCurrPlayer());
                    Toast.makeText(getContext(), "Player " + boardManager.getBoard().getCurrPlayer() + "'s turn", Toast.LENGTH_SHORT).show();
                    HashMap<String, User> users = fm.readObject();
                    assert users != null;
                    users.get(username).addState(boardManager.getBoard(), 1);
                    fm.saveObject(users);
                    if (boardManager.puzzleSolved()) {
                        ScoreboardActivity sc = new ScoreboardActivity();
                        if (boardManager.getBoard().getCurrPlayer() == 1) {//Player already swapped therefore p2
                            System.out.println("Line 143 S-Gest");
                            sc.updateUserHighScore(username, 1);
                            switchToScoreboardScreen();
                        }
                        else{
                            sc.updateUserHighScore(username, 1);//TODO: Fix this - Only the current username can win!
                            System.out.println("Line 149 S-Gest");
                            switchToScoreboardScreen();
                        }
                        //switchToScoreboardScreen();
                    }

                }
                else if (tileOwner != boardManager.getBoard().getCurrPlayer() && tileOwner != 0){
                    Toast.makeText(context, "It is Player " + boardManager.getBoard().getCurrPlayer() + "'s turn!", Toast.LENGTH_SHORT).show();
                }
// else if (){
//
//                }
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
