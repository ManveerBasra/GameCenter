package com.example.group_0571.gamecentre;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewTreeObserver;

import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObserver;
import com.example.group_0571.gamecentre.mvcGameModel.GestureDetectGridView;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeGameActivityController;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeState;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeStateManager;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;
import com.example.group_0571.gamecentre.tile.Tile;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class used to test TicTacToeGameActivityController
 */
@RunWith(MockitoJUnitRunner.class)
public class TicTacToeGameActivityControllerTest {

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
     * Objects set to correctly and fully initialize gameActivityController
     */
    private TicTacToeState state;
    private ScoreboardManager scoreboardManager;

    /**
     * GameActivityController to be tested.
     */
    private TicTacToeGameActivityController gameActivityController;

    /**
     * Make a List of tiles
     *
     * @return the tiles
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 3 * 3;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TicTacToeTile(context, TicTacToeTile.BLANK_TILE));
        }
        return tiles;
    }

    /**
     * Create gameActivityController with proper initialized values.
     */
    @Before
    public void setUp() {
        List<Tile> tiles = makeTiles();
        state = new TicTacToeState(3, tiles, 3);
        scoreboardManager = new ScoreboardManager(new ArrayList<User>());
        Mockito.when(preferences.edit()).thenReturn(preferenceEditor);
        PreferenceHandler preferenceHandler = new PreferenceHandler(preferences, context);
        // setup GameActivityController
        gameActivityController = TicTacToeGameActivityController.getInstance();
        gameActivityController.setStateManager(new TicTacToeStateManager(state, "user"));
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
        TicTacToeGameActivityController gameActivityController1 = TicTacToeGameActivityController.getInstance();
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
        StateManager stateManager = new TicTacToeStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        assertEquals(stateManager, gameActivityController.getStateManager());
    }

    /**
     * Ensure getStateManager correctly returns the stateManager
     */
    @Test
    public void testGetStateManager() {
        StateManager stateManager = new TicTacToeStateManager(null, "user");
        gameActivityController.setStateManager(stateManager);
        StateManager stateManager2 = gameActivityController.getStateManager();
        assertEquals(stateManager, stateManager2);
    }


    /**
     * Ensure setupGridView correctly sets-up gridView
     */
    @Test
    public void testSetupGridView() {
        StateManager stateManager = new TicTacToeStateManager(state, "user");
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
        List<Tile> tiles = makeTiles();
        state = new TicTacToeState(3, tiles, 3);
        state.makeMove(0, 0, false);
        state.makeMove(0, 1, false);
        state.makeMove(1, 1, false);
        state.makeMove(1, 2, false);
        state.makeMove(2, 2, false);
        gameActivityController.setStateManager(new TicTacToeStateManager(state, "user"));
        assertFalse(gameActivityController.undoLastMove());
    }

    /**
     * Ensure adding an observer works when update() is called
     */
    @Test
    public void testRegisterObserver() {
        TicTacToeGameActivityControllerTest.MockGameActivityObserver mockObserver = new TicTacToeGameActivityControllerTest.MockGameActivityObserver();
        gameActivityController.registerObserver(mockObserver);
        gameActivityController.notifyUpdate();
        assertTrue(mockObserver.updated);
    }

    /**
     * Ensure notifyGameFinished works with a mock observer.
     */
    @Test
    public void testNotifyGameFinished() {
        TicTacToeGameActivityControllerTest.MockGameActivityObserver observer = new TicTacToeGameActivityControllerTest.MockGameActivityObserver();
        gameActivityController.registerObserver(observer);
        gameActivityController.notifyGameFinished(false);
        assertTrue(observer.gameFinished);
    }

    /**
     * Ensure notify update is called with a mock observer.
     */
    @Test
    public void testNotifyUpdate() {
        TicTacToeGameActivityControllerTest.MockGameActivityObserver observer = new TicTacToeGameActivityControllerTest.MockGameActivityObserver();
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
        ((TicTacToeTile) state.getTile(0, 0)).setValue(1);
        ((TicTacToeTile) state.getTile(1, 1)).setValue(1);
        ((TicTacToeTile) state.getTile(2, 2)).setValue(1);
        TicTacToeGameActivityController.getInstance().setStateManager(new TicTacToeStateManager(state, "user"));

        TicTacToeGameActivityControllerTest.MockGameActivityObserver observer = new TicTacToeGameActivityControllerTest.MockGameActivityObserver();
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
        List<Tile> tiles = makeTiles();
        state = new TicTacToeState(3, tiles, 3);
        gameActivityController.setStateManager(new TicTacToeStateManager(state, "user"));

        TicTacToeGameActivityControllerTest.MockGameActivityObserver observer = new TicTacToeGameActivityControllerTest.MockGameActivityObserver();
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
