package com.example.group_0571.gamecentre;

import com.example.group_0571.gamecentre.scoreboard.Scoreboard;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests to test Score class functionality.
 */
public class ScoreboardTest {
    /**
     * Filler variable to initialize scoreboard object.
     */
    private List<User> scores = new ArrayList<>();

    /**
     * Scoreboard object used to test.
     */
    private Scoreboard scoreboard;

    /**
     * Initialize scores and scoreboard before testing.
     */
    @Before
    public void setUp() {
        scores.add(new User("user", "game", 1));
        scores.add(new User("user", "game", 2));

        scoreboard = new Scoreboard(scores);
    }

    /**
     * Test getScoreByUser function with null ArrayList.
     */
    @Test
    public void testGetScoresByUserEmpty() {
        List<User> expected = new ArrayList<>();
        assertEquals(expected, new Scoreboard(null).getScoresByUser());
    }

    /**
     * Test getScoreByUser function with non empty ArrayList.
     */
    @Test
    public void testGetScoresByUserNonEmpty() {
        assertEquals(scores, scoreboard.getScoresByUser());
    }

    /**
     * Test setUsers function.
     */
    @Test
    public void testSetUsers() {
        List<User> emptyScores = new ArrayList<>();
        scoreboard.setUsers(emptyScores);
        assertEquals(emptyScores, scoreboard.getScoresByUser());
        scoreboard.setUsers(scores);
    }

    /**
     * Test getScoreByGame function.
     */
    @Test
    public void testGetScoresByGame() {
        List<User> expected = new ArrayList<>();
        expected.add(scores.get(1));
        expected.add(scores.get(0));
        assertEquals(expected, scoreboard.getScoresByGame("game"));
    }


    /**
     * Test addScore function with no new high score.
     */
    @Test
    public void testAddScoreNoHighScore() {
        assertEquals(scores, scoreboard.getScoresByUser());

        User score = new User("user", "game", 0);
        Map<String, User> userToUsers = new HashMap<>();
        userToUsers.put("user", new User("user", "game", 1));
        assertNull(scoreboard.addScore(userToUsers, score));
    }

    /**
     * Test addScore function with a new high score.
     */
    @Test
    public void testAddScoreHighScore() {
        assertEquals(scores, scoreboard.getScoresByUser());

        User score = new User("user", "game", 0);
        List<User> expected = new ArrayList<>();
        expected.add(score);
        Map<String, User> userToUsers = new HashMap<>();
        assertEquals(expected, scoreboard.addScore(userToUsers, score));
    }

    /**
     * Test addScore function with a null 'fileScores' HashMap.
     */
    @Test
    public void testAddScoreNullHashMap() {
        assertEquals(scores, scoreboard.getScoresByUser());

        User score = new User("user", "game", 0);
        List<User> expected = new ArrayList<>();
        expected.add(score);
        assertEquals(expected, scoreboard.addScore(null, score));
    }

    /**
     * Test addScore function with a new game.
     */
    @Test
    public void testAddScoreNewGame() {
        assertEquals(scores, scoreboard.getScoresByUser());

        User score = new User("user", "game2", 0);
        User score2 = new User("user", "game", 0);
        List<User> expected = new ArrayList<>();
        expected.add(score2);
        Map<String, User> userToScores = new HashMap<>();
        userToScores.put("user", score2);
        assertEquals(expected, scoreboard.addScore(userToScores, score));
    }
}
