package com.example.group_0571.gamecentre.mvcGameModel;

import android.content.Context;
import android.widget.Toast;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.StateManager;

/**
 * Process tap movements on a StateManager.
 */
public class MovementController {
    /**
     * The StateManager to use.
     */
    private StateManager stateManager;

    /**
     * Set the StateManager.
     *
     * @param stateManager the StateManager
     */
    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Process a tap movement based on the position given.
     *
     * @param context  the Context from which a Toast should be shown
     * @param position the position where the tap occurred
     */
    public void processTapMovement(Context context, int position) {
        if (stateManager.isValidTap(position) && !stateManager.isFinished()) {
            stateManager.touchMove(position);
        } else if (stateManager.isValidTap(position) && stateManager.isFinished()) {
            Toast.makeText(context, context.getString(R.string.game_over), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.invalid_tap), Toast.LENGTH_SHORT).show();
        }
    }
}
