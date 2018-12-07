package com.example.group_0571.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a User object with username, game, and score attributes, able to be serializable
 * and compared using maximum scores of the score attribute.
 */
public class User implements Serializable, Comparable<User> {
    /**
     * The unique username associated with the User data.
     */
    private String username;

    /**
     * List of Strings of the games that the user has played. (parallel list with score).
     */
    private List<String> game;

    /**
     * List of Integers of the scores the user has earned (parallel list with game).
     */
    private List<Integer> score;

    /**
     * The StateManager of the last game the user was playing.
     */
    private StateManager lastStateManager;

    /**
     * Initialize a new user with a username and lastBoardManager.
     *
     * @param username         the username of the user
     * @param lastStateManager the user's lastBoardManager
     */
    public User(String username, StateManager lastStateManager) {
        this.username = username;
        this.lastStateManager = lastStateManager;
    }

    /**
     * Initialize User with a username, game, and score.
     *
     * @param username the username associated with the score
     * @param game     the game to store parallel to the score list
     * @param score    the score to store parallel to the game list
     */

    public User(String username, String game, int score) {
        this.username = username;
        this.game = new ArrayList<>(Collections.singletonList(game));
        this.score = new ArrayList<>(Collections.singletonList(score));
    }

    /**
     * Initialize a User based on User other.
     *
     * @param other User to copy data from
     */
    public User(User other) {
        this.username = other.getUsername();
        this.game = new ArrayList<>(other.getGame());
        this.score = new ArrayList<>(other.getScore());
        this.lastStateManager = other.getLastStateManager();
    }

    /**
     * Return this user's lastStateManager.
     *
     * @return the lastStateManager
     */
    public StateManager getLastStateManager() {
        return lastStateManager;
    }

    /**
     * Initialize the previous board manager instance for the user.
     *
     * @param lastStateManager the last StateManager this user played with
     */
    public void setLastStateManager(StateManager lastStateManager) {
        this.lastStateManager = lastStateManager;
    }

    /**
     * Return the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username != null ? this.username : "";
    }

    /**
     * Retrieve the List of games parallel to score.
     *
     * @return the List
     */
    public List<String> getGame() {
        return this.game;
    }

    /**
     * Retrieves the List of scores parallel to list.
     *
     * @return the List
     */
    public List<Integer> getScore() {
        return this.score;
    }

    /**
     * Obtains a maximum score to be displayed in ByUserFragment
     *
     * @return the maximum score
     */
    public int getMaxScore() {
        return Collections.max(score);
    }

    @Override
    public int compareTo(@NonNull User other) {
        // reversed to sort from highest to lowest
        return other.getMaxScore() - this.getMaxScore();
    }

    @Override
    public boolean equals(Object other) {
        // self check
        if (this == other)
            return true;
        // null check
        if (other == null)
            return false;
        // type check and cast
        if (getClass() != other.getClass())
            return false;
        User user = (User) other;
        // field comparison
        return Objects.equals(this.username, user.getUsername())
                && Objects.equals(this.game, user.getGame())
                && Objects.equals(this.score, user.getScore());
    }
}
