package com.example.group_0571.gamecentre.activityObserverModel;

import com.example.group_0571.gamecentre.User;

import java.util.List;

/**
 * Interface to allow GameActivities to observe their respective GameControllers
 */
public interface GameActivityObserver {

    /**
     * Called when game is finished, update View and navigate to ScoreboardActivity
     *
     * @param users        List of users to be saved
     * @param newHighScore whether a new high score was achieved
     */
    void onGameFinished(List<User> users, boolean newHighScore);

    /**
     * Called after every move to auto-save game.
     */
    void update();
}
