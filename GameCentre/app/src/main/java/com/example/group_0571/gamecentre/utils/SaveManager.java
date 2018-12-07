package com.example.group_0571.gamecentre.utils;

import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle interactions between Activities and FileUtil, abstracts away from FileUtil
 * <p>
 * Implements Singleton design pattern
 */
public class SaveManager {
    /**
     * Final Values for save filenames
     */
    public static final String SCORES_PATH = "scores.ser";
    public static final String ST_SAVE_FILENAME = "sliding_tiles_save_file.ser";
    public static final String ST_TEMP_SAVE_FILENAME = "sliding_tiles_save_file_temp.ser";
    public static final String TTT_SAVE_FILENAME = "tictactoe_save_file.ser";
    public static final String TTT_TEMP_SAVE_FILENAME = "tictactoe_save_file_temp.ser";
    public static final String P_SAVE_FILENAME = "pipes_save_file.ser";
    public static final String P_TEMP_SAVE_FILENAME = "pipes_save_file_temp.ser";

    /**
     * FileUtil object used to save/read to files
     */
    private FileUtil fileUtil;

    /**
     * Reference to singleton instance
     */
    private static SaveManager single_instance = null;

    /**
     * Initialize FileUtil instance to be used
     */
    private SaveManager() {
        fileUtil = FileUtil.getInstance();
    }

    /**
     * Create singleton instance if exists, else return singleton_instance SaveManager
     */
    public static SaveManager getInstance() {
        if (single_instance == null) {
            single_instance = new SaveManager();
        }
        return single_instance;
    }

    /**
     * Load saved scores from SCORES_PATH
     *
     * @param inputStream InputStream of SCORES_PATH
     * @return a List of saved Users
     */
    @SuppressWarnings("unchecked")
    public List<User> loadScoresFromFile(InputStream inputStream) {
        if (inputStream != null) {
            return (ArrayList<User>) fileUtil.readObjectFromFile(inputStream);
        }
        return null;
    }

    /**
     * Outputs the Scoreboard's Users to file
     *
     * @param outputStream OutputStream of the save file
     * @param scoresByUser List of Users
     */
    public void saveScoresToFile(OutputStream outputStream, List<User> scoresByUser) {
        if (outputStream != null) {
            fileUtil.writeObjectToFile(outputStream, scoresByUser);
        }
    }

    /**
     * Get username's lastStateManager from the Hashmap of user saved in SAVE_FILENAME.
     *
     * @param inputStream InputStream of SAVE_FILENAME
     * @param username    String of username to read
     * @return a StateManager object, null if user doesn't have a last saved state
     */
    @SuppressWarnings("unchecked")
    public StateManager loadFromSaveFileByUser(InputStream inputStream, String username) {
        if (inputStream == null) {
            return null;
        }
        Map<String, User> fileMap =
                (HashMap<String, User>) fileUtil.readObjectFromFile(inputStream);
        //Using this implementation the user should only be put in the HashMap if they've
        //played a game
        if (fileMap != null && fileMap.containsKey(username) && fileMap.get(username)
                .getLastStateManager() != null) {
            return fileMap.get(username).getLastStateManager();
        }
        return null;
    }

    /**
     * Get username's lastStateManager from the Hashmap of user saved in SAVE_FILENAME.
     *
     * @param inputStream InputStream of SAVE_FILENAME
     * @return a saved Map of Username to User, null if nothing was found
     */
    @SuppressWarnings("unchecked")
    public Map<String, User> loadFromSaveFileAllUsers(InputStream inputStream) {
        if (inputStream != null) {
            return (HashMap<String, User>) fileUtil.readObjectFromFile(inputStream);
        }
        return null;
    }

    /**
     * Read the last StateManager from TEMP_SAVE_FILENAME
     *
     * @param inputStream InputStream of TEMP_SAVE_FILENAME
     * @return a StateManager object, null if nothing was found
     */
    public StateManager loadFromTempFile(InputStream inputStream) {
        if (inputStream != null) {
            return (StateManager) fileUtil.readObjectFromFile(inputStream);
        }
        return null;
    }

    /**
     * Save the StateManager to TEMP_SAVE_FILENAME.
     *
     * @param outputStream OutputStream of TEMP_SAVE_FILENAME
     * @param stateManager Game's StateManager to be saved
     */
    public void saveToTempFile(OutputStream outputStream, StateManager stateManager) {
        if (outputStream != null && stateManager != null) {
            fileUtil.writeObjectToFile(outputStream, stateManager);
        }
    }

    /**
     * Save the User to the HashMap of users in SAVE_FILENAME
     *
     * @param outputStream OutputStream of SAVE_FILENAME
     * @param fileMap      Map of username's to Users to save and add User to
     * @param username     User's username
     * @param stateManager Game's StateManager to be saved
     */
    @SuppressWarnings("unchecked")
    public void saveToSaveFile(OutputStream outputStream, Map<String, User> fileMap, String username, StateManager stateManager) {
        if (stateManager == null || stateManager.gameFinished()) return;
        if (fileMap == null) {
            fileMap = new HashMap<>();
        }
        User user;
        if (fileMap.containsKey(username)) {
            user = fileMap.get(username);
            user.setLastStateManager(stateManager);
        } else {
            user = new User(username, stateManager);
        }
        fileMap.put(username, user);

        if (outputStream != null) {
            fileUtil.writeObjectToFile(outputStream, fileMap);
        }
    }

    /**
     * Save the User to the HashMap of users in SAVE_FILENAME
     *
     * @param outputStream OutputStream of SAVE_FILENAME
     * @param fileMap      Map of username's to Users to save and add User to
     * @param user         User to be saved
     */
    @SuppressWarnings("unchecked")
    public void saveToSaveFile(OutputStream outputStream, Map<String, User> fileMap, User user) {
        if (fileMap == null) {
            fileMap = new HashMap<>();
        }
        if (!fileMap.containsKey(user.getUsername())) {
            fileMap.put(user.getUsername(), user);
        }
        if (outputStream != null) {
            fileUtil.writeObjectToFile(outputStream, fileMap);
        }
    }
}
