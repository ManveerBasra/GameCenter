package com.example.group_0571.gamecentre.tileTests;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.tile.DrawableTile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the DrawableTile class.
 */
public class DrawableTileTest {
    /**
     * A MockContext used for testing.
     */
    private GamecentreMockContext context = new GamecentreMockContext();

    /**
     * The id used for testing.
     */
    private static final int ID = 1;

    /**
     * The DrawableTile used for testing.
     */
    private DrawableTile tile = new DrawableTile(context, ID);

    /**
     * Test whether the correct id is returned.
     */
    @Test
    public void testGetId() {
        assertEquals(ID, tile.getId());
    }

    /**
     * Test whether the correct background id is returned.
     */
    @Test
    public void testGetBackground() {
        assertEquals(GamecentreMockContext.IDENTIFIER, tile.getBackground());
    }

    /**
     * Test whether the correct drawable resource id is returned.
     */
    @Test
    public void testGetDrawableResource() {
        assertEquals(GamecentreMockContext.IDENTIFIER, tile.getDrawableResource(context,
                GamecentreMockContext.IDENTIFIER));
    }
}
