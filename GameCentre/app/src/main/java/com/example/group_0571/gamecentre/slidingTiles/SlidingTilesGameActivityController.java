package com.example.group_0571.gamecentre.slidingTiles;

import android.view.View;

import android.widget.ImageButton;

import com.example.group_0571.gamecentre.GameActivityController;
import com.example.group_0571.gamecentre.State;

import com.example.group_0571.gamecentre.User;

import com.example.group_0571.gamecentre.tile.BitmapTile;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;


import java.util.Observable;

/**
 * Controller for SlidingTilesGameActivity, integrates Singleton Design Pattern
 */
public class SlidingTilesGameActivityController extends GameActivityController {

    /**
     * Single Instance of this class to conform with Singleton Design Pattern
     */
    private static SlidingTilesGameActivityController single_instance;
    /**
     * Object to handle interactions with SharedPreferences
     */
    private PreferenceHandler preferenceHandler;

    /**
     * Private constructor, only called once in first use of object.
     */
    private SlidingTilesGameActivityController() {
    }

    /**
     * Uses Singleton Design Pattern to maintain a single instance of class
     * across all handles to object.
     *
     * @return the only instance of SlidingTilesGameActivityController
     */
    public static SlidingTilesGameActivityController getInstance() {
        if (single_instance == null) {
            single_instance = new SlidingTilesGameActivityController();
        }
        return single_instance;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    protected void updateTileButtons() {
        State board = stateManager.getState();
        boolean isDrawableTile = board.getTile(0, 0) instanceof DrawableTile;
        int nextPos = 0;
        for (View v : tileButtons) {
            int row = nextPos / board.getNUM_ROWS();
            int col = nextPos % board.getNUM_COLS();
            if (isDrawableTile) {
                DrawableTile tile = ((DrawableTile) board.getTile(row, col));
                v.setBackgroundResource(tile.getBackground());
            } else {
                BitmapTile tile = ((BitmapTile) board.getTile(row, col));
                ((ImageButton) v).setImageBitmap(tile.getBackground());
            }
            nextPos++;
        }
    }

    /**
     * Returns the user's score obtained from the number of moves
     *
     * @return the integer score value or 0 if it's a faulty score.
     */
    protected int decodeScore(int moves) {
        if (moves > 0) {
            return (int) Math.ceil(52431 / moves);
        }
        return 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        notifyUpdate();
        if (stateManager.gameFinished()) {
            User newUser = new User(stateManager.getUsername(), "Sliding Tiles",
                    decodeScore(stateManager.getState().getScore()));
            notifyGameFinished(scoreboardManager.addScore(newUser));
            stateManager.getState().resetScore(); // reset game board moves to 0
        }
    }

    /**
     * Setup PreferenceHandler, called from *GameActivity.OnCreate
     * Also get newest undo limit from preference handler
     *
     * @param preferenceHandler PreferenceHandler to handle interactions with preferences
     */
    public void setPreferenceHandler(PreferenceHandler preferenceHandler) {
        this.preferenceHandler = preferenceHandler;
        this.updateUndoLimit();
    }

    /**
     * Update State's undo limit with undo limit in saved preferences
     */
    public void updateUndoLimit() {
        if (preferenceHandler != null) {
            stateManager.getState().setUndoLimit(preferenceHandler.getUndoLimit());
        }
    }
}
