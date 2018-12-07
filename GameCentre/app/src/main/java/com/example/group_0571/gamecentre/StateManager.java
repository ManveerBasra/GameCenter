package com.example.group_0571.gamecentre;

import com.example.group_0571.gamecentre.tile.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage a state.
 */
public abstract class StateManager implements Serializable {

    /**
     * The state being managed.
     */
    private State state;

    /**
     * The username of the user who is playing this game.
     */
    private String username;

    /**
     * Returns true if the game has been finished.
     */
    protected boolean finished;

    /**
     * Manage a state that has been pre-populated.
     *
     * @param state the state
     */
    public StateManager(State state, String username) {
        this.state = state;
        this.username = username;
        this.finished = gameFinished();
    }

    /**
     * Construct a StateManager with only a username
     *
     * @param username the user's username
     */
    public StateManager(String username) {
        this.username = username;
    }

    /**
     * Return the current state.
     *
     * @return the current state
     */
    public State getState() {
        return state;
    }

    /**
     * Return the user who was playing this game.
     *
     * @return the user who was playing this game
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return if the current game is finished, this is used when we need to stop the player
     * from making moves after a win.
     *
     * @return if the current game is finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Return whether the game is solved.
     *
     * @return whether the puzzle is solved
     */
    public abstract boolean gameFinished();

    /**
     * Return whether the clicked tile is valid
     *
     * @param position the tile to check
     * @return whether the clicked tile is valid
     */
    public abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the state, executing actions as appropriate.
     *
     * @param position the position
     */
    public abstract void touchMove(int position);

    /**
     * Gets surrounding tiles on the board
     *
     * @param row the row on board
     * @param col column on board
     * @return Tile objects surrounding position
     */
    protected List<Tile> getSurroundingTiles(int row, int col) {
        State board = this.getState();
        List<Tile> surroundingTiles = new ArrayList<>();
        // Add 4 surrounding tiles
        // above
        surroundingTiles.add(row == 0 ? null : board.getTile(row - 1, col));
        // below
        surroundingTiles.add(row == board.getNUM_ROWS() - 1 ? null : board.getTile(row + 1, col));
        // left
        surroundingTiles.add(col == 0 ? null : board.getTile(row, col - 1));
        // right
        surroundingTiles.add(col == board.getNUM_COLS() - 1 ? null : board.getTile(row, col + 1));
        return surroundingTiles;
    }
}
