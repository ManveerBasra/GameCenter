package com.example.group_0571.gamecentre.ticTacToe;

import android.view.View;

import com.example.group_0571.gamecentre.GameActivityController;

import com.example.group_0571.gamecentre.User;

import com.example.group_0571.gamecentre.tile.TicTacToeTile;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;

import java.util.Observable;

/**
 * Controller for SlidingTilesGameActivity, integrates Singleton Design Pattern
 */
public class TicTacToeGameActivityController extends GameActivityController {

    /**
     * Single Instance of this class to conform with Singleton Design Pattern
     */
    private static TicTacToeGameActivityController single_instance;
    /**
     * Object to handle interactions with SharedPreferences
     */
    private PreferenceHandler preferenceHandler;

    /**
     * Private constructor, only called once in first use of object.
     */
    private TicTacToeGameActivityController() {
    }

    /**
     * Uses Singleton Design Pattern to maintain a single instance of class
     * across all handles to object.
     *
     * @return the only instance of SlidingTilesGameActivityController
     */
    public static TicTacToeGameActivityController getInstance() {
        if (single_instance == null) {
            single_instance = new TicTacToeGameActivityController();
        }
        return single_instance;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    @Override
    protected void updateTileButtons() {
        TicTacToeState state = (TicTacToeState) this.stateManager.getState();
        int nextPos = 0;
        for (View v : tileButtons) {
            int row = nextPos / state.getNUM_ROWS();
            int col = nextPos % state.getNUM_COLS();
            TicTacToeTile tile = ((TicTacToeTile) state.getTile(row, col));
            v.setBackgroundResource(tile.getBackground());
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
            return (int) Math.ceil(4431 / moves);
        }
        return 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        notifyUpdate();
        if (stateManager.gameFinished()) {
            if (((TicTacToeStateManager) stateManager).getWinner() == 1) { // updates score if user won
                User newUser = new User(stateManager.getUsername(), "Tic Tac Toe",
                        decodeScore(stateManager.getState().getScore()));
                notifyGameFinished(scoreboardManager.addScore(newUser));
            }
            notifyGameFinished(false);
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
