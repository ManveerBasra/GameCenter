package com.example.group_0571.gamecentre.pipeGame;

import android.content.Context;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;

import org.junit.Test;

public class PipeStateFactoryTest {

    /**
     * the mock context used for testing
     */
    private Context context = new GamecentreMockContext();

    /**
     * A PipeStateFactory for testing
     */
    private PipeStateFactory pipeStateFactory = new PipeStateFactory(context);

    /**
     * Test to make sure makeBoard makes a board of the correct size
     */
    @Test
    public void testMakeBoard() {
        for (int i = 5; i < 12; i ++) {
            PipeState board = pipeStateFactory.makeBoard(i);
            assert (board.getNUM_COLS() == i);
            assert (board.getNUM_ROWS() == i);
            for (int x = 1; x < i; x++) {
                assert (board.getTile(0, x - 1) instanceof StartFinishPipeTile);
                assert (board.getTile(i - 1, x - 1) instanceof StartFinishPipeTile);
            }
        }

    }
}
