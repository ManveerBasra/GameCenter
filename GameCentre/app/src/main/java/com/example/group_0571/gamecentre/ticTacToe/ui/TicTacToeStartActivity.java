package com.example.group_0571.gamecentre.ticTacToe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeState;
import com.example.group_0571.gamecentre.ticTacToe.TicTacToeStateManager;
import com.example.group_0571.gamecentre.tile.TicTacToeTile;
import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.tile.Tile;
import com.example.group_0571.gamecentre.utils.SaveManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The starting activity for TicTacToe.
 */
public class TicTacToeStartActivity extends AppCompatActivity {

    /**
     * The user's name.
     */
    private String username;
    /**
     * The stateManager
     */
    private StateManager stateManager;
    /**
     * Handle to SaveManager class to read/write data
     */
    private SaveManager saveManager;
    /**
     * The current user
     */
    private User user;
    /**
     * Handle to the board spinner
     */
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_start);
        spinner = findViewById(R.id.board_spinner);
        username = getIntent().getStringExtra("user");
        saveManager = SaveManager.getInstance();
        setUser();
        saveToTempFile();
    }

    /**
     * OnClick method for board spinner.
     *
     * @param v View clicked
     */
    public void playButtonOnClick(View v) {
        TicTacToeStartActivity.this.stateManager = new TicTacToeStateManager
                (this.newState(spinner.getSelectedItemPosition() + 3), username);
        switchToGame();
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
            Toast.makeText(TicTacToeStartActivity.this,
                    "Loaded Game", Toast.LENGTH_SHORT).show();
            switchToGame();
        } else {
            Toast.makeText(TicTacToeStartActivity.this,
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
            Toast.makeText(TicTacToeStartActivity.this, "Game Saved",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TicTacToeStartActivity.this, "Save Unsuccessful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Create an empty state.
     *
     * @return a state
     */
    protected TicTacToeState newState(int dimension) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i != dimension * dimension; i++) {
            tiles.add(new TicTacToeTile(this, TicTacToeTile.BLANK_TILE));
        }
        return new TicTacToeState(dimension, tiles, 3);
    }

    /**
     * Initializes the user from file and saves the user into file.
     */
    protected void setUser() {
        Map<String, User> fileMap;
        try {
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.TTT_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            user = fileMap != null && fileMap.containsKey(username) ? fileMap.get(username) :
                    new User(username, stateManager);
            saveManager.saveToSaveFile(openFileOutput(SaveManager.TTT_SAVE_FILENAME, MODE_PRIVATE), fileMap, user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            this.stateManager = saveManager.loadFromTempFile(openFileInput(SaveManager.TTT_TEMP_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the TicTacToeGameActivity view to play the game.
     */
    protected void switchToGame() {
        Intent tmp = new Intent(TicTacToeStartActivity.this,
                TicTacToeGameActivity.class);
        tmp.putExtra("username", username);
        saveToTempFile();
        startActivity(tmp);
    }

    /**
     * Get username's lastStateManager from the Hashmap of user saved in SAVE_FILENAME.
     *
     * @return a boolean representing whether a TicTacToeStateManager was successfully retrieved
     */
    @SuppressWarnings("unchecked")
    protected boolean loadFromSaveFile() {
        try {
            stateManager = saveManager.loadFromSaveFileByUser(
                    openFileInput(SaveManager.TTT_SAVE_FILENAME), username);
            return stateManager != null;
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return false;
        }
    }

    /**
     * Save the state manager to TEMP_SAVE_FILENAME.
     */
    protected void saveToTempFile() {
        try {
            saveManager.saveToTempFile(openFileOutput(SaveManager.TTT_TEMP_SAVE_FILENAME, MODE_PRIVATE),
                    stateManager);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a new TicTacToeStateManager in SAVE_FILENAME
     *
     * @return a boolean representing whether a TicTacToeStateManager was successfully saved
     */
    @SuppressWarnings("unchecked")
    protected boolean saveToSaveFile() {
        try {
            if (this.stateManager != null && !this.stateManager.gameFinished()) {
                Map<String, User> fileMap = saveManager
                        .loadFromSaveFileAllUsers(openFileInput(SaveManager.TTT_SAVE_FILENAME));
                user.setLastStateManager(stateManager);
                saveManager.saveToSaveFile(openFileOutput(SaveManager.TTT_SAVE_FILENAME, MODE_PRIVATE),
                        fileMap, user);
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
