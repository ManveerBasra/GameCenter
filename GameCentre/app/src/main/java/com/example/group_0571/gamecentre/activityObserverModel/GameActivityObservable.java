package com.example.group_0571.gamecentre.activityObserverModel;

/**
 * Interface to allow GameControllers to be observed by their respective GameActivities.
 */
public interface GameActivityObservable {

    /**
     * Register observer (*GameActivity) with this observable.
     *
     * @param observer GameActivityObserver object to register
     */
    void registerObserver(GameActivityObserver observer);

    /**
     * Notify observers that game has finished.
     *
     * @param newHighScore whether user has achieved a new high score
     */
    void notifyGameFinished(boolean newHighScore);

    /**
     * Notify observers whenever a move has been made.
     */
    void notifyUpdate();
}
