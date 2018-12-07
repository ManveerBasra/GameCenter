package com.example.group_0571.gamecentre.pipeGame;

import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.tile.PipeTile;
import com.example.group_0571.gamecentre.tile.StartFinishPipeTile;

/**
 * manages a PipeState
 */
public class PipeStateManager extends StateManager {

    public PipeStateManager(PipeState board, String username) {
        super(board, username);
    }

    @Override
    public boolean gameFinished() {
        if (getState() == null) return false;
        int cols = getState().getNUM_COLS();
        for (int i = 0; i != cols; i++) {
            PipeTile currTile = (PipeTile) getState().getTile(0, i);
            boolean isSolution = recFinishedHelper("bottom", currTile,
                    0, i, false);
            if (isSolution) {
                this.finished = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if there is a path coming from currTile that leads to a StartFinishPipeTile that
     * is an end tile
     *
     * @param omitOrientation the face of the tile on which to not look for a path
     * @param currTile        the tile we are on in the path
     * @param row             the row of our current tile
     * @param col             the column of our current tile
     * @param seenStart       true if we should terminate if we see another start tile
     * @return true iff a valid path can be made with PipeTiles from currTile to an end
     * StartFinishTile
     */
    private boolean recFinishedHelper(String omitOrientation, PipeTile currTile, int row, int col,
                                      boolean seenStart) {
        boolean solutions = false;
        if (currTile.getLinkableFaces().contains(omitOrientation)) {
            if (currTile instanceof StartFinishPipeTile &&
                    (currTile.getLinkableFaces().contains("top"))) {
                return true;
            } else if (currTile instanceof StartFinishPipeTile) {
                if (!seenStart) {
                    solutions = recFinishedHelper("top", (PipeTile) getState()
                            .getTile(row + 1, col), row + 1, col, true);
                } else {
                    return false;
                }
            } else if (omitOrientation.equals("bottom")) {
                solutions = checkNonTopPaths(currTile, row, col);
            } else if (omitOrientation.equals("top")) {
                solutions = checkNonBottomPaths(currTile, row, col);
            } else if (omitOrientation.equals("left")) {
                solutions = checkNonLeftPaths(currTile, row, col);
            } else if (omitOrientation.equals("right")) {
                solutions = checkNonRightPaths(currTile, row, col);
            }
        }
        return solutions;
    }

    /**
     * Check if there is a path to an end tile in a direction other than right (increasing column
     * index)
     *
     * @param currTile the tile we are on in the path
     * @param row      the row of our current tile
     * @param col      the column of our current tile
     * @return true iff there is a path to an ending StartFinishPipeTile
     */
    private boolean checkNonRightPaths(PipeTile currTile, int row, int col) {
        boolean solutions = false;
        if (currTile.getLinkableFaces().contains("bottom") |
                currTile.getLinkableFaces().contains("bottom")) {
            solutions = recFinishedHelper("top", (PipeTile) getState()
                    .getTile(row + 1, col), row + 1, col, true);
        } else if (currTile.getLinkableFaces().contains("top") |
                currTile.getLinkableFaces().contains("top")) {
            solutions = recFinishedHelper("bottom", (PipeTile) getState()
                    .getTile(row - 1, col), row - 1, col, true);
        } else if ((currTile.getLinkableFaces().contains("left") | currTile
                .getLinkableFaces()
                .contains("left")) && col != 0) {
            solutions = recFinishedHelper("right", (PipeTile) getState()
                    .getTile(row, col - 1), row, col - 1, true);
        }
        return solutions;
    }

    /**
     * Check if there is a path to an end tile in a direction other than left (decreasing column
     * index)
     *
     * @param currTile the tile we are on in the path
     * @param row      the row of our current tile
     * @param col      the column of our current tile
     * @return true iff there is a path to an ending StartFinishPipeTile
     */
    private boolean checkNonLeftPaths(PipeTile currTile, int row, int col) {
        boolean solutions = false;
        if (currTile.getLinkableFaces().contains("bottom") |
                currTile.getLinkableFaces().contains("bottom")) {
            solutions = recFinishedHelper("top", (PipeTile) getState()
                    .getTile(row + 1, col), row + 1, col, true);
        } else if ((currTile.getLinkableFaces().contains("right") | currTile
                .getLinkableFaces()
                .contains("right")) && col != getState().getNUM_COLS() - 1) {
            solutions = recFinishedHelper("left", (PipeTile) getState()
                    .getTile(row, col + 1), row, col + 1, true);
        } else if (currTile.getLinkableFaces().contains("top") |
                currTile.getLinkableFaces().contains("top")) {
            solutions = recFinishedHelper("bottom", (PipeTile) getState()
                    .getTile(row - 1, col), row - 1, col, true);
        }
        return solutions;
    }

    /**
     * Check if there is a path to an end tile in a direction other than down (decreasing row index)
     *
     * @param currTile the tile we are on in the path
     * @param row      the row of our current tile
     * @param col      the column of our current tile
     * @return true iff there is a path to an ending StartFinishPipeTile
     */
    private boolean checkNonBottomPaths(PipeTile currTile, int row, int col) {
        boolean solutions = false;
        if (currTile.getLinkableFaces().contains("bottom") |
                currTile.getLinkableFaces().contains("bottom")) {
            solutions = recFinishedHelper("top", (PipeTile) getState()
                    .getTile(row + 1, col), row + 1, col, true);
        } else if ((currTile.getLinkableFaces().contains("right") |
                currTile.getLinkableFaces().contains("right")) && col != getState()
                .getNUM_COLS() - 1) {
            solutions = recFinishedHelper("left", (PipeTile) getState()
                    .getTile(row, col + 1), row, col + 1, true);
        } else if ((currTile.getLinkableFaces().contains("left") |
                currTile.getLinkableFaces().contains("left")) && col != 0) {
            solutions = recFinishedHelper("right", (PipeTile) getState()
                    .getTile(row, col - 1), row, col - 1, true);
        }
        return solutions;
    }

    /**
     * Check if there is a path to an end tile in a direction other than up (increasing row index)
     *
     * @param currTile the tile we are on in the path
     * @param row      the row of our current tile
     * @param col      the column of our current tile
     * @return true iff there is a path to an ending StartFinishPipeTile
     */
    private boolean checkNonTopPaths(PipeTile currTile, int row, int col) {
        boolean solutions = false;
        if (currTile.getLinkableFaces().contains(("top")) |
                currTile.getLinkableFaces().contains("top")) {
            solutions = recFinishedHelper("bottom", (PipeTile) getState()
                    .getTile(row - 1, col), row - 1, col, true);
        } else if ((currTile.getLinkableFaces().contains(("right")) |
                currTile.getLinkableFaces().contains("right")) && col !=
                getState().getNUM_COLS() - 1) {
            solutions = recFinishedHelper("left", (PipeTile) getState()
                    .getTile(row, col + 1), row, col + 1, true);
        } else if ((currTile.getLinkableFaces().contains("left") | currTile
                .getLinkableFaces()
                .contains("left")) && col != 0) {
            solutions = recFinishedHelper("right", (PipeTile) getState()
                    .getTile(row, col - 1), row, col - 1, true);
        }
        return solutions;
    }

    @Override
    public boolean isValidTap(int position) {
        return true;
    }

    @Override
    public void touchMove(int position) {
        PipeState board = (PipeState) getState();
        int row = position / board.getNUM_ROWS();
        int col = position % board.getNUM_COLS();
        board.rotateTile(row, col, false);
    }
}
