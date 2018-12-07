package com.example.group_0571.gamecentre;

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
 * Unit tests for the User class.
 */
public class UserTest {
    /**
     * StateManager used for testing.
     */
    private SlidingTilesStateManager stateManager;

    /**
     * Constants used for testing Users.
     */
    private static final String USERNAME1 = "username1";
    private static final String USERNAME2 = "username2";
    private static final String GAME = "game";
    private static final int SCORE = 0;

    /**
     * User objects for testing using the different constructors.
     */
    private User user1, user2, user3;

    /**
     * Create a SlidingTilesStateManager to pass into a User.
     *
     * @return a SlidingTilesStateManager
     */
    private SlidingTilesStateManager createStateManager() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new DrawableTile(new GamecentreMockContext(), 1));
        State board = new SlidingTilesState(1, tiles, 0);
        return new SlidingTilesStateManager(board, "");
    }

    /**
     * Initialize the User variables.
     */
    @Before
    public void setUp() {
        stateManager = createStateManager();
        user1 = new User(USERNAME1, stateManager);
        user2 = new User(USERNAME2, GAME, SCORE);
        user3 = new User(user2);
    }

    /**
     * Test whether the last state manager is correctly returned.
     */
    @Test
    public void testGetLastStateManager() {
        assertEquals(stateManager, user1.getLastStateManager());
        assertNull(user2.getLastStateManager());
        assertNull(user3.getLastStateManager());
    }

    /**
     * Test whether the last state manager is correctly updated.
     */
    @Test
    public void testSetLastStateManager() {
        SlidingTilesStateManager stateManager2 = createStateManager();
        user1.setLastStateManager(stateManager2);
        assertEquals(stateManager2, user1.getLastStateManager());
        assertNull(user2.getLastStateManager());
        assertNull(user3.getLastStateManager());
    }

    /**
     * Test whether the username is correctly returned.
     */
    @Test
    public void testGetUsername() {
        assertEquals(USERNAME1, user1.getUsername());
        assertEquals(USERNAME2, user2.getUsername());
        assertEquals(USERNAME2, user3.getUsername());
    }

    /**
     * Test whether the list of games is correctly returned.
     */
    @Test
    public void testGetGame() {
        List<String> game = new ArrayList<>();
        game.add(GAME);
        assertNull(user1.getGame());
        assertEquals(game, user2.getGame());
        assertEquals(game, user3.getGame());
    }

    /**
     * Test whether the list of scores is correctly returned.
     */
    @Test
    public void testGetScore() {
        List<Integer> score = new ArrayList<>();
        score.add(SCORE);
        assertNull(user1.getScore());
        assertEquals(score, user2.getScore());
        assertEquals(score, user3.getScore());
    }

    /**
     * Test whether the maximum score is correctly returned.
     */
    @Test
    public void testGetMaxScore() {
        assertEquals(SCORE, user2.getMaxScore());
        assertEquals(SCORE, user3.getMaxScore());
    }

    /**
     * Test whether Users are correctly compared.
     */
    @Test
    public void testCompareTo() {
        User u = new User("u", "game", 5);
        assertTrue(u.compareTo(user2) < 0);
    }

    /**
     * Test whether Users are correctly determined to be equal.
     */
    @Test
    public void testEquals() {
        User user1Alias = user1;
        assertEquals(user1, user1Alias);
        assertNotEquals(user1, null);
        assertNotEquals(user1, "");
        assertEquals(user2, user3);
    }
}
