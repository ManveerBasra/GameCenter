package com.example.group_0571.gamecentre;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObservable;
import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObserver;
import com.example.group_0571.gamecentre.mvcGameModel.CustomAdapter;
import com.example.group_0571.gamecentre.mvcGameModel.GestureDetectGridView;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller for game activities.
 */
public abstract class GameActivityController implements GameActivityObservable, Observer {
    /**
     * GameActivity object associated with this controller
     */
    private GameActivityObserver observer;

    /**
     * Object to handle interactions with Scoreboard
     */
    protected ScoreboardManager scoreboardManager;

    /**
     * The SlidingTilesStateManager
     */
    protected StateManager stateManager;

    /**
     * List of views of clickable tiles
     */
    protected List<View> tileButtons;

    /**
     * GridView user interacts with, populated with tiles
     */
    private GestureDetectGridView gridView;

    /**
     * Values from gridView for tile view dimensions
     */
    private int columnWidth;
    private int columnHeight;

    /**
     * Get the clickable tile views on current gridView.
     *
     * @return the list of TileButtons
     */
    public List<View> getTileButtons() {
        return this.tileButtons;
    }

    /**
     * Setup StateManager, called from *GameActivity.OnCreate
     *
     * @param stateManager StateManager to handle interactions with State
     */
    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * Return StateManager, called from *GameActivity.OnCreate
     *
     * @return current StateManager object
     */
    public StateManager getStateManager() {
        return this.stateManager;
    }

    /**
     * Setup ScoreboardManager, called from *GameActivity.OnCreate
     *
     * @param scoreboardManager ScoreboardManager to handle interactions with scoreboard
     */
    public void setScoreboardManager(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    protected void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Setup GestureDetectGridView to handle user taps
     *
     * @param gestureDetectGridView grid view to display tile buttons
     */
    public void setupGridView(GestureDetectGridView gestureDetectGridView) {
        this.gridView = gestureDetectGridView;
        State board = stateManager.getState();
        gridView.setNumColumns(board.getNUM_COLS());
        gridView.setStateManager(this.stateManager);
        board.addObserver(this);
    }

    /**
     * Set up desired dimensions in gridView observer
     *
     * @param viewTreeObserver GridView's Observer
     */
    public void setupGridViewTreeObserver(ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                State board = stateManager.getState();
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                        this);
                columnWidth = gridView.getMeasuredWidth() / board.getNUM_COLS();
                columnHeight = gridView.getMeasuredHeight() / board.getNUM_ROWS();
                display();
            }
        });
    }

    /**
     * Create the view buttons for displaying the tiles.
     *
     * @param context the context of the current activity
     */
    public void createTileButtons(Context context) {
        tileButtons = new ArrayList<>();
        State board = stateManager.getState();
        for (int row = 0; row != board.getNUM_ROWS(); row++) {
            for (int col = 0; col != board.getNUM_COLS(); col++) {
                Tile tile = board.getTile(row, col);
                this.tileButtons.add(tile.getButton(context));
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    protected abstract void updateTileButtons();

    /**
     * Update State's undo limit with undo limit in saved preferences
     */
    public void updateScoreboardUsers(List<User> users) {
        this.scoreboardManager.getScoreboard().setUsers(users);
    }

    /**
     * Undo the last move made if game isn't yet finished.
     *
     * @return whether undo was successful or not
     */
    public boolean undoLastMove() {
        if (!stateManager.isFinished()) {
            return stateManager.getState().undoLastMove();
        }
        return false;
    }

    /**
     * Returns the user's score obtained from the number of moves
     *
     * @return the integer score value or 0 if it's a faulty score.
     */
    protected abstract int decodeScore(int moves);

    @Override
    public void registerObserver(GameActivityObserver observer) {
        this.observer = observer;
    }

    @Override
    public void notifyGameFinished(boolean newHighScore) {
        observer.onGameFinished(scoreboardManager.getScoreboard().getScoresByUser(), newHighScore);
    }

    @Override
    public void notifyUpdate() {
        observer.update();
    }

    @Override
    public abstract void update(Observable o, Object arg);
}
