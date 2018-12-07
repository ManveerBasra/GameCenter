package com.example.group_0571.gamecentre.ticTacToe;

import com.example.group_0571.gamecentre.State;
import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;

/**
 * Manage the state of a TicTacToe game.
 */
public class TicTacToeStateManager extends StateManager {
    /**
     * Handle to the winner.
     */
    private int winner;

    /**
     * Initialize the state manager with a state and username.
     *
     * @param state    the TicTacToe state
     * @param username the current user
     */
    public TicTacToeStateManager(State state, String username) {
        super(state, username);
    }

    /**
     * Get the winner of the game as an integer value 1 or 2.
     *
     * @return the winner
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Check the lower left to top right diagonal.
     *
     * @param s state
     * @param n size of board
     * @return true if user has won
     */
    private boolean checkAntiDiag(TicTacToeState s, int n) {
        int firstElement = ((TicTacToeTile) s.getTile(0, n - 1)).getValue();
        for (int x = 0; x < s.getNUM_ROWS(); x++) {
            if ((((TicTacToeTile) s.getTile(x, ((n - 1) - x))).getValue() == TicTacToeTile.BLANK_TILE) ||
                    (((TicTacToeTile) s.getTile(x, (n - 1) - x)).getValue() != firstElement)) {
                break;
            } else if (x == n - 1) {
                winner = ((TicTacToeTile) s.getTile(x, ((n - 1) - x))).getValue();
                finished = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Check the top left to lower right diagonal.
     *
     * @param s state
     * @param n size of board
     * @return true if user has won
     */
    private boolean checkDiag(TicTacToeState s, int n) {
        int firstElement = ((TicTacToeTile) s.getTile(0, 0)).getValue();
        for (int x = 0; x < n; x++) {
            if ((((TicTacToeTile) s.getTile(x, x)).getValue() == TicTacToeTile.BLANK_TILE) ||
                    (((TicTacToeTile) s.getTile(x, x)).getValue() != firstElement)) {
                break;
            } else if (x == n - 1) {
                winner = ((TicTacToeTile) s.getTile(x, x)).getValue();
                finished = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Check the columns.
     *
     * @param s state
     * @param n size of board
     * @return true if user has won
     */
    private boolean checkCols(TicTacToeState s, int n) {
        int firstElement;
        for (int x = 0; x < n; x++) {
            firstElement = ((TicTacToeTile) s.getTile(0, x)).getValue();
            for (int i = 0; i < n; i++) {
                if ((((TicTacToeTile) s.getTile(i, x)).getValue() == TicTacToeTile.BLANK_TILE) ||
                        (((TicTacToeTile) s.getTile(i, x)).getValue() != firstElement)) {
                    break;
                } else if (i == n - 1) {
                    winner = ((TicTacToeTile) s.getTile(i, x)).getValue();
                    finished = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check the rows.
     *
     * @param s state
     * @param n size of board
     * @return true if user has won
     */
    private boolean checkRows(TicTacToeState s, int n) {
        int firstElement;
        for (int x = 0; x < n; x++) {
            firstElement = ((TicTacToeTile) s.getTile(x, 0)).getValue();
            for (int i = 0; i < n; i++) {
                if ((((TicTacToeTile) s.getTile(x, i)).getValue() == TicTacToeTile.BLANK_TILE) ||
                        (((TicTacToeTile) s.getTile(x, i)).getValue() != firstElement)) {
                    break;
                } else if (i == n - 1) {
                    winner = ((TicTacToeTile) s.getTile(x, i)).getValue();
                    finished = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return if game is finished
     *
     * @return if game is finished
     */
    @Override
    public boolean gameFinished() {
        TicTacToeState s = (TicTacToeState) getState();
        if (s == null) return false;
        int n = s.getNUM_ROWS();
        // check finished
        if (checkRows(s, n) || checkCols(s, n) || checkDiag(s, n) || checkAntiDiag(s, n))
            return true;
        // check that there is at least one empty tile
        for (int x = 0; x < s.getNUM_ROWS(); x++) {
            for (int y = 0; y < s.getNUM_COLS(); y++) {
                if (((TicTacToeTile) s.getTile(x, y)).getValue() == TicTacToeTile.BLANK_TILE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if valid to make a move at position (row-major order)
     *
     * @param position the tile to check
     * @return if valid to make move
     */
    @Override
    public boolean isValidTap(int position) {
        TicTacToeState s = (TicTacToeState) getState();
        int row = position / s.getNUM_COLS();
        int col = position % s.getNUM_ROWS();

        return ((TicTacToeTile) s.getTile(row, col)).getValue() == TicTacToeTile.BLANK_TILE;
    }

    /**
     * Execute move at position (row-major order)
     *
     * @param position the position
     */
    @Override
    public void touchMove(int position) {
        TicTacToeState s = (TicTacToeState) getState();
        int row = position / s.getNUM_COLS();
        int col = position % s.getNUM_ROWS();

        s.makeMove(row, col, false);
    }
}
