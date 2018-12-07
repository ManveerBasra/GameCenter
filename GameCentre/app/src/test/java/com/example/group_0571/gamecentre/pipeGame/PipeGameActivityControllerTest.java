package com.example.group_0571.gamecentre.pipeGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewTreeObserver;

import com.example.group_0571.gamecentre.GamecentreMockContext;
import com.example.group_0571.gamecentre.SlidingTilesGameActivityControllerTest;
import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObserver;
import com.example.group_0571.gamecentre.mvcGameModel.GestureDetectGridView;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesGameActivityController;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesState;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.tile.BentPipeTile;
import com.example.group_0571.gamecentre.tile.DrawableTile;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;
import com.example.group_0571.gamecentre.tile.StraightPipeTile;
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
 * Class used to test PipeGameActivityController
 */
@RunWith(MockitoJUnitRunner.class)
public class PipeGameActivityControllerTest {

    /**
     * Mock context to be used to get resources
     */
    private Context context = new GamecentreMockContext();

    /**
     * Mockito mocked objects
     */
    @Mock
    private GestureDetectGridView gridView;

    /**
     * Objects sed to correctly and fully initialize gameActivityController
     */
    private PipeState state;
    private ScoreboardManager scoreboardManager;

    /**
     * GameActivityController to be tested.
     */
    private PipeGameActivityController gameActivityController;

    private final int DIMENSIONS = 5;

    /**
     * Make a List of tiles
     *
     * @return the tiles
     */
    private List<Tile> makeTiles() {
        List<Tile> tileList = new ArrayList<>();
        for (int i = 0; i != DIMENSIONS; i++) {
            tileList.add(new StartFinishPipeTile(context, true));
        }
        for (int x = 0; x != DIMENSIONS * (DIMENSIONS - 2); x++) {
            int tileDeterminant = (int) (Math.random() * 100);
            if (tileDeterminant % 2 == 0) {
                tileList.add(new BentPipeTile(context));
            } else {
                tileList.add(new StraightPipeTile(context));
            }
        }
        for (int y = 0; y != DIMENSIONS; y++) {
            tileList.add(new StartFinishPipeTile(context, false));
        }

        return tileList;
    }


    /**
     * Create gameActivityController with proper initialized values.
     */
    @Before
    public void setUp(){
        List<Tile> tiles = makeTiles();
        state = new PipeState(5, tiles);
        scoreboardManager = new ScoreboardManager(new ArrayList<User>());
        // setup GameActivityController
        gameActivityController = PipeGameActivityController.getInstance();
        gameActivityController.setStateManager(new PipeStateManager(state, "user"));
        gameActivityController.setScoreboardManager(scoreboardManager);
        gameActivityController.createTileButtons(context);
        gameActivityController.setupGridView(gridView);

    }

    /**
     * Ensure getInstance returns the same instance of the class
     */
    @Test
    public void testGetInstance() {
        PipeGameActivityController gameActivityController1 = PipeGameActivityController.getInstance();
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
        StateManager stateManager = new PipeStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        assertEquals(stateManager, gameActivityController.getStateManager());
    }

    /**
     * Ensure getStateManager correctly returns the stateManager
     */
    @Test
    public void testGetStateManager() {
        StateManager stateManager = new PipeStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        StateManager stateManager2 = gameActivityController.getStateManager();
        assertEquals(stateManager, stateManager2);
    }


    /**
     * Ensure setupGridView correctly sets-up gridView
     */
    @Test
    public void testSetupGridView() {
        StateManager stateManager = new PipeStateManager(state, "user");
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
        assertEquals(25, gameActivityController.getTileButtons().size());

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
     * Ensure update() works correctly with a non finished game.
     */
    @Test
    public void testUpdate_UnfinishedGame() {
        StateManager pipeStateManager;
        do {
            pipeStateManager = new PipeStateManager
                    (new PipeStateFactory(context).makeBoard(5), "user");
        } while (pipeStateManager.gameFinished());
        gameActivityController.setStateManager(pipeStateManager);

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