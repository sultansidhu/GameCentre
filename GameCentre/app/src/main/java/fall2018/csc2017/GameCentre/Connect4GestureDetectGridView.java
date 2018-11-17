package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.ConnectionRequest;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Connect4GestureDetectGridView extends GridView
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
     *  Tile position selected by the user.
     */
    private int tileSelected = -1;

    /**
     * Username of user currently playing.
     */
    private String username;

    /*
    Overloaded Constructor that takes a Context
    */
    public Connect4GestureDetectGridView(Context context) {
        super(context);
        init(context);
        LoginManager lm = new LoginManager();
        username = lm.getPersonLoggedIn();
    }

    private void init(final Context context)
    {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /*
            This function is invoked on every tap of the user
            */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = Connect4GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                Tile currTile = boardManager.getBoard().getTile(position / boardManager.getBoard().NUM_COLS, position % boardManager.getBoard().NUM_COLS);
                // TODO: CHANGE THE 7 TO A VARIABLE HOLDING THE SELECTED SIZE; THIS MUST BE PASSED THROUGH INTENTS

                if (boardManager.isValidTap(position))
                {
                    mController.processTapMovement(context, boardManager, position);
                    return true;
                }
                else
                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();


//                int tileOwner;
//                if (currTile.getBackground() == R.drawable.black) { tileOwner = 1; }
//                else if (currTile.getBackground() == R.drawable.red) {tileOwner = 2; }
//                else {tileOwner = 0; }
//
//                // CHECK IF THE TAP IS VALID
//                if (tileOwner == boardManager.getCurrPlayer()) {
//                    processTapMovement();
//                    }
//                    else {
//                        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else if (tileOwner == 0 && tileSelected != -1 && boardManager.isValidTap(tileSelected, position)) {
//                    mController.processTapMovement(context, boardManager, position);
//                    tileSelected = -1;
//                    boardManager.setCurrPlayer(3 - boardManager.getCurrPlayer());
//                    // TODO: autosave game
//                    return true;
//                }
//                else {
//                    Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//
//                return false;
                //}
                return false;
            }
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }


}
