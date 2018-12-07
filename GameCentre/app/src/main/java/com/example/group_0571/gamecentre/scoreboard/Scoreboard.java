package com.example.group_0571.gamecentre.scoreboard;

import android.support.annotation.Nullable;

import com.example.group_0571.gamecentre.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class to store and handle score data.
 */
public class Scoreboard {

    /**
     * The local list of Users.
     */
    private List<User> users;

    /**
     * Constructor uses Dependency-Injection to initialize users
     *
     * @param users List of users
     */
    public Scoreboard(List<User> users) {
        this.users = users;
    }

    /**
     * Return a list of User objects (containing score data) by User
     *
     * @return the List<User> sorted by highest-to-lowest scores
     */
    public List<User> getScoresByUser() {
        if (users != null) {
            return users;
        }
        return new ArrayList<>();
    }

    /**
     * Update current users
     *
     * @param users List of updated Users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * Searches for a List of User objects for those who have played game, gameName
     *
     * @param gameName to generate a List of user users from
     * @return the List of Users
     */
    public List<User> getScoresByGame(String gameName) {
        List<User> users = new ArrayList<>();
        for (User s : getScoresByUser()) {
            // for every game played in score s
            if (s.getGame().contains(gameName)) {
                int score = s.getScore().get(s.getGame().indexOf(gameName));
                int i = getInsertIndex(score, users, gameName);
                users.add(i, s);
            }
        }
        return users;
    }

    /**
     * Returns the index to insert to the List<User> representing users by game
     * such that the resulting ArrayList is in sorted order.
     *
     * @param value    being inserted
     * @param users    to insert value in
     * @param gameName being sorted
     * @return the index
     */
    private int getInsertIndex(int value, List<User> users, String gameName) {
        int gameIndex;
        for (int x = 0; x < users.size(); x++) {
            gameIndex = users.get(x).getGame().indexOf(gameName);
            // if current score
            if (users.get(x).getScore().get(gameIndex) < value) {
                return x;
            }
        }
        return users.size();
    }


    /**
     * Combines new user data into a preexisting Map obtained from FileUtil
     *
     * @param fileScores the Map of User objects to compare with User newUser
     * @param newUser    containing score data
     * @return the List containing new high scores (if possible) otherwise null
     */
    @Nullable
    public List<User> addScore(Map<String, User> fileScores, User newUser) {
        List<User> users = new ArrayList<>();
        if (fileScores != null) {
            User fileUser = fileScores.get(newUser.getUsername()); // handle to user's file information
            fileUser = validateScore(fileUser, newUser);
            if (fileUser == null) {
                return null;
            } else {
                users.add(fileUser); // add fileUser if it is valid
            }
            users.addAll(convertMapToScores(fileScores, fileUser));
            Collections.sort(users);
        } else {
            users.add(newUser);
        }
        return users;
    }

    /**
     * Determine if the newUser object is a high score.
     *
     * @param fileUser object to compare with newUser
     * @param newUser  object being compared
     * @return a User object if the user beat their high score, null if they did not
     */
    @Nullable
    private User validateScore(User fileUser, User newUser) {
        if (fileUser != null) {
            if (fileUser.getGame().contains(newUser.getGame().get(0))) {     // if fileUser has newUser's game data
                int index = fileUser.getGame().indexOf(newUser.getGame().get(0));
                if (newUser.getScore().get(0) <= fileUser.getScore().get(index)) {
                    return null;   // return null if the new score isn't greater than file's
                }
                fileUser.getScore().remove(index);
                fileUser.getScore().add(index, newUser.getScore().get(0));
            } else {
                // didn't find game but found user, so add a new game and score entry.
                fileUser.getGame().add(newUser.getGame().get(0));
                fileUser.getScore().add(newUser.getScore().get(0));
            }
        } else {
            fileUser = new User(newUser); // can't find score in file, so create a new instance
        }
        return fileUser;
    }

    /**
     * Convert a User Map into an List for ease of storage during score validation process.
     *
     * @param scores     List of users to convert from a Map into an List<User>
     * @param removeUser User to remove from the List to account for duplicates
     * @return the List representing a Map of users
     */
    private List<User> convertMapToScores(Map<String, User> scores, User removeUser) {
        List<User> newUsers = new ArrayList<>();
        if (scores != null) {
            for (Map.Entry<String, User> entry : scores.entrySet()) {
                if (!removeUser.getUsername().equals(entry.getKey())) {
                    newUsers.add(entry.getValue());
                }
            }
        }
        return newUsers;
    }
}
