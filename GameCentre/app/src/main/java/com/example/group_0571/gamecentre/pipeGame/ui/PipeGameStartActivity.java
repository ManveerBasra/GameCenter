package com.example.group_0571.gamecentre.pipeGame.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.StateManager;
import com.example.group_0571.gamecentre.User;
import com.example.group_0571.gamecentre.pipeGame.PipeStateManager;
import com.example.group_0571.gamecentre.pipeGame.PipeStateFactory;
import com.example.group_0571.gamecentre.utils.SaveManager;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class PipeGameStartActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    private StateManager pipeBoardManager;

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
    private PipeStateFactory pipeStateFactory;

    /**
     * Handle to SaveManager class to read/write data
     */
    private SaveManager saveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_game_start);
        username = getIntent().getStringExtra("user");
        saveManager = SaveManager.getInstance();
        this.pipeStateFactory = new PipeStateFactory(getBaseContext());
        setUser();
    }

    /**
     * Make a new PipeState and PipeBoardManage when the user clicks the button to play a new game
     * and transition to the GameActivity
     *
     * @param v the view the button was clicked on
     */
    public void newGameButtonOnClick(View v) {
        EditText editText = findViewById(R.id.editText);
        try {
            Integer dimension = Integer.parseInt(editText.getText().toString());
            if (dimension < 5) {
                Toast.makeText(this, R.string.small_board, Toast.LENGTH_SHORT).show();
            } else {
                do {
                    this.pipeBoardManager = new PipeStateManager(this.pipeStateFactory.makeBoard
                            (dimension), username);
                } while (pipeBoardManager.gameFinished());
                this.user.setLastStateManager(this.pipeBoardManager);
                switchToGame();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.number_input, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * OnClick for load button.
     *
     * @param v View clicked
     */
    public void loadButtonOnClick(View v) {
        boolean loadSuccessful = loadFromSaveFile();
        if (loadSuccessful) {
            saveToTempFile();
            Toast.makeText(PipeGameStartActivity.this,
                    "Loaded Game", Toast.LENGTH_SHORT).show();
            switchToGame();
        } else {
            Toast.makeText(PipeGameStartActivity.this,
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
            Toast.makeText(PipeGameStartActivity.this, "Game Saved",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PipeGameStartActivity.this, "Save Unsuccessful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initializes the user and saves the object into file.
     */
    private void setUser() {
        Map<String, User> fileMap;
        try {
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.P_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            user = fileMap != null && fileMap.containsKey(username) ? fileMap.get(username) :
                    new User(username, pipeBoardManager);
            saveManager.saveToSaveFile(openFileOutput(SaveManager.P_SAVE_FILENAME, MODE_PRIVATE), fileMap, user);
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
            pipeBoardManager = saveManager.loadFromTempFile(openFileInput(SaveManager.P_TEMP_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(PipeGameStartActivity.this,
                PipeGameActivity.class);
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
            pipeBoardManager = saveManager.loadFromSaveFileByUser(
                    openFileInput(SaveManager.P_SAVE_FILENAME), username);
            return pipeBoardManager != null;
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
            saveManager.saveToTempFile(openFileOutput(SaveManager.P_TEMP_SAVE_FILENAME, MODE_PRIVATE),
                    pipeBoardManager);
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
            fileMap = saveManager.loadFromSaveFileAllUsers(openFileInput(SaveManager.P_SAVE_FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileMap = new HashMap<>();
        }
        try {
            if (this.pipeBoardManager != null && !this.pipeBoardManager.gameFinished()) {
                user.setLastStateManager(pipeBoardManager);
                saveManager.saveToSaveFile(openFileOutput(SaveManager.P_SAVE_FILENAME, MODE_PRIVATE), fileMap, user);
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
