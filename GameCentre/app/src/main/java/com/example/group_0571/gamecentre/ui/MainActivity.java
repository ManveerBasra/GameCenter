package com.example.group_0571.gamecentre.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.scoreboard.ui.ScoreboardActivity;

/**
 * The main activity when starting the application.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * OnClick method for sign up button.
     *
     * @param v View clicked
     */
    public void signUpButtonOnClick(View v) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    /**
     * OnClick method for sign in button.
     *
     * @param v View clicked
     */
    public void signInButtonOnClick(View v) {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
    }

    /**
     * OnClick method for scoreboard button.
     *
     * @param v View clicked
     */
    public void scoreboardButtonOnClick(View v) {
        startActivity(new Intent(MainActivity.this, ScoreboardActivity.class));
    }
}
