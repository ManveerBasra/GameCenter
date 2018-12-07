package com.example.group_0571.gamecentre.pipeGame;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.tile.PipeTile;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;
import com.example.group_0571.gamecentre.tile.StraightPipeTile;
import com.example.group_0571.gamecentre.tile.Tile;

import org.junit.Test;

import java.util.ArrayList;

public class PipeStateManagerTest {

    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * A PipeStateFactory for testing
     */
    private PipeStateFactory pipeStateFactory = new PipeStateFactory(context);

    /**
     * A pipeStateManager for testing
     */
    private PipeStateManager pipeStateManager = new PipeStateManager
            (pipeStateFactory.makeBoard(6), "user");

    private ArrayList<Tile> makeSolvedBoard(int dimensions) {
        ArrayList<Tile> tileList = new ArrayList<>();
        for (int i = 0; i != dimensions; i++) {
            tileList.add(new StartFinishPipeTile(context, true));
        }
        for (int x = 0; x != dimensions * (dimensions - 2); x++) {
            tileList.add(new StraightPipeTile(context, R.drawable.vertical_pipe));
        }
        for (int y = 0; y != dimensions; y++) {
            tileList.add(new StartFinishPipeTile(context, false));
        }
        return tileList;
    }


    /**
     * Test gameFinished and associated helper methods
     */
    @Test
    public void testGameFinished() {
        for (int i = 5; i != 12; i++) {
            PipeState pipeState = new PipeState(i, makeSolvedBoard(i));
            pipeStateManager = new PipeStateManager(pipeState, "user");
            boolean finished = pipeStateManager.gameFinished();
            assert (finished);
        }

    }

    /**
     * Test that isValidTap always returns true
     */
    @Test
    public void testIsValidTap() {
        for (int i = 0; i != 25; i ++) {
            assert(pipeStateManager.isValidTap(i));
        }
    }

    /**
     * Test that touchMove moves rotates the correct tile
     */
    @Test
    public void touchMove() {
        do {
            pipeStateManager = new PipeStateManager
                    (pipeStateFactory.makeBoard(5), "user");
        } while (pipeStateManager.gameFinished());

        int before = ((PipeTile)(pipeStateManager.getState().getTile(1, 1)))
                .getBackground();
        pipeStateManager.touchMove(6);
        pipeStateManager.getState().getTile(1, 1);
        int after = ((PipeTile)(pipeStateManager.getState().getTile(1, 1)))
                .getBackground();
        assert (before != after);
    }

}
