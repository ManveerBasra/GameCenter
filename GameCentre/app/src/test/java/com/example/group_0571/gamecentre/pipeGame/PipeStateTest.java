package com.example.group_0571.gamecentre.pipeGame;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.tile.PipeTile;

import org.junit.Test;

public class PipeStateTest {

    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * A PipeStateFactory for testing
     */
    private PipeStateFactory pipeStateFactory = new PipeStateFactory(context);

    /**
     * A PipeStateFactory for testing
     */
    private PipeState pipeState = pipeStateFactory.makeBoard(5);

    /**
     * Test that setUndoLimit sets the undoLimit to -1
     */
    @Test
    public void testSetUndoLimit () {
        for (int i = 0; i != 100; i++) {
            pipeState.setUndoLimit(i);
            assert (pipeState.getUndoLimit() == -1);
        }
    }

    /**
     * Test if rotateTile() rotates the correct tile
     */
    @Test
    public void testRotateTile() {
        int before = ((PipeTile)pipeState.getTile(2, 2)).getBackground();
        pipeState.rotateTile(2, 2,false );
        int after = ((PipeTile)pipeState.getTile(2, 2)).getBackground();
        assert (after != before);
    }

    /**
     * Test if undoLastMove allows user to undo correct number of times on specific tile
     */
    @Test
    public void testUndoLastMove () {
        pipeState.rotateTile(3, 3,false);
        pipeState.rotateTile(3, 3,false);
        assert (pipeState.undoLastMove());
        assert (pipeState.undoLastMove());
        assert (!pipeState.undoLastMove());
        pipeState.rotateTile(3, 3,false);
        pipeState.rotateTile(4, 4,false);
        assert (pipeState.undoLastMove());
        assert (!pipeState.undoLastMove());
    }
}
