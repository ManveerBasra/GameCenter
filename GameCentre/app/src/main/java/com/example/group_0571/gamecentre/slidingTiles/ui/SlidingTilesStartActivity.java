package com.example.group_0571.gamecentre.slidingTiles.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesBoardFactory;
import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesStateManager;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.utils.SaveManager;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesStartActivity extends AppCompatActivity {
    /**
     * The board manager.
     */
    private StateManager slidingTilesStateManager;

    /**
     * The user's name.
     */
    private String username;

    /**
     * The current user
     */
    private User user;

    /**
     * The SlidingTilesBoardFactory
     */
    private SlidingTilesBoardFactory slidingTilesBoardFactory;

    /**
     * Handle to SaveManager class to read/write data
     */
    private SaveManager saveManager;

    /**
     * Handle to the board spinner
     */
    private Spinner spinner;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("user");
        saveManager = SaveManager.getInstance();
        slidingTilesBoardFactory = new SlidingTilesBoardFactory(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()));
        setUser();
        saveToTempFile();
        setContentView(R.layout.sliding_tiles_activity);
        spinner = findViewById(R.id.board_spinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sliding_tiles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sliding_tiles_settings_button) {
            Intent intent = new Intent(this, SlidingTilesSettingsActivity.class);
            // username needs to be passed because when the back button is pressed, this activity
            // is recreated and expects an intent with the username to be passed
            intent.putExtra("user", username);
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * OnClick method for board spinner.
     *
     * @param v View clicked
     */
    public void playButtonOnClick(View v) {
        this.slidingTilesStateManager = new SlidingTilesStateManager
                (this.slidingTilesBoardFactory.makeBoard(spinner.getSelectedItemPosition() + 3), username);
        switchToGame();
    }

    /**
     * Initializes the user and saves the object into file.
     */
    private void setUser() {
        Map<String, User> fileMap;
        try {
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.ST_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            user = fileMap != null && fileMap.containsKey(username) ? fileMap.get(username) :
                    new User(username, slidingTilesStateManager);
            saveManager.saveToSaveFile(openFileOutput(SaveManager.ST_SAVE_FILENAME, MODE_PRIVATE), fileMap, user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * OnClick for load button.
     *
     * @param v View clicked
     */
    public void loadButtonOnClick(View v) {
        Boolean loadSuccessful = loadFromSaveFile();
        if (loadSuccessful) {
            saveToTempFile();
            Toast.makeText(SlidingTilesStartActivity.this,
                    "Loaded Game", Toast.LENGTH_SHORT).show();
            switchToGame();
        } else {
            Toast.makeText(SlidingTilesStartActivity.this,
                    "No Old Games to Load",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * OnClick for save button.
     *
     * @param v View clicked
     */
    public void saveButtonOnClick(View v) {
        Boolean saveSuccessful = saveToSaveFile();
        if (saveSuccessful) {
            saveToTempFile();
            Toast.makeText(SlidingTilesStartActivity.this, "Game Saved",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SlidingTilesStartActivity.this, "Save Unsuccessful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            slidingTilesStateManager = saveManager.loadFromTempFile(openFileInput(SaveManager.ST_TEMP_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(SlidingTilesStartActivity.this,
                SlidingTilesGameActivity.class);
        tmp.putExtra("username", username);
        saveToTempFile();
        startActivity(tmp);
    }

    /**
     * Get username's lastSlidingTilesStateManager from the Hashmap of user saved in SAVE_FILENAME.
     *
     * @return a boolean representing whether a SlidingTilesStateManager was successfully retrieved
     */
    @SuppressWarnings("unchecked")
    private boolean loadFromSaveFile() {
        try {
            slidingTilesStateManager = saveManager.loadFromSaveFileByUser(
                    openFileInput(SaveManager.ST_SAVE_FILENAME), username);
            return slidingTilesStateManager != null;
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return false;
        }
    }

    /**
     * Save the board manager to TEMP_SAVE_FILENAME.
     */
    private void saveToTempFile() {
        try {
            saveManager.saveToTempFile(openFileOutput(SaveManager.ST_TEMP_SAVE_FILENAME, MODE_PRIVATE),
                    slidingTilesStateManager);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new SlidingTilesStateManager to the ArrayList saved in SAVE_FILENAME
     *
     * @return a boolean representing whether a SlidingTilesStateManager was successfully saved
     */
    @SuppressWarnings("unchecked")
    private boolean saveToSaveFile() {
        Map<String, User> fileMap;
        try {
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.ST_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            if (this.slidingTilesStateManager != null && !this.slidingTilesStateManager.gameFinished()) {
                user.setLastStateManager(slidingTilesStateManager);
                saveManager.saveToSaveFile(openFileOutput(SaveManager.ST_SAVE_FILENAME, MODE_PRIVATE), fileMap, user);
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
