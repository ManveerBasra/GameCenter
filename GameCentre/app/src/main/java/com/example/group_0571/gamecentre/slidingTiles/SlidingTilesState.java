package com.example.group_0571.gamecentre.slidingTiles;

import android.support.annotation.NonNull;

import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The sliding tiles board.
 */
public class SlidingTilesState extends State implements Iterable<Tile> {
    /**
     * A stack of previous moves.
     */
    private Stack<Integer[]> previousMoves = new Stack<>();

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public SlidingTilesState(int dimension, List<Tile> tiles, int undoLimit) {
        super(dimension, tiles, undoLimit);
    }

    /**
     * Set the maximum number of undos possible, and reset Stack and undosPossible.
     *
     * @param undoLimit maximum number of undos
     */
    public void setUndoLimit(int undoLimit) {
        this.undoLimit = undoLimit;
        if (undoLimit == -1) { // unlimited number of undos
            undosPossible = -1;
        } else {
            // We want the user to be able to undo a maximum of undoLimit times
            undosPossible = previousMoves.size() >= undoLimit ? undoLimit : previousMoves.size();
        }
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2).
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     * @param undo true if undoing last move
     */
    public void swapTiles(int row1, int col1, int row2, int col2, boolean undo) {
        Tile oldTile = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = oldTile;
        if (undo) {
            this.score -= 1;
        } else {
            this.score += 1;
            Integer[] move = {row1, col1, row2, col2};
            previousMoves.push(move);
            if (undosPossible < undoLimit) {
                undosPossible++;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Undo the last move completed.
     *
     * @return whether undo was possible.
     */
    public boolean undoLastMove() {
        if ((!previousMoves.empty()) && (undoLimit == -1 || undosPossible > 0)) {
            Integer[] move = previousMoves.pop();
            swapTiles(move[0], move[1], move[2], move[3], true);
            undosPossible--;
            return true;
        } else if (undosPossible == 0) {
            // if user can't undo anymore, we can reset the stack
            previousMoves = new Stack<>();
        }
        return false;
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new SlidingTilesStateTilesIterator();
    }

    /**
     * Iterate over tiles on the board.
     */
    private class SlidingTilesStateTilesIterator implements Iterator<Tile> {
        /**
         * The index number of the next tile, counted row-major order.
         */
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return this.nextIndex < (getNUM_COLS() * getNUM_ROWS());
        }

        @Override
        public Tile next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more tiles to return.");
            }

            int row = this.nextIndex / getNUM_COLS();
            int col = this.nextIndex % getNUM_COLS();
            this.nextIndex++;
            return getTile(row, col);
        }
    }
}
