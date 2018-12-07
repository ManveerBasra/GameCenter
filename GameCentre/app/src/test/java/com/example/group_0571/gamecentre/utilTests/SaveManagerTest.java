package com.example.group_0571.gamecentre.utilTests;

import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeStateManager;
import com.example.group_0571.gamecentre.utils.FileUtil;
import com.example.group_0571.gamecentre.utils.SaveManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SaveManagerTest {

    /**
     * SaveManager object to be tested
     */
    private SaveManager saveManager;

    /**
     * FileUtil object to deal with reading/writing objects to files
     */
    private FileUtil fileUtil;

    /**
     * Data Structures to be used for testing
     */
    private List<User> scoresList;
    private HashMap<String, User> usersMap;

    /**
     * Initialize saveManager and fileUtil, and initialize and populate scoresList and usersMap
     */
    @Before
    public void setUp() {
        saveManager = SaveManager.getInstance();
        fileUtil = FileUtil.getInstance();

        User user = new User("username", "game", 0);

        scoresList = new ArrayList<>();
        scoresList.add(user);

        usersMap = new HashMap<>();
        usersMap.put("username", user);
    }

    /**
     * Delete files created while testing
     */
    @After
    public void tearDown() {
        new File("testTemp.ser").delete();
        new File("testScores.ser").delete();
        new File("testUsers.ser").delete();
        new File("testSave.ser").delete();
    }

    /**
     * Ensure getInstance does return the same instance of SaveManager
     */
    @Test
    public void testGetInstance() {
        SaveManager saveManager2 = SaveManager.getInstance();
        assertEquals(saveManager2, saveManager);
    }

    /**
     * Ensure nothing is read from a null InputStream
     */
    @Test
    public void testLoadScoresFromFile_NullInputStream() {
        List<User> emptyScores = saveManager.loadScoresFromFile(null);
        assertNull(emptyScores);
    }

    /**
     * Ensure loading scores works with a empty file by saving an empty ArrayList before reading
     */
    @Test
    public void testLoadScoresFromFile_EmptyFile() {
        List<User> scores = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testScores.ser"));
            fileUtil.writeObjectToFile(outputStream, new ArrayList<User>());
            scores = saveManager.loadScoresFromFile(new FileInputStream(new File("testScores.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testScores.ser");
        }
        assertEquals(new ArrayList<User>(), scores);
    }

    /**
     * Ensure loading scores works with a non empty file by saving scoresList before reading
     */
    @Test
    public void testLoadScoresFromFile_NonEmptyFile() {
        List<User> loadedScores = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testScores.ser"));
            fileUtil.writeObjectToFile(outputStream, scoresList);
            loadedScores = saveManager.loadScoresFromFile(new FileInputStream(new File("testScores.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testScores.ser");
        }
        assertEquals(scoresList, loadedScores);
    }

    /**
     * Ensure saving Scores with a null OutputStream doesn't do anything
     */
    @Test
    public void testSaveScoresToFile_NullOutputStream() {
        saveManager.saveScoresToFile(null, new ArrayList<User>());

        File file = new File("testScores.ser");
        assertFalse(file.isFile());
    }

    /**
     * Ensure saving scores to an empty file correctly saves by first saving an empty ArrayList
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveScoresToFile_EmptyFile() {
        try {
            saveManager.saveScoresToFile(new FileOutputStream(new File("testScores.ser")), new ArrayList<User>());
            FileInputStream inputStream = new FileInputStream(new File("testScores.ser"));
            List<User> loadedScores = (List<User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(loadedScores, new ArrayList<User>());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testScores.ser");
        }
    }

    /**
     * Ensure saving scores to an non empty file correctly saves by first saving scoresList
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveScoresToFile_NonEmptyFile() {
        try {
            saveManager.saveScoresToFile(new FileOutputStream(new File("testScores.ser")), scoresList);
            FileInputStream inputStream = new FileInputStream(new File("testScores.ser"));
            List<User> loadedScores = (List<User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(loadedScores, scoresList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testScores.ser");
        }
    }

    /**
     * Ensure nothing is read from a null InputStream
     */
    @Test
    public void testLoadFromSaveFileByUser_NullInputStream() {
        StateManager stateManager = saveManager.loadFromSaveFileByUser(null, "user");
        assertNull(stateManager);
    }

    /**
     * Ensure loading stateManager works with an empty file by first saving an empty HashMap
     */
    @Test
    public void testLoadFromSaveFileByUser_EmptyFile() {
        StateManager stateManager = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testUsers.ser"));
            fileUtil.writeObjectToFile(outputStream, new HashMap<String, User>());
            stateManager = saveManager.loadFromSaveFileByUser(new FileInputStream(new File("testUsers.ser")), "user");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testUsers.ser");
        }
        assertNull(stateManager);
    }

    /**
     * Ensure loading stateManager works with a non empty file by first saving usersMap
     */
    @Test
    public void testLoadFromSaveFileByUser_NonEmptyFile() {
        StateManager stateManager = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testUsers.ser"));
            fileUtil.writeObjectToFile(outputStream, usersMap);
            stateManager = saveManager.loadFromSaveFileByUser(new FileInputStream(new File("testUsers.ser")), "username");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testUserss.ser");
        }
        assertNull(stateManager);
    }


    /**
     * Ensure nothing is read from a null InputStream
     */
    @Test
    public void testLoadFromTempFile_NullInputStream() {
        StateManager stateManager = saveManager.loadFromTempFile(null);
        assertNull(stateManager);
    }

    /**
     * Ensure loading stateManager from an empty file works by first saving a null object
     */
    @Test
    public void testLoadFromTempFile_EmptyFile() {
        StateManager stateManager = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testTemp.ser"));
            fileUtil.writeObjectToFile(outputStream, null);
            stateManager = saveManager.loadFromTempFile(new FileInputStream(new File("testTemp.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testTemp.ser");
        }
        assertNull(stateManager);
    }

    /**
     * Ensure loading stateManager from an empty file works by first saving a new StateManager
     */
    @Test
    public void testLoadFromTempFile_NonEmptyFile() {
        StateManager loadedStateManager = null;
        StateManager stateManager = new TicTacToeStateManager(null, "user");
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testTemp.ser"));
            fileUtil.writeObjectToFile(outputStream, stateManager);
            loadedStateManager = saveManager.loadFromTempFile(new FileInputStream(new File("testTemp.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testScores.ser");
        }
        assertEquals(stateManager.getState(), loadedStateManager.getState());
    }

    /**
     * Ensure saving to a file with a null OutputStream doesn't do anything
     */
    @Test
    public void testSaveToTempFile_NullOutputStream() {
        saveManager.saveToTempFile(null, null);

        File file = new File("testTemp.ser");
        assertFalse(file.isFile());
    }

    /**
     * Ensure saving to an empty file correctly saves by first saving a null object
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToTempFile_EmptyFile() {
        try {
            saveManager.saveToTempFile(new FileOutputStream(new File("testTemp.ser")), null);
            FileInputStream inputStream = new FileInputStream(new File("testTemp.ser"));
            StateManager loadedStateManager = (StateManager) fileUtil.readObjectFromFile(inputStream);
            assertNull(loadedStateManager);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testTemp.ser");
        }
    }

    /**
     * Ensure saving to a non empty file correctly saves by first saving a new StateManager
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToTempFile_NonEmptyFile() {
        TicTacToeStateManager stateManager = new TicTacToeStateManager(null, "user");
        try {
            saveManager.saveToTempFile(new FileOutputStream(new File("testTemp.ser")), stateManager);
            FileInputStream inputStream = new FileInputStream(new File("testTemp.ser"));
            StateManager loadedStateManager = (StateManager) fileUtil.readObjectFromFile(inputStream);
            assertEquals(loadedStateManager.getState(), stateManager.getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testTemp.ser");
        }
    }

    /**
     * Ensure saving to a file with a null OutputStream doesn't do anything
     */
    @Test
    public void testSaveToSaveFile_NullOutputStream() {
        saveManager.saveToSaveFile(null, null, "user", null);

        File file = new File("testSave.ser");
        assertFalse(file.isFile());
    }

    /**
     * Ensure saving to an empty file correctly saves
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToSaveFile_EmptyFile() {
        TicTacToeStateManager stateManager = new TicTacToeStateManager(null, "user");
        try {
            OutputStream outputStream = new FileOutputStream(new File("testSave.ser"));
            saveManager.saveToSaveFile(outputStream, null, "user", stateManager);
            InputStream inputStream = new FileInputStream(new File("testSave.ser"));
            Map<String, User> loadedMap = (Map<String, User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(stateManager.getState(), loadedMap.get("user").getLastStateManager().getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testSave.ser");
        }
    }

    /**
     * Ensure saving to a non empty file correctly saves by first saving a new User
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToSaveFile_NonEmptyFile() {
        TicTacToeStateManager stateManager = new TicTacToeStateManager(null, "user");
        Map<String, User> fileMap = new HashMap<>();
        fileMap.put("user", new User("user", stateManager));
        try {
            OutputStream outputStream = new FileOutputStream(new File("testSave.ser"));
            saveManager.saveToSaveFile(outputStream, fileMap, "user", stateManager);
            InputStream inputStream = new FileInputStream(new File("testSave.ser"));
            Map<String, User> loadedMap = (Map<String, User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(stateManager.getState(), loadedMap.get("user").getLastStateManager().getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testSave.ser");
        }
    }

    /**
     * Ensure saving to a file with a null OutputStream doesn't do anything
     */
    @Test
    public void testSaveToSaveFileByUser_NullOutputStream() {
        saveManager.saveToSaveFile(null, null, new User("user", null));

        File file = new File("testSave.ser");
        assertFalse(file.isFile());
    }

    /**
     * Ensure saving to an empty file correctly saves
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToSaveFileByUser_EmptyFile() {
        TicTacToeStateManager stateManager = new TicTacToeStateManager(null, "user");
        try {
            OutputStream outputStream = new FileOutputStream(new File("testSave.ser"));
            saveManager.saveToSaveFile(outputStream, null, new User("user", stateManager));
            InputStream inputStream = new FileInputStream(new File("testSave.ser"));
            Map<String, User> loadedMap = (Map<String, User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(stateManager.getState(), loadedMap.get("user").getLastStateManager().getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testSave.ser");
        }
    }

    /**
     * Ensure saving to a non empty file correctly saves by first saving a new User
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSaveToSaveFileByUser_NonEmptyFile() {
        TicTacToeStateManager stateManager = new TicTacToeStateManager(null, "user");
        Map<String, User> fileMap = new HashMap<>();
        fileMap.put("user", new User("user", stateManager));
        try {
            OutputStream outputStream = new FileOutputStream(new File("testSave.ser"));
            saveManager.saveToSaveFile(outputStream, fileMap, new User("user", stateManager));
            InputStream inputStream = new FileInputStream(new File("testSave.ser"));
            Map<String, User> loadedMap = (Map<String, User>) fileUtil.readObjectFromFile(inputStream);
            assertEquals(stateManager.getState(), loadedMap.get("user").getLastStateManager().getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testSave.ser");
        }
    }

    /**
     * Ensure nothing is read from a null InputStream
     */
    @Test
    public void testLoadFromSaveFileAllUsers_NullInputStream() {
        Map<String, User> fileMap = saveManager.loadFromSaveFileAllUsers(null);
        assertNull(fileMap);
    }

    /**
     * Ensure loading from an empty file correctly loads by first saving an empty HashMap
     */
    @Test
    public void testLoadFromSaveFileAllUsers_EmptyFile() {
        Map<String, User> fileMap = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testUsers.ser"));
            fileUtil.writeObjectToFile(outputStream, new HashMap<String, User>());
            fileMap = saveManager.loadFromSaveFileAllUsers(new FileInputStream(new File("testUsers.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testUsers.ser");
        }
        assertEquals(new HashMap<String, User>(), fileMap);
    }

    /**
     * Ensure loading from an empty file correctly loads by first saving usersMap
     */
    @Test
    public void testLoadFromSaveFileAllUsers_NonEmptyFile() {
        Map<String, User> fileMap = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File("testUsers.ser"));
            fileUtil.writeObjectToFile(outputStream, usersMap);
            fileMap = saveManager.loadFromSaveFileAllUsers(new FileInputStream(new File("testUsers.ser")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Could not find testUsers.ser");
        }
        assertEquals(usersMap, fileMap);
    }
}