package com.example.group_0571.gamecentre;

import com.example.group_0571.gamecentre.mvcGameModel.MovementController;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeState;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeStateManager;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;
import com.example.group_0571.gamecentre.tile.Tile;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the MovementController class.
 */
public class MovementControllerTest {
    /**
     * A MockContext used for testing.
     */
    private GamecentreMockContext context = new GamecentreMockContext();

    /**
     * The TicTacToeState used for testing.
     */
    private TicTacToeState state;

    /**
     * The MovementController used for testing.
     */
    private MovementController movementController;

    /**
     * Initialize the StateManager and MovementController.
     */
    @Before
    public void setUp() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tiles.add(new TicTacToeTile(context, TicTacToeTile.BLANK_TILE));
        }
        state = new TicTacToeState(3, tiles, 0);
        movementController = new MovementController();
        movementController.setStateManager(new TicTacToeStateManager(state, ""));
    }

    /**
     * Test whether processTapMovement correctly moves tiles on a board.
     */
    @Test
    public void testProcessTapMovement() {
        movementController.processTapMovement(context, 0);
        TicTacToeTile tile = (TicTacToeTile) state.getTile(0, 0);
        assertEquals(1, tile.getValue());
    }
}
