package com.example.group_0571.gamecentre.scoreboard;

import com.example.group_0571.gamecentre.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle interactions between UI and Scoreboard
 */
public class ScoreboardManager {

    /**
     * Scoreboard object to handle score data
     */
    private Scoreboard scoreboard;

    /**
     * Constructor initializes Scoreboard member object (uses Dependency-Injection)
     *
     * @param users List to instantiate
     */
    public ScoreboardManager(List<User> users) {
        scoreboard = new Scoreboard(users);
    }

    /**
     * Getter for Scoreboard member object
     *
     * @return the Scoreboard
     */
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /**
     * Add newUser to scoreboard and update scoreboard's score reference.
     *
     * @param newUser to add
     * @return true if a high score was obtained
     */
    public boolean addScore(User newUser) {
        Map<String, User> scoresMap =
                convertScoresToMap(scoreboard.getScoresByUser());
        List<User> newUsers = scoreboard.addScore(scoresMap, newUser);

        if (newUsers != null) {
            scoreboard.setUsers(newUsers);
            return true;
        }
        return false;
    }

    /**
     * Convert a User List into a Map for ease of retrieval during score validation process.
     *
     * @param users the List of users to convert into a Map
     * @return the Map representing a List of users
     */
    public Map<String, User> convertScoresToMap(List<User> users) {
        Map<String, User> newMap = new HashMap<>();
        if (users != null) {
            for (User s : users) {
                newMap.put(s.getUsername(), s);
            }
        }
        return newMap;
    }
}
