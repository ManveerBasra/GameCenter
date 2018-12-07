package com.example.group_0571.gamecentre.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.pipeGame.ui.PipeGameStartActivity;
import com.example.group_0571.gamecentre.scoreboard.ui.ScoreboardActivity;
import com.example.group_0571.gamecentre.slidingTiles.ui.SlidingTilesStartActivity;
import com.example.group_0571.gamecentre.ticTacToe.ui.TicTacToeStartActivity;

/**
 * The activity for choosing games.
 */
public class GameCentreActivity extends AppCompatActivity {
    /**
     * The username of the user who is currently logged in.
     */
    private String username;

    /**
     * Spinner for selecting a game.
     */
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecentre);
        username = getIntent().getStringExtra("user");
        spinner = findViewById(R.id.games_spinner);
    }

    /**
     * OnClick method for the play button.
     *
     * @param view the View clicked
     */
    public void playButtonClick(View view) {
        Intent tmp = new Intent();
        if (spinner.getSelectedItem().equals(getString(R.string.sliding_tiles_game))) {
            tmp.setClass(this, SlidingTilesStartActivity.class);
        } else if (spinner.getSelectedItem().equals(getString(R.string.tictactoe_game))) {
            tmp.setClass(this, TicTacToeStartActivity.class);
        } else if (spinner.getSelectedItem().equals("Pipes")) {
            tmp.setClass(this, PipeGameStartActivity.class);
        }
        tmp.putExtra("user", username);
        startActivity(tmp);
    }

    /**
     * OnClick method for the scoreboard button.
     *
     * @param view the View clicked
     */
    public void scoreboardButtonClick(View view) {
        startActivity(new Intent(this, ScoreboardActivity.class));
    }
}
