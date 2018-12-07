package com.example.group_0571.gamecentre;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewTreeObserver;

import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObserver;
import com.example.group_0571.gamecentre.mvcGameModel.GestureDetectGridView;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesGameActivityController;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesState;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.tile.Tile;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class used to test SlidingTilesGameActivityController
 */
@RunWith(MockitoJUnitRunner.class)
public class SlidingTilesGameActivityControllerTest {

    /**
     * Mock context to be used to get resources
     */
    private Context context = new GamecentreMockContext();

    /**
     * Mockito mocked objects
     */
    @Mock
    private SharedPreferences preferences;
    @Mock
    private SharedPreferences.Editor preferenceEditor;
    @Mock
    private GestureDetectGridView gridView;

    /**
     * Objects sed to correctly and fully initialize gameActivityController
     */
    private SlidingTilesState state;
    private ScoreboardManager scoreboardManager;

    /**
     * GameActivityController to be tested.
     */
    private SlidingTilesGameActivityController gameActivityController;

    /**
     * Make a List of tiles, order depends on shuffle parameter
     *
     * @param shuffle whether or not to shuffle tiles out of order (unsolved)
     * @return the tiles
     */
    private List<Tile> makeTiles(boolean shuffle) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 3 * 3;
        for (int tileNum = 1; tileNum < numTiles; tileNum++) {
            tiles.add(new DrawableTile(context, tileNum));
        }
        tiles.add(new DrawableTile(context, DrawableTile.BLANK_TILE));
        if (shuffle) Collections.shuffle(tiles);

        return tiles;
    }

    /**
     * Create gameActivityController with proper initialized values.
     */
    @Before
    public void setUp() {
        List<Tile> tiles = makeTiles(false);
        state = new SlidingTilesState(3, tiles, 3);
        scoreboardManager = new ScoreboardManager(new ArrayList<User>());
        Mockito.when(preferences.edit()).thenReturn(preferenceEditor);
        PreferenceHandler preferenceHandler = new PreferenceHandler(preferences, context);
        // setup GameActivityController
        gameActivityController = SlidingTilesGameActivityController.getInstance();
        gameActivityController.setStateManager(new SlidingTilesStateManager(state, "user"));
        gameActivityController.setScoreboardManager(scoreboardManager);
        gameActivityController.createTileButtons(context);
        gameActivityController.setupGridView(gridView);
        gameActivityController.setPreferenceHandler(preferenceHandler);

    }

    /**
     * Ensure getInstance returns the same instance of the class
     */
    @Test
    public void testGetInstance() {
        SlidingTilesGameActivityController gameActivityController1 = SlidingTilesGameActivityController.getInstance();
        assertEquals(gameActivityController1, gameActivityController);
    }

    /**
     * Ensure setStateManager correctly sets a null stateManager
     */
    @Test
    public void testSetStateManager_Null() {
        gameActivityController.setStateManager(null);
        assertNull(gameActivityController.getStateManager());
    }

    /**
     * Ensure setStateManager correctly sets a non-null stateManager
     */
    @Test
    public void testSetStateManager_NotNull() {
        StateManager stateManager = new SlidingTilesStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        assertEquals(stateManager, gameActivityController.getStateManager());
    }

    /**
     * Ensure getStateManager correctly returns the stateManager
     */
    @Test
    public void testGetStateManager() {
        StateManager stateManager = new SlidingTilesStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        StateManager stateManager2 = gameActivityController.getStateManager();
        assertEquals(stateManager, stateManager2);
    }


    /**
     * Ensure setupGridView correctly sets-up gridView
     */
    @Test
    public void testSetupGridView() {
        StateManager stateManager = new SlidingTilesStateManager(state, "user");
        gameActivityController.setStateManager(stateManager);
        gameActivityController.setupGridView(gridView);
        assertEquals(0, gridView.getNumColumns());
    }

    /**
     * Ensure setupGridViewTreeObserver doesn't break gridView
     */
    @Test
    public void testSetupGridViewTreeObserver() {
        ViewTreeObserver treeObserver = Mockito.mock(ViewTreeObserver.class);
        gameActivityController.setupGridViewTreeObserver(treeObserver);
        assertNull(gridView.getViewTreeObserver());
    }

    /**
     * Ensure tileButtons are correctly initialized and have proper length.
     */
    @Test
    public void testCreateTileButtons() {
        gameActivityController.createTileButtons(context);
        assertEquals(9, gameActivityController.getTileButtons().size());

    }

    /**
     * Ensure updateScoreboardUsers correctly updates users in scoreboard.
     */
    @Test
    public void testUpdateScoreboardUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("user", "game", 0));
        gameActivityController.updateScoreboardUsers(users);
        assertEquals(users, scoreboardManager.getScoreboard().getScoresByUser());
    }


    /**
     * Ensure setting undo limit to 0 doesn't allow for any undos.
     */
    @Test
    public void testUpdateUndoLimit_NotNullPreferences() {
        PreferenceHandler preferenceHandler = new PreferenceHandler(preferences, context);
        gameActivityController.setPreferenceHandler(preferenceHandler);
        preferenceHandler.setUndoLimit(0);
        gameActivityController.updateUndoLimit();
        assertFalse(gameActivityController.undoLastMove());
    }

    /**
     * Ensure the user can't undo on a finished board.
     */
    @Test
    public void testUndoLastMove_FinishedGame() {
        List<Tile> tiles = makeTiles(true);
        state = new SlidingTilesState(3, tiles, 3);
        gameActivityController.setStateManager(new SlidingTilesStateManager(state, "user"));
        assertFalse(gameActivityController.undoLastMove());
    }

    /**
     * Ensure adding an observer works when update() is called
     */
    @Test
    public void testRegisterObserver() {
        MockGameActivityObserver mockObserver = new MockGameActivityObserver();
        gameActivityController.registerObserver(mockObserver);
        gameActivityController.notifyUpdate();
        assertTrue(mockObserver.updated);
    }

    /**
     * Ensure notifyGameFinished works with a mock observer.
     */
    @Test
    public void testNotifyGameFinished() {
        MockGameActivityObserver observer = new MockGameActivityObserver();
        gameActivityController.registerObserver(observer);
        gameActivityController.notifyGameFinished(false);
        assertTrue(observer.gameFinished);
    }

    /**
     * Ensure notify update is called with a mock observer.
     */
    @Test
    public void testNotifyUpdate() {
        MockGameActivityObserver observer = new MockGameActivityObserver();
        gameActivityController.registerObserver(observer);
        gameActivityController.notifyUpdate();
        assertTrue(observer.updated);
    }

    /**
     * Ensure update() works correctly when game is finished,
     * so notifyGameFinished() is also called.
     */
    @Test
    public void testUpdate_FinishedGame() {
        MockGameActivityObserver observer = new MockGameActivityObserver();
        gameActivityController.registerObserver(observer);
        gameActivityController.update(null, null);
        assertTrue(observer.updated);
        assertTrue(observer.gameFinished);
    }

    /**
     * Ensure update() works correctly with a non finished game.
     */
    @Test
    public void testUpdate_UnfinishedGame() {
        List<Tile> tiles = makeTiles(true);
        state = new SlidingTilesState(3, tiles, 3);
        gameActivityController.setStateManager(new SlidingTilesStateManager(state, "user"));

        MockGameActivityObserver observer = new MockGameActivityObserver();
        gameActivityController.registerObserver(observer);
        gameActivityController.update(null, null);
        assertTrue(observer.updated);
        assertFalse(observer.gameFinished);
    }

    /**
     * Mock GameActivityObserver used for testing
     */
    private class MockGameActivityObserver implements GameActivityObserver {

        /**
         * Dummy values for easy checks to whether overridden methods have been called.
         */
        private boolean updated = false;
        private boolean gameFinished = false;

        @Override
        public void onGameFinished(List<User> users, boolean newHighScore) {
            gameFinished = true;
        }

        @Override
        public void update() {
            updated = true;
        }
    }
}