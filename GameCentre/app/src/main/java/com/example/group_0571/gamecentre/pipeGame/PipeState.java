package com.example.group_0571.gamecentre.pipeGame;

import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.tile.PipeTile;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.List;

/**
 * The board/state to display on the grid for the pipe game
 */
public class PipeState extends State {

    /**
     * The row of the PipeTile that was last rotated
     */
    private int rowToUndo = -1;

    /**
     * The column of the PipeTile that was last rotated
     */
    private int colToUndo = -1;

    /**
     * The number of moves that have been made on the tile that was last moved
     */
    private int moveOnTarget = 0;

    /**
     * The number of undos that have been performed on the tile that was last moved
     */
    private int undoOnTarget = 0;

    /**
     * Create a new PipeState
     *
     * @param dimensions the dimensions of the PipeGame board
     * @param pipes      the list of pipes on the board
     */
    public PipeState(int dimensions, List<Tile> pipes) {
        super(dimensions, pipes, 1);
    }

    /**
     * Add the current state of the PipeState to previousBoards the rotate the tile at
     * the specified row and column counterclockwise
     *
     * @param row the row number -1 (the index in the outer array) of the tile to be rotated
     * @param col the column number -1 (the index of the tile in the inner array)
     */
    void rotateTile(int row, int col, boolean undo) {
        ((PipeTile) this.tiles[row][col]).rotate();
        if (!undo) {
            if (this.rowToUndo != row | this.colToUndo != col) {
                this.rowToUndo = row;
                this.colToUndo = col;
                this.moveOnTarget = 0;
                this.undoOnTarget = 0;
            }
            this.score++;
            moveOnTarget++;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean undoLastMove() {
        if (rowToUndo >= 0 && colToUndo >= 0 && this.undoOnTarget < this.moveOnTarget) {
            rotateTile(rowToUndo, colToUndo, true);
            rotateTile(rowToUndo, colToUndo, true);
            rotateTile(rowToUndo, colToUndo, true);
            this.score--;
            this.undoOnTarget++;
            return true;
        }
        return false;
    }

    @Override
    public void setUndoLimit(int undoLimit) {
        this.undoLimit = -1;
    }

    /**
     * get the undo limit for this PipeState
     *
     * @return the undoLimit
     */
    public int getUndoLimit() {
        return this.undoLimit;
    }
}
