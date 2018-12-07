package com.example.group_0571.gamecentre.tictactoeTests;

import com.example.group_0571.gamecentre.GamecentreMockContext;
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
 * Unit tests for the TicTacToeState class.
 */
public class TicTacToeStateTest {
    /**
     * A MockContext used for testing.
     */
    private GamecentreMockContext context = new GamecentreMockContext();

    /**
     * The tictactoe state manager for testing.
     */
    private TicTacToeStateManager stateManager;

    /**
     * Some 'changeable' values needed for State instantiation.
     */
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 3;
    private static final String USERNAME = "user";

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = NUM_ROWS * NUM_COLS;
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new TicTacToeTile(context, TicTacToeTile.BLANK_TILE));
        }

        return tiles;
    }

    /**
     * Initialize variables needed for testing.
     */
    @Before
    public void setUp() {
        List<Tile> tiles = makeTiles();
        TicTacToeState state = new TicTacToeState(NUM_ROWS, tiles, 3);
        stateManager = new TicTacToeStateManager(state, USERNAME);
    }

    /**
     * Test whether getter for NUM_COLS returns the correct value.
     */
    @Test
    public void testGetNumCols() {
        assertEquals(NUM_COLS, stateManager.getState().getNUM_COLS());
    }

    /**
     * Test whether getter for NUM_ROWS returns the correct value.
     */
    @Test
    public void testGetNumRows() {
        assertEquals(NUM_ROWS, stateManager.getState().getNUM_ROWS());
    }

    /**
     * Test whether score is correct after two moves.
     */
    @Test
    public void testGetScore() {
        assertEquals(0, stateManager.getState().getScore());
        ((TicTacToeState) stateManager.getState()).makeMove(1, 1, false);
        assertEquals(1, stateManager.getState().getScore());
    }

    /**
     * Test whether makeMove changes correct tile.
     */
    @Test
    public void testMakeMove() {
        assertEquals(TicTacToeTile.BLANK_TILE, ((TicTacToeTile) stateManager.getState().getTile(1, 1)).getValue());
        ((TicTacToeState) stateManager.getState()).makeMove(1, 1, false);
        assertEquals(1, ((TicTacToeTile) stateManager.getState().getTile(1, 1)).getValue());
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);
        assertEquals(2, ((TicTacToeTile) stateManager.getState().getTile(1, 2)).getValue());
    }

    /**
     * Test whether isValidHelp works for invalid taps.
     */
    @Test
    public void testIsValidTapInvalidTap() {
        ((TicTacToeState) stateManager.getState()).makeMove(2, 2, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 0, false);
        assertFalse(stateManager.isValidTap(8));
        assertFalse(stateManager.isValidTap(0));
    }

    /**
     * Test whether isValidTap works for valid taps.
     */
    @Test
    public void testIsValidTapValidTap() {
        assertTrue(stateManager.isValidTap(0));
        assertTrue(stateManager.isValidTap(4));
    }

    /**
     * Test whether undo limit is properly set for a numeric limit.
     */
    @Test
    public void testSetUndoLimitNumericLimit() {
        stateManager.getState().setUndoLimit(0);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 0, false);
        assertFalse(stateManager.getState().undoLastMove());
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);
        assertFalse(stateManager.getState().undoLastMove());
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);
        stateManager.getState().setUndoLimit(3);
        assertTrue(stateManager.getState().undoLastMove());
    }

    /**
     * Test whether undo limit is properly set for an unlimited limit.
     */
    @Test
    public void testSetUndoLimitUnlimited() {
        stateManager.getState().setUndoLimit(-1);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);
        assertTrue(stateManager.getState().undoLastMove());
    }

    /**
     * Test whether undo-ing the last move works as expected.
     */
    @Test
    public void testUndoLastMove() {
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 0, false);
        assertEquals(1, ((TicTacToeTile) stateManager.getState().getTile(1, 2)).getValue());
        assertEquals(2, ((TicTacToeTile) stateManager.getState().getTile(0, 0)).getValue());
        stateManager.getState().undoLastMove();
        assertEquals(TicTacToeTile.BLANK_TILE, ((TicTacToeTile) stateManager.getState().getTile(0, 0)).getValue());
        assertEquals(1, ((TicTacToeTile) stateManager.getState().getTile(1, 2)).getValue());
    }

    /**
     * Test whether the game is finished with 3 in a row as a winner.
     */
    @Test
    public void testGameFinished() {
        assertFalse(stateManager.gameFinished());
        ((TicTacToeState) stateManager.getState()).makeMove(0, 0, false);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 0, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 1, false);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 1, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 2, false);
        assertTrue(stateManager.gameFinished());
    }

    /**
     * Test whether all tiles not being 0 is indicated as game being over.
     */
    @Test
    public void testFullTilesGameFinished() {
        assertFalse(stateManager.gameFinished());
        ((TicTacToeState) stateManager.getState()).makeMove(0, 0, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 1, false);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 0, false);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 1, false);
        ((TicTacToeState) stateManager.getState()).makeMove(0, 2, false);
        ((TicTacToeState) stateManager.getState()).makeMove(2, 0, false);
        ((TicTacToeState) stateManager.getState()).makeMove(2, 1, false);
        ((TicTacToeState) stateManager.getState()).makeMove(2, 2, false);
        ((TicTacToeState) stateManager.getState()).makeMove(1, 2, false);

        assertTrue(stateManager.gameFinished());
    }
}
