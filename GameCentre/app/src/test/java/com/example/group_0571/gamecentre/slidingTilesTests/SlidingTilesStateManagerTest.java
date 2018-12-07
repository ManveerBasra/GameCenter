package com.example.group_0571.gamecentre.slidingTilesTests;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesState;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.tile.Tile;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the SlidingTilesStateManager class.
 */
public class SlidingTilesStateManagerTest {
    /**
     * The SlidingTilesStateManager used for testing.
     */
    private SlidingTilesStateManager slidingTilesStateManager;

    /**
     * Create a state.
     *
     * @param solved whether the SlidingTilesState should be solved
     * @return a SlidingTilesState
     */
    private SlidingTilesState createSlidingTilesState(boolean solved) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            tiles.add(new DrawableTile(new GamecentreMockContext(), solved ? i : 9 - i));
        }
        tiles.add(new DrawableTile(new GamecentreMockContext(), DrawableTile.BLANK_TILE));
        return new SlidingTilesState(3, tiles, 0);
    }

    /**
     * Create a SlidingTilesStateManager with a solved state.
     */
    @Before
    public void setUp() {
        slidingTilesStateManager = new SlidingTilesStateManager(createSlidingTilesState(true), "");
    }

    /**
     * Test whether username was correctly set
     */
    @Test
    public void testGetUsername() {
        assertEquals("", slidingTilesStateManager.getUsername());
    }

    /**
     * Test whether gameFinished correctly returns if the tiles are in order.
     */
    @Test
    public void testGameFinished() {
        assertTrue(slidingTilesStateManager.gameFinished());
        slidingTilesStateManager = new SlidingTilesStateManager(createSlidingTilesState(false), "");
        assertFalse(slidingTilesStateManager.gameFinished());
    }

    /**
     * Test whether isValidTap correctly returns if a tap is valid.
     */
    @Test
    public void testIsValidTap() {
        assertTrue(slidingTilesStateManager.isValidTap(7));
        assertFalse(slidingTilesStateManager.isValidTap(6));
    }

    /**
     * Test whether touchMove correctly swaps tiles.
     */
    @Test
    public void testTouchMove() {
        SlidingTilesState state = (SlidingTilesState) slidingTilesStateManager.getState();
        slidingTilesStateManager.touchMove(7);
        assertEquals(DrawableTile.BLANK_TILE, state.getTile(2, 1).getId());
        slidingTilesStateManager.touchMove(4);
        assertEquals(DrawableTile.BLANK_TILE, state.getTile(1, 1).getId());
        slidingTilesStateManager.touchMove(5);
        assertEquals(DrawableTile.BLANK_TILE, state.getTile(1, 2).getId());
        slidingTilesStateManager.touchMove(8);
        assertEquals(DrawableTile.BLANK_TILE, state.getTile(2, 2).getId());
    }
}
