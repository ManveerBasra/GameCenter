package com.example.group_0571.gamecentre;

import com.example.group_0571.gamecentre.tile.Tile;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public abstract class State extends Observable implements Serializable {
    /**
     * The number of rows.
     */
    private final int NUM_ROWS;

    /**
     * The number of rows.
     */
    private final int NUM_COLS;

    /**
     * The maximum number of times the user can undo
     * A value of -1 means they can undo an unlimited number of times.
     */
    protected int undoLimit;

    /**
     * The number of possible undos from current state.
     * A value < 0 means an unlimited number of undos.
     */
    protected int undosPossible;

    /**
     * The score of game (number of moves performed).
     */
    protected int score = 0;

    /**
     * The tiles on the board in row-major order.
     */
    protected Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public State(int dimension, List<Tile> tiles, int undoLimit) {
        this.NUM_ROWS = dimension;
        this.NUM_COLS = dimension;
        this.undoLimit = undoLimit;
        Iterator<Tile> iter = tiles.iterator();
        this.tiles = new Tile[NUM_ROWS][NUM_COLS];

        for (int row = 0; row != this.NUM_ROWS; row++) {
            for (int col = 0; col != this.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of columns in this board.
     *
     * @return the number of rows in this board
     */
    public int getNUM_COLS() {
        return NUM_COLS;
    }

    /**
     * Return the number of rows in this board.
     *
     * @return the number of rows in this board
     */
    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the current score of the game.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score. To be used in GameActivities once the user has won so they may
     * not abuse getting higher highscores.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Undo the last move completed.
     *
     * @return whether undo was possible.
     */
    public abstract boolean undoLastMove();

    /**
     * Set the maximum number of undos possible, and reset Stack and undosPossible.
     *
     * @param undoLimit maximum number of undos
     */
    public abstract void setUndoLimit(int undoLimit);

}