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
 * The activity for signing up.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * List of fields
     */
    private List<EditText> fields;

    /**
     * Handle to the file utility class
     */
    private FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.sign_up);
        fileUtil = FileUtil.getInstance();
        // initialize EditText fields
        fields = new ArrayList<>();
        fields.add((EditText) findViewById(R.id.sign_up_username));
        fields.add((EditText) findViewById(R.id.sign_up_password));
        fields.add((EditText) findViewById(R.id.sign_up_re_enter_password));

    }

    /**
     * OnClick method for sign up button.
     *
     * @param v View clicked
     */
    @SuppressWarnings("unchecked")
    public void signUpButtonActionOnClick(View v) {
        Map<String, String> users = getUsers();
        if (new UserFieldValidator(fields).validateSignUpFields(this, users.keySet())) {
            String username = fields.get(0).getText().toString();
            String password = fields.get(1).getText().toString();
            users.put(username, password);
            try {
                fileUtil.writeObjectToFile(openFileOutput("users.ser", MODE_PRIVATE), users);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, R.string.sign_up_successful, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignInActivity.class));
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
            return (HashMap<String, String>) fileUtil.readObjectFromFile(openFileInput("users.ser"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
