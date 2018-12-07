package com.example.group_0571.gamecentre.tictactoeTests;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the TicTacToeTile class.
 */
public class TicTacToeTileTest {
    /**
     * The id used for testing.
     */
    private static final int ID = 1;

    /**
     * The TicTacToeTile used for testing.
     */
    private TicTacToeTile tile = new TicTacToeTile(new GamecentreMockContext(), ID);

    /**
     * Test whether the correct value is returned.
     */
    @Test
    public void testGetValue() {
        assertEquals(ID, tile.getValue());
    }

    /**
     * Test whether a new value is correctly set when the value is the blank tile id.
     */
    @Test
    public void testSetValueBlank() {
        tile.setValue(TicTacToeTile.BLANK_TILE);
        assertEquals(TicTacToeTile.BLANK_TILE, tile.getValue());
    }

    /**
     * Test whether a new value is correctly set when the value is not the blank tile id.
     */
    @Test
    public void testSetValueNotBlank() {
        tile.setValue(2);
        assertEquals(2, tile.getValue());
    }
}
