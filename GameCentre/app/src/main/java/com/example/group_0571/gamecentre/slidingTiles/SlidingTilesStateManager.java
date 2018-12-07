package com.example.group_0571.gamecentre.slidingTiles;

import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTilesStateManager extends StateManager {
    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public SlidingTilesStateManager(State board, String username) {
        super(board, username);
    }

    @Override
    public boolean gameFinished() {
        boolean solved = true;
        if (getState() == null) return false;
        Iterator<Tile> tiles = ((SlidingTilesState) getState()).iterator();
        Tile last = tiles.next();
        while (tiles.hasNext()) {
            Tile newTile = tiles.next();
            if (last.compareTo(newTile) < 0) {
                solved = false;
            }
            last = newTile;
        }
        if (solved) {
            finished = true;
        }
        return solved;
    }

    @Override
    public boolean isValidTap(int position) {
        SlidingTilesState board = ((SlidingTilesState) getState());
        int row = position / board.getNUM_COLS();
        int col = position % board.getNUM_COLS();

        boolean validTap = false;
        List<Tile> surroundingTiles = getSurroundingTiles(row, col);
        for (int i = 0; i < 4; i++) {
            Tile currTile = surroundingTiles.get(i);
            if (currTile != null && currTile.getId() == Tile.BLANK_TILE) {
                validTap = true;
            }
        }
        return validTap;
    }

    @Override
    public void touchMove(int position) {
        SlidingTilesState board = ((SlidingTilesState) getState());
        int row = position / board.getNUM_ROWS();
        int col = position % board.getNUM_COLS();

        List<Tile> surroundingTiles = getSurroundingTiles(row, col);
        for (int i = 0; i < 4; i++) {
            Tile currTile = surroundingTiles.get(i);
            if (currTile != null && currTile.getId() == Tile.BLANK_TILE) {
                if (i == 0) {
                    board.swapTiles(row, col, row - 1, col, false);
                } else if (i == 1) {
                    board.swapTiles(row, col, row + 1, col, false);
                } else if (i == 2) {
                    board.swapTiles(row, col, row, col - 1, false);
                } else {
                    board.swapTiles(row, col, row, col + 1, false);
                }
            }
        }
    }
}
