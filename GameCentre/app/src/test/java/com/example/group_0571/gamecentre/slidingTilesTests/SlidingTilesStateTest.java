package com.example.group_0571.gamecentre.slidingTilesTests;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesState;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.tile.Tile;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Unit tests for the SlidingTilesState class.
 */
public class SlidingTilesStateTest {
    /**
     * SlidingTilesState object used for testing.
     */
    private SlidingTilesState state;

    /**
     * Some 'changeable' values needed for SlidingTilesState instantiation.
     */
    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 4;

    /**
     * Context to get tile drawables from.
     */
    private GamecentreMockContext context = new GamecentreMockContext();

    /**
     * Make a List of tiles that are in order.
     *
     * @return the tiles
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = NUM_ROWS * NUM_COLS;
        for (int tileNum = 1; tileNum < numTiles; tileNum++) {
            tiles.add(new DrawableTile(context, tileNum));
        }
        tiles.add(new DrawableTile(context, DrawableTile.BLANK_TILE));

        return tiles;
    }

    /**
     * Initialize variables needed for testing.
     */
    @Before
    public void setUp() {
        List<Tile> tiles = makeTiles();
        state = new SlidingTilesState(NUM_ROWS, tiles, 3);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        state.swapTiles(0, 0, 0, 1, false);
    }

    /**
     * Test whether getter for NUM_COLS returns the correct value.
     */
    @Test
    public void testGetNumCols() {
        assertEquals(NUM_COLS, state.getNUM_COLS());
    }

    /**
     * Test whether getter for NUM_ROWS returns the correct value.
     */
    @Test
    public void testGetNumRows() {
        assertEquals(NUM_ROWS, state.getNUM_ROWS());
    }

    /**
     * Test whether getter for score returns the correct value.
     */
    @Test
    public void testGetScore() {
        swapFirstTwoTiles();
        assertEquals(1, state.getScore());
        state.resetScore();
        assertEquals(0, state.getScore());
    }

    /**
     * Test whether undo limit is properly set for a numeric limit.
     */
    @Test
    public void testSetUndoLimitNumericLimit() {
        state.setUndoLimit(0);
        assertFalse(state.undoLastMove());
        swapFirstTwoTiles();
        assertFalse(state.undoLastMove());
        swapFirstTwoTiles();
        state.setUndoLimit(3);
        assertTrue(state.undoLastMove());
    }

    /**
     * Test whether undo limit is properly set for an unlimited limit.
     */
    @Test
    public void testSetUndoLimitUnlimited() {
        state.setUndoLimit(-1);
        swapFirstTwoTiles();
        assertTrue(state.undoLastMove());
    }

    /**
     * Test whether undo-ing the last move works as expected.
     */
    @Test
    public void testUndoLastMove() {
        assertEquals(1, state.getTile(0, 0).getId());
        assertEquals(2, state.getTile(0, 1).getId());
        swapFirstTwoTiles();
        state.undoLastMove();
        assertEquals(1, state.getTile(0, 0).getId());
        assertEquals(2, state.getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        assertEquals(1, state.getTile(0, 0).getId());
        assertEquals(2, state.getTile(0, 1).getId());
        state.swapTiles(0, 0, 0, 1, false);
        assertEquals(2, state.getTile(0, 0).getId());
        assertEquals(1, state.getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        assertEquals(15, state.getTile(3, 2).getId());
        assertEquals(25, state.getTile(3, 3).getId());
        state.swapTiles(3, 3, 3, 2, false);
        assertEquals(25, state.getTile(3, 2).getId());
        assertEquals(15, state.getTile(3, 3).getId());
    }

    /**
     * Test whether the iterator throws NoSuchElementException when there are no more tiles to iterate over.
     */
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElement() {
        Iterator<Tile> iterator = state.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next();
        fail("NoSuchElementException not thrown");
    }
}
