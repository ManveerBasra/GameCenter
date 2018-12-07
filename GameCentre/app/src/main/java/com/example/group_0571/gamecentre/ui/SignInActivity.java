package com.example.group_0571.gamecentre.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group_0571.gamecentre.R;
import com.example.group_0571.gamecentre.utils.UserFieldValidator;
import com.example.group_0571.gamecentre.utils.FileUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The activity for signing in.
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * Handle to list of EditText views
     */
    List<EditText> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle(R.string.sign_in);

        // initialize the fields
        fields = new ArrayList<>();
        fields.add((EditText) findViewById(R.id.sign_in_username));
        fields.add((EditText) findViewById(R.id.sign_in_password));
    }

    /**
     * OnClick method for sign in button.
     *
     * @param v View clicked
     */
    public void signInActionButtonOnClick(View v) {
        EditText usernameField = findViewById(R.id.sign_in_username);
        EditText passwordField = findViewById(R.id.sign_in_password);

        if (new UserFieldValidator(fields).validateSignInFields(this)) {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            Map<String, String> users = getUsers();
            if (users != null && users.containsKey(username) && users.get(username).equals(password)) {
                Intent tmp = new Intent(this, GameCentreActivity.class);
                tmp.putExtra("user", username);
                startActivity(tmp);
            } else {
                Toast.makeText(this, R.string.sign_in_incorrect, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Obtains a Map of users from file
     *
     * @return a filled or empty Map
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> getUsers() {
        try {
            return (HashMap<String, String>) FileUtil.getInstance().readObjectFromFile(openFileInput("users.ser"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
