package com.example.group_0571.gamecentre.pipeGame;

import android.view.View;

import com.example.group_0571.gamecentre.GameActivityController;
import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.tile.PipeTile;

import java.util.Observable;

public class PipeGameActivityController extends GameActivityController {
    /**
     * Single Instance of this class to conform with Singleton Design Pattern
     */
    private static PipeGameActivityController single_instance;

    /**
     * Private constructor, only called once in first use of object.
     */
    private PipeGameActivityController() {
    }

    /**
     * Uses Singleton Design Pattern to maintain a single instance of class
     * across all handles to object.
     *
     * @return the only instance of SlidingTilesGameActivityController
     */
    public static PipeGameActivityController getInstance() {
        if (single_instance == null) {
            single_instance = new PipeGameActivityController();
        }
        return single_instance;
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        notifyUpdate();
        if (stateManager.gameFinished()) {
            User newUser = new User(stateManager.getUsername(), "Pipes",
                    decodeScore(stateManager.getState().getScore()));
            notifyGameFinished(scoreboardManager.addScore(newUser));
            stateManager.getState().resetScore(); // reset game board moves to 0
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    protected void updateTileButtons() {
        State state = stateManager.getState();
        int nextPos = 0;
        for (View v : tileButtons) {
            int row = nextPos / state.getNUM_ROWS();
            int col = nextPos % state.getNUM_COLS();
            PipeTile tile = ((PipeTile) state.getTile(row, col));
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
            return (int) Math.ceil(6431 / moves);
        }
        return 0;
    }
}
