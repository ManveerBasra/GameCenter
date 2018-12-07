package com.example.group_0571.gamecentre.slidingTiles.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.group_0571.gamecentre.activityObserverModel.GameActivityObserver;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesGameActivityController;
import com.example.group_0571.gamecentre.mvcGameModel.GestureDetectGridView;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.scoreboard.ui.ScoreboardActivity;
import com.example.group_0571.gamecentre.scoreboard.ScoreboardManager;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.utils.SaveManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SlidingTiles game activity
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements GameActivityObserver {

    /**
     * The username of the current user
     */
    private String username;

    /**
     * Handle to the SaveManager class for reading/writing data.
     */
    private SaveManager saveManager;

    /**
     * Observable controller to handle interactions with StateManager.
     */
    private SlidingTilesGameActivityController gameActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveManager = SaveManager.getInstance();
        username = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_sliding_tiles_game);
        setupGameActivityController();
    }

    /**
     * Fully instantiate and setup gameActivityController.
     */
    private void setupGameActivityController() {
        gameActivityController = SlidingTilesGameActivityController.getInstance();
        gameActivityController.registerObserver(this);
        setStateManager();
        gameActivityController.createTileButtons(this);
        gameActivityController.setScoreboardManager(new ScoreboardManager(getScoresFromFile()));
        gameActivityController.setPreferenceHandler(new PreferenceHandler(
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()), getBaseContext()));
        GestureDetectGridView gridView = findViewById(R.id.grid);
        gameActivityController.setupGridView(gridView);
        gameActivityController.setupGridViewTreeObserver(gridView.getViewTreeObserver());
    }

    /**
     * Set the controller's state manager from file
     */
    private void setStateManager() {
        try {
            gameActivityController.setStateManager(saveManager.
                    loadFromTempFile(openFileInput(SaveManager.ST_TEMP_SAVE_FILENAME)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sliding_tiles_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.undo_button) {
            if (!gameActivityController.undoLastMove()) {
                Toast.makeText(SlidingTilesGameActivity.this, getString(R.string
                        .cannot_undo), Toast.LENGTH_SHORT).show();
            } else if (gameActivityController.getStateManager().isFinished()) {
                Toast.makeText(SlidingTilesGameActivity.this, getString(R.string.game_over),
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToTempFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameActivityController.updateUndoLimit();
        gameActivityController.updateScoreboardUsers(getScoresFromFile());
    }

    @Override
    public void update() {
        saveToSaveFile();
        saveToTempFile();
    }

    /**
     * Save the board manager to SlidingTilesStartActivity.TEMP_SAVE_FILENAME
     */
    private void saveToTempFile() {
        try {
            saveManager.saveToTempFile(openFileOutput(SaveManager.ST_TEMP_SAVE_FILENAME, MODE_PRIVATE),
                    gameActivityController.getStateManager());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the user's last SlidingTilesStateManager
     */
    @SuppressWarnings("unchecked")
    private void saveToSaveFile() {
        Map<String, User> fileMap;
        try {
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.ST_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            saveManager.saveToSaveFile(openFileOutput(SaveManager.ST_SAVE_FILENAME, MODE_PRIVATE), fileMap, username, gameActivityController.getStateManager());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a List of Scores from File
     *
     * @return list of User objects containing scores
     */
    @SuppressWarnings("unchecked")
    private List<User> getScoresFromFile() {
        try {
            return saveManager.loadScoresFromFile(openFileInput(SaveManager.SCORES_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Saves current scores to scores.ser
     * We perform necessary input/output streams here.
     *
     * @param users List of users to be saved
     */
    private void saveToScoreboard(List<User> users) {
        try {
            saveManager.saveScoresToFile(openFileOutput(ScoreboardActivity.SCORES_PATH, MODE_PRIVATE),
                    users);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the user to the scoreboard after they have finished playing.
     */
    private void moveToScoreboard() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SlidingTilesGameActivity.this,
                        ScoreboardActivity.class));
            }
        }, 4000);
    }

    /**
     * Displays a toast message delayed by a factor of Toast.LENGTH_SHORT (2000ms)
     * Handler method is adopted from:
     * https://www.programcreek.com/java-api-examples/?class=android.os.Handler&method=postDelayed
     *
     * @param message the message to be displayed
     */
    private void toastDelayed(final String message) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SlidingTilesGameActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    @Override
    public void onGameFinished(List<User> users, boolean newHighScore) {
        Toast.makeText(SlidingTilesGameActivity.this, getString(R.string.you_win), Toast.LENGTH_SHORT).show();
        if (newHighScore) toastDelayed(getString(R.string.new_highscore));
        saveToScoreboard(users);
        moveToScoreboard();
    }
}