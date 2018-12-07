package com.example.group_0571.gamecentre.ticTacToe;

import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;
import com.example.group_0571.gamecentre.tile.Tile;

import java.util.List;
import java.util.Stack;

/**
 * Makes and undoes moves on a TicTacToe game.
 */
public class TicTacToeState extends State {
    /**
     * Record turn number of game.
     */
    private int turn = 0;
    /**
     * Player one identifier int.
     */
    private static final int PLAYER_ONE = 1;
    /**
     * Player two identifier int.
     */
    private static final int PLAYER_TWO = 2;
    /**
     * Stack of previously executed moves.
     */
    private Stack<Integer[]> previousMoves = new Stack<>();

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param dimension the dimension of the board
     * @param tiles     the tiles for the board
     * @param undoLimit the maximum number of undos.
     */
    public TicTacToeState(int dimension, List<Tile> tiles, int undoLimit) {
        super(dimension, tiles, undoLimit);
    }

    /**
     * Updates the undo limit to undoLimit.
     *
     * @param undoLimit maximum number of undos
     */
    @Override
    public void setUndoLimit(int undoLimit) {
        this.undoLimit = undoLimit;
        if (undoLimit == -1) {
            undosPossible = -1;
        } else {
            // We want the user to be able to undo a maximum of undoLimit times
            if (previousMoves.size() >= undoLimit) {
                undosPossible = undoLimit;
            } else {
                undosPossible = previousMoves.size();
            }
        }
    }

    /**
     * Undo the last move executed in the game.
     *
     * @return if undo is successful
     */
    @Override
    public boolean undoLastMove() {
        if ((undoLimit == -1 || undosPossible > 0) && (!previousMoves.empty())) {
            Integer[] move = previousMoves.pop();
            makeMove(move[0], move[1], true);
            undosPossible--;
            return true;
        } else if (undosPossible == 0) {
            // if user can't undo anymore, we can reset the stack
            previousMoves = new Stack<>();
        }
        return false;
    }

    /**
     * Place an X or O at the given position
     *
     * @param row  tile row of move
     * @param col  tile column of move
     * @param undo if this is an undo or player move.
     */
    public void makeMove(int row, int col, boolean undo) {
        int player;
        if (undo) {
            player = TicTacToeTile.BLANK_TILE;
        } else {
            player = turn % 2 == 0 ? TicTacToeState.PLAYER_ONE : TicTacToeState.PLAYER_TWO;
            score = turn % 2 == 0 ? score + 1 : score;
            Integer[] move = {row, col};
            previousMoves.push(move);
            undosPossible = undosPossible < undoLimit ? undosPossible + 1 : undosPossible;
        }
        turn = undo ? turn - 1 : turn + 1;
        ((TicTacToeTile) tiles[row][col]).setValue(player);
        setChanged();
        notifyObservers();
    }
}
