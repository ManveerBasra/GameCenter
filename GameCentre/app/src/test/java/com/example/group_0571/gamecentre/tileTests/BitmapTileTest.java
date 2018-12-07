package com.example.group_0571.gamecentre.tileTests;

import android.graphics.Bitmap;

import com.example.group_0571.gamecentre.tile.BitmapTile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Unit tests for the BitmapTile class.
 */
@RunWith(MockitoJUnitRunner.class)
public class BitmapTileTest {
    /**
     * The Bitmap used for testing.
     */
    @Mock
    private Bitmap bitmap;

    /**
     * The BitmapTile used for testing.
     */
    private BitmapTile bitmapTile;

    /**
     * The id used for testing.
     */
    private static final int ID = 1;

    /**
     * Initialize the BitmapTile.
     */
    @Before
    public void initBitmap() {
        bitmapTile = new BitmapTile(ID, bitmap);
    }

    /**
     * Test whether the correct id is returned.
     */
    @Test
    public void testGetId() {
        assertEquals(ID, bitmapTile.getId());
    }
}
