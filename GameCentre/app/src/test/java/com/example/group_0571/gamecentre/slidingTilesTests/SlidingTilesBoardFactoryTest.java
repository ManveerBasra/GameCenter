package com.example.group_0571.gamecentre.slidingTilesTests;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesBoardFactory;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesImage;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesState;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.tile.Tile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class SlidingTilesBoardFactoryTest {

    /**
     * BoardFactory object to be tested
     */
    private SlidingTilesBoardFactory stateFactory;

    /**
     * Mock context to be used to get resources
     */
    private Context context = new GamecentreMockContext();

    /**
     * Mockito mocked preferences
     */
    @Mock
    private SharedPreferences preferences;

    /**
     * Setup state factory object, and SharedPreferences mock
     */
    @Before
    public void setUp() {
        stateFactory = new SlidingTilesBoardFactory(context, preferences);

        Mockito.when(preferences.getInt(eq("undo_limit"), anyInt())).thenReturn(3);
    }

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int dimension) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = dimension * dimension;
        for (int tileNum = 1; tileNum < numTiles; tileNum++) {
            tiles.add(new DrawableTile(context, tileNum));
        }
        tiles.add(new DrawableTile(context, DrawableTile.BLANK_TILE));

        return tiles;
    }

    /**
     * Test number of inversions on an odd sized board
     * <p>
     * Confirm whether the number of inversions yields a solvable board.
     */
    @Test
    public void testCheckSolvableSize3Board() {
        List<Tile> tiles = makeTiles(3);
        Tile temp = tiles.get(7);
        tiles.set(7, tiles.get(0));
        tiles.set(0, temp);
        assertFalse(stateFactory.isSolvable(tiles)); // due to odd number of inversions on an odd board
        temp = tiles.get(6);
        tiles.set(6, tiles.get(7));
        tiles.set(7, temp);
        assertTrue(stateFactory.isSolvable(tiles)); // due to even number of inversions on odd board
    }

    /**
     * Test number of inversions on an even sized board with blank tile located
     * on an odd index (counting from the bottom row)
     * <p>
     * Confirm whether the number of inversions yields a solvable board.
     */
    @Test
    public void testCheckSolvableSize4BoardBlankTileOdd() {
        List<Tile> tiles = makeTiles(4);
        Tile temp = tiles.get(15);
        tiles.set(15, tiles.get(0));
        tiles.set(0, temp);
        assertFalse(stateFactory.isSolvable(tiles));
    }

    /**
     * Test number of inversions on an even sized board with blank tile located
     * on an even index (counting from the bottom row)
     * <p>
     * Confirm whether the number of inversions yields a solvable board.
     */
    @Test
    public void testCheckSolvableSize4BoardBlankTileEven() {
        List<Tile> tiles = makeTiles(4);
        Tile temp = tiles.get(15);
        tiles.set(15, tiles.get(4));
        tiles.set(4, temp);
        assertTrue(stateFactory.isSolvable(tiles));
    }

    /**
     * Test makeBoard with DrawableTiles and an odd sized state.
     * <p>
     * Test whether score is initialized to 0, size is correctly set,
     * and whether game is not finished.
     */
    @Test
    public void testMakeBoardDrawableTilesSize3Board() {
        Mockito.when(preferences.getInt(eq("image type"), anyInt())).thenReturn(SlidingTilesImage.DEFAULT_TILES);
        SlidingTilesState state = stateFactory.makeBoard(3);

        assertEquals(0, state.getScore());
        assertEquals(3, state.getNUM_COLS());
        // Used to check whether state is finished
        SlidingTilesStateManager stateManager = new SlidingTilesStateManager(state, "user");
        assertFalse(stateManager.isFinished());
    }

    /**
     * Test makeBoard with DrawableTiles and an even sized state.
     * <p>
     * Test whether score is initialized to 0, size is correctly set,
     * and whether game is not finished.
     */
    @Test
    public void testMakeBoardDrawableTilesSize4Board() {
        Mockito.when(preferences.getInt(eq("image type"), anyInt())).thenReturn(SlidingTilesImage.DEFAULT_TILES);
        SlidingTilesState state = stateFactory.makeBoard(4);

        assertEquals(0, state.getScore());
        assertEquals(4, state.getNUM_COLS());
        // Used to check whether state is finished
        SlidingTilesStateManager stateManager = new SlidingTilesStateManager(state, "user");
        assertFalse(stateManager.isFinished());
    }

    /**
     * Test blank tile not found exception
     * <p>
     * Forcibly lead to exception state
     */
    @Test(expected = NoSuchElementException.class)
    public void testBlankTileNotFoundException() {
        List<Tile> tiles = makeTiles(4);
        tiles.set(15, tiles.get(0));
        stateFactory.isSolvable(tiles);
    }

}