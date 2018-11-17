package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.Toast;

public class ShogiGestureDetectGridView extends GridView {

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
     * Returns whether a game piece is currently selected by the user.
     */
    private int tileSelected = -1;

    /*
    Overloaded Constructor that takes a Context
    */
    public ShogiGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }
    /*
    Overloaded Constructor that takes a Context and AttributeSet
    */
    public ShogiGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /*
    Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
    */

    public ShogiGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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
                Tile currTile = boardManager.getBoard().getTile(position%7, position%7);

                int proposedPlayer;
                if (currTile.getBackground() == R.drawable.black) { proposedPlayer = 1;}
                else if (currTile.getBackground() == R.drawable.red) {proposedPlayer = 2; }
                else {proposedPlayer = 0; }

                if (proposedPlayer == boardManager.getCurrPlayer()) {
                    if (tileSelected == -1) {
                        tileSelected = position;
                        return true;
                    }
                    else {
                        // TODO: implement isValidTap properly.
                        if (boardManager.isValidTap(position)) {
                            mController.processTapMovement(context, position);
                            return true;
                        } else { Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show() ;}
                    }


                }
                else {
                    Toast.makeText(context, "NOT YOUR TURN!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    public boolean peekBoardManagerSolved(Board board) {
        return new ShogiBoardManager(board).puzzleSolved();
    }


}
