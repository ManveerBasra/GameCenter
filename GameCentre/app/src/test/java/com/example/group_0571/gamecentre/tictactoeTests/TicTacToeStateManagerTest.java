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
 * Unit tests for the TicTacToeStateManager class.
 */
public class TicTacToeStateManagerTest {
    /**
     * The TicTacToeState used for testing.
     */
    private TicTacToeState state;

    /**
     * The TicTacToeStateManager used for testing.
     */
    private TicTacToeStateManager stateManager;

    /**
     * Initialize the TicTacToeStateManager with an empty board.
     */
    @Before
    public void setUp() {
        GamecentreMockContext context = new GamecentreMockContext();
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tiles.add(new TicTacToeTile(context, TicTacToeTile.BLANK_TILE));
        }
        state = new TicTacToeState(3, tiles, 0);
        stateManager = new TicTacToeStateManager(state, "");
    }

    /**
     * Test whether isValidTap correctly returns whether a tap is on a blank tile.
     */
    @Test
    public void testIsValidTap() {
        assertTrue(stateManager.isValidTap(0));
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        assertFalse(stateManager.isValidTap(0));
    }

    /**
     * Test whether touchMove correctly sets the value of a tile depending on the player's turn.
     */
    @Test
    public void testTouchMove() {
        stateManager.touchMove(1);
        TicTacToeTile tile = (TicTacToeTile) state.getTile(0, 1);
        assertEquals(1, tile.getValue());

        stateManager.touchMove(2);
        tile = (TicTacToeTile) state.getTile(0, 2);
        assertEquals(2, tile.getValue());

        stateManager.touchMove(3);
        tile = (TicTacToeTile) state.getTile(1, 0);
        assertEquals(1, tile.getValue());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when a player
     * has three in a column.
     */
    @Test
    public void testGameFinishedColumnWin() {
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(1, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(2, 0)).setValue(1);

        assertTrue(stateManager.gameFinished());
        assertEquals(1, stateManager.getWinner());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when a player
     * has three in a row.
     */
    @Test
    public void testGameFinishedRowWin() {
        ((TicTacToeTile) state.getTile(0, 0)).setValue(2);
        ((TicTacToeTile) state.getTile(0, 1)).setValue(2);
        ((TicTacToeTile) state.getTile(0, 2)).setValue(2);

        assertTrue(stateManager.gameFinished());
        assertEquals(2, stateManager.getWinner());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when a player
     * has three in a diagonal.
     */
    @Test
    public void testGameFinishedDiagWin() {
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(1, 1)).setValue(1);
        ((TicTacToeTile) state.getTile(2, 2)).setValue(1);

        assertTrue(stateManager.gameFinished());
        assertEquals(1, stateManager.getWinner());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when a player
     * has three in an anti-diagonal.
     */
    @Test
    public void testGameFinishedAntiDiagWin() {
        ((TicTacToeTile) state.getTile(0, 2)).setValue(2);
        ((TicTacToeTile) state.getTile(1, 1)).setValue(2);
        ((TicTacToeTile) state.getTile(2, 0)).setValue(2);

        assertTrue(stateManager.gameFinished());
        assertEquals(2, stateManager.getWinner());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when there is a tie.
     */
    @Test
    public void testGameFinishedTie() {
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(0, 1)).setValue(2);
        ((TicTacToeTile) state.getTile(0, 2)).setValue(1);
        ((TicTacToeTile) state.getTile(1, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(1, 1)).setValue(2);
        ((TicTacToeTile) state.getTile(1, 2)).setValue(2);
        ((TicTacToeTile) state.getTile(2, 0)).setValue(2);
        ((TicTacToeTile) state.getTile(2, 1)).setValue(1);
        ((TicTacToeTile) state.getTile(2, 2)).setValue(1);

        assertTrue(stateManager.gameFinished());
        assertEquals(0, stateManager.getWinner());
    }

    /**
     * Test whether gameFinished correctly returns whether the game is finished when it is not.
     */
    @Test
    public void testGameFinishedUnfinished() {
        assertFalse(stateManager.gameFinished());
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        assertFalse(stateManager.gameFinished());
    }
}
