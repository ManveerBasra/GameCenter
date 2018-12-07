package com.example.group_0571.gamecentre;

import com.example.group_0571.gamecentre.scoreboard.Scoreboard;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.utils.FileUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for the ScoreboardManager class.
 */
public class ScoreboardManagerTest {
    /**
     * Filler variable to initialize scoreboard object.
     */
    private List<User> scores = new ArrayList<>();

    /**
     * ScoreboardManager object to test with.
     */
    private ScoreboardManager scoreboardManager;

    /**
     * Initialize scores and scoreboardManager before testing.
     */
    @Before
    public void setUp() {
        scores.add(new User("user", "game", 1));
        scores.add(new User("user", "game", 2));
        scoreboardManager = new ScoreboardManager(scores);
    }

    /**
     * Test if scoreboard is correctly returned.
     */
    @Test
    public void testGetScoreboard() {
        Scoreboard expected = new Scoreboard(scores);
        assertEquals(expected.getScoresByUser(), scoreboardManager.getScoreboard().getScoresByUser());
    }

    /**
     * Test if addScore functionality works with no new high score.
     */
    @Test
    public void testAddScoreNotHighScore() {
        List<User> expected = scoreboardManager.getScoreboard().getScoresByUser();
        User score = new User("user", "game", 1);

        expected.add(score);
        assertFalse(scoreboardManager.addScore(score));
        assertEquals(expected, scoreboardManager.getScoreboard().getScoresByUser());
    }

    /**
     * Test if addScore functionality works with a new high score.
     */
    @Test
    public void testAddScoreNewHighScore() {
        assertTrue(scoreboardManager.addScore(new User("user", "game", 3)));
    }

    /**
     * Test convertScoresToMap function with empty length ArrayList.
     */
    @Test
    public void testConvertScoresToMapEmpty() {
        Map<String, User> expected = new HashMap<>();
        List<User> emptyScores = new ArrayList<>();
        assertEquals(expected, scoreboardManager.convertScoresToMap(emptyScores));
    }

    /**
     * Test convertScoresToMap function with non empty ArrayList.
     */
    @Test
    public void testConvertScoresToMapNotEmpty() {
        Map<String, User> expected = new HashMap<>();
        List<User> scoresNonEmpty = new ArrayList<>();

        User score = new User("user", "game", 1);
        expected.put("user", score);
        scoresNonEmpty.add(score);
        assertEquals(expected, scoreboardManager.convertScoresToMap(scoresNonEmpty));
    }
}
